package com.jh.cavy.manage.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "weixin")
public class WeixinProperties {
    private String appId;
    private String secretId;
    private String getOpenidUrl;

}
