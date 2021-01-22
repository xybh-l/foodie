package com.xybh.service;

import com.xybh.pojo.Category;

import java.util.List;

/**
 * @Author: xybh
 * @Description:
 * @Date: Created in 9:18 2021/1/22
 * @Modified:
 */
public interface CategoryService {


    /**
     * 查询所有一级分类
     * @return 一级分类列表
     */
    List<Category> queryAllRootLevelCat();
}
