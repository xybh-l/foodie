package com.xybh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Author: xybh
 * @Description:
 * @Date: Created in 23:05 2021/1/20
 * @Modified:
 */
@SpringBootApplication
// 对mapper进行扫描
@MapperScan("com.xybh.mapper")
// 开启定时任务
@EnableScheduling
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
