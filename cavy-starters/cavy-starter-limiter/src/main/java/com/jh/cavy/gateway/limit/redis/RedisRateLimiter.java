package com.jh.cavy.gateway.limit.redis;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 限制一段时间内的访问次数
 * @author xujiahao
 * @date 17:20 2021/8/8
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisRateLimiter {

    /**
     * 限流唯一标识
     * @return
     */
    String key() default "rate.limit:";

    /**
     * 限流时间
     * @return
     */
    int time() default 1;

    /**
     * 限流次数
     * @return
     */
    int count() default 100;

    /**
     *是否限制IP,默认 否
     * @return
     */
    boolean restrictionsIp() default false;
}

