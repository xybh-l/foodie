package com.xybh.pojo.vo;

/**
 * @Author: xybh
 * @Description:
 * @Date: Created in 9:37 2021/1/22
 * @Modified:
 */
public class SubCategoryVO {
    private Integer subId;
    private String subName;
    private Integer subType;
    private Integer subFatherId;

    public Integer getSubId() {
        return subId;
    }

    public void setSubId(Integer subId) {
        this.subId = subId;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public Integer getSubType() {
        return subType;
    }

    public void setSubType(Integer subType) {
        this.subType = subType;
    }

    public Integer getSubFatherId() {
        return subFatherId;
    }

    public void setSubFatherId(Integer subFatherId) {
        this.subFatherId = subFatherId;
    }

    @Override
    public String toString() {
        return "SubCategoryVO{" +
                "subId=" + subId +
                ", subName='" + subName + '\'' +
                ", subType=" + subType +
                ", subFatherId=" + subFatherId +
                '}';
    }
}
