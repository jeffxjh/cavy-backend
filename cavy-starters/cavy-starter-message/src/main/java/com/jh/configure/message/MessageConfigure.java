package com.jh.configure.message;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@AutoConfiguration  // 新注解，替代 @Configuration
@ConditionalOnProperty(name = "message.enable",havingValue = "true",matchIfMissing = true)
@ComponentScan(basePackages = {"com.jh.cavy.message"})
public class MessageConfigure {
}
