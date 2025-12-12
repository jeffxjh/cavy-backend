package com.jh.cavy.message.rabbitmq.core;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 消息监听器注解：标记BaseListen实现类，自动创建队列/绑定
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component // 让Spring扫描到该类
public @interface MsgListen {
    /**
     * 监听routerKey（对应消息路由键）
     */
    String value();

    /**
     * 交换机名称前缀（默认复用监听key）
     */
    String exchangePrefix() default RabbitMqExchangeConfig.MSG_EXCHANGE;

    /**
     * 队列名称前缀（默认复用监听key）
     */
    String queuePrefix() default RabbitMqExchangeConfig.MSG_QUEUE;

    /**
     * 队列是否持久化（默认true）
     */
    boolean durable() default true;
}