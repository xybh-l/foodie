package com.xybh.controller;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.xybh.enums.PayMethod;
import com.xybh.enums.PaymentStatus;
import com.xybh.pojo.Orders;
import com.xybh.pojo.bo.MerchantOrdersBO;
import com.xybh.resources.AlipayConfig;
import com.xybh.service.PaymentOrderService;
import com.xybh.utils.CurrencyUtils;
import com.xybh.utils.JSONResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * @Author: xybh
 * @Description:
 * @Date: Created in 16:26 2021/1/26
 * @Modified:
 */
@RestController
@RequestMapping("payment")
public class PaymentController {

    final static Logger log = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    private PaymentOrderService paymentOrderService;


    /**
     * 接受商户订单信息，保存到自己的数据库
     */
    @PostMapping("/createMerchantOrder")
    public JSONResult createMerchantOrder(@RequestBody MerchantOrdersBO merchantOrdersBO) {

        String merchantOrderId = merchantOrdersBO.getMerchantOrderId();
        String merchantUserId = merchantOrdersBO.getMerchantUserId();
        Integer amount = merchantOrdersBO.getAmount();
        Integer payMethod = merchantOrdersBO.getPayMethod();
        String returnUrl = merchantOrdersBO.getReturnUrl();

        if (StringUtils.isBlank(merchantOrderId)) {
            return JSONResult.errorMsg("参数[orderId]不能为空");
        }
        if (StringUtils.isBlank(merchantUserId)) {
            return JSONResult.errorMsg("参数[userId]不能为空");
        }
        if (amount == null || amount < 1) {
            return JSONResult.errorMsg("参数[realPayAmount]不能为空并且不能小于1");
        }
        if (payMethod == null) {
            return JSONResult.errorMsg("参数[payMethod]不能为空并且不能小于1");
        }
        if (!payMethod.equals(PayMethod.WEIXIN.type) && !payMethod.equals(PayMethod.ALIPAY.type)) {
            return JSONResult.errorMsg("参数[payMethod]目前只支持微信支付或支付宝支付");
        }
        if (StringUtils.isBlank(returnUrl)) {
            return JSONResult.errorMsg("参数[returnUrl]不能为空");
        }

        // 保存传来的商户订单信息
        boolean isSuccess = false;
        try {
            isSuccess = paymentOrderService.createPaymentOrder(merchantOrdersBO);
        } catch (Exception e) {
            e.printStackTrace();
            JSONResult.errorException(e.getMessage());
        }

        if (isSuccess) {
            return JSONResult.ok("商户订单创建成功！");
        } else {
            return JSONResult.errorMsg("商户订单创建失败，请重试...");
        }
    }

    /**
     * 提供给大家查询的方法，用于查询订单信息
     *
     * @param merchantOrderId
     * @param merchantUserId
     * @return
     */
    @PostMapping("getPaymentCenterOrderInfo")
    public JSONResult getPaymentCenterOrderInfo(@RequestParam String merchantOrderId,@RequestParam String merchantUserId) {

        if (StringUtils.isBlank(merchantOrderId) || StringUtils.isBlank(merchantUserId)) {
            return JSONResult.errorMsg("查询参数不能为空！");
        }

        Orders orderInfo = paymentOrderService.queryOrderInfo(merchantUserId, merchantOrderId);

        return JSONResult.ok(orderInfo);
    }

    /**
     *
     * @Description: 前往支付宝进行支付
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/goAlipay")
    public JSONResult goAlipay(@RequestParam String merchantOrderId,@RequestParam String merchantUserId) {

        // 查询订单详情
        Orders waitPayOrder = paymentOrderService.queryOrderByStatus(merchantUserId, merchantOrderId, PaymentStatus.WAIT_PAY.type);

        //获得初始化的AlipayClient
        AlipayClient client = AlipayConfig.getInstance();

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.RETURN_URL);
        alipayRequest.setNotifyUrl(AlipayConfig.NOTIFY_URL);
        // 商户订单号, 商户网站订单系统中唯一订单号, 必填
        String out_trade_no = merchantOrderId;
        // 付款金额, 必填 单位元
        String total_amount = CurrencyUtils.getFen2YuanWithPoint(waitPayOrder.getAmount());
//    	String total_amount = "0.01";	// 测试用 1分钱
        // 订单名称, 必填
        String subject = "天天吃货-付款用户[" + merchantUserId + "]";
        // 商品描述, 可空, 目前先用订单名称
        String body = subject;

        // 该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。
        String timeout_express = "1d";

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"time_express\":\""+ timeout_express +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        //请求
        String alipayForm = "";
        try {
            alipayForm = client.pageExecute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        log.info("支付宝支付 - 前往支付页面, alipayForm: \n{}", alipayForm);

        return JSONResult.ok(alipayForm);
    }

}
