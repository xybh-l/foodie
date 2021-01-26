package com.xybh.service;

import com.xybh.pojo.Orders;
import com.xybh.pojo.bo.MerchantOrdersBO;

/**
 * @Author: xybh
 * @Description:
 * @Date: Created in 18:02 2021/1/26
 * @Modified:
 */
public interface PaymentOrderService {

    /**
     * 创建支付中心的订单
     * @param merchantOrdersBO 订单BO
     * @return
     */
    boolean createPaymentOrder(MerchantOrdersBO merchantOrdersBO);

    /**
     * 查询未支付订单
     * @param merchantUserId    订单用户id
     * @param merchantOrderId   订单id
     * @param orderStatus       订单状态
     * @return
     */
    Orders queryOrderByStatus(String merchantUserId, String merchantOrderId, Integer orderStatus);

    /**
     * 修改订单状态为已支付
     * @param merchantOrderId
     * @param paidAmount
     * @return
     */
    String updateOrderPaid(String merchantOrderId, Integer paidAmount);

    /**
     * 查询订单回调url
     * @param merchantOrderId
     * @return
     */
    String queryMerchantReturnUrl(String merchantOrderId);
    /**
     * 查询订单信息
     * @param merchantUserId
     * @param merchantOrderId
     * @return
     */
    Orders queryOrderInfo(String merchantUserId, String merchantOrderId);

}
