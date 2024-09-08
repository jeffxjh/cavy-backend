package com.jh.cavy.message.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * 通过实现 ReturnCallback 接口，启动消息失败返回，比如路由不到队列时触发回调
 */
@Slf4j
public class ReturnCallBackHandler implements RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnsCallback  {

    @Override
    public void returnedMessage(ReturnedMessage returnedMessage) {
        log.info("消息主体：" + returnedMessage.getMessage());
        log.info("应答码：" + returnedMessage.getExchange());
        log.info("原因描述：" + returnedMessage.getReplyText());
        log.info("交换机：" + returnedMessage.getExchange());
        log.info("消息使用的路由键：" + returnedMessage.getRoutingKey());
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        String id = correlationData.getId();//消息唯一标识
        if (b) {
            System.out.println("消息到达交换机");
        } else {
            System.out.println("消息未到达交换机");
        }

    }
}