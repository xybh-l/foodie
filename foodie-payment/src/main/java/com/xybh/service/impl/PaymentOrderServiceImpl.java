package com.xybh.service.impl;

import cn.hutool.core.util.IdUtil;
import com.xybh.enums.PaymentStatus;
import com.xybh.enums.YesOrNo;
import com.xybh.mapper.OrdersMapper;
import com.xybh.pojo.Orders;
import com.xybh.pojo.bo.MerchantOrdersBO;
import com.xybh.service.PaymentOrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author: xybh
 * @Description:
 * @Date: Created in 18:01 2021/1/26
 * @Modified:
 */
@Service
public class PaymentOrderServiceImpl implements PaymentOrderService {

    @Resource
    private OrdersMapper ordersMapper;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean createPaymentOrder(MerchantOrdersBO merchantOrdersBO) {
        Orders order = new Orders();
        BeanUtils.copyProperties(merchantOrdersBO, order);
        order.setId(IdUtil.getSnowflake(0, 0).nextIdStr());
        order.setCreatedTime(new Date());
        order.setIsDelete(YesOrNo.NO.type);
        order.setPayStatus(PaymentStatus.WAIT_PAY.type);
        order.setComeFrom("吃货商城");

        int insert = ordersMapper.insert(order);
        return insert == 1;
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public Orders queryOrderByStatus(String merchantUserId, String merchantOrderId, Integer orderStatus) {
        Orders queryOrder = new Orders();
        queryOrder.setMerchantOrderId(merchantOrderId);
        queryOrder.setMerchantUserId(merchantUserId);
        queryOrder.setPayStatus(orderStatus);
        Orders waitPayOrder = ordersMapper.selectOne(queryOrder);

        return waitPayOrder;
    }

    @Transactional(propagation=Propagation.REQUIRED)
    @Override
    public String updateOrderPaid(String merchantOrderId, Integer paidAmount) {

        Example example = new Example(Orders.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("merchantOrderId", merchantOrderId);

        Orders paidOrder = new Orders();
        paidOrder.setPayStatus(PaymentStatus.PAID.type);
        paidOrder.setAmount(paidAmount);

        int result = ordersMapper.updateByExampleSelective(paidOrder, example);

        return queryMerchantReturnUrl(merchantOrderId);
    }

    @Override
    @Transactional(propagation=Propagation.SUPPORTS)
    public String queryMerchantReturnUrl(String merchantOrderId) {

        Orders queryOrder = new Orders();
        queryOrder.setMerchantOrderId(merchantOrderId);
        Orders order = ordersMapper.selectOne(queryOrder);

        return order.getReturnUrl();
    }

    @Override
    public Orders queryOrderInfo(String merchantUserId, String merchantOrderId) {

        Orders orderInfo = new Orders();
        orderInfo.setMerchantOrderId(merchantOrderId);
        orderInfo.setMerchantUserId(merchantUserId);
        return ordersMapper.selectOne(orderInfo);
    }
}
