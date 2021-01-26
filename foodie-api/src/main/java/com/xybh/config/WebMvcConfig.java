package com.xybh.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: xybh
 * @Description:
 * @Date: Created in 14:53 2021/1/26
 * @Modified:
 */
@Configuration
public class WebMvcConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder){
        return builder.build();
    }
}
