package com.xybh.my.mapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @Author: xybh
 * @Description:
 * @Date: Created in 8:47 2021/1/21
 * @Modified:
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
