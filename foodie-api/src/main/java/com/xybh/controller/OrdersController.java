package com.xybh.controller;

import com.alibaba.fastjson.JSON;
import com.xybh.enums.OrderStatusEnum;
import com.xybh.enums.PayMethod;
import com.xybh.pojo.OrderStatus;
import com.xybh.pojo.bo.ShopcartBO;
import com.xybh.pojo.bo.SubmitOrderBO;
import com.xybh.pojo.vo.MerchantOrdersVO;
import com.xybh.pojo.vo.OrderVO;
import com.xybh.service.OrderService;
import com.xybh.utils.JSONResult;
import com.xybh.utils.RedisOperator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
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
public class OrdersController extends BaseController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RedisOperator redisOperator;

    @ApiOperation(value = "用户下单", notes = "用户下单", httpMethod = "POST")
    @PostMapping("/create")
    public JSONResult create(@RequestBody SubmitOrderBO submitOrderBO,
                             HttpServletRequest request,
                             HttpServletResponse response) {

        if (!submitOrderBO.getPayMethod().equals(PayMethod.ALIPAY.type) &&
                !submitOrderBO.getPayMethod().equals(PayMethod.WEIXIN.type)) {
            return JSONResult.errorMsg("支付方式不支持");
        }

        String shopcartJson = redisOperator.get(FOODIE_SHOPCART + ":" + submitOrderBO.getUserId());
        if(StringUtils.isBlank(shopcartJson)){
            return JSONResult.errorMsg("购物车数据不正确");
        }

        List<ShopcartBO> shopcartList = JSON.parseArray(shopcartJson, ShopcartBO.class);

        // 1.创建订单
        OrderVO orderVO = orderService.createOrder(submitOrderBO, shopcartList);
        String orderId = orderVO.getOrderId();
        MerchantOrdersVO merchantOrdersVO = orderVO.getMerchantOrdersVO();
        merchantOrdersVO.setReturnUrl(PAY_RETURN_URL);

        // 为了方便测试购买,所有的支付金额都统一为一分钱
        merchantOrdersVO.setAmount(1);

        // 2.创建订单以后,移除购物车中已结算(已提交)的商品
        // TODO 整合Redis后,完善购物车中的已结算商品清除,并且同步到前端的cookie中
//        CookieUtils.setCookie(request, response, FOODIE_SHOPCART, "", true);

        // 3.先支付中心发送当前订单,用于保存支付中心的订单数据
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        header.add("userId", "imooc");
        header.add("password", "imooc");

        HttpEntity<MerchantOrdersVO> entity = new HttpEntity<>(merchantOrdersVO, header);

        ResponseEntity<JSONResult> responseEntity =
                restTemplate.postForEntity(PAYMENT_URL,
                        entity,
                        JSONResult.class);
        JSONResult paymentResult = responseEntity.getBody();
        assert paymentResult != null;
        if (paymentResult.getStatus() != HttpStatus.OK.value()) {
            return JSONResult.errorMsg("支付中心订单创建失败,请联系管理员");
        }
        return JSONResult.ok(orderId);
    }

    @PostMapping("/getPaidOrderInfo")
    public JSONResult getPaidOrderInfo(String orderId) {

        OrderStatus orderStatus = orderService.queryOrderStatusInfo(orderId);
        return JSONResult.ok(orderStatus);
    }

    @PostMapping("/notifyMerchantOrderPaid")
    public Integer notifyMerchantOrderPaid(String merchantOrderId) {
        orderService.updateOrderStatus(merchantOrderId, OrderStatusEnum.WAIT_DELIVER.type);
        return HttpStatus.OK.value();
    }
}
