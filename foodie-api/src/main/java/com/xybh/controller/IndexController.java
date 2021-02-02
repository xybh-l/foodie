package com.xybh.controller;

import com.alibaba.fastjson.JSON;
import com.xybh.enums.YesOrNo;
import com.xybh.pojo.Carousel;
import com.xybh.pojo.Category;
import com.xybh.pojo.vo.CategoryVO;
import com.xybh.pojo.vo.NewItemsVO;
import com.xybh.service.CarouselService;
import com.xybh.service.CategoryService;
import com.xybh.utils.JSONResult;
import com.xybh.utils.RedisOperator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    @Autowired
    private RedisOperator redisOperator;

    @ApiOperation(value = "获取首页轮播图列表", notes = "获取首页轮播图列表", httpMethod = "GET")
    @GetMapping("/carousel")
    public JSONResult carousel() {

        String carousel = redisOperator.get("carousel");
        List<Carousel> list;
        if(StringUtils.isBlank(carousel)){
            list = carouselService.queryAll(YesOrNo.YES.type);
            redisOperator.set("carousel", JSON.toJSONString(list));
        }else {
            list = JSON.parseArray(carousel, Carousel.class);
        }
        return JSONResult.ok(list);
    }

    /**
     * 1.后台管理系统，一旦广告（轮播图）发生更改，就可以删除缓存，然后重置
     * 2.定时重置，比如每天凌晨三点重置
     * 3.每个轮播图都可能是一个广告，每一个广告都会有一个过期时间，过期了，再重置
     */

    @ApiOperation(value = "查询所有一级分类", notes = "查询所有一级分类", httpMethod = "GET")
    @GetMapping("/cats")
    public JSONResult cats() {
        String cats = redisOperator.get("cats");
        List<Category> categories;
        if(StringUtils.isBlank(cats)){
           categories = categoryService.queryAllRootLevelCat();
           redisOperator.set("cats", JSON.toJSONString(categories));
        }else {
            categories = JSON.parseArray(cats, Category.class);
        }
        return JSONResult.ok(categories);
    }

    @ApiOperation(value = "查询商品子分类", notes = "查询商品子分类", httpMethod = "GET")
    @GetMapping("/subCat/{rootCatId}")
    public JSONResult subCat(@ApiParam(name = "rootCatId", value = "一级分类id", required = true)
                             @PathVariable Integer rootCatId) {
        if (rootCatId == null) {
            return JSONResult.errorMsg("分类不存在");
        }
        String subCat = redisOperator.hget("subCats", rootCatId.toString());
        List<CategoryVO> subCatList;
        if(StringUtils.isBlank(subCat)){
           subCatList = categoryService.getSubCatList(rootCatId);
           redisOperator.hset("subCats", rootCatId.toString(), JSON.toJSONString(subCatList));
        }else {
            subCatList = JSON.parseArray(subCat, CategoryVO.class);
        }

        return JSONResult.ok(subCatList);
    }

    @ApiOperation(value = "查询六个最新商品信息", notes = "查询六个最新商品信息", httpMethod = "GET")
    @GetMapping("/sixNewItems/{rootCatId}")
    public JSONResult sixNewItems(@ApiParam(name = "rootCatId", value = "一级分类id", required = true)
                                  @PathVariable String rootCatId) {
        if (rootCatId == null) {
            return JSONResult.errorMsg("分类不存在");
        }
        List<NewItemsVO> list = categoryService.getSixNewItemLazy(rootCatId);
        return JSONResult.ok(list);
    }


}
