package com.xybh.enums;

/**
 * @Author: xybh
 * @Description: 性别枚举类
 * @Date: Created in 13:46 2021/1/21
 * @Modified:
 */
public enum Sex {
    //性别女
    woman(0, "女"),
    //性别男
    man(1, "男"),
    //性别保密
    secret(2, "保密");

    public final Integer type;
    public final String value;

    Sex(int type, String value) {
        this.type = type;
        this.value = value;
    }
}
