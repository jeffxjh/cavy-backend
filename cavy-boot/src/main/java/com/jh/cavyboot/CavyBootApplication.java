package com.jh.cavyboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;



@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.jh.configure"})
public class CavyBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(CavyBootApplication.class, args);
    }

}
