package com.jh.cavy.message.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


/*
* 修改了交换机配置 需要在rabbitmq服务端把交换机和队列删除，否则配置不生效，因为交换机没有重建
* */
@Configuration
public class RabbitMqConfig {
    //消费者直接通过key与交换机绑定
    public final static String DIRECT_QUEUE = "directQueue";
    //消费者直接通过模糊匹配key与交换机绑定
    public final static String TOPIC_QUEUE_ONE = "topic_queue_one";
    public final static String TOPIC_QUEUE_TWO = "topic_queue_two";
    //消费者直接与交换机绑定
    public final static String FANOUT_QUEUE_ONE = "fanout_queue_one";
    public final static String FANOUT_QUEUE_TWO = "fanout_queue_two";

    public final static String DIRECT_EXCHANGE = "direct_exchange";
    public final static String TOPIC_EXCHANGE = "topic_exchange";
    public final static String FANOUT_EXCHANGE = "fanout_exchange";

    public final static String TOPIC_ROUTING_KEY_ONE = "*.key.*";
    public final static String TOPIC_ROUTING_KEY_TWO = "*.key";


    public static final String DEAD_LETTER_EXCHANGE = "dead_letter_exchange";
    public static final String DEAD_LETTER_ROUTING_KEY = "dlr_key";
    public static final String DEAD_LETTER_QUEUE = "dead_letter_queue";

    //  direct模式队列
    @Bean
    public Queue directQueue() {
        return new Queue(DIRECT_QUEUE, true);
    }

    //  topic 订阅者模式队列
    @Bean
    public Queue topicQueueOne() {
        //创建死信队列的组成成分map，用于存放组成成分的相关成员
        Map<String, Object> args = new <String, Object>HashMap(2);
        //设死信交换机
        args.put("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE);
        args.put("x-message-ttl", 3000L);
        //死信队列的路由
        args.put("x-dead-letter-routing-key", DEAD_LETTER_ROUTING_KEY);
        return new Queue(TOPIC_ROUTING_KEY_ONE, true, false, false, args);
    }

    @Bean
    public Queue topicQueueTwo() {
        return new Queue(TOPIC_QUEUE_TWO, true);
    }

    //  fanout 广播者模式队列
    @Bean
    public Queue fanoutQueueOne() {
        return new Queue(FANOUT_QUEUE_ONE, true);
    }

    @Bean
    public Queue fanoutQueueTwo() {
        return new Queue(FANOUT_QUEUE_TWO, true);
    }

    //  topic 交换器
    @Bean
    public TopicExchange topExchange() {
        return new TopicExchange(TOPIC_EXCHANGE, true, false);
    }

    //  fanout 交换器
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(FANOUT_EXCHANGE, true, false);
    }

    //  direct 交换器
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(DIRECT_EXCHANGE, true, false);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(directQueue()).to(directExchange()).with(DIRECT_QUEUE);
    }

    //   订阅者模式绑定
    @Bean
    public Binding topExchangeBingingOne() {
        return BindingBuilder.bind(topicQueueOne()).to(topExchange()).with(TOPIC_ROUTING_KEY_ONE);
    }

    @Bean
    public Binding topicExchangeBingingTwo() {
        return BindingBuilder.bind(topicQueueTwo()).to(topExchange()).with(TOPIC_ROUTING_KEY_TWO);
    }

    //   广播模式绑定
    @Bean
    public Binding fanoutExchangeBingingOne() {
        return BindingBuilder.bind(fanoutQueueOne()).to(fanoutExchange());
    }

    @Bean
    public Binding fanoutExchangeBingingTwo() {
        return BindingBuilder.bind(fanoutQueueTwo()).to(fanoutExchange());
    }

    /**
     * 死信队列跟交换机类型没有关系 不一定为directExchange  不影响该类型交换机的特性.
     */
    @Bean("deadLetterExchange")
    public TopicExchange deadLetterExchange() {
        return ExchangeBuilder.topicExchange(DEAD_LETTER_EXCHANGE).durable(true).build();
    }

    @Bean("deadLetterQueue")
    public Queue deadLetterQueue() {
        return new Queue(DEAD_LETTER_QUEUE, true);
    }

    /**
     * 死信队列绑定到死信交换器上.
     * @return the binding
     */
    @Bean
    public Binding deadLetterBinding() {
        return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange()).with(DEAD_LETTER_ROUTING_KEY);
    }


}
