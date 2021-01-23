package com.xybh.pojo.vo;

/**
 * @Author: xybh
 * @Description: 用来展示商品搜索列表的VO
 * @Date: Created in 13:54 2021/1/22
 * @Modified:
 */
public class SearchItemsVO {
    private String itemId;
    private String itemName;
    private Integer price;
    private Integer sellCounts;
    private String imgUrl;

    @Override
    public String toString() {
        return "SearchItemsVO{" +
                "itemId='" + itemId + '\'' +
                ", itemName='" + itemName + '\'' +
                ", price=" + price +
                ", sellCounts=" + sellCounts +
                ", imgUrl='" + imgUrl + '\'' +
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getSellCounts() {
        return sellCounts;
    }

    public void setSellCounts(Integer sellCounts) {
        this.sellCounts = sellCounts;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
