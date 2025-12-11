package com.jh.cavy.common.AnnotationResult;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.AsyncHandlerMethodReturnValueHandler;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import jakarta.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class CustomResponseReturnValueHandler implements HandlerMethodReturnValueHandler, AsyncHandlerMethodReturnValueHandler {

    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    public boolean isAsyncReturnValue(Object o, MethodParameter methodParameter) {
        return supportsReturnType(methodParameter);
    }

    @Override
    public boolean supportsReturnType(MethodParameter methodParameter) {
        return methodParameter.getAnnotatedElement().getAnnotation(CustomResponse.class) != null;
    }

    @Override
    public void handleReturnValue(Object data, MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest) throws Exception {
        modelAndViewContainer.setRequestHandled(true);
        HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);
        response.setContentType(APPLICATION_JSON_VALUE);

        String message = "操作成功";
        String status = "success";

        if ((data instanceof Boolean) && !((Boolean) data)) {
            message = "操作失败";
            status = "fail";
        }

        CustomResponseContent responseContent = CustomResponseContent.builder()
                                                        .code(response.getStatus())
                                                        .status(status)
                                                        .message(message)
                                                        .data(data)
                                                        .build();

        response.getWriter()
                .write(objectMapper.writeValueAsString(responseContent));

    }
}