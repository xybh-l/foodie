package com.xybh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Author: xybh
 * @Description:
 * @Date: Created in 23:05 2021/1/20
 * @Modified:
 */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@MapperScan("com.xybh.mapper") // 对mapper进行扫描
@EnableScheduling // 开启定时任务
@EnableRedisHttpSession // 开启使用Redis作为spring session
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
