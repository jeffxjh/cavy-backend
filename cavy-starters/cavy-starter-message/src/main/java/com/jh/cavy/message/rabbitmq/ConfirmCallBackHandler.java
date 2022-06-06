package com.jh.cavy.message.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * 通过实现ConfirmCallBack接口，消息发送到交换器Exchange后触发回调。
 */
@Slf4j
public class ConfirmCallBackHandler implements RabbitTemplate.ConfirmCallback {

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        log.info("消息唯一标识："+correlationData);
        log.info("确认结果："+ack);
        if (!ack){
            log.info("失败原因："+cause);
        }
    }
}