package com.xybh.service.center;

import com.xybh.pojo.OrderItems;
import com.xybh.pojo.Orders;
import com.xybh.pojo.Users;
import com.xybh.pojo.bo.center.OrderItemsCommentBO;
import com.xybh.utils.PagedGridResult;

import java.util.List;

/**
 * @Author: xybh
 * @Description:
 * @Date: Created in 17:25 2021/1/27
 * @Modified:
 */
public interface MyCommentService {

    /**
     * 根据订单id查询关联的商品
     * @param orderId   订单id
     * @return
     */
    List<OrderItems> queryPendingComment(String orderId);

    /**
     * 保存评论
     * @param orderId       订单id
     * @param userId        用户id
     * @param commentList   评论
     */
    void saveComments(String orderId, String userId, List<OrderItemsCommentBO> commentList);

    /**
     * 查询我的评价
     * @param userId    用户id
     * @param page      页码
     * @param pageSize  每页展示数量
     * @return
     */
    PagedGridResult queryMyComments(String userId, Integer page, Integer pageSize);
}
