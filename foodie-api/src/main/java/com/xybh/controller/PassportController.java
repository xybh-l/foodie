package com.xybh.controller;

import com.alibaba.fastjson.JSON;
import com.xybh.pojo.Users;
import com.xybh.pojo.bo.ShopcartBO;
import com.xybh.pojo.bo.UserBO;
import com.xybh.pojo.vo.UsersVO;
import com.xybh.service.UserService;
import com.xybh.utils.CookieUtils;
import com.xybh.utils.JSONResult;
import com.xybh.utils.JsonUtils;
import com.xybh.utils.RedisOperator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Author: xybh
 * @Description:
 * @Date: Created in 12:19 2021/1/21
 * @Modified:
 */
@Api(value = "注册登录", tags = {"用于注册登录的相关接口"})
@RestController
@RequestMapping("passport")
public class PassportController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisOperator redisOperator;

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

        //setNullProperty(user);

        // 实现用户的Redis会话
        UsersVO usersVO = convertUsersVO(user);

        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(usersVO), true);
        // 同步购物车数据
        syncShopcartData(user.getId(), request, response);

        return JSONResult.ok(usersVO);
    }

    @ApiOperation(value = "退出登录", notes = "注销")
    @PostMapping("/logout")
    public JSONResult logout(@RequestParam String userId,
                             HttpServletRequest request,
                             HttpServletResponse response) {
        // 清除用户相关的cookie
        CookieUtils.deleteCookie(request, response, "user");

        // 用户退出登录,清空购物车
        CookieUtils.deleteCookie(request, response, FOODIE_SHOPCART);

        // 分布式会话中需要清除用户数据
        redisOperator.del(REDIS_USER_TOKEN + ":" + userId);
        return JSONResult.ok();
    }

    /**
     * 注册登录后,同步cookie和redis中的购物车数据
     */
    private void syncShopcartData(String userId,
                                  HttpServletRequest request,
                                  HttpServletResponse response) {
        // 1.redis中无数据, 如果cookie中的购物车也为空,则不操作
        // 2.redis中无数据, 如果cookie中的购物车不为空,这此时直接放入redis
        // 3.redis中有数据, 如果cookie中的购物车为空,则直接把redis中的购物车覆盖本地cookie
        // 4.redis中有数据, 如果cookie中的购物车不为空,则合并购物车(以cookie中的数据为主)
        // 5.同步到redis中后,覆盖本地cookie购物车的数据

        // 从redis中获取购物车
        String shopcartJsonRedis = redisOperator.get(FOODIE_SHOPCART + ":" + userId);
        // 从cookie中获取购物车
        String shopcartStrCookie = CookieUtils.getCookieValue(request, FOODIE_SHOPCART, true);

        if (StringUtils.isBlank(shopcartJsonRedis)) {
            if (StringUtils.isNotBlank(shopcartStrCookie)) {
                redisOperator.set(FOODIE_SHOPCART + ":" + userId, shopcartStrCookie);
            }
        } else {
            if (StringUtils.isBlank(shopcartStrCookie) || "[]".equals(shopcartStrCookie)) {
                CookieUtils.setCookie(request, response, FOODIE_SHOPCART, shopcartJsonRedis, true);
            } else {
                List<ShopcartBO> shopcartListRedis = JSON.parseArray(shopcartJsonRedis, ShopcartBO.class);
                List<ShopcartBO> shopcartListCookie = JSON.parseArray(shopcartStrCookie, ShopcartBO.class);

                // 定义一个待删除list
                List<ShopcartBO> pendingDeleteList = new ArrayList<>();
                for (ShopcartBO redisShopcart : shopcartListRedis) {
                    String redisSpecId = redisShopcart.getSpecId();
                    for (ShopcartBO cookieShopcart :
                            shopcartListCookie) {
                        String cookieSpecId = cookieShopcart.getSpecId();
                        if (redisSpecId.equals(cookieSpecId)) {
                            // 覆盖购买数量，不累加
                            redisShopcart.setBuyCounts(cookieShopcart.getBuyCounts());
                            // 把cookieShopcart放入待删除列表,用于最后的删除合并
                            pendingDeleteList.add(cookieShopcart);
                        }
                    }
                }
                // 从现有cookie中删除对应的覆盖过的数据
                shopcartListCookie.removeAll(pendingDeleteList);
                // 合并两个list
                shopcartListRedis.addAll(shopcartListCookie);
                // 更新到redis和cookie
                CookieUtils.setCookie(request, response, FOODIE_SHOPCART, JSON.toJSONString(shopcartListRedis), true);
                redisOperator.set(FOODIE_SHOPCART + ":" + userId, JSON.toJSONString(shopcartListRedis));
            }
        }
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

}
