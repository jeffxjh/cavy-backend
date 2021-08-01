package com.jh.configure;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages ="com.jh.cavymanage.elasticsearch")
@MapperScan(basePackages = {"com.jh.cavymanage.mapper"})
@ComponentScan(basePackages = {"com.jh.cavymanage"})
public class ManageConfigure {
}
