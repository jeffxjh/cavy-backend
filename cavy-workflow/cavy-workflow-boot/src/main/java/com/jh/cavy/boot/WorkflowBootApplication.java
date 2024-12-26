package com.jh.cavy.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
//@EnableDiscoveryClient
//@EnableLimit
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.jh.configure"})
public class WorkflowBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(WorkflowBootApplication.class, args);
    }
}
