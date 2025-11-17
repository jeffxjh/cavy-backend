package com.jh.configure;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableCaching
@Configuration
@ComponentScan(basePackages = {"com.jh.configure","com.jh.cavy.manage","com.jh.cavy.workflow"})
@EnableConfigurationProperties
public class BootConfigure {
}
