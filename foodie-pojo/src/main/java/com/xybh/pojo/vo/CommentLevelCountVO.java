package com.xybh.pojo.vo;

/**
 * @Author: xybh
 * @Description:
 * @Date: Created in 13:17 2021/1/22
 * @Modified:
 */
public class CommentLevelCountVO {
    public Integer totalCounts;
    public Integer goodCounts;
    public Integer normalCounts;
    public Integer badCounts;

    @Override
    public String toString() {
        return "CommentLevelCountVO{" +
                "totalCounts=" + totalCounts +
                ", goodCounts=" + goodCounts +
                ", normalCounts=" + normalCounts +
                ", badCounts=" + badCounts +
                '}';
    }

    public Integer getTotalCounts() {
        return totalCounts;
    }

    public void setTotalCounts(Integer totalCounts) {
        this.totalCounts = totalCounts;
    }

    public Integer getGoodCounts() {
        return goodCounts;
    }

    public void setGoodCounts(Integer goodCounts) {
        this.goodCounts = goodCounts;
    }

    public Integer getNormalCounts() {
        return normalCounts;
    }

    public void setNormalCounts(Integer normalCounts) {
        this.normalCounts = normalCounts;
    }

    public Integer getBadCounts() {
        return badCounts;
    }

    public void setBadCounts(Integer badCounts) {
        this.badCounts = badCounts;
    }
}
