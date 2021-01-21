package com.xybh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Author: xybh
 * @Description:
 * @Date: Created in 23:05 2021/1/20
 * @Modified:
 */
@SpringBootApplication
//对mapper进行扫描
@MapperScan("com.xybh.mapper")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
