package com.xybh.pojo.vo;

/**
 * @Author: xybh
 * @Description:
 * @Date: Created in 14:18 2021/1/23
 * @Modified:
 */
public class ShopcartVO {
    private String itemId;
    private String itemImgUrl;
    private String itemName;
    private String specId;
    private String specName;
    private String priceDiscount;
    private String priceNormal;

    @Override
    public String toString() {
        return "ShopcartBO{" +
                "itemId='" + itemId + '\'' +
                ", itemImgUrl='" + itemImgUrl + '\'' +
                ", itemName='" + itemName + '\'' +
                ", specId='" + specId + '\'' +
                ", specName='" + specName + '\'' +
                ", priceDiscount='" + priceDiscount + '\'' +
                ", priceNormal='" + priceNormal + '\'' +
                '}';
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemImgUrl() {
        return itemImgUrl;
    }

    public void setItemImgUrl(String itemImgUrl) {
        this.itemImgUrl = itemImgUrl;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getSpecId() {
        return specId;
    }

    public void setSpecId(String specId) {
        this.specId = specId;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public String getPriceDiscount() {
        return priceDiscount;
    }

    public void setPriceDiscount(String priceDiscount) {
        this.priceDiscount = priceDiscount;
    }

    public String getPriceNormal() {
        return priceNormal;
    }

    public void setPriceNormal(String priceNormal) {
        this.priceNormal = priceNormal;
    }
}
