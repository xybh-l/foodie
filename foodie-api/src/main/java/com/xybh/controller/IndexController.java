package com.xybh.controller;

import com.xybh.enums.YesOrNo;
import com.xybh.pojo.Carousel;
import com.xybh.pojo.Category;
import com.xybh.service.CarouselService;
import com.xybh.service.CategoryService;
import com.xybh.utils.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: xybh
 * @Description:
 * @Date: Created in 20:03 2021/1/21
 * @Modified:
 */
@RestController
@RequestMapping("index")
@Api(value = "首页", tags = {"首页展示的相关接口"})
public class IndexController {

    @Autowired
    private CarouselService carouselService;
    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "获取首页轮播图列表", notes = "获取首页轮播图列表")
    @GetMapping("/carousel")
    public JSONResult carousel() {
        List<Carousel> list = carouselService.queryAll(YesOrNo.YES.type);
        return JSONResult.ok(list);
    }

    @ApiOperation(value = "查询所有一级分类", notes = "查询所有一级分类")
    @GetMapping("/cats")
    public JSONResult cats() {
        List<Category> categories = categoryService.queryAllRootLevelCat();
        return JSONResult.ok(categories);
    }

    @GetMapping("/subcat")
    public JSONResult subCat() {
        return JSONResult.ok();
    }
}
