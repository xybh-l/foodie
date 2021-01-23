package com.xybh.controller;

import com.xybh.enums.YesOrNo;
import com.xybh.pojo.*;
import com.xybh.pojo.vo.CategoryVO;
import com.xybh.pojo.vo.CommentLevelCountVO;
import com.xybh.pojo.vo.ItemInfoVO;
import com.xybh.pojo.vo.NewItemsVO;
import com.xybh.service.CarouselService;
import com.xybh.service.CategoryService;
import com.xybh.service.ItemService;
import com.xybh.utils.JSONResult;
import com.xybh.utils.PagedGridResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: xybh
 * @Description:
 * @Date: Created in 20:03 2021/1/21
 * @Modified:
 */
@RestController
@RequestMapping("items")
@Api(value = "商品接口", tags = {"商品信息展示的相关接口"})
public class ItemsController extends BaseController {

    @Autowired
    private ItemService itemService;

    @ApiOperation(value = "查询商品详情", notes = "查询商品详情", httpMethod = "GET")
    @GetMapping("/info/{itemId}")
    public JSONResult subCat(@ApiParam(name = "itemId", value = "商品id", required = true)
                             @PathVariable String itemId) {
        if (StringUtils.isBlank(itemId)) {
            return JSONResult.errorMsg("商品不存在");
        }
        Items items = itemService.queryItemById(itemId);
        List<ItemsImg> itemsImgList = itemService.queryItemImgList(itemId);
        List<ItemsSpec> itemsSpecList = itemService.queryItemSpecList(itemId);
        ItemsParam itemsParam = itemService.queryItemParamList(itemId);

        ItemInfoVO itemInfo = new ItemInfoVO();
        itemInfo.setItem(items);
        itemInfo.setItemImgList(itemsImgList);
        itemInfo.setItemSpecList(itemsSpecList);
        itemInfo.setItemParams(itemsParam);

        return JSONResult.ok(itemInfo);
    }

    @ApiOperation(value = "查询商品评价等级", notes = "查询商品评价等级", httpMethod = "GET")
    @GetMapping("/commentLevel")
    public JSONResult commentLevel(@ApiParam(name = "itemId", value = "商品id", required = true)
                                   @RequestParam String itemId) {
        if (StringUtils.isBlank(itemId)) {
            return JSONResult.errorMsg("商品不存在");
        }
        CommentLevelCountVO commentLevelCount = itemService.queryCommentsCounts(itemId);

        return JSONResult.ok(commentLevelCount);


    }

    @ApiOperation(value = "查询商品评价详情", notes = "查询商品评价详情", httpMethod = "GET")
    @GetMapping("/comments")
    public JSONResult commentLevel(@ApiParam(name = "itemId", value = "商品id", required = true)
                                   @RequestParam String itemId,
                                   @ApiParam(name = "level", value = "评价等级")
                                   @RequestParam(value = "level", required = false) Integer level,
                                   @ApiParam(name = "page", value = "页数")
                                   @RequestParam(value = "page", required = false) Integer page,
                                   @ApiParam(name = "pageSize", value = "每页显示的评论数")
                                   @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        if (StringUtils.isBlank(itemId)) {
            return JSONResult.errorMsg("商品不存在");
        }
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = COMMENT_PAGE_SIZE;
        }
        PagedGridResult grid = itemService.queryPagedComments(itemId, level, page, pageSize);
        return JSONResult.ok(grid);
    }


    @ApiOperation(value = "搜索商品列表", notes = "搜索商品列表", httpMethod = "GET")
    @GetMapping("/search")
    public JSONResult commentLevel(@ApiParam(name = "keywords", value = "关键词", required = true)
                                   @RequestParam String keywords,
                                   @ApiParam(name = "sort", value = "排序")
                                   @RequestParam(value = "sort", required = false) String sort,
                                   @ApiParam(name = "page", value = "页数")
                                   @RequestParam(value = "page", required = false) Integer page,
                                   @ApiParam(name = "pageSize", value = "每页显示的商品数")
                                   @RequestParam(value = "pageSize", required = false) Integer pageSize) {

        if (StringUtils.isBlank(keywords)) {
            return JSONResult.errorMsg(null);
        }

        if (page == null) {
            page = 1;
        }

        if (pageSize == null) {
            pageSize = PAGE_SIZE;
        }

        PagedGridResult grid = itemService.searchItems(keywords, sort, page, pageSize);
        return JSONResult.ok(grid);
    }

    @ApiOperation(value = "通过分类ID搜索商品列表", notes = "通过分类ID搜索商品列表", httpMethod = "GET")
    @GetMapping("/catItems")
    public JSONResult commentLevel(@ApiParam(name = "catId", value = "三级分类ID", required = true)
                                   @RequestParam Integer catId,
                                   @ApiParam(name = "sort", value = "排序")
                                   @RequestParam(value = "sort", required = false) String sort,
                                   @ApiParam(name = "page", value = "页数")
                                   @RequestParam(value = "page", required = false) Integer page,
                                   @ApiParam(name = "pageSize", value = "每页显示的商品数")
                                   @RequestParam(value = "pageSize", required = false) Integer pageSize) {

        if (catId == null) {
            return JSONResult.errorMsg(null);
        }

        if (page == null) {
            page = 1;
        }

        if (pageSize == null) {
            pageSize = PAGE_SIZE;
        }

        PagedGridResult grid = itemService.searchItemsByThirdCat(catId, sort, page, pageSize);
        return JSONResult.ok(grid);
    }
}
