package com.jh.cavy.message.rabbitmq.core;

import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 仅声明核心交换机，无其他依赖，避免循环
 */
@Configuration
public class RabbitMqExchangeConfig {
    public static final String MSG_QUEUE = "msg.listener.";
    public static final String MSG_EXCHANGE = "msg.exchange";
    public static final String MSG_EXCHANGE_BEAN = "msgExchangeBeanName";

    @Bean(MSG_EXCHANGE_BEAN)
    public TopicExchange defaultExchange() {
        return ExchangeBuilder.topicExchange(MSG_EXCHANGE)
                       .durable(true)
                       .build();
    }
}