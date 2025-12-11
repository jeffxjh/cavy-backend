package com.jh.cavy.cache.service.impl;


import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;
import com.github.benmanes.caffeine.cache.RemovalListener;
import com.jh.cavy.cache.service.CacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class CaffeineHandle implements CacheService {
    private final TimeUnit unit = TimeUnit.MINUTES;
    private final Cache<String, Object> caffeineCache;

    public CaffeineHandle() {
        caffeineCache = initCache();
    }

    /**
     * 初始化
     *
     * @return
     */
    private Cache<String, Object> initCache() {
        Caffeine<java.lang.Object, java.lang.Object> caffeine = Caffeine.newBuilder();
        //key的最大size
        caffeine.maximumSize(200);
        //expireAfterWrite全局时间淘汰策略
        long duration = 60 * 2L;
        caffeine.expireAfterWrite(duration, this.unit);
        caffeine.removalListener((RemovalListener<String, Object>)
                                         (key, value, removalCause) ->
                                                 log.debug("移除了key:{}  value:{} cause:{}", key, value, removalCause));
        return caffeine.build();
    }

    @Override
    public boolean expire(String key, long time) {
        return false;
    }

    @Override
    public long getExpire(String key) {
        return 0;
    }

    @Override
    public boolean hasKey(String key) {
        return caffeineCache.getIfPresent(key) != null;
    }

    @Override
    public void del(String... key) {
        for (String s : key) {
            caffeineCache.invalidate(s);
        }
    }

    @Override
    public Object get(String key) {
        return caffeineCache.getIfPresent(key);
    }

    @Override
    public boolean set(String key, Object value) {
        return false;
    }

    @Override
    public boolean set(String key, Object value, long time) {
        return false;
    }

    @Override
    public long incr(String key, long delta) {
        return 0;
    }

    @Override
    public long decr(String key, long delta) {
        return 0;
    }

    @Override
    public Object hget(String key, String item) {
        return caffeineCache.getIfPresent(key + item);
    }

    @Override
    public Map<Object, Object> hmget(String key) {
        return null;
    }

    @Override
    public boolean hmset(String key, Map<String, Object> map) {
        return false;
    }

    @Override
    public boolean hmset(String key, Map<String, Object> map, long time) {
        return false;
    }


    @Override
    public boolean hset(String key, String item, Object value) {
        caffeineCache.put(key + item, value);
        return true;
    }

    @Override
    public boolean hset(String key, String item, Object value, long time) {
        return hset(key, item, value);
    }

    @Override
    public void hdel(String key, Object... item) {

    }

    @Override
    public boolean hHasKey(String key, String item) {
        return false;
    }

    @Override
    public double hincr(String key, String item, double by) {
        return 0;
    }

    @Override
    public double hdecr(String key, String item, double by) {
        return 0;
    }

    @Override
    public Set<Object> sGet(String key) {
        return null;
    }

    @Override
    public boolean sHasKey(String key, Object value) {
        return false;
    }

    @Override
    public long sSet(String key, Object... values) {
        return 0;
    }

    @Override
    public long sSetAndTime(String key, long time, Object... values) {
        return 0;
    }

    @Override
    public long sGetSetSize(String key) {
        return 0;
    }

    @Override
    public long setRemove(String key, Object... values) {
        return 0;
    }

    @Override
    public List<Object> lGet(String key, long start, long end) {
        return null;
    }

    @Override
    public long lGetListSize(String key) {
        return 0;
    }

    @Override
    public Object lGetIndex(String key, long index) {
        return null;
    }

    @Override
    public boolean lSet(String key, Object value) {
        return false;
    }

    @Override
    public boolean lSet(String key, Object value, long time) {
        return false;
    }

    @Override
    public boolean lSetAll(String key, List<Object> value) {
        return false;
    }

    @Override
    public boolean lSetAll(String key, List<Object> value, long time) {
        return false;
    }

    @Override
    public boolean rSet(String key, Object value) {
        return false;
    }

    @Override
    public boolean rSet(String key, Object value, long time) {
        return false;
    }

    @Override
    public boolean rSetAll(String key, List<Object> value) {
        return false;
    }

    @Override
    public boolean rSetAll(String key, List<Object> value, long time) {
        return false;
    }

    @Override
    public boolean lUpdateIndex(String key, long index, Object value) {
        return false;
    }

    @Override
    public long lRemove(String key, long count, Object value) {
        return 0;
    }

    @Override
    public void rangeRemove(String key, Long stard, Long end) {

    }

    @Override
    public RedisTemplate redisTemplate() {
        return null;
    }

    @Override
    public Number execute(DefaultRedisScript<Number> script, List keys, Object... args) {
        return null;
    }
}
