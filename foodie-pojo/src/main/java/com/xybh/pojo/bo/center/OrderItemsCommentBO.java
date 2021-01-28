package com.xybh.pojo.bo.center;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: xybh
 * @Description:
 * @Date: Created in 16:02 2021/1/28
 * @Modified:
 */
@ApiModel(value = "订单评论BO", description = "订单评论BO")
public class OrderItemsCommentBO {
    @ApiModelProperty(name = "commentId", value = "评论Id", required = true)
    private String commentId;
    @ApiModelProperty(name = "itemId", value = "商品Id", required = true)
    private String itemId;
    @ApiModelProperty(name = "itemName", value = "商品名", required = true)
    private String itemName;
    @ApiModelProperty(name = "itemSpecId", value = "商品规格Id", required = true)
    private String itemSpecId;
    @ApiModelProperty(name = "itemSpecName", value = "商品规格名", required = true)
    private String itemSpecName;
    @ApiModelProperty(name = "commentLevel", value = "评论等级", required = true)
    private Integer commentLevel;
    @ApiModelProperty(name = "content", value = "评论内容", required = true)
    private String content;

    @Override
    public String toString() {
        return "OrderItemsCommentBO{" +
                "commentId='" + commentId + '\'' +
                ", itemId='" + itemId + '\'' +
                ", itemName='" + itemName + '\'' +
                ", itemSpecId='" + itemSpecId + '\'' +
                ", itemSpecName='" + itemSpecName + '\'' +
                ", commentLevel=" + commentLevel +
                ", content='" + content + '\'' +
                '}';
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
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

    public String getItemSpecId() {
        return itemSpecId;
    }

    public void setItemSpecId(String itemSpecId) {
        this.itemSpecId = itemSpecId;
    }

    public String getItemSpecName() {
        return itemSpecName;
    }

    public void setItemSpecName(String itemSpecName) {
        this.itemSpecName = itemSpecName;
    }

    public Integer getCommentLevel() {
        return commentLevel;
    }

    public void setCommentLevel(Integer commentLevel) {
        this.commentLevel = commentLevel;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
