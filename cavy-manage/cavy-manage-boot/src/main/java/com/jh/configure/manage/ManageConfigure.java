package com.jh.configure.manage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@ComponentScan(basePackages = {"com.jh.cavy.manage"})
@MapperScan(basePackages = {"com.jh.cavy.manage.mapper"})
@EnableScheduling
@EnableCaching
@EnableConfigurationProperties
public class ManageConfigure {
}
