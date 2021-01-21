package com.xybh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author: xybh
 * @Description:
 * @Date: Created in 15:07 2021/1/21
 * @Modified:
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    // swagger2访问地址: 主机名:端口号/swagger-ui.html
    //  bootstrap页面: 主机名:端口号/doc.html

    /**
     * 配置swagger2核心内容 docket
     * @return
     */
    @Bean
    public Docket createRestApi() {
                    //指定api类型为SWAGGER2
        return new Docket(DocumentationType.SWAGGER_2)
                // 用于定义api文档的汇总信息
                .apiInfo(apiInfo())
                // 指定Controller的包名
                .select()
                .apis(RequestHandlerSelectors
                       .basePackage("com.xybh.controller"))
                //包名底下所有controller
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 文档页标题
                .title("吃货商城后端API文档")
                // 文档描述
                .description("专为吃货商城编写的后端API文档")
                // 联系人
                .contact(new Contact("xybh", null, null))
                // 版本信息
                .version("v0.0.1")
                // 项目URL
                .termsOfServiceUrl("127.0.0.1")
                .build();
    }

}
