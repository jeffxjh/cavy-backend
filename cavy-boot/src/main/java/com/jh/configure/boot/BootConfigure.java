package com.jh.configure.boot;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@EnableCaching
@Configuration
@ComponentScan(basePackages = {"com.jh.cavy.boot"})
@EnableConfigurationProperties
public class BootConfigure {
}
