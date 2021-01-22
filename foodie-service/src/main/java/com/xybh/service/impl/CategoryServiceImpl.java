package com.xybh.service.impl;

import com.xybh.mapper.CategoryMapper;
import com.xybh.pojo.Category;
import com.xybh.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
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

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public List<Category> queryAllRootLevelCat() {

        Example example = new Example(Category.class);
        example.createCriteria()
                .andEqualTo("type", 1);
        List<Category> result = categoryMapper.selectByExample(example);
        return result;
    }
}
