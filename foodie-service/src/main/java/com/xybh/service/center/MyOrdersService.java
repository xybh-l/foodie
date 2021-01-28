package com.xybh.service.center;

import com.xybh.pojo.Orders;
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

    /**
     * 查询我的订单
     * @param orderId   订单号
     * @param userId    用户id
     * @return
     */
    Orders queryByOrder(String orderId, String userId);

    /**
     * 更新订单状态为确认收货
     * @param orderId   订单号
     * @return
     */
    boolean updateReceiveOrderStatus(String orderId);

    /**
     * 删除订单(逻辑删除)
     * @param userId    用户id
     * @param orderId   订单状态
     * @return
     */
    boolean deleteOrder(String userId, String orderId);

    /**
     * 查询订单状态数量
     * @param userId    用户id
     * @param status    订单状态
     * @return
     */
    Integer queryStatusCounts(String userId, Integer status);
}
