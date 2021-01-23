package com.xybh.controller;

import com.alibaba.fastjson.JSON;
import com.xybh.pojo.Users;
import com.xybh.pojo.bo.UserBO;
import com.xybh.service.UserService;
import com.xybh.utils.CookieUtils;
import com.xybh.utils.JSONResult;
import com.xybh.utils.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @Author: xybh
 * @Description:
 * @Date: Created in 12:19 2021/1/21
 * @Modified:
 */
@Api(value = "注册登录", tags = {"用于注册登录的相关接口"})
@RestController
@RequestMapping("passport")
public class PassportController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户名是否存在", notes = "用户名是否存在")
    @GetMapping("/usernameIsExist")
    public JSONResult usernameIsExist(@RequestParam String username) {
        // 1.判断用户名不能为空
        if (StringUtils.isBlank(username)) {
            return JSONResult.errorMsg("用户名不能为空");
        }

        // 2.查找注册的用户名是否存在
        boolean isExist = userService.queryUsernameIsExist(username);
        if (isExist) {
            return JSONResult.errorMsg("用户名已存在");
        }
        // 3.请求成功,用户名没有重复
        return JSONResult.ok();
    }


    @ApiOperation(value = "用户注册", notes = "用户注册")
    @PostMapping("/regist")
    public JSONResult regist(@RequestBody UserBO userBO) {
        String username = userBO.getUsername();
        String password = userBO.getPassword();
        String confirmPassword = userBO.getConfirmPassword();
        // 0.判断账号密码不为空
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password) || StringUtils.isBlank(confirmPassword)) {
            return JSONResult.errorMsg("账号或密码不能为空");
        }
        // 1.判断用户名是否存在
        if (userService.queryUsernameIsExist(username)) {
            return JSONResult.errorMsg("用户名已存在");
        }
        // 2.判断密码位数
        if (password.length() < 6) {
            return JSONResult.errorMsg("密码长度不能小于6位");
        }
        // 3.判断两次输入的密码是否一致
        if (!password.equals(confirmPassword)) {
            return JSONResult.errorMsg("两次输入的密码不正确");
        }
        // 4.实现注册
        userService.createUser(userBO);
        return JSONResult.ok();
    }


    @ApiOperation(value = "用户登录", notes = "用户登录")
    @PostMapping("/login")
    public JSONResult login(@RequestBody UserBO userBO,
                            HttpServletRequest request,
                            HttpServletResponse response) {
        String username = userBO.getUsername();
        String password = userBO.getPassword();
        // 0.判断账号密码不为空
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return JSONResult.errorMsg("账号或密码不能为空");
        }
        // 1.实现登录
        Users user = userService.queryUserForLogin(username, password);
        if (user == null) {
            return JSONResult.errorMsg("账号或密码错误");
        }

        setNullProperty(user);
        CookieUtils. setCookie(request, response, "user", JsonUtils.objectToJson(user), true);

        // TODO 生成用户token, 存入redis会话
        // TODO 同步购物车数据

        return JSONResult.ok(user);
    }

    private Users setNullProperty(Users user) {
        user.setPassword(null);
        user.setMobile(null);
        user.setEmail(null);
        user.setCreatedTime(null);
        user.setUpdatedTime(null);
        user.setBirthday(null);

        return user;
    }

    @ApiOperation(value = "退出登录", notes = "注销")
    @PostMapping("/logout")
    public JSONResult logout(@RequestParam String userId,
                            HttpServletRequest request,
                            HttpServletResponse response) {
        // 清除用户相关的cookie
        CookieUtils.deleteCookie(request, response, "user");

        // TODO 用户退出登录,清空购物车
        // TODO 分布式会话中需要清楚用户数据
        return JSONResult.ok();
    }

}
