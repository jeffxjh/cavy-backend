package com.jh.cavy.common.Result;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jh.cavy.common.exception.APIException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.logging.LogLevel;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.WebUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;

@Slf4j
@RestControllerAdvice(basePackages = {"com.jh.cavy"}) // 注意哦，这里要加上需要扫描的包
public class ResponseControllerAdvice implements ResponseBodyAdvice<Object> {
    @Value("${logging.level.com.jh.cavy.common.Result.ResponseControllerAdvice:info}")
    private String enableLog;
    private static final int DEFAULT_MAX_PAYLOAD_LENGTH = 10000;
    public static final String REQUEST_MESSAGE_PREFIX = "Request [";
    public static final String REQUEST_MESSAGE_SUFFIX = "]";
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> aClass) {
        // 如果接口返回的类型本身就是ResultVO那就没有必要进行额外的操作，返回false
        return !returnType.getGenericParameterType().equals(ResultVO.class);
    }

    @Override
    public Object beforeBodyWrite(Object data, MethodParameter returnType, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest request, ServerHttpResponse response) {
        ServletServerHttpRequest servletServerHttpRequest = (ServletServerHttpRequest) request;
        if (enableLog.equalsIgnoreCase(LogLevel.DEBUG.toString())){
            log.debug(createRequestMessage(servletServerHttpRequest.getServletRequest(), REQUEST_MESSAGE_PREFIX, REQUEST_MESSAGE_SUFFIX));
        }

        if (data instanceof ResultVO) {
            return data;
        }
        // String类型不能直接包装，所以要进行些特别的处理
        if (returnType.getGenericParameterType().equals(String.class)) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                // 将数据包装在ResultVO里后，再转换为json字符串响应给前端
                return objectMapper.writeValueAsString(new ResultVO<>(data));
            } catch (JsonProcessingException e) {
                throw new APIException("返回String类型错误");
            }
        }
        // 将原本的数据包装在ResultVO里
        return new ResultVO<>(data);
    }
    protected String createRequestMessage(HttpServletRequest request, String prefix, String suffix) {
        StringBuilder msg = new StringBuilder();
        msg.append(prefix);
        msg.append(request.getMethod()).append(" ");
        msg.append(request.getRequestURI());


        String queryString = request.getQueryString();
        if (queryString != null) {
            msg.append('?').append(queryString);
        }


        String client = request.getRemoteAddr();
        if (StringUtils.hasLength(client)) {
            msg.append(", client=").append(client);
        }
        HttpSession session = request.getSession(false);
        if (session != null) {
            msg.append(", session=").append(session.getId());
        }
        String user = request.getRemoteUser();
        if (user != null) {
            msg.append(", user=").append(user);
        }

        HttpHeaders headers = new ServletServerHttpRequest(request).getHeaders();
        msg.append(", headers=").append(headers);

        String payload = getMessagePayload(request);
        if (payload != null) {
            msg.append(", payload=").append(payload);
        }

        msg.append(suffix);
        return msg.toString();
    }

    protected String getMessagePayload(HttpServletRequest request) {
        ContentCachingRequestWrapper wrapper =
                WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
        if (wrapper != null) {
            byte[] buf = wrapper.getContentAsByteArray();
            if (buf.length > 0) {
                int length = Math.min(buf.length, DEFAULT_MAX_PAYLOAD_LENGTH);
                try {
                    return new String(buf, 0, length, wrapper.getCharacterEncoding());
                } catch (UnsupportedEncodingException ex) {
                    return "[unknown]";
                }
            }
        }
        return null;
    }
}