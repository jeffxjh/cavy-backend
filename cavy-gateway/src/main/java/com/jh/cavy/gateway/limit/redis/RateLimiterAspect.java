package com.jh.cavy.gateway.limit.redis;

import com.jh.cavy.cache.service.CacheService;
import com.jh.cavy.common.exception.RateLimiterException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Aspect
@Slf4j
@Component
public class RateLimiterAspect {
    @Resource
    private CacheService cacheService;

    private static final ThreadLocal<String> ipThreadLocal = new ThreadLocal<>();

    @Qualifier("gatewaylimitLua")
    @Autowired
    private DefaultRedisScript<Number> redisScript;

    @Around("@annotation(com.jh.cavy.gateway.limit.redis.RedisRateLimiter)")
    public Object interceptor(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            Class<?> targetClass = method.getDeclaringClass();
            RedisRateLimiter rateLimit = method.getAnnotation(RedisRateLimiter.class);

            if (rateLimit != null) {
                HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
                boolean restrictionsIp = rateLimit.restrictionsIp();
                if (restrictionsIp) {
                    ipThreadLocal.set(getIpAddr(request));
                }
                StringBuilder stringBuffer = new StringBuilder();
                stringBuffer.append(rateLimit.key());
                if (StringUtils.isNotBlank(ipThreadLocal.get())) {
                    stringBuffer.append(ipThreadLocal.get()).append("-");
                }
                stringBuffer.append("-").append(targetClass.getName()).append("- ").append(method.getName());

                List<String> keys = Collections.singletonList(stringBuffer.toString());

                Number number = cacheService.execute(redisScript, keys, rateLimit.count(), rateLimit.time());

                if (number != null && number.intValue() != 0 && number.intValue() <= rateLimit.count()) {
                    log.info("限流时间段内访问第：{} 次", number.toString());
                    return joinPoint.proceed();
                } else {
                    assert number != null;
                    log.error("已经到设置限流次数,当前次数:{}", number.toString());
                    throw new RateLimiterException("服务器繁忙,请稍后再试");
                }
            } else {
                return joinPoint.proceed();
            }
        } finally {
            ipThreadLocal.remove();
        }
    }

    public static String getIpAddr(HttpServletRequest request) {
        String ipAddress = null;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null && ipAddress.length() > 15) {
                // "***.***.***.***".length()= 15
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress = "";
        }
        return ipAddress;
    }
}