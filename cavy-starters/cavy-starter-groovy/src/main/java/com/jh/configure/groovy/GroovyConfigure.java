package com.jh.configure.groovy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(value = "com.jh.cavy.groovy.mapper")
@ComponentScan(basePackages = {"com.jh.cavy.groovy"})
public class GroovyConfigure {
}
