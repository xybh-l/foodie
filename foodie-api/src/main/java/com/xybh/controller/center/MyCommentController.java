package com.xybh.controller.center;

import cn.hutool.http.HttpStatus;
import com.xybh.controller.BaseController;
import com.xybh.enums.YesOrNo;
import com.xybh.pojo.OrderItems;
import com.xybh.pojo.Orders;
import com.xybh.pojo.bo.center.OrderItemsCommentBO;
import com.xybh.service.center.MyCommentService;
import com.xybh.utils.JSONResult;
import com.xybh.utils.PagedGridResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: xybh
 * @Description:
 * @Date: Created in 13:57 2021/1/28
 * @Modified:
 */
@Api(value = "用户中心-我的评价", tags = {"用户中心-我的评价相关接口"})
@RestController
@RequestMapping("/mycomments")
public class MyCommentController extends BaseController {
    @Autowired
    private MyCommentService commentService;

    @ApiOperation(value = "查询我的评价", notes = "查询我的评价", httpMethod = "POST")
    @PostMapping("pending")
    public JSONResult comments(@RequestParam String userId,
                               @RequestParam String orderId) {
        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("");
        }
        // 判断用户和订单是否关联
        JSONResult checkResult = checkUserOrder(userId, orderId);
        if (checkResult.getStatus() != HttpStatus.HTTP_OK) {
            return checkResult;
        }
        // 判断该笔订单是否已经被评价
        Orders myOrder = (Orders) checkResult.getData();
        if (YesOrNo.YES.type.equals(myOrder.getIsComment())) {
            return JSONResult.errorMsg("该笔订单已经评价");
        }
        List<OrderItems> list = commentService.queryPendingComment(orderId);
        return JSONResult.ok(list);
    }

    @ApiOperation(value = "保存评论列表", notes = "保存评论列表", httpMethod = "POST")
    @PostMapping("saveList")
    public JSONResult saveList(@RequestParam String userId,
                               @RequestParam String orderId,
                               @RequestBody List<OrderItemsCommentBO> commentList) {
        // 判断用户和订单是否关联
        JSONResult checkResult = checkUserOrder(userId, orderId);
        if (checkResult.getStatus() != HttpStatus.HTTP_OK) {
            return checkResult;
        }
        // 判断评论内容list不能为空
        if (commentList == null || commentList.isEmpty()) {
            return JSONResult.errorMsg("评论内容不能为空");
        }

        commentService.saveComments(orderId, userId, commentList);
        return JSONResult.ok();
    }

    @ApiOperation(value = "查询我的评价", notes = "查询我的评价", httpMethod = "POST")
    @PostMapping("query")
    public JSONResult query(@RequestParam String userId,
                            @RequestParam(value = "orderStatus", required = false) Integer orderStatus,
                            @RequestParam Integer page,
                            @RequestParam Integer pageSize) {
        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("");
        }
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = COMMON_PAGE_SIZE;
        }

        PagedGridResult result = commentService.queryMyComments(userId, page, pageSize);
        return JSONResult.ok(result);
    }

}
