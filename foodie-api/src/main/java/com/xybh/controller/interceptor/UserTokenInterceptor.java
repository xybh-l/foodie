package com.xybh.controller.interceptor;

import com.alibaba.fastjson.JSON;
import com.xybh.utils.JSONResult;
import com.xybh.utils.RedisOperator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * @Author: xybh
 * @Description:
 * @Date: Created in 12:29 2021/2/5
 * @Modified:
 */
public class UserTokenInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisOperator redisOperator;

    private final String REDIS_USER_TOKEN = "redis_user_token";

    /**
     * 拦截请求,在访问controller之前
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String userId = request.getHeader("headerUserId");
        String userToken = request.getHeader("headerUserToken");
        if(StringUtils.isBlank(userId) || StringUtils.isBlank(userToken)){
            returnErrorResponse(response, JSONResult.errorMsg("请登录"));
            return false;
        }
        String token = redisOperator.get(REDIS_USER_TOKEN + ":" + userId);
        if(StringUtils.isBlank(token)){
            returnErrorResponse(response, JSONResult.errorMsg("状态异常"));
            return false;
        }else{
            if(!token.equals(userToken)){
                returnErrorResponse(response, JSONResult.errorMsg("请登录"));
                return false;
            }
        }
         // false:请求被拦截,被驳回,验证出现问题
         // true:请求在经过校验之后,是可以放行的
        return true;
    }

    /**
     * 请求访问controller之后,渲染视图之前
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 请求访问controller之后,渲染视图之后
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    public void returnErrorResponse(HttpServletResponse response,
                                           JSONResult result){
        response.setContentType("text/json");
        response.setCharacterEncoding("utf-8");
        try (OutputStream out = response.getOutputStream()) {
            out.write(JSON.toJSONString(result).getBytes(StandardCharsets.UTF_8));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
