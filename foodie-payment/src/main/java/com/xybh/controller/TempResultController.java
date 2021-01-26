package com.xybh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: xybh
 * @Description:
 * @Date: Created in 18:45 2021/1/26
 * @Modified:
 */
@Controller
@ApiIgnore
public class TempResultController {

    @GetMapping("/alipayResult")
    public String alipayResult() {
        return "alipayResult";
    }
}