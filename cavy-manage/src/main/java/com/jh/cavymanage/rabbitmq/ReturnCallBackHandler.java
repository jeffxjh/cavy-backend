package com.jh.cavymanage.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * 通过实现 ReturnCallback 接口，启动消息失败返回，比如路由不到队列时触发回调
 */
@Slf4j
public class ReturnCallBackHandler implements RabbitTemplate.ReturnCallback {

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.info("消息主体：" + message);
        log.info("应答码：" + replyCode);
        log.info("原因描述：" + replyText);
        log.info("交换机：" + exchange);
        log.info("消息使用的路由键：" + routingKey);
    }
}