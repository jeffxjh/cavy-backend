package com.jh.cavy.boot.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class AppAfterRun implements CommandLineRunner {
    @Autowired
    private ApplicationContext applicationContext  ;
    @Override
    public void run(String... args) throws Exception {
        String[] names = applicationContext.getBeanDefinitionNames();
        int index = 1 ;
        for (String name : names) {
            log.trace("{}: {}", index++ , name );
        }
        log.info("项目启动 容器注入javaBean:{}个.",names.length);
    }
}
