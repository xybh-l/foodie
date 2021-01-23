package com.xybh.service;

import com.xybh.pojo.Category;
import com.xybh.pojo.vo.CategoryVO;
import com.xybh.pojo.vo.NewItemsVO;

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

    /**
     * 查询一级分类下的二级分类
     * @param rootId 一级分类id
     * @return  CategoryVO 分类列表
     */
    List<CategoryVO> getSubCatList(Integer rootId);

    /**
     * 查询最新的6条商品信息
     * @param rootId 一级分类id
     * @return
     */
    List<NewItemsVO> getSixNewItemLazy(String rootId);
}
