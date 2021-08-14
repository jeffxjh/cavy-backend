package com.jh.configure.common;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(
        basePackages = { "com.jh.cavy.common" }
)
public class CommonConfigure {
}
