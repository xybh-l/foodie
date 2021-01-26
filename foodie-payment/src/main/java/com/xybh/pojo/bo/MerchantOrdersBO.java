package com.xybh.pojo.bo;

/**
 * @author a1353
 */
public class MerchantOrdersBO {

    // 商户订单号
    private String merchantOrderId;
    // 商户方的发起用户的用户主键id
    private String merchantUserId;
    // 实际支付总金额（包含商户所支付的订单费邮费总额）
    private Integer amount;
    // 支付方式 1:微信   2:支付宝
    private Integer payMethod;
    // 支付成功后的回调地址（自定义）
    private String returnUrl;

    @Override
    public String toString() {
        return "MerchantOrdersBO{" +
                "merchantOrderId='" + merchantOrderId + '\'' +
                ", merchantUserId='" + merchantUserId + '\'' +
                ", amount=" + amount +
                ", payMethod=" + payMethod +
                ", returnUrl='" + returnUrl + '\'' +
                '}';
    }

    public String getMerchantOrderId() {
        return merchantOrderId;
    }

    public void setMerchantOrderId(String merchantOrderId) {
        this.merchantOrderId = merchantOrderId;
    }

    public String getMerchantUserId() {
        return merchantUserId;
    }

    public void setMerchantUserId(String merchantUserId) {
        this.merchantUserId = merchantUserId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(Integer payMethod) {
        this.payMethod = payMethod;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }
}