package com.xybh.config;

import com.xybh.service.OrderService;
import com.xybh.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Author: xybh
 * @Description:
 * @Date: Created in 22:16 2021/1/26
 * @Modified:
 */
@Component
public class OrderJob {

    @Autowired
    private OrderService orderService;

    /**
     * 使用定时任务关闭超时未支付订单,会存在弊端:
     * 1. 会有时间差，程序不严谨
     *      10:39下单, 11:00不足1小时, 12:00检查, 超过1小时多39分钟
     * 2. 不支持集群
     *      单机没问题,使用集群后会有多个定时任务
     *      解决方案: 只使用一台计算机节点,单独用来运行所有的定时任务
     * 3. 会对数据库全表搜索，及其影响数据库性能
     *      select * from order where orderStatus = 10;
     * 定时任务,仅仅适用于小型轻量级项目,传统项目
     *
     * 消息队列:MQ -> RabbitMQ, RocketMQ, Kafka, ZeroMQ...
     */
    @Scheduled(cron = "0 0 0/1 * * *")
    public void autoCloseOrder() {
        orderService.closeOrder();
        System.out.println("执行定时任务,当前时间为:"+ DateUtil.getCurrentDateString(DateUtil.DATETIME_PATTERN));
    }
}
