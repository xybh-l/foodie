package com.xybh.service.impl.center;

import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xybh.enums.OrderStatusEnum;
import com.xybh.enums.YesOrNo;
import com.xybh.mapper.ItemsCommentsMapper;
import com.xybh.mapper.OrderItemsMapper;
import com.xybh.mapper.OrderStatusMapper;
import com.xybh.mapper.OrdersMapper;
import com.xybh.mapper.ext.ItemsCommentsExtMapper;
import com.xybh.mapper.ext.OrdersExtMapper;
import com.xybh.pojo.OrderItems;
import com.xybh.pojo.OrderStatus;
import com.xybh.pojo.Orders;
import com.xybh.pojo.bo.center.OrderItemsCommentBO;
import com.xybh.pojo.vo.MyCommentVO;
import com.xybh.pojo.vo.MyOrdersVO;
import com.xybh.service.center.MyCommentService;
import com.xybh.service.center.MyOrdersService;
import com.xybh.utils.PagedGridResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: xybh
 * @Description:
 * @Date: Created in 17:25 2021/1/27
 * @Modified:
 */
@Service
public class MyCommentServiceImpl extends BaseService implements MyCommentService{

    @Resource
    private OrderItemsMapper orderItemsMapper;
    @Resource
    private ItemsCommentsExtMapper itemsCommentsExtMapper;
    @Resource
    private OrdersMapper ordersMapper;
    @Resource
    private OrderStatusMapper orderStatusMapper;


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void saveComments(String orderId, String userId, List<OrderItemsCommentBO> commentList) {
        // 1.保存评价 items_comments
        for (OrderItemsCommentBO comment :commentList) {
            comment.setCommentId(IdUtil.getSnowflake(0,0).nextIdStr());
        }
        HashMap<String, Object> map = new HashMap<>(2);
        map.put("userId", userId);
        map.put("commentList", commentList);
        itemsCommentsExtMapper.saveComments(map);
        // 2.修改订单表 改为已评价 orders
        Orders orders = new Orders();
        orders.setId(orderId);
        orders.setIsComment(YesOrNo.YES.type);
        ordersMapper.updateByPrimaryKeySelective(orders);

        // 3.修改订单状态表的留言时间 order_status
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderId);
        orderStatus.setCommentTime(new Date());
        orderStatusMapper.updateByPrimaryKeySelective(orderStatus);
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public List<OrderItems> queryPendingComment(String orderId) {
        OrderItems orderItems = new OrderItems();
        orderItems.setOrderId(orderId);
        return orderItemsMapper.select(orderItems);
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public PagedGridResult queryMyComments(String userId, Integer page, Integer pageSize) {
        HashMap<String, Object> map = new HashMap<>(2);
        map.put("userId", userId);
        PageHelper.startPage(page, pageSize);
        List<MyCommentVO> list = itemsCommentsExtMapper.queryMyComments(map);
        return setterPagedGrid(list, page);
    }
}
