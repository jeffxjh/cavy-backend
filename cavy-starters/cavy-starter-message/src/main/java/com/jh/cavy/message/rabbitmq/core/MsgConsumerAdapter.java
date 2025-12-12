package com.jh.cavy.message.rabbitmq.core;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jh.cavy.message.rabbitmq.core.mapper.MsgLogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class MsgConsumerAdapter {

    private final Map<String, BaseListen> listenMap = new ConcurrentHashMap<>();
    private final MsgLogMapper msgLogMapper;
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 监听自身收集的队列列表，彻底解耦RabbitMqAutoConfig
     */
    @RabbitListener(queues = "#{@rabbitMqAutoConfig.businessQueueNames}")
    public void consume(
            String msgData,
            @Header(name = "msgLogId", required = false) Long msgLogId,
            @Header(name = "amqp_receivedRoutingKey") String routingKey
    )
    {
        RabbitMqAutoConfig rabbitMqAutoConfig = SpringUtil.getBean(RabbitMqAutoConfig.class);
        BaseListen listen = rabbitMqAutoConfig.getListenMap().get(routingKey);
        if (listen == null) {
            throw new RuntimeException("未找到监听器：" + routingKey);
        }

        String consumerUser = "system";
        try {
            // 修正：JSON反序列化（原代码convertValue错误，应使用readValue）
            Object data = objectMapper.readValue(msgData, Object.class);
            listen.onMessage(data, consumerUser);

            // 更新日志
            if (msgLogId != null) {
                MsgLog updateLog = new MsgLog();
                updateLog.setId(msgLogId);
                updateLog.setIsConsumed(1);
                updateLog.setConsumerTime(new Date());
                updateLog.setConsumerUser(consumerUser);
                updateLog.setNeedResend(0);
                msgLogMapper.updateById(updateLog);
            }
        }
        catch (Exception e) {
            if (msgLogId != null) {
                MsgLog updateLog = new MsgLog();
                updateLog.setId(msgLogId);
                updateLog.setNeedResend(1);
                MsgLog oldLog = msgLogMapper.selectById(msgLogId);
                updateLog.setResendCount(oldLog.getResendCount() + 1);
                msgLogMapper.updateById(updateLog);
            }
            throw new RuntimeException("消费失败：" + e.getMessage(), e);
        }
    }


}