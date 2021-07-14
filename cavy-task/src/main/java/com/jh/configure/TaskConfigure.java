package com.jh.configure;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = {"com.jh.cavytask.mapper"})
@ComponentScan(basePackages = {"com.jh.cavytask"})
public class TaskConfigure {
}
