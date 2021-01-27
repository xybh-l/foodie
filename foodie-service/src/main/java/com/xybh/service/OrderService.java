package com.xybh.service;

import cn.hutool.db.sql.Order;
import com.xybh.pojo.OrderStatus;
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

    /**
     * 根据订单号查询用户id
     * @param orderId
     * @return
     */
    String queryUserIdByOrderId(String orderId);

    /**
     * 查询订单状态
     * @param orderId 订单id
     * @return
     */
    OrderStatus queryOrderStatusInfo(String orderId);

    /**
     * 关闭订单
     */
    void closeOrder();
}
