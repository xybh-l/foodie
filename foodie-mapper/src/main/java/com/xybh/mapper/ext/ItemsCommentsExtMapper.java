package com.xybh.mapper.ext;

import com.xybh.my.mapper.MyMapper;
import com.xybh.pojo.ItemsComments;
import com.xybh.pojo.vo.MyCommentVO;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ItemsCommentsExtMapper extends MyMapper<ItemsComments> {

    /**
     * 保存评价信息
     * @param map 参数信息
     */
    void saveComments(@Param("paramsMap") HashMap<String, Object> map);

    /**
     * 查询我的评价
     * @param map 参数信息
     * @return
     */
    List<MyCommentVO> queryMyComments(@Param("paramsMap") HashMap<String, Object> map);
}