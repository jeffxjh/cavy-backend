package com.jh.cavy.gateway.limit.semaphore;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SemaphoreLimit {

    String limitKey() default ""; //限流的方法名

    int value()  default 0;  //发放的许可证数量

}
