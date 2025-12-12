package com.jh.cavy.message.rabbitmq.core;

import org.springframework.core.annotation.AnnotationUtils;

/**
 * 消息监听基础接口
 * 所有消息消费者需实现该接口
 */
public interface BaseListen {
    /**
     * 消息消费方法
     */
    void onMessage(Object data, String consumerUser);

    /**
     * 自动从@MsgListen注解获取key，无需业务类实现
     */
    default String getListenKey() {
        MsgListen msgListen = AnnotationUtils.findAnnotation(this.getClass(), MsgListen.class);
        if (msgListen == null) {
            throw new RuntimeException("当前类未添加@MsgListen注解：" + this.getClass().getName());
        }
        return msgListen.value();
    }
}