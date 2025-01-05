package com.jh.configure.workflow;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;



//@EnableDiscoveryClient
@ConditionalOnProperty(prefix="nacos",name = "enable", havingValue = "true")
@Configuration
public class NacosConfig {
}
