package com.xybh.service.impl;

import cn.hutool.core.util.IdUtil;
import com.xybh.enums.OrderStatusEnum;
import com.xybh.enums.YesOrNo;
import com.xybh.mapper.OrderItemsMapper;
import com.xybh.mapper.OrderStatusMapper;
import com.xybh.mapper.OrdersMapper;
import com.xybh.pojo.*;
import com.xybh.pojo.bo.SubmitOrderBO;
import com.xybh.pojo.vo.MerchantOrdersVO;
import com.xybh.pojo.vo.OrderVO;
import com.xybh.service.AddressService;
import com.xybh.service.ItemService;
import com.xybh.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;


/**
 * @author a1353
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrdersMapper ordersMapper;
    @Resource
    private OrderItemsMapper orderItemsMapper;
    @Resource
    private OrderStatusMapper orderStatusMapper;

    @Autowired
    private AddressService addressService;
    @Autowired
    private ItemService itemService;

    @Override
    public String queryUserIdByOrderId(String orderId) {
        Orders orders = ordersMapper.selectByPrimaryKey(orderId);
        if(orders != null){
            return orders.getUserId();
        }
        return null;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void updateOrderStatus(String orderId, Integer orderStatus) {
        OrderStatus paidStatus = new OrderStatus();
        paidStatus.setOrderId(orderId);
        paidStatus.setOrderStatus(orderStatus);
        paidStatus.setPayTime(new Date());

        orderStatusMapper.updateByPrimaryKeySelective(paidStatus);
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public OrderStatus queryOrderStatusInfo(String orderId) {
        return orderStatusMapper.selectByPrimaryKey(orderId);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public OrderVO createOrder(SubmitOrderBO submitOrderBO) {
        String userId = submitOrderBO.getUserId();
        Integer payMethod = submitOrderBO.getPayMethod();
        String addressId = submitOrderBO.getAddressId();
        String itemSpecIds = submitOrderBO.getItemSpecIds();
        String leftMsg = submitOrderBO.getLeftMsg();
        // 包邮费用设置为0
        Integer postAmount = 0;

        // 1.新订单数据保存
        String orderId = IdUtil.getSnowflake(0, 0).nextIdStr();
        Orders order = new Orders();
        order.setUserId(userId);
        order.setId(orderId);
        UserAddress address = addressService.queryUserAddress(userId, addressId);
        order.setReceiverName(address.getReceiver());
        order.setReceiverMobile(address.getMobile());
        order.setReceiverAddress(address.getProvince() + " " + address.getCity() + " "
                + address.getDistrict() + " " + address.getDetail());

        order.setPostAmount(postAmount);
        order.setPayMethod(payMethod);
        order.setLeftMsg(leftMsg);

        order.setIsComment(YesOrNo.NO.type);
        order.setIsDelete(YesOrNo.NO.type);
        order.setCreatedTime(new Date());
        order.setUpdatedTime(order.getCreatedTime());
        // 2.循环根据itemSpecIds保存订单商品信息表
        String[] itemSpec = itemSpecIds.split(",");
        int totalAmount = 0;
        int realPayAmount = 0;
        for (String itemSpecId : itemSpec) {

            // TODO 整合redis后,商品购买的数量重新从redis的购物车中获取
            int buyCounts = 1;
            // 2.1  根据规格id,查询规格的具体信息,主要获取价格
            ItemsSpec itemsSpec = itemService.queryItemSpecById(itemSpecId);
            totalAmount += itemsSpec.getPriceNormal() * buyCounts;
            realPayAmount += itemsSpec.getPriceDiscount() * buyCounts;

            // 2.2 根据规格id, 获取商品信息以及商品图片
            String itemId = itemsSpec.getItemId();
            Items item = itemService.queryItemById(itemId);
            String imgUrl = itemService.queryItemMainImgById(itemId);

            // 2.3 循环保存子订单数据到数据库
            OrderItems subOrderItem = new OrderItems();
            String subOrderId = IdUtil.getSnowflake(0, 0).nextIdStr();
            subOrderItem.setId(subOrderId);
            subOrderItem.setOrderId(orderId);
            subOrderItem.setItemId(itemId);
            subOrderItem.setItemName(item.getItemName());
            subOrderItem.setItemImg(imgUrl);
            subOrderItem.setBuyCounts(buyCounts);
            subOrderItem.setItemSpecId(itemSpecId);
            subOrderItem.setItemSpecName(itemsSpec.getName());
            subOrderItem.setPrice(itemsSpec.getPriceDiscount());
            orderItemsMapper.insert(subOrderItem);

            // 2.4 在用户提交订单以后, 规格表需要扣除库存
            itemService.decreaseItemSpecStock(itemSpecId, buyCounts);
        }
        order.setTotalAmount(totalAmount);
        order.setRealPayAmount(realPayAmount);
        ordersMapper.insert(order);
        // 3.保存订单状态表
        OrderStatus waitPayOrderStatus = new OrderStatus();
        waitPayOrderStatus.setOrderId(orderId);
        waitPayOrderStatus.setOrderStatus(OrderStatusEnum.WAIT_PAY.type);
        waitPayOrderStatus.setCreatedTime(new Date());
        orderStatusMapper.insert(waitPayOrderStatus);

        // 4.构建商户订单,用于传给支付中心
        MerchantOrdersVO merchantOrdersVO = new MerchantOrdersVO();
        merchantOrdersVO.setMerchantOrderId(orderId);
        merchantOrdersVO.setMerchantUserId(userId);
        merchantOrdersVO.setAmount(realPayAmount + postAmount);
        merchantOrdersVO.setPayMethod(payMethod);

        OrderVO orderVO = new OrderVO();
        orderVO.setOrderId(orderId);
        orderVO.setMerchantOrdersVO(merchantOrdersVO);
        return orderVO;
    }
}
