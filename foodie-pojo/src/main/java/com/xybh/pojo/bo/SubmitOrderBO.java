package com.xybh.pojo.bo;

/**
 * @Author: xybh
 * @Description: 用于创建订单的BO对象
 * @Date: Created in 22:29 2021/1/25
 * @Modified:
 */
public class SubmitOrderBO {
    private String userId;
    private String itemSpecIds;
    private String addressId;
    private Integer payMethod;
    private String leftMsg;

    @Override
    public String toString() {
        return "SubmitOrderBO{" +
                "userId='" + userId + '\'' +
                ", itemSpecIds='" + itemSpecIds + '\'' +
                ", addressId='" + addressId + '\'' +
                ", payMethod=" + payMethod +
                ", leftMsg='" + leftMsg + '\'' +
                '}';
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public Integer getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(Integer payMethod) {
        this.payMethod = payMethod;
    }

    public String getLeftMsg() {
        return leftMsg;
    }

    public void setLeftMsg(String leftMsg) {
        this.leftMsg = leftMsg;
    }

    public String getItemSpecIds() {
        return itemSpecIds;
    }

    public void setItemSpecIds(String itemSpecIds) {
        this.itemSpecIds = itemSpecIds;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
