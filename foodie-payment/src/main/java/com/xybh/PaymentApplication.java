package com.xybh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Author: xybh
 * @Description:
 * @Date: Created in 16:22 2021/1/26
 * @Modified:
 */
@SpringBootApplication
@MapperScan(basePackages = "com.xybh.mapper")
public class PaymentApplication {
    public static void main(String[] args) {
        SpringApplication.run(PaymentApplication.class, args);
    }
}
