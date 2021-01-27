package com.xybh.pojo.bo.center;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * @Author: xybh
 * @Description:
 * @Date: Created in 13:26 2021/1/21
 * @Modified:
 */

@ApiModel(value = "用户对象BO", description = "由客户端传入的数据封装在entity中")
public class CenterUserBO {

    @ApiModelProperty(value = "用户名", name = "username", example = "xybh", required = true)
    private String username;
    @ApiModelProperty(value = "密码", name = "password", example = "123456", required = true)
    private String password;

    @NotBlank(message = "用户昵称不能为空")
    @Length(max = 12, message = "用户昵称不能超过12位")
    @ApiModelProperty(value = "昵称", name = "nickname", example = "张三", required = true)
    private String nickname;

    @Length(max = 12, message = "用户真实信息不能超过12位")
    @ApiModelProperty(value = "真实姓名", name = "realname", example = "张三", required = true)
    private String realname;

    @Pattern(regexp = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$", message = "手机号格式不正确")
    @ApiModelProperty(value = "手机号", name = "mobile", example = "13125376119", required = true)
    private String mobile;

    @Pattern(regexp = "^(\\w-*\\.*)+@(\\w-?)+(\\.\\w{2,})+$", message = "邮箱地址格式不正确")
    @ApiModelProperty(value = "邮箱地址", name = "email", example = "1353433900@qq.com", required = true)
    private String email;

    @Min(value = 0, message = "性别选择不正确")
    @Max(value = 2, message = "性别选择不正确")
    @ApiModelProperty(value = "性别", name = "sex", example = "0:女 1:男 2:保密", required = true)
    private Integer sex;

    @ApiModelProperty(value = "生日", name = "birthday", example = "2021-1-27", required = true)
    private Date birthday;

    @Override
    public String toString() {
        return "CenterUserBO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", realname='" + realname + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", sex=" + sex +
                ", birthday=" + birthday +
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
