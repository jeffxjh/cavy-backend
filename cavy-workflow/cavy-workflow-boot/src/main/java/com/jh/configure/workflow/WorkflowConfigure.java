package com.jh.configure.workflow;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableCaching
@Configuration
@ComponentScan(basePackages = {"com.jh.cavy.workflow"})
@EnableConfigurationProperties
public class WorkflowConfigure {
}
