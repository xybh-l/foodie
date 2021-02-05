package com.xybh.controller;

import com.xybh.pojo.Orders;
import com.xybh.pojo.Users;
import com.xybh.pojo.vo.UsersVO;
import com.xybh.service.center.MyOrdersService;
import com.xybh.utils.JSONResult;
import com.xybh.utils.RedisOperator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.UUID;

/**
 * @Author: xybh
 * @Description:
 * @Date: Created in 14:26 2021/1/22
 * @Modified:
 */
@RestController
public class BaseController {

    @Autowired
    public MyOrdersService ordersService;
    @Autowired
    public RedisOperator redisOperator;

    public static final String FOODIE_SHOPCART = "shopcart";
    public static final Integer COMMON_PAGE_SIZE = 10;
    public static final Integer PAGE_SIZE = 20;

    public static final String REDIS_USER_TOKEN = "redis_user_token";

    /**
     * 微信支付成功 -> 支付中心 -> 吃货平台 -> 回调通知的url
     */
    public static final String PAY_RETURN_URL = "http://localhost:8088/orders/notifyMerchantOrderPaid";
//    public static final String PAY_RETURN_URL = "http://api.z.xybh.online:8088/foodie-api/orders/notifyMerchantOrderPaid";

    /**
     * 支付中心
     */
    public static final String PAYMENT_URL = "http://localhost:8089/payment/createMerchantOrder";
//    public static final String PAYMENT_URL = "http://payment.z.xybh.online:8089/foodie-payment/payment/createMerchantOrder";

    /**
     * 查询订单详情
     */
    public static final String QUERY_PAY_INFO_URL = "http://localhost:8089/payment/getPaymentCenterOrderInfo";

    /**
     * 用户上传头像的位置
     */
    public static final String IMAGE_USER_FACE_URL =  "E:"+File.separator+"Tomcat"+File.separator+"apache-tomcat-9.0.37"+File.separator+"webapps"+File.separator+"foodie-center"+File.separator+"face";

    /**
     * 用于验证订单与用户是否有关联
     *
     * @param userId    用户id
     * @param orderId   订单号
     * @return
     */
    public JSONResult checkUserOrder(String userId, String orderId) {
        Orders order = ordersService.queryByOrder(orderId, userId);
        if (order == null) {
            return JSONResult.errorMsg("订单不存在");
        }
        return JSONResult.ok(order);
    }

    /**
     * 生成token
     * @param user
     * @return
     */
    public UsersVO convertUsersVO(Users user) {
        // 生成用户token, 存入redis会话
        String uniqueToken = UUID.randomUUID().toString().trim();
        redisOperator.set(REDIS_USER_TOKEN + ":" + user.getId(), uniqueToken);
        UsersVO usersVO = new UsersVO();
        BeanUtils.copyProperties(user, usersVO);
        usersVO.setUniqueToken(uniqueToken);
        return usersVO;
    }
}
