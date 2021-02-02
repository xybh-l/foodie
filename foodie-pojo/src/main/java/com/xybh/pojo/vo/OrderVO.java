package com.xybh.pojo.vo;

import com.xybh.pojo.bo.ShopcartBO;

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

    private List<ShopcartBO> toBeRemovedShopcartList;

    public List<ShopcartBO> getToBeRemovedShopcartList() {
        return toBeRemovedShopcartList;
    }

    public void setToBeRemovedShopcartList(List<ShopcartBO> toBeRemovedShopcartList) {
        this.toBeRemovedShopcartList = toBeRemovedShopcartList;
    }

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
