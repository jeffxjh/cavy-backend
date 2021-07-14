package com.jh.configure;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = {"com.jh.cavyfile.minio.mapper"})
@ComponentScan(basePackages = {"com.jh.cavyfile"})
public class FileConfigure {
}
