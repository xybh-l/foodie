package com.xybh.controller;

import com.xybh.enums.PayMethod;
import com.xybh.pojo.UserAddress;
import com.xybh.pojo.bo.AddressBo;
import com.xybh.pojo.bo.SubmitOrderBO;
import com.xybh.service.AddressService;
import com.xybh.utils.JSONResult;
import com.xybh.utils.MobileEmailUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: xybh
 * @Description:
 * @Date: Created in 19:25 2021/1/23
 * @Modified:
 */
@Api(value = "订单相关接口", tags = {"订单相关相关的api接口"})
@RestController
@RequestMapping("orders")
public class OrdersController {

    @ApiOperation(value = "用户下单", notes = "用户下单", httpMethod = "POST")
    @PostMapping("/create")
    public JSONResult create(SubmitOrderBO submitOrderBO) {

        if (!submitOrderBO.getPayMethod().equals(PayMethod.ALIPAY.type) &&
                !submitOrderBO.getPayMethod().equals(PayMethod.WEIXIN.type)) {
            return JSONResult.errorMsg("支付方式不支持");
        }
        // 1.创建订单
        // 2.创建订单以后,移除购物车中已结算(已提交)的商品
        // 3.先支付中心发送当前订单,用于保存支付中心的订单数据

        return JSONResult.ok();
    }
}
