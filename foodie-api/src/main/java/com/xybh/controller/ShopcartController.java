package com.xybh.controller;

import com.alibaba.fastjson.JSON;
import com.xybh.pojo.ItemsSpec;
import com.xybh.pojo.bo.ShopcartBO;
import com.xybh.service.ItemService;
import com.xybh.utils.JSONResult;
import com.xybh.utils.RedisOperator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
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
public class ShopcartController extends BaseController {

    @Autowired
    private RedisOperator redisOperator;

    @ApiOperation(value = "添加商品到购物车", notes = "添加商品到购物车", httpMethod = "POST")
    @PostMapping("/add")
    public JSONResult add(@RequestParam String userId,
                          @RequestBody ShopcartBO shopcartBO) {
        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("");
        }
        // 前端用户在登录的情况下,添加商品到购物车,会同时在后端同步到redis缓存
        // 需要判断当前购物车中包含已经存在的商品，如果存在则累加购买数量
        String shopcartJson = redisOperator.get(FOODIE_SHOPCART + ":" + userId);
        List<ShopcartBO> shopcartList;
        if (StringUtils.isNotBlank(shopcartJson)) {
            // redis中已经有购物车了
            shopcartList = JSON.parseArray(shopcartJson, ShopcartBO.class);
            // 判断购物车中是否已经存在已有商品,如果有的话counts累加
            boolean isHaving = false;
            for (ShopcartBO sc : shopcartList) {
                String tmpSpecId = sc.getSpecId();
                if (tmpSpecId.equals(shopcartBO.getSpecId())) {
                    sc.setBuyCounts(sc.getBuyCounts() + shopcartBO.getBuyCounts());
                    isHaving = true;
                }
            }
            if (!isHaving) {
                shopcartList.add(shopcartBO);
            }
        } else {
            // redis中没有购物车
            shopcartList = new ArrayList<>();
            // 直接添加到购物车中
            shopcartList.add(shopcartBO);
        }

        // 覆盖现有redis中的购物车
        redisOperator.set(FOODIE_SHOPCART + ":" + userId, JSON.toJSONString(shopcartList));

        return JSONResult.ok();
    }

    @ApiOperation(value = "删除购物车中的商品", notes = "删除购物车中的商品", httpMethod = "POST")
    @PostMapping("/del")
    public JSONResult del(@RequestParam String userId,
                          @RequestParam String itemSpecId) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(itemSpecId)) {
            return JSONResult.errorMsg("参数不能为空");
        }

        String shopcartJson = redisOperator.get(FOODIE_SHOPCART + ":" + userId);
        List<ShopcartBO> shopcartList = null;
        if (StringUtils.isNotBlank(shopcartJson)) {
            shopcartList = JSON.parseArray(shopcartJson, ShopcartBO.class);
            System.out.println(itemSpecId);
            shopcartList.removeIf(sc -> itemSpecId.equals(sc.getSpecId()));
        }
        redisOperator.set(FOODIE_SHOPCART + ":" + userId, JSON.toJSONString(shopcartList));

        return JSONResult.ok();
    }
}
