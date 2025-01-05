package com.jh.configure.boot;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        Contact contact = new Contact()
                                  .name("strongmore")                             // 作者名称
                                  .email("xxx@qq.com")                            // 作者邮箱
                                  .url("https://www.cnblogs.com/strongmore/")     // 介绍作者的URL地址
                                  .extensions(Collections.emptyMap());            // 使用Map配置信息（如key为"name","email","url"）
        Info info = new Info()
                            .title("接口文档")                               // Api接口文档标题（必填）
                            .description("接口文档")                         // Api接口文档描述
                            .version("1.2.1")                               // Api接口版本
                            .contact(contact);                              // 设置联系人信息
        return new OpenAPI()
                       .openapi("3.0.1")                               // Open API 3.0.1(默认)
                       .info(info);                                    // 配置Swagger3.0描述信息
    }


    @Bean
    public GroupedOpenApi httpApi() {
        return GroupedOpenApi.builder()
                       .group("http")
                       .pathsToMatch("/**")
                       .build();
    }
}