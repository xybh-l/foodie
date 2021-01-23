package com.xybh.pojo.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: xybh
 * @Description:
 * @Date: Created in 14:18 2021/1/23
 * @Modified:
 */
@ApiModel("购物车信息BO")
public class ShopcartBO {
    @ApiModelProperty(value = "商品id", name = "itemId", required = true)
    private String itemId;
    @ApiModelProperty(value = "商品图片url", name = "itemImgUrl", required = true)
    private String itemImgUrl;
    @ApiModelProperty(value = "商品名称", name = "itemName", required = true)
    private String itemName;
    @ApiModelProperty(value = "商品规格id", name = "specId", required = true)
    private String specId;
    @ApiModelProperty(value = "商品规格名称", name = "specName", required = true)
    private String specName;
    @ApiModelProperty(value = "购买总数", name = "buyCounts", required = true)
    private Integer buyCounts;
    @ApiModelProperty(value = "商品折扣单价", name = "priceDiscount", required = true)
    private String priceDiscount;
    @ApiModelProperty(value = "商品平常价格", name = "priceNormal", required = true)
    private String priceNormal;

    @Override
    public String toString() {
        return "ShopcartBO{" +
                "itemId='" + itemId + '\'' +
                ", itemImgUrl='" + itemImgUrl + '\'' +
                ", itemName='" + itemName + '\'' +
                ", specId='" + specId + '\'' +
                ", specName='" + specName + '\'' +
                ", buyCounts=" + buyCounts +
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

    public Integer getBuyCounts() {
        return buyCounts;
    }

    public void setBuyCounts(Integer buyCounts) {
        this.buyCounts = buyCounts;
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
