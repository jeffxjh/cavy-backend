package com.jh.cavy.message.rabbitmq.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jh.cavy.message.rabbitmq.core.mapper.MsgLogMapper;
import jakarta.annotation.Resource;
import lombok.Getter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.AnnotationUtils;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 仅负责扫描@MsgListen，创建队列/绑定，不缓存监听器、不收集队列名称
 */
@Getter
@MapperScan(basePackages = "com.jh.cavy.message.rabbitmq.core.mapper")
@Configuration
public class RabbitMqAutoConfig implements ApplicationContextAware {
    // 延迟注入，打破循环依赖
    @Resource
    @Lazy
    @Qualifier(RabbitMqExchangeConfig.MSG_EXCHANGE_BEAN)
    private TopicExchange msgExchange;
    private final Map<String, BaseListen> listenMap = new ConcurrentHashMap<>();
    // 自行收集队列名称，不再依赖RabbitMqAutoConfig
    private List<String> businessQueueNames = new ArrayList<>();



    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        BeanDefinitionRegistry registry = (BeanDefinitionRegistry) applicationContext.getAutowireCapableBeanFactory();

        // 扫描所有BaseListen实现类，创建队列/绑定
        Map<String, BaseListen> listenBeans = applicationContext.getBeansOfType(BaseListen.class);
        for (BaseListen listen : listenBeans.values()) {
            MsgListen msgListen = AnnotationUtils.findAnnotation(listen.getClass(), MsgListen.class);
            if (msgListen == null) continue;

            String listenKey = msgListen.value();
            String queueName = msgListen.queuePrefix() + listenKey;

            // 1. 注册队列Bean（避免重复注册）
            String queueBeanName = listenKey + "Queue";
            if (!registry.containsBeanDefinition(queueBeanName)) {
                BeanDefinition queueDefinition = BeanDefinitionBuilder
                                                         .genericBeanDefinition(Queue.class, () -> QueueBuilder.durable(queueName).build())
                                                         .setScope(BeanDefinition.SCOPE_SINGLETON)
                                                         .getBeanDefinition();
                registry.registerBeanDefinition(queueBeanName, queueDefinition);
            }

            // 2. 注册绑定Bean（避免重复注册）
            String bindingBeanName = listenKey + "Binding";
            if (!registry.containsBeanDefinition(bindingBeanName)) {
                BeanDefinition bindingDefinition = BeanDefinitionBuilder
                                                           .genericBeanDefinition(Binding.class, () ->
                                                           {
                                                               Queue queue = applicationContext.getBean(queueBeanName, Queue.class);
                                                               return BindingBuilder.bind(queue).to(msgExchange).with(listenKey);
                                                           })
                                                           .setScope(BeanDefinition.SCOPE_SINGLETON)
                                                           .getBeanDefinition();
                registry.registerBeanDefinition(bindingBeanName, bindingDefinition);
            }
        }
        // 1. 扫描所有BaseListen，缓存监听器+收集队列名称
        Map<String, BaseListen> beansOfType = applicationContext.getBeansOfType(BaseListen.class);
        for (BaseListen listen : beansOfType.values()) {
            MsgListen msgListen = AnnotationUtils.findAnnotation(listen.getClass(), MsgListen.class);
            if (msgListen == null) continue;

            // 缓存监听器
            String listenKey = msgListen.value();
            listenMap.put(listenKey, listen);

            // 收集队列名称（与RabbitMqAutoConfig逻辑一致）
            String queueName = msgListen.queuePrefix() + listenKey;
            businessQueueNames.add(queueName);
        }
        // 去重
        businessQueueNames = new ArrayList<>(new LinkedHashSet<>(businessQueueNames));
    }
}