package com.jh.cavy.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;


@Configuration
//@EnableDiscoveryClient
//@EnableLimit
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.jh.configure"})
public class CavyBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(CavyBootApplication.class, args);
    }

    @GetMapping("/test")
    public void test() {
        System.out.println("test-----------------------------------------------------");
    }
}
