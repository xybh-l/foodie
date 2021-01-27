package com.xybh.controller.center;

import com.alibaba.fastjson.JSON;
import com.xybh.pojo.Users;
import com.xybh.pojo.bo.center.CenterUserBO;
import com.xybh.service.center.CenterUserService;
import com.xybh.utils.CookieUtils;
import com.xybh.utils.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: xybh
 * @Description:
 * @Date: Created in 17:40 2021/1/27
 * @Modified:
 */
@Api(value = "用户信息接口", tags = {"用户信息相关接口"})
@RestController
@RequestMapping("userInfo")
public class CenterUserController {

    @Autowired
    private CenterUserService centerUserService;

    @ApiOperation(value = "修改用户信息", notes = "修改用户信息",httpMethod = "PUT")
    @PutMapping("update")
    public JSONResult update(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId,
            @RequestBody @Valid CenterUserBO userBo,
            BindingResult result,
            HttpServletRequest request, HttpServletResponse response) {
        if(result.hasErrors()){
            Map<String, String> errorMap = getErrors(result);
            return JSONResult.errorMap(errorMap);
        }
        if(StringUtils.isBlank(userId)){
            return JSONResult.errorMsg("");
        }
        Users userResult = centerUserService.updateUserInfo(userId, userBo);
        setNullProperty(userResult);
        CookieUtils.setCookie(request, response, "user", JSON.toJSONString(userResult), true);

        // TODO 后续要改,增加令牌token,整合进redis,分布式会话
        return JSONResult.ok();
    }

    private Map<String, String> getErrors(BindingResult result){
        Map<String, String> map = new HashMap<>(8);
        List<FieldError> errors = result.getFieldErrors();
        for (FieldError error:
             errors) {
            // 发生验证错误所对应的某个属性
            String errorField = error.getField();
            // 发生验证错误的信息
            String message = error.getDefaultMessage();

            map.put(errorField, message);
        }
        return map;
    }
    private void setNullProperty(Users user) {
        user.setPassword(null);
        user.setMobile(null);
        user.setEmail(null);
        user.setCreatedTime(null);
        user.setUpdatedTime(null);
        user.setBirthday(null);
    }
}
