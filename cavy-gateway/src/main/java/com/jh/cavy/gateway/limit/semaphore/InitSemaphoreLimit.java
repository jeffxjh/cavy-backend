package com.jh.cavy.gateway.limit.semaphore;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.Semaphore;
@Slf4j
@Component
public class InitSemaphoreLimit implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> beanMap = applicationContext.getBeansWithAnnotation(RestController.class);
        beanMap.forEach((k, v) -> {
            Class<?> controllerClass = v.getClass();
            log.info(controllerClass.toString());
            log.info(controllerClass.getSuperclass().toString());
            //获取所有声明的方法
            Method[] allMethods = controllerClass.getSuperclass().getDeclaredMethods();
            for (Method method : allMethods) {
                log.info(method.getName());
                //判断方法是否使用了限流注解
                if (method.isAnnotationPresent(SemaphoreLimit.class)) {
                    //获取配置的限流量,实际值可以动态获取,配置key,根据key从配置文件获取
                    int value = method.getAnnotation(SemaphoreLimit.class).value();
                    String key = method.getAnnotation(SemaphoreLimit.class).limitKey();
                    log.info("limitKey:{},许可证数是{}",key,value);
                    //key作为key.value为具体限流量,传递到切面的map中
                    SemaphoreLimitAspect.semaphoreMap.put(key, new Semaphore(value));
                }
            }
        });
    }
}