package com.xybh.pojo.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: xybh
 * @Description:
 * @Date: Created in 19:45 2021/1/23
 * @Modified:
 */
@ApiModel(value = "新增或修改地址的BO")
public class AddressBo {
    @ApiModelProperty(name = "userId", value = "用户id")
    private String userId;
    @ApiModelProperty(name = "receiver", value = "收货人")
    private String receiver;
    private String mobile;
    private String province;
    private String city;
    private String district;
    private String detail;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
