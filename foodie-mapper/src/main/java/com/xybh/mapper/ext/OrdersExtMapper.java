package com.xybh.mapper.ext;

import com.xybh.pojo.vo.CategoryVO;
import com.xybh.pojo.vo.MyOrdersVO;
import com.xybh.pojo.vo.NewItemsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: xybh
 * @Description:
 * @Date: Created in 9:32 2021/1/22
 * @Modified:
 */
public interface OrdersExtMapper {

    /**
     * 查询个人订单信息
     * @param map  查询条件
     * @return
     */
    List<MyOrdersVO> queryMyOrders(@Param("paramsMap") Map<String, Object> map);
}
