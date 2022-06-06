package com.jh.cavy.cache.config;

import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;


@Slf4j
@Component
public class RedisLock {

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final Long SUCCESS = 1L;

    private final ThreadLocal<String> threadLocal = new ThreadLocal<>();

    @Autowired
    @Qualifier("lockluascript")
    private DefaultRedisScript<Long> lockRedisScript;

    @Autowired
    @Qualifier("unlockluascript")
    private DefaultRedisScript<Long> unLockRedisScript;

    /**
     * 获取锁
     *
     * @param lockKey    redis的key
     * @param expireTime redis的key 的过期时间 防止死锁，导致其他请求无法正常执行业务
     * @return
     */
    public boolean lock(String lockKey, int expireTime) {
        try {
            String value = IdUtil.fastSimpleUUID();
            threadLocal.set(value);
            // 对非string类型的序列化
            Object result = redisTemplate.execute(lockRedisScript, Collections.singletonList(lockKey), value, String.valueOf(expireTime));
            return SUCCESS.equals(result);
        } catch (Exception e) {
            // 任何异常都是加锁失败
            log.error("获取锁异常-----------------> e={}", e.getMessage());
            threadLocal.remove();
            return false;
        }

    }

    /**
     * @param lockKey     key
     * @param expireTime  过期时间(秒)
     * @param retryTimes  重试次数
     * @param sleepMillis 每次重试间隔时间(毫秒)
     * @return boolean
     * @Title: tryLock
     * @Description: 获取锁
     * @author He LongYun
     * @date 2020年5月18日下午3:19:24
     */
    public boolean tryLock(String lockKey, int expireTime, int retryTimes, Long sleepMillis) {
        boolean result = lock(lockKey, expireTime);
        while ((!result) && retryTimes-- > 0) {
            try {
                log.debug("每次重试时间间隔-----> retryTimes={} ", retryTimes);
                Thread.sleep(sleepMillis);
            } catch (InterruptedException e) {
                return false;
            }
            result = lock(lockKey, expireTime);
        }
        return result;
    }

    /**
     * 释放锁
     *
     * @param lockKey redis的key
     * @return
     */
    public boolean unlock(String lockKey) {
        if (threadLocal.get() == null) {
            return true;
        }
        try {
            Object result = redisTemplate.execute(unLockRedisScript, Collections.singletonList(lockKey), threadLocal.get());
            if (SUCCESS.equals(result)) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        } finally {
            threadLocal.remove();
        }
    }

}