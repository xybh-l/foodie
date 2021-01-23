package com.xybh.enums;

/**
 * @Author: xybh
 * @Description: 评论等级枚举类
 * @Date: Created in 13:23 2021/1/22
 * @Modified:
 */
public enum CommentLevel {
    // 1.好评
    GOOD(1, "好评"),
    // 2.中评
    NORMAL(2, "中评"),
    // 3.差评
    BAD(3, "差评");

    public Integer type;
    public String desc;

    CommentLevel(Integer type, String desc){
        this.type = type;
        this.desc = desc;
    }
}
