package com.xybh.controller;

import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: xybh
 * @Description:
 * @Date: Created in 14:26 2021/1/22
 * @Modified:
 */
@RestController
public class BaseController {

    public static final String FOODIE_SHOPCART = "shopcart";
    public static final Integer COMMENT_PAGE_SIZE = 10;
    public static final Integer PAGE_SIZE = 20;

    // 微信支付成功 -> 支付中心 -> 吃货平台
    public static final String PAY_RETURN_URL = "http://localhost:8088/orders/notifyMerchantOrderPaid";

    // 支付中心
    public static final String PAYMENT_URL = "https://localhost:8089/foodie-payment/payment/createMerchantOrder";
}
