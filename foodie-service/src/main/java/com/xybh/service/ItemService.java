package com.xybh.service;

import com.xybh.pojo.*;
import com.xybh.pojo.vo.CommentLevelCountVO;
import com.xybh.pojo.vo.SearchItemsVO;
import com.xybh.pojo.vo.ShopcartVO;
import com.xybh.utils.PagedGridResult;

import java.util.List;


public interface ItemService {

    /**
     * 根据商品ID查询详情
     *
     * @param itemId 商品ID
     * @return
     */
    Items queryItemById(String itemId);

    /**
     * 根据商品ID查询图片列表
     *
     * @param itemId 商品ID
     * @return
     */
    List<ItemsImg> queryItemImgList(String itemId);

    /**
     * 根据商品ID查询商品规格
     *
     * @param itemId 商品ID
     * @return
     */
    List<ItemsSpec> queryItemSpecList(String itemId);

    /**
     * 根据商品ID查询商品参数
     *
     * @param itemId 商品ID
     * @return
     */
    ItemsParam queryItemParamList(String itemId);

    /**
     * 根据商品ID查询商品的评价等级
     *
     * @param itemId 商品ID
     * @return
     */
    CommentLevelCountVO queryCommentsCounts(String itemId);

    /**
     * 根据商品ID和评价等级分页查询商品评价
     *
     * @param itemId 商品ID
     * @param level  评价等级
     * @param page 页数
     * @param size 每页显示页数
     * @return
     */
    PagedGridResult queryPagedComments(String itemId, Integer level, Integer page, Integer size);

    /**
     * 根据商品名称搜索商品
     *
     * @param keyword 商品模糊查询名称
     * @param sort 排序方式
     * @param page 当前页数
     * @param pageSize 每页数量
     * @return
     */
    PagedGridResult searchItems(String keyword, String sort, Integer page, Integer pageSize);

    /**
     * 根据商品名称搜索商品
     *
     * @param catId 分类ID
     * @param sort 排序方式
     * @param page 当前页数
     * @param pageSize 每页数量
     * @return
     */
    PagedGridResult searchItemsByThirdCat(Integer catId, String sort, Integer page, Integer pageSize);

    /**
     * 根据规格ids查询最新的商品信息
     * @param specIds 商品规格id列表
     * @return
     */
    List<ShopcartVO> queryItemsBySpecIds(String specIds);

    /**
     * 根据商品规格id获取规格对象的具体信息
     * @param specId 商品规格id
     * @return
     */
    ItemsSpec queryItemSpecById(String specId);

    /**
     * 根据商品id获取商品图片主图
     * @param itemId 商品id
     * @return
     */
    String queryItemMainImgById(String itemId);

    /**
     * 减少库存
     * @param specId    商品规格id
     * @param buyCounts 购买数量
     */
    void decreaseItemSpecStock(String specId, Integer buyCounts);

}
