package com.xybh.enums;

/**
 * @Author: xybh
 * @Description: 支付方式 枚举
 * @Date: Created in 11:11 2021/1/26
 * @Modified:
 */

public enum PayMethod {
    /**
     * 微信支付
     */
    WEIXIN(1,"微信"),
    /**
     * 支付宝支付
     */
    ALIPAY(2,"支付宝");

    public final Integer type;
    public final String value;

    PayMethod(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
