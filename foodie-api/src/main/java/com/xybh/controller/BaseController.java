package com.xybh.controller;

import org.springframework.web.bind.annotation.RestController;

import java.io.File;

/**
 * @Author: xybh
 * @Description:
 * @Date: Created in 14:26 2021/1/22
 * @Modified:
 */
@RestController
public class BaseController {

    public static final String FOODIE_SHOPCART = "shopcart";
    public static final Integer COMMON_PAGE_SIZE = 10;
    public static final Integer PAGE_SIZE = 20;

    /**
     * 微信支付成功 -> 支付中心 -> 吃货平台
     */
    public static final String PAY_RETURN_URL = "http://localhost:8088/orders/notifyMerchantOrderPaid";

    /**
     * 支付中心
     */
    public static final String PAYMENT_URL = "http://localhost:8089/payment/createMerchantOrder";
//    public static final String PAYMENT_URL = "https://payment.t.mukewang.com/foodie-payment/payment/createMerchantOrder";

    /**
     * 查询订单详情
     */
    public static final String QUERY_PAY_INFO_URL = "http://localhost:8089/payment/getPaymentCenterOrderInfo";

    /**
     * 用户上传头像的位置
     */
    public static final String IMAGE_USER_FACE_URL =  "E:"+File.separator+"Tomcat"+File.separator+"apache-tomcat-9.0.37"+File.separator+"webapps"+File.separator+"foodie-center"+File.separator+"face";
}
