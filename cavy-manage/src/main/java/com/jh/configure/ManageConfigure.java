package com.jh.configure;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = {"com.jh.cavymanage.mapper"})
@ComponentScan(basePackages = {"com.jh.cavymanage"})
public class ManageConfigure {
}
