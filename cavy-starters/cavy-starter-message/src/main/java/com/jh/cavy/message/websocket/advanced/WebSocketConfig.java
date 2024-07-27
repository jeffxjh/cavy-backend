package com.jh.cavy.message.websocket.advanced;

import com.jh.cavy.common.Resquest.RequestHeadHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Slf4j
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new MyWebSocketHandler(), "/ws/serverTwo")//设置连接路径和处理
                .setAllowedOrigins("*")
                .addInterceptors(new MyWebSocketInterceptor());//设置拦截器
        //registry.addHandler(new MyWebSocketHandler(), "/sockjs/wsservice").setAllowedOrigins("*").addInterceptors(new MyWebSocketInterceptor()).withSockJS();
    }

    /**
     * 自定义拦截器拦截WebSocket请求
     */
    static class MyWebSocketInterceptor implements HandshakeInterceptor {
        //前置拦截一般用来注册用户信息，绑定 WebSocketSession
        @Override
        public boolean beforeHandshake(@NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response,
                                       @NonNull WebSocketHandler wsHandler, @NonNull Map<String, Object> attributes) throws Exception {
            log.info("==>前置拦截");
            HttpServletRequest req = ((ServletServerHttpRequest) request).getServletRequest();
            String token = req.getHeader("Sec-WebSocket-Protocol");
            //if (!(request instanceof ServletServerHttpRequest)) return true;
            //ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(((ServletServerHttpRequest) request).getServletRequest());
            //String userName = (String) requestWrapper.getSession().getAttribute("userName");
            attributes.put("userName", RequestHeadHolder.getAccount());
            attributes.put("token", token);
            return true;
        }

        @Override
        public void afterHandshake(@NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response,
                                   @NonNull WebSocketHandler wsHandler, Exception exception) {
            log.info("==>后置拦截");
        }
    }
}
