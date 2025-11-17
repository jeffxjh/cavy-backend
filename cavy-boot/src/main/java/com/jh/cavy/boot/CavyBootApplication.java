package com.jh.cavy.boot;

import com.jh.cavy.workflow.api.service.WorkflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;


@Configuration
//@EnableDiscoveryClient
//@EnableLimit
//@EnableFeignClients("com.jh.cavy.workflow")
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.jh.configure"})
@RestController
public class CavyBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(CavyBootApplication.class, args);
    }

    @Autowired
    private WorkflowService workflowService;

    @GetMapping("/test")
    public void test() {
        workflowService.testget("id","name");
        workflowService.testpost(new HashMap<>(){{put("id","1");}});
        System.out.println("test-----------------------------------------------------");
    }
}
