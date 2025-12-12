package com.jh.configure.boot;

import com.jh.cavy.message.rabbitmq.core.BaseListen;
import com.jh.cavy.message.rabbitmq.core.MsgListen;
import org.springframework.stereotype.Component;

/**
 * 订单消息监听器示例
 */
/**
 * 订单消息监听器：仅需添加@MsgListen注解，无任何配置代码
 */
@MsgListen("order.msg")
@Component
public class OrderListen implements BaseListen {

    @Override
    public void onMessage(Object data, String consumerUser) {
        // 纯业务逻辑：解析订单、处理业务
        System.out.println("订单监听器消费消息：" + data + "，消费用户：" + consumerUser);
    }

    @Override
    public String getListenKey() {
        // 与@MsgListen的value保持一致（也可直接返回注解值，见下文优化）
        return "order.msg";
    }
}