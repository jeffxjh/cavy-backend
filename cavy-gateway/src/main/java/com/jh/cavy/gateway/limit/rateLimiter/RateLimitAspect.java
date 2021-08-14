package com.jh.cavy.gateway.limit.rateLimiter;

import com.google.common.util.concurrent.RateLimiter;
import com.jh.cavy.cache.service.CacheService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 单机情况使用
 * 令牌桶算法的原理是系统会以一个恒定的速度往桶里放入令牌，
 * 而如果请求需要被处理，则需要先从桶里获取一个令牌，当
 * 桶里没有令牌可取时，则拒绝服务，
 * 令牌桶算法通过发放令牌，
 * 根据令牌的rate频率做请求频率限制，容量限制等
 * @author xujiahao
 * @date 15:50 2021/8/8
 */
@Component
@Scope
@Aspect
@SuppressWarnings("UnstableApiUsage")
public class RateLimitAspect {
    @Resource
    private CacheService cacheService;
    //每秒只发出3个令牌,内部采用令牌捅算法实现
    private static RateLimiter rateLimiter = RateLimiter.create(3.0);

    /**
     * 业务层切点
     */
    @Pointcut("@annotation(com.jh.cavy.gateway.limit.rateLimiter.RateLimit)")
    public void ServiceAspect() { }

    @Around("ServiceAspect()")
    public Object around(ProceedingJoinPoint joinPoint) {
        Object obj = null;
        try {
            //tryAcquire()是非阻塞, rateLimiter.acquire()是阻塞的
            if (rateLimiter.tryAcquire()) {
                obj = joinPoint.proceed();
            } else {
                //拒绝了请求（服务降级）
                obj = "The system is busy, please visit after a while";
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return obj;
    }
}
