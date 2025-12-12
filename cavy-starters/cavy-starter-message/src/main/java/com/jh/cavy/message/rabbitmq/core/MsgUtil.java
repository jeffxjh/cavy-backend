package com.jh.cavy.message.rabbitmq.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jh.cavy.message.rabbitmq.core.mapper.MsgLogMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 消息发送工具类
 */
@Component
@RequiredArgsConstructor
public class MsgUtil {

    private final RabbitTemplate rabbitTemplate;
    private final MsgLogMapper msgLogMapper;

    /**
     * 发送消息
     * @param key 监听key（对应BaseListen的getListenKey）
     * @param data 业务数据
     * @param isDurable 是否持久化（持久化则写入msg_log表）
     */
    @SneakyThrows
    public void sendMsg(String key, Object data, boolean isDurable) {
        // 1. 序列化消息数据
        String msgData = new ObjectMapper().writeValueAsString(data);

        // 2. 持久化处理：写入日志表
        Long logId;
        if (isDurable) {
            MsgLog msgLog = new MsgLog();
            msgLog.setMsgKey(key);
            msgLog.setMsgData(msgData);
            msgLog.setCreateTime(new Date());
            msgLog.setIsConsumed(0); // 未消费
            msgLog.setNeedResend(0); // 不需要重发
            msgLog.setResendCount(0);
            msgLog.setIsDurable(1); // 持久化
            msgLogMapper.insert(msgLog);
            logId = msgLog.getId();
        }
        else {
            logId = null;
        }

        // 3. 发送消息到RabbitMQ
        try {
            rabbitTemplate.convertAndSend(
                    RabbitMqExchangeConfig.MSG_EXCHANGE,
                    key,
                    msgData,
                    message -> {
                        // 持久化消息（队列已持久化，消息需标记为持久化）
                        if (isDurable) {
                            message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                            // 携带日志ID，便于消费时更新日志
                            message.getMessageProperties().setHeader("msgLogId", logId);
                        }
                        return message;
                    }
            );
        } catch (Exception e) {
            // 发送失败：更新日志为需要重发
            if (isDurable && logId != null) {
                MsgLog updateLog = new MsgLog();
                updateLog.setId(logId);
                updateLog.setNeedResend(1); // 需要重发
                msgLogMapper.updateById(updateLog);
            }
            throw new RuntimeException("消息发送失败：" + e.getMessage(), e);
        }
    }
}
