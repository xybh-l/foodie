package com.xybh.mapper.ext;

import com.xybh.pojo.vo.CategoryVO;

import java.util.List;

/**
 * @Author: xybh
 * @Description:
 * @Date: Created in 9:32 2021/1/22
 * @Modified:
 */
public interface CategoryExtMapper{

    /**
     * 获取一级分类下的子分类
     * @param rootCatId 一级分类id
     * @return
     */
    List<CategoryVO> getSubCatList(Integer rootCatId);
}
