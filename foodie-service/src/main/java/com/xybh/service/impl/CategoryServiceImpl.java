package com.xybh.service.impl;

import com.xybh.mapper.CategoryMapper;
import com.xybh.mapper.ext.CategoryExtMapper;
import com.xybh.pojo.Category;
import com.xybh.pojo.vo.CategoryVO;
import com.xybh.pojo.vo.NewItemsVO;
import com.xybh.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: xybh
 * @Description:
 * @Date: Created in 9:19 2021/1/22
 * @Modified:
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private CategoryExtMapper categoryExtMapper;

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public List<Category> queryAllRootLevelCat() {

        Example example = new Example(Category.class);
        example.createCriteria()
                .andEqualTo("type", 1);
        return categoryMapper.selectByExample(example);
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public List<CategoryVO> getSubCatList(Integer rootId) {
        return categoryExtMapper.getSubCatList(rootId);
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public List<NewItemsVO> getSixNewItemLazy(String rootId) {
        HashMap<String, Object> map = new HashMap<>(2);
        map.put("rootCatId", rootId);
        return categoryExtMapper.getSixNewItemLazy(map);
    }
}
