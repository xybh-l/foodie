package com.xybh.service;

import com.xybh.pojo.UserAddress;
import com.xybh.pojo.bo.AddressBo;
import com.xybh.pojo.bo.SubmitOrderBO;

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
    void createOrder(SubmitOrderBO submitOrderBO);

}
