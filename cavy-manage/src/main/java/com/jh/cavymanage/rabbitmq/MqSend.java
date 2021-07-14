package com.jh.cavymanage.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalTime;

@Component
@Slf4j
public class MqSend {
    @Resource
    private RabbitTemplate rabbitTemplate;

    public void sendMsg(String exchange,String routingKey, String msg) {
        rabbitTemplate.convertAndSend(exchange,routingKey, msg);
        log.info("发送消息:{} 时间:{}", routingKey, LocalTime.now());
    }

    public void sendMsgCallBack(String callBackId,String exchange,String routingKey, String msg) {
        //回调id
        CorrelationData cId = new CorrelationData(callBackId);
        Object object = rabbitTemplate.convertSendAndReceive(exchange, routingKey, msg, cId);
        log.info("发送消息:{},object:{}", msg, object);
    }

}