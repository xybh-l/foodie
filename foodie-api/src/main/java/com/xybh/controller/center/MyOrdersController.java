package com.xybh.controller.center;

import cn.hutool.http.HttpStatus;
import com.xybh.controller.BaseController;
import com.xybh.enums.OrderStatusEnum;
import com.xybh.pojo.bo.center.OrderStatusCountBO;
import com.xybh.utils.JSONResult;
import com.xybh.utils.PagedGridResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: xybh
 * @Description:
 * @Date: Created in 13:57 2021/1/28
 * @Modified:
 */
@Api(value = "用户中心-我的订单", tags = {"用户中心-我的订单相关接口"})
@RestController
@RequestMapping("/myorders")
public class MyOrdersController extends BaseController {
    @ApiOperation(value = "查询我的订单", notes = "查询我的订单", httpMethod = "POST")
    @PostMapping("query")
    public JSONResult query(@RequestParam String userId,
                            @RequestParam(value = "orderStatus", required = false) Integer orderStatus,
                            @RequestParam Integer page,
                            @RequestParam Integer pageSize) {
        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("");
        }
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = COMMON_PAGE_SIZE;
        }

        PagedGridResult result = ordersService.queryMyOrders(userId, orderStatus, page, pageSize);
        return JSONResult.ok(result);
    }

    @ApiOperation(value = "商家发货", notes = "模拟商家发货操作", httpMethod = "GET")
    @GetMapping("/deliver")
    public JSONResult deliver(
            @RequestParam String orderId) {
        if (StringUtils.isBlank(orderId)) {
            return JSONResult.errorMsg("订单号不能为空");
        }
        ordersService.updateDeliverOrderStatus(orderId);
        return JSONResult.ok();
    }

    @ApiOperation(value = "用户确认收货", notes = "用户确认收货", httpMethod = "POST")
    @PostMapping("/confirmReceive")
    public JSONResult confirmReceive(
            @RequestParam String orderId,
            @RequestParam String userId) {
        if (StringUtils.isBlank(orderId) || StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("订单号或用户id不能为空");
        }
        JSONResult checkResult = checkUserOrder(userId, orderId);
        if (checkResult.getStatus() != HttpStatus.HTTP_OK) {
            return checkResult;
        }
        boolean result = ordersService.updateReceiveOrderStatus(orderId);
        if (!result) {
            return JSONResult.errorMsg("订单确认收货失败");
        }
        return JSONResult.ok();
    }

    @ApiOperation(value = "用户删除订单", notes = "用户删除订单", httpMethod = "POST")
    @PostMapping("/delete")
    public JSONResult delete(
            @RequestParam String orderId,
            @RequestParam String userId) {
        if (StringUtils.isBlank(orderId) || StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("订单号或用户id不能为空");
        }
        boolean result = ordersService.deleteOrder(userId, orderId);
        if (!result) {
            return JSONResult.errorMsg("订单删除失败");
        }
        return JSONResult.ok();
    }

    @ApiOperation(value = "查询订单状态数量", notes = "查询订单状态数量", httpMethod = "POST")
    @PostMapping("statusCounts")
    public JSONResult statusCounts(@RequestParam String userId) {
        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("用户id不能为空");
        }
        Integer waitPayCounts = ordersService.queryStatusCounts(userId, OrderStatusEnum.WAIT_PAY.type);
        Integer waitDeliverCounts = ordersService.queryStatusCounts(userId, OrderStatusEnum.WAIT_DELIVER.type);
        Integer waitReceiveCounts = ordersService.queryStatusCounts(userId, OrderStatusEnum.WAIT_RECEIVE.type);
        Integer waitCommentCounts = ordersService.queryStatusCounts(userId, OrderStatusEnum.SUCCESS.type);
        int totalCounts = waitPayCounts + waitDeliverCounts + waitReceiveCounts + waitCommentCounts;

        OrderStatusCountBO orderStatusCountBO = new OrderStatusCountBO();
        orderStatusCountBO.setWaitPayCounts(waitPayCounts);
        orderStatusCountBO.setWaitDeliverCounts(waitDeliverCounts);
        orderStatusCountBO.setWaitReceiveCounts(waitReceiveCounts);
        orderStatusCountBO.setWaitCommentCounts(waitCommentCounts);
        orderStatusCountBO.setTotalCounts(totalCounts);

        return JSONResult.ok(orderStatusCountBO);
    }

    @ApiOperation(value = "查询订单动态", notes = "查询订单动态", httpMethod = "POST")
    @PostMapping("trend")
    public JSONResult query(@RequestParam String userId,
                            @RequestParam Integer page,
                            @RequestParam Integer pageSize) {
        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("");
        }
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = COMMON_PAGE_SIZE;
        }

        PagedGridResult result = ordersService.getOrdersTrend(userId, page, pageSize);
        return JSONResult.ok(result);
    }
}
