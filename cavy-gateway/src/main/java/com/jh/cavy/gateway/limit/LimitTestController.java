package com.jh.cavy.gateway.limit;

import com.jh.cavy.cache.config.RedisLock;
import com.jh.cavy.gateway.limit.RateLimiter.RateLimit;
import com.jh.cavy.gateway.limit.Semaphore.SemaphoreLimit;
import com.jh.cavy.gateway.limit.redis.RedisRateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;
@Slf4j
@RestController
public class LimitTestController {

    @RateLimit
    @RequestMapping("/ratelimit")
    public String ratelimit() throws Exception {
        //假设业务处理了1秒
        TimeUnit.SECONDS.sleep(1);
        return "success";
    }

    /**
     * 设置limitKey=SemaphoreKey,并且许可证只有3个
     */
    @SemaphoreLimit(limitKey = "SemaphoreKey", value = 3)
    @RequestMapping("/SemaphoreLimit")
    public String SemaphoreLimit() throws Exception {
        //假设业务处理了1秒
        TimeUnit.SECONDS.sleep(1);
        return "success";
    }

    @RedisRateLimiter(count = 10, time = 50, restrictionsIp = true)
    @RequestMapping("/redisLimit")
    public String redisLimit() throws Exception {
        //假设业务处理了1秒
        TimeUnit.SECONDS.sleep(2);
        return "success";
    }

    @Autowired
    private RedisLock redisLock;
    @RequestMapping(value = "/lock")
    public void lock() {
        String lockKey = "my:redis:lock:" + System.currentTimeMillis();
        boolean lock = false;
        try {
            lock = redisLock.lock(lockKey, 30);
            Thread.sleep(2000);
            log.info("加锁----> lockKey={},lock={}", lockKey, lock);
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            if (lock) {
                // boolean unlock = redisLock.unlock(lockKey);
                // log.info("解锁----> lockKey={},unlock={}", lockKey, unlock);
            }
        }
    }
}