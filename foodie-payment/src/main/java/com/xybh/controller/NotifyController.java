package com.xybh.controller;

import com.alipay.api.AlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.xybh.resources.AlipayConfig;
import com.xybh.service.PaymentOrderService;
import com.xybh.utils.CurrencyUtils;
import com.xybh.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @Author: xybh
 * @Description:
 * @Date: Created in 21:09 2021/1/26
 * @Modified:
 */
@RestController
@RequestMapping("/payment/notice")
public class NotifyController {
    final static Logger log = LoggerFactory.getLogger(NotifyController.class);

    @Autowired
    private PaymentOrderService paymentOrderService;
    @Autowired
    private RestTemplate restTemplate;
    /**
     * @Description: 支付成功后的支付宝异步通知
     */
    @RequestMapping(value="/alipay")
    public String alipay(HttpServletRequest request, HttpServletResponse response) throws Exception {

        log.info("支付成功后的支付宝异步通知");

        //获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = iter.next();
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
//			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(params,
                AlipayConfig.ALIPAY_PUBLIC_KEY,
                AlipayConfig.CHARSET,
                AlipayConfig.SIGN_TYPE); //调用SDK验证签名

        if(signVerified) {//验证成功
            // 商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
            // 支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
            // 交易状态
            String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
            // 付款金额
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");

            if (trade_status.equals("TRADE_SUCCESS")){
                String merchantReturnUrl = paymentOrderService.updateOrderPaid(out_trade_no, CurrencyUtils.getYuan2Fen(total_amount));
                notifyFoodieShop(out_trade_no, merchantReturnUrl);
            }

            log.info("************* 支付成功(支付宝异步通知) - 时间: {} *************", DateUtil.getCurrentDateString(DateUtil.DATETIME_PATTERN));
            log.info("* 订单号: {}", out_trade_no);
            log.info("* 支付宝交易号: {}", trade_no);
            log.info("* 实付金额: {}", total_amount);
            log.info("* 交易状态: {}", trade_status);
            log.info("*****************************************************************************");

            return "success";
        }else {
            //验证失败
            log.info("验签失败, 时间: {}", DateUtil.getCurrentDateString(DateUtil.DATETIME_PATTERN));
            return "fail";
        }
    }

    /**
     * 通知天天吃货商户平台
     */
    private void notifyFoodieShop(String merchantOrderId, String merchantReturnUrl) {
        MultiValueMap<String, String> requestEntity = new LinkedMultiValueMap<>();
        requestEntity.add("merchantOrderId", merchantOrderId);
        String httpStatus = restTemplate.postForObject(merchantReturnUrl, requestEntity, String.class);
        System.out.println("*** 通知天天吃货后返回的状态码 httpStatus: " + httpStatus + " ***");
        log.info("*** 通知天天吃货后返回的状态码 httpStatus: {} ***", httpStatus);
    }

}
