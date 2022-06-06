package com.jh.configure.file;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.jh.cavy.file"})
@MapperScan(basePackages = {"com.jh.cavy.file.minio.mapper"})
public class FileConfigure {
}
