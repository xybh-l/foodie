package com.xybh.pojo.bo.center;

import com.xybh.enums.OrderStatusEnum;

/**
 * @Author: xybh
 * @Description:
 * @Date: Created in 17:26 2021/1/28
 * @Modified:
 */
public class OrderStatusCountBO {
    Integer waitPayCounts;
    Integer waitDeliverCounts;
    Integer waitReceiveCounts;
    Integer waitCommentCounts;
    Integer totalCounts;

    @Override
    public String toString() {
        return "OrderStatusCountBO{" +
                "waitPayCounts=" + waitPayCounts +
                ", waitDeliverCounts=" + waitDeliverCounts +
                ", waitReceiveCounts=" + waitReceiveCounts +
                ", waitCommentCounts=" + waitCommentCounts +
                ", totalCounts=" + totalCounts +
                '}';
    }

    public Integer getWaitPayCounts() {
        return waitPayCounts;
    }

    public void setWaitPayCounts(Integer waitPayCounts) {
        this.waitPayCounts = waitPayCounts;
    }

    public Integer getWaitDeliverCounts() {
        return waitDeliverCounts;
    }

    public void setWaitDeliverCounts(Integer waitDeliverCounts) {
        this.waitDeliverCounts = waitDeliverCounts;
    }

    public Integer getWaitReceiveCounts() {
        return waitReceiveCounts;
    }

    public void setWaitReceiveCounts(Integer waitReceiveCounts) {
        this.waitReceiveCounts = waitReceiveCounts;
    }

    public Integer getWaitCommentCounts() {
        return waitCommentCounts;
    }

    public void setWaitCommentCounts(Integer waitCommentCounts) {
        this.waitCommentCounts = waitCommentCounts;
    }

    public Integer getTotalCounts() {
        return totalCounts;
    }

    public void setTotalCounts(Integer totalCounts) {
        this.totalCounts = totalCounts;
    }
}
