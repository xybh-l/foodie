package com.xybh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Author: xybh
 * @Description:
 * @Date: Created in 21:50 2021/2/5
 * @Modified:
 */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@MapperScan(basePackages = "com.xybh.mapper")
public class SsoApplication {
    public static void main(String[] args) {
        SpringApplication.run(SsoApplication.class, args);
    }
}
