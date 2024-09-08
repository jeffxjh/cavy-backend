package com.jh.cavy.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Getter
@Setter
//@Primary
@ConfigurationProperties(prefix="jwt")
@AutoConfigureBefore(value = {JwtTokenUtil.class})
//@Component //springboot3.2之后无需定义否则bean注入重复
//这里可以不用指定配置文件位置
//在spring.factories中已经加载了JwtEnvironmentPostProcessor类，该类加载了jwt.properties文件
//但是似乎使用PropertySource注解更加简洁
//@PropertySource(value = "classpath:conf/jwt.properties")
public class JwtProperties {
    private String header;
    private String tokenStartWith;
    private String base64Secret;
    private Long tokenValidityInSeconds;
    private String onlineKey;
    private String codeKey;
    private String cookieKey;
    private Integer filterOrder;
    private String redisKey = "token";
    private String appScope;

}
