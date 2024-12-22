package com.jh.cavy.feign;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 使用该注解表明是需要前端访问
 */
@Documented
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.SOURCE)
public @interface Access
{

    /**
     * 请求类型
     * 不填默认 POST
     * 取值参看
     *
     * @see org.springframework.http.HttpMethod
     */
    String method() default "POST";

    /**
     * 请求路径
     * 不填默认 方法名
     */
    String[] path() default {};

    String type();
}
