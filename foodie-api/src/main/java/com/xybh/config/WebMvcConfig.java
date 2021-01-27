package com.xybh.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: xybh
 * @Description:
 * @Date: Created in 14:53 2021/1/26
 * @Modified:
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     *  实现静态资源的映射
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                // 映射swagger2
                .addResourceLocations("classpath:/META-INF/resources/")
                // 映射本地静态资源
                .addResourceLocations("file:E:/Tomcat/apache-tomcat-9.0.37/webapps/foodie-center/");
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder){
        return builder.build();
    }
}
