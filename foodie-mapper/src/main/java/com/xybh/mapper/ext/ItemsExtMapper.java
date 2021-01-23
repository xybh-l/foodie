package com.xybh.mapper.ext;

import com.xybh.pojo.vo.ItemsCommentsVO;
import com.xybh.pojo.vo.SearchItemsVO;
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
     * @param map 参数
     * @return 商品信息VO
     */
    List<SearchItemsVO> searchItems(@Param("paramsMap") HashMap<String, Object> map);
}