package com.jh.configure.cache;

import com.jh.cavy.cache.service.CacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

@Configuration
@ComponentScan(basePackages = {"com.jh.cavy.cache"})
@RequiredArgsConstructor
public class CacheConfigure {
    private final ApplicationContext applicationContext;
    @Value("${cache.use:caffeineHandle}") // 默认值为 englishGreetingService
    private String cacheServiceName;

    @Bean
    @Primary
    public CacheService createCacheService() {
        return (CacheService) applicationContext.getBean(cacheServiceName);
    }
}
