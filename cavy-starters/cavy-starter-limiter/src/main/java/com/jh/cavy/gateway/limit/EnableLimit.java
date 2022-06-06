package com.jh.cavy.gateway.limit;


import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target({ElementType.TYPE})
@Import(LimitImportSelector.class)
public @interface EnableLimit {

    /**
     * 扫描包路径
     */
    String[] basePackages() default {};
    /**
     * 指定枚举类型 默认使用redis
     */
    LimitType enableLimit() default LimitType.REDIS;

    enum LimitType {
        REDIS(1000, "com.jh.cavy.gateway.limit.redis"),

        SEMAPHORE(1001, "com.jh.cavy.gateway.limit.semaphore"),

        RATELIMITER(1002, "com.jh.cavy.gateway.limit.rateLimiter");

        public int code;
        public String path;

        LimitType(int code, String path) {
            this.code = code;
            this.path = path;
        }


        public static String getValue(int code) {
            for (LimitType ele : values()) {
                if (ele.code == (code)) return ele.path;
            }
            return null;
        }

    }


}
