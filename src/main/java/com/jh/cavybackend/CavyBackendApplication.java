package com.jh.cavybackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//@MapperScan(basePackages = {"com.jh.cavybackend.mapper"})
@SpringBootApplication
public class CavyBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(CavyBackendApplication.class, args);
    }

}
