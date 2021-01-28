package com.xybh.service.impl.center;

import cn.hutool.db.sql.Order;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xybh.enums.OrderStatusEnum;
import com.xybh.enums.YesOrNo;
import com.xybh.mapper.OrderStatusMapper;
import com.xybh.mapper.OrdersMapper;
import com.xybh.mapper.UsersMapper;
import com.xybh.mapper.ext.OrdersExtMapper;
import com.xybh.pojo.OrderStatus;
import com.xybh.pojo.Orders;
import com.xybh.pojo.Users;
import com.xybh.pojo.bo.center.CenterUserBO;
import com.xybh.pojo.vo.MyOrdersVO;
import com.xybh.service.center.CenterUserService;
import com.xybh.service.center.MyOrdersService;
import com.xybh.utils.PagedGridResult;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: xybh
 * @Description:
 * @Date: Created in 17:25 2021/1/27
 * @Modified:
 */
@Service
public class MyOrdersServiceImpl implements MyOrdersService {

    @Resource
    private OrdersExtMapper ordersExtMapper;
    @Resource
    private OrdersMapper ordersMapper;
    @Resource
    private OrderStatusMapper orderStatusMapper;

    @Override
    public PagedGridResult queryMyOrders(String userId, Integer orderStatus, Integer page, Integer pageSize) {
        HashMap<String, Object> map = new HashMap<>(2);
        map.put("userId", userId);
        if (orderStatus != null) {
            map.put("orderStatus", orderStatus);
        }
        PageHelper.startPage(page, pageSize);
        List<MyOrdersVO> list = ordersExtMapper.queryMyOrders(map);
        return setterPagedGrid(list, page);
    }


    private PagedGridResult setterPagedGrid(List<?> list, Integer page) {
        PageInfo<?> pageList = new PageInfo<>(list);
        PagedGridResult grid = new PagedGridResult();
        grid.setPage(page);
        grid.setRows(list);
        grid.setTotal(pageList.getPages());
        grid.setRecords(pageList.getTotal());
        return grid;
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public Orders queryByOrder(String orderId, String userId) {
        Orders order = new Orders();
        order.setId(orderId);
        order.setUserId(userId);
        order.setIsDelete(YesOrNo.NO.type);

        return ordersMapper.selectOne(order);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void updateDeliverOrderStatus(String orderId) {
        OrderStatus updateOrder = new OrderStatus();
        updateOrder.setOrderStatus(OrderStatusEnum.WAIT_RECEIVE.type);
        updateOrder.setDeliverTime(new Date());

        Example example = new Example(OrderStatus.class);
        example.createCriteria()
                .andEqualTo("orderId", orderId)
                .andEqualTo("orderStatus", OrderStatusEnum.WAIT_DELIVER.type);

        orderStatusMapper.updateByExampleSelective(updateOrder, example);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean updateReceiveOrderStatus(String orderId) {
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderStatus(OrderStatusEnum.SUCCESS.type);
        orderStatus.setSuccessTime(new Date());

        Example example = new Example(OrderStatus.class);
        example.createCriteria()
                .andEqualTo("orderId", orderId)
                .andEqualTo("orderStatus", OrderStatusEnum.WAIT_RECEIVE.type);
        int result = orderStatusMapper.updateByExampleSelective(orderStatus, example);
        return result == 1;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean deleteOrder(String userId, String orderId) {
        Orders orders = new Orders();
        orders.setIsDelete(YesOrNo.YES.type);
        orders.setUpdatedTime(new Date());

        Example example = new Example(Orders.class);
        example.createCriteria()
                .andEqualTo("id", orderId)
                .andEqualTo("userId", userId);

        int result = ordersMapper.updateByExampleSelective(orders, example);
        return result == 1;
    }
}
