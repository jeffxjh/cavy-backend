package com.jh.configure.favour;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.jh.cavy.favour"})
@MapperScan(basePackages = {"com.jh.cavy.favour.mapper"})
public class FavourConfigure {
}
