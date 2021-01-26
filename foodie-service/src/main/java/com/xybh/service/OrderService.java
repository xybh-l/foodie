package com.xybh.service;

import com.xybh.pojo.UserAddress;
import com.xybh.pojo.bo.AddressBo;
import com.xybh.pojo.bo.SubmitOrderBO;
import com.xybh.pojo.vo.OrderVO;

import java.util.List;

/**
 * @Author: xybh
 * @Description:
 * @Date: Created in 19:26 2021/1/23
 * @Modified:
 */
public interface OrderService {

    /**
     * 创建订单
     * @param submitOrderBO 订单BO
     */
    OrderVO createOrder(SubmitOrderBO submitOrderBO);

    /**
     * 更新订单状态
     * @param orderId       订单id
     * @param orderStatus   订单状态
     */
    void updateOrderStatus(String orderId, Integer orderStatus);
}
