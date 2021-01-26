package com.xybh.pojo.vo;

import java.util.List;

/**
 * @Author: xybh
 * @Description:
 * @Date: Created in 9:41 2021/1/22
 * @Modified:
 */
public class OrderVO {
    private String orderId;
    private MerchantOrdersVO merchantOrdersVO;

    @Override
    public String toString() {
        return "OrderVO{" +
                "orderId='" + orderId + '\'' +
                ", merchantOrdersVO=" + merchantOrdersVO +
                '}';
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public MerchantOrdersVO getMerchantOrdersVO() {
        return merchantOrdersVO;
    }

    public void setMerchantOrdersVO(MerchantOrdersVO merchantOrdersVO) {
        this.merchantOrdersVO = merchantOrdersVO;
    }
}
