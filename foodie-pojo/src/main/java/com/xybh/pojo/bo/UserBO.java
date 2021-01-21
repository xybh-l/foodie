package com.xybh.pojo.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: xybh
 * @Description:
 * @Date: Created in 13:26 2021/1/21
 * @Modified:
 */

@ApiModel(value = "用户对象BO", description = "由客户端传入的数据封装在entity中")
public class UserBO {

    @ApiModelProperty(value = "用户名", name = "username", example = "xybh", required = true)
    private String username;
    @ApiModelProperty(value = "密码", name = "password", example = "123456", required = true)
    private String password;
    @ApiModelProperty(value = "确认密码", name = "confirmPassword", example = "123456", required = false)
    private String confirmPassword;

    @Override
    public String toString() {
        return "UserBO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
