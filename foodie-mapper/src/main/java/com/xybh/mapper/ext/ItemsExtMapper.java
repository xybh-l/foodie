package com.xybh.mapper.ext;

import com.xybh.pojo.vo.ItemsCommentsVO;
import com.xybh.pojo.vo.SearchItemsVO;
import com.xybh.pojo.vo.ShopcartVO;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * @author a1353
 */
public interface ItemsExtMapper {
    /**
     * 根据商品ID和评价等级查询评价信息
     *
     * @param map 参数
     * @return 评价信息VO
     */
    List<ItemsCommentsVO> queryItemComments(@Param("paramsMap") HashMap<String, Object> map);

    /**
     * 根据商品名称及排序条件模糊查询商品
     *
     * @param map 参数
     * @return 商品信息VO
     */
    List<SearchItemsVO> searchItems(@Param("paramsMap") HashMap<String, Object> map);

    /**
     * 根据三级分类查询商品
     *
     * @param map 参数
     * @return 商品信息VO
     */
    List<SearchItemsVO> searchItemsByThirdCat(@Param("paramsMap") HashMap<String, Object> map);

    /**
     * 根据商品规格ID查询商品信息
     *
     * @param specIdsList 商品规格ID列表
     * @return
     */
    List<ShopcartVO> queryItemsBySpecIds(@Param("paramsList") List<String> specIdsList);

    /**
     * 减少库存
     * @param specId        商品规格id
     * @param pendingCount  待减去数量
     * @return
     */
    int decreaseItemSpecStock(@Param("specId") String specId, @Param("pendingCounts") Integer pendingCount);
}