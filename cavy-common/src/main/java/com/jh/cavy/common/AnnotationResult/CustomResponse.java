package com.jh.cavy.common.AnnotationResult;

import java.lang.annotation.*;



/**
 * 自定义注解定制返回体
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CustomResponse {
}
