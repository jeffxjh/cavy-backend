package com.jh.cavy.message.rabbitmq;

import com.jh.cavy.message.config.RabbitMqConfig;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class MqListen {

    @RabbitListener(queuesToDeclare = @Queue(RabbitMqConfig.DIRECT_QUEUE))
    public void receiverMq(String msg, Channel channel, Message message) {
        try {
            long deliveryTag = message.getMessageProperties().getDeliveryTag();
            log.info("队列接收到direct消息:{}", msg);
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @RabbitListener(bindings = {
            @QueueBinding(value = @Queue(value = RabbitMqConfig.TOPIC_QUEUE_ONE, durable = "true"),
                    exchange = @Exchange(value = RabbitMqConfig.TOPIC_EXCHANGE, type = ExchangeTypes.TOPIC),
                    key = RabbitMqConfig.TOPIC_ROUTING_KEY_ONE)})
    public void receiverMqExchange(String msg, Channel channel, Message message) throws IOException {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            log.info("接收到{} 交换机，{} 队列，{} key,消息:{}", RabbitMqConfig.TOPIC_EXCHANGE, RabbitMqConfig.TOPIC_QUEUE_ONE, RabbitMqConfig.TOPIC_ROUTING_KEY_ONE, msg);
            //发生异常
            int i = 1 / 0;
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            channel.basicReject(deliveryTag, false);
            //channel.basicNack(deliveryTag, false, false);
            log.error("接收消息失败,进入死信队列");
        }
    }

    @RabbitListener(bindings = {
            @QueueBinding(value = @Queue(value = RabbitMqConfig.TOPIC_QUEUE_TWO, durable = "true"),
                    exchange = @Exchange(value = RabbitMqConfig.TOPIC_EXCHANGE, type = ExchangeTypes.TOPIC),
                    key = RabbitMqConfig.TOPIC_ROUTING_KEY_TWO)})
    public void receiverMqExchangeTwo(String msg, Channel channel, Message message) throws IOException {

        log.info("接收到{} 交换机，{} 队列，{} key,消息:{}", RabbitMqConfig.TOPIC_EXCHANGE, RabbitMqConfig.TOPIC_QUEUE_TWO, RabbitMqConfig.TOPIC_ROUTING_KEY_TWO, msg);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    @RabbitListener(queues = RabbitMqConfig.FANOUT_QUEUE_ONE)
    public void receiverMqFanoutOne(String msg, Channel channel, Message message) {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            log.info("队列{}接收到广播消息:{}", RabbitMqConfig.FANOUT_QUEUE_ONE, msg);
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            e.printStackTrace();
            //多次失败重新放回会导致队列堵塞或死循环问题 丢弃这条消息
            //channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
            log.error("接收消息失败");
        }
    }

    @RabbitListener(queues = RabbitMqConfig.FANOUT_QUEUE_TWO)
    public void receiverMqFanoutTwo(String msg) {
        log.info("队列{}接收到广播消息:{}", RabbitMqConfig.FANOUT_QUEUE_TWO, msg);
    }

    //如果不存在，自动创建队列和交换器并且绑定
    @RabbitListener(bindings = {
            @QueueBinding(value = @Queue(value = RabbitMqConfig.DEAD_LETTER_QUEUE, durable = "true"),
                    exchange = @Exchange(value = RabbitMqConfig.DEAD_LETTER_EXCHANGE, type = ExchangeTypes.TOPIC),
                    key = RabbitMqConfig.DEAD_LETTER_ROUTING_KEY)})
    public void receiverDeadLetter(String msg, Channel channel, Message message) throws IOException {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            log.info("死信队列接收到{} 交换机，{} 队列，{} key,消息:{}", RabbitMqConfig.DEAD_LETTER_EXCHANGE, RabbitMqConfig.DEAD_LETTER_QUEUE, RabbitMqConfig.DEAD_LETTER_ROUTING_KEY, msg);
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            e.printStackTrace();
            // 解决方案，剔除此消息，然后记录到db中去补偿
            // 第三个参数requeue true为重新入队，如果异常不消除会进入死循环
            //channel.basicNack(deliveryTag, false, false);
            //todo 写入数据库后续补偿
            //拒绝消息
            channel.basicReject(deliveryTag, true);
        }
    }
}
