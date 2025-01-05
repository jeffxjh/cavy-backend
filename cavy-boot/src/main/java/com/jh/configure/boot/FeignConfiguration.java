package com.jh.configure.boot;

import cn.hutool.core.collection.CollectionUtil;
import feign.Feign;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Enumeration;
import java.util.Objects;

@Slf4j
@ConditionalOnClass({Feign.class})
@Configuration
public class FeignConfiguration implements RequestInterceptor {
    private final RequestMappingHandlerMapping requestMappingHandlerMapping = new FeignRequestMappingHandlerMapping();

    private static class FeignRequestMappingHandlerMapping extends RequestMappingHandlerMapping {
        @Override
        protected boolean isHandler(@NonNull Class<?> beanType) {
            return super.isHandler(beanType) &&
                           beanType.getAnnotation(FeignClient.class) == null;
        }
    }

    /**
     * 将原请求中Header的所有参数，原样传递至Feign请求中。
     */
    @Override
    public void apply(RequestTemplate template) {
        HttpServletRequest request = getHttpServletRequest();
        if (Objects.isNull(request)) {
            return;
        }

        // 对消息头进行配置
        Enumeration<String> headerNames = request.getHeaderNames();
        if (CollectionUtil.isEmpty(headerNames)) {
            return;
        }
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            // 跳过content-length，因为feign会自动设置content-length
            // <https://github.com/spring-cloud/spring-cloud-openfeign/issues/390>
            if ("content-length".equalsIgnoreCase(key)) {
                continue;
            }
            String value = request.getHeader(key);
            template.header(key, value);
        }

        // 对请求体进行配置
        Enumeration<String> bodyNames = request.getParameterNames();
        StringBuilder body = new StringBuilder();
        if (bodyNames != null) {
            while (bodyNames.hasMoreElements()) {
                String name = bodyNames.nextElement();
                String values = request.getParameter(name);
                body.append(name).append("=").append(values).append("&");
            }
        }
        if (!body.isEmpty()) {
            body.deleteCharAt(body.length() - 1);
            template.body(body.toString());
        }
    }

    private HttpServletRequest getHttpServletRequest() {
        try {
            // 这种方式获取的HttpServletRequest是线程安全的
            return ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        } catch (Exception e) {
            return null;
        }
    }
}