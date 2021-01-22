package com.xybh.service;

import com.xybh.pojo.Carousel;

import java.util.List;

/**
 * @Author: xybh
 * @Description:
 * @Date: Created in 19:58 2021/1/21
 * @Modified:
 */
public interface CarouselService {
    /**
     * 查询所有轮播图
     * @param isShow
     * @return
     */
    List<Carousel> queryAll(Integer isShow);
}
