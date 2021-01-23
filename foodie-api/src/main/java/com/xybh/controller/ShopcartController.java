package com.xybh.controller;

import com.xybh.pojo.ItemsSpec;
import com.xybh.pojo.bo.ShopcartBO;
import com.xybh.service.ItemService;
import com.xybh.utils.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author: xybh
 * @Description:
 * @Date: Created in 14:16 2021/1/23
 * @Modified:
 */
@Api(value = "购物车接口", tags = {"购物车相关接口"})
@RestController
@RequestMapping("shopcart")
public class ShopcartController {

    @Autowired
    private ItemService itemService;

    @ApiOperation(value = "添加商品到购物车", notes = "添加商品到购物车", httpMethod = "POST")
    @PostMapping("/add")
    public JSONResult add(@RequestParam String userId,
                          @RequestBody ShopcartBO shopcartBO,
                          HttpServletRequest request,
                          HttpServletResponse response) {
        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("");
        }
        System.out.println(shopcartBO);
        // TODO 前端用户在登录的情况下,添加商品到购物车,会同时在后端同步到redis缓存

        return JSONResult.ok();
    }
}
