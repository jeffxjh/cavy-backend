package com.jh.cavyboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;



@Configuration
@EnableDiscoveryClient
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.jh.configure"})
public class CavyBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(CavyBootApplication.class, args);
    }

}
