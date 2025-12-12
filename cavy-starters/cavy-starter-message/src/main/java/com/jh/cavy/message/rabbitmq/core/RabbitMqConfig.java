// package com.jh.cavy.message.rabbitmq.core;
//
// import lombok.experimental.FieldNameConstants;
// import org.mybatis.spring.annotation.MapperScan;
// import org.springframework.amqp.core.*;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
//
// /**
//  * RabbitMQ配置：声明交换机、队列绑定规则
//  * 队列命名规则：listen.key-queue
//  * 交换机：统一使用topic交换机
//  */
// @Configuration(value = "coreRabbitMqConfig")
// @MapperScan(basePackages = "com.jh.cavy.message.rabbitmq.core.mapper")
// @FieldNameConstants
// public class RabbitMqConfig {
//
//     /**
//      * 全局消息交换机
//      */
//     public static final String MSG_EXCHANGE = "msg.exchange";
//     public static final String MSG_ROUTING_KEY = "msg.routingKey";
//     public static final String DEFAULT_MSG_EXCHANGE_BEAN_NAME = "defaultMsgExchangeBeanName";
//     public static final String DEFAULT_MSG_QUEUE_BEAN_NAME = "defaultMsgQueueBeanName";
//     public static final String DEFAULT_MSG_BINDING_BEAN_NAME = "defaultMsgBindingBeanName";
//
//     /**
//      * 声明topic交换机
//      */
//     @Bean(DEFAULT_MSG_EXCHANGE_BEAN_NAME)
//     public Exchange msgExchange() {
//         return ExchangeBuilder.topicExchange(MSG_EXCHANGE)
//                        .durable(true) // 交换机持久化
//                        .build();
//     }
//
//     /**
//      * 为每个BaseListen创建队列并绑定到交换机（可通过扫描实现动态绑定，此处简化）
//      *
//      * @param listen 消息监听器
//      * @return 队列
//      */
//     @Bean(DEFAULT_MSG_QUEUE_BEAN_NAME)
//     public Queue listenQueue(@Autowired(required = false) BaseListen listen) {
//         String routingKey = MSG_ROUTING_KEY;
//         if (listen != null) {
//             routingKey = listen.getListenKey();
//         }
//         String queueName = routingKey + ".queue";
//         return QueueBuilder.durable(queueName) // 队列持久化
//                        .build();
//     }
//
//     /**
//      * 绑定队列到交换机
//      *
//      * @param listen   消息监听器
//      * @param queue    队列
//      * @param exchange 交换机
//      * @return 绑定关系
//      */
//     @Bean(DEFAULT_MSG_BINDING_BEAN_NAME)
//     public Binding listenBinding(@Autowired(required = false) BaseListen listen,
//                                  @Autowired(required = false) Queue defaultMsgQueueBeanName,
//                                  @Autowired(required = false) Exchange defaultMsgExchangeBeanName) {
//         return BindingBuilder.bind(defaultMsgQueueBeanName)
//                        .to(defaultMsgExchangeBeanName)
//                        .with(listen.getListenKey()) // 路由键=监听key
//                        .noargs();
//     }
// }