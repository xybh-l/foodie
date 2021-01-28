package com.xybh.service.impl.center;

import com.github.pagehelper.PageInfo;
import com.xybh.utils.PagedGridResult;

import java.util.List;

/**
 * @Author: xybh
 * @Description:
 * @Date: Created in 17:00 2021/1/28
 * @Modified:
 */
public class BaseService {

    public PagedGridResult setterPagedGrid(List<?> list, Integer page) {
        PageInfo<?> pageList = new PageInfo<>(list);
        PagedGridResult grid = new PagedGridResult();
        grid.setPage(page);
        grid.setRows(list);
        grid.setTotal(pageList.getPages());
        grid.setRecords(pageList.getTotal());
        return grid;
    }
}
