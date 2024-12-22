package com.jh.cavy.feign;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自动生成RestController和OpenFeignClient
 *
 * @author jeffx
 * @date 2024/10/27
 */
@Documented
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.SOURCE)
public @interface RestFeignClient {
    /**
     * 是否微服务 是否生成@FeignClient
     */
    boolean isMicro() default false;

    /**
     * @RequestPath @FeignClient 路径
     */
    String path();

    /**
     * @FeignClient 微服务名词
     */
    String name();
}
