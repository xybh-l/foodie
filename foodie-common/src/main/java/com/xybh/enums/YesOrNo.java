package com.xybh.enums;

/**
 * @Author: xybh
 * @Description: 性别枚举类
 * @Date: Created in 13:46 2021/1/21
 * @Modified:
 */
public enum YesOrNo {
    NO(0, "否"),
    YES(1, "是");

    public final Integer type;
    public final String value;

    YesOrNo(int type, String value) {
        this.type = type;
        this.value = value;
    }
}
