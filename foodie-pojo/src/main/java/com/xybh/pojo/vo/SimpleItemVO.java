package com.xybh.pojo.vo;

import java.util.Date;

/**
 * @Author: xybh
 * @Description: 六个最新商品的简单数据类型
 * @Date: Created in 10:26 2021/1/22
 * @Modified:
 */
public class SimpleItemVO {
    private String itemId;
    private String itemName;
    private String itemUrl;
    private Date createdTime;

    @Override
    public String toString() {
        return "SimpleItemVO{" +
                "itemId=" + itemId +
                ", itemName='" + itemName + '\'' +
                ", itemUrl='" + itemUrl + '\'' +
                ", createdTime=" + createdTime +
                '}';
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemUrl() {
        return itemUrl;
    }

    public void setItemUrl(String itemUrl) {
        this.itemUrl = itemUrl;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}
