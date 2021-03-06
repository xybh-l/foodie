package com.xybh.service.impl;

import com.xybh.mapper.CarouselMapper;
import com.xybh.pojo.Carousel;
import com.xybh.service.CarouselService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: xybh
 * @Description:
 * @Date: Created in 19:59 2021/1/21
 * @Modified:
 */
@Service
public class CarouselServiceImpl implements CarouselService {

    @Resource
    private CarouselMapper carouselMapper;

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public List<Carousel> queryAll(Integer isShow) {
        Example example = new Example(Carousel.class);
        example.createCriteria()
                .andEqualTo("isShow", isShow);
        example.orderBy("sort").asc();
        List<Carousel> carousels = carouselMapper.selectByExample(example);
        return carousels;
    }
}
