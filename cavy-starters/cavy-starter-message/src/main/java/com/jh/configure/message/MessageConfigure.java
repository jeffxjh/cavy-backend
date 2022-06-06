package com.jh.configure.message;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "message.enable",havingValue = "true")
@ComponentScan(basePackages = {"com.jh.cavy.message"})
public class MessageConfigure {
}
