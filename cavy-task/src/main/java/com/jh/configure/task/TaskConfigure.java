package com.jh.configure.task;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = {"com.jh.cavy.task.mapper"})
public class TaskConfigure {
}
