package com.jh.cavy.groovy;

import com.jh.cavy.groovy.service.ScriptInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;

@EnableAutoConfiguration
public class GroovyApplication implements ApplicationRunner {
    public static void main(String[] args) {
        SpringApplication.run(GroovyApplication.class, args);
    }

    @Autowired
    ScriptInterface scriptInterface;





    @Override
    public void run(ApplicationArguments args) throws Exception {
        scriptInterface.exec();
    }
}
