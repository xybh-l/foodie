package com.xybh;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @Author: xybh
 * @Description:
 * @Date: Created in 12:54 2021/1/29
 * @Modified:
 */
public class WarStarterPaymentApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(PaymentApplication.class);
    }
}
