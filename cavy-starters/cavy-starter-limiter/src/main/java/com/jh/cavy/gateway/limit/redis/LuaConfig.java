package com.jh.cavy.gateway.limit.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;

@Configuration
public class LuaConfig {
    @Bean("gatewaylimitLua")
    public DefaultRedisScript<Number> getRedisScript() {
        DefaultRedisScript<Number> redisScript = new DefaultRedisScript<>();
        redisScript.setLocation(new ClassPathResource("lua/ratelimit.lua"));
        redisScript.setResultType(Number.class);
        return redisScript;
    }
}
