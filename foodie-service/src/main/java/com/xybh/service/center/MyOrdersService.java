package com.xybh.service.center;

import com.xybh.utils.PagedGridResult;

/**
 * @Author: xybh
 * @Description:
 * @Date: Created in 17:25 2021/1/27
 * @Modified:
 */
public interface MyOrdersService {

    /**
     * 查询我的订单列表
     *
     * @param userId      用户id
     * @param orderStatus 订单状态
     * @param page        页码
     * @param pageSize    每页订单数
     * @return
     */
    PagedGridResult queryMyOrders(String userId, Integer orderStatus, Integer page, Integer pageSize);

    /**
     * 更新订单发货状态
     * @param orderId   订单id
     */
    void updateDeliverOrderStatus(String orderId);
}
