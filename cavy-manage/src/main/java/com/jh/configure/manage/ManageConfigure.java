package com.jh.configure.manage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.jh.cavy"})
@MapperScan(basePackages = {"com.jh.cavy.manage.mapper"})
public class ManageConfigure {
}
