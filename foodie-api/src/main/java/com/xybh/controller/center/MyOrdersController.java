package com.xybh.controller.center;

import com.xybh.controller.BaseController;
import com.xybh.service.center.MyOrdersService;
import com.xybh.utils.JSONResult;
import com.xybh.utils.PagedGridResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private MyOrdersService ordersService;

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
            @RequestParam String orderId){
        if (StringUtils.isBlank(orderId)){
            return JSONResult.errorMsg("订单号不能为空");
        }
        ordersService.updateDeliverOrderStatus(orderId);
        return JSONResult.ok();
    }
}
