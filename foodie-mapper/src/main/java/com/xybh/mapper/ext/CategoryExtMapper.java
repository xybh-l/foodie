package com.xybh.mapper.ext;

import com.xybh.pojo.vo.CategoryVO;
import com.xybh.pojo.vo.NewItemsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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

    /**
     * 懒加载最新的六个商品信息
     * @param map 参数信息
     * @return
     */
    List<NewItemsVO> getSixNewItemLazy(@Param("paramMap") Map<String, Object> map);
}
