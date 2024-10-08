package com.jh.cavy.common.exception;

import com.jh.cavy.common.Result.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(APIException.class)
    public ResultVO<String> APIExceptionHandler(APIException e) {
        // 注意哦，这里返回类型是自定义响应体
        return new ResultVO<>(e.getCode(), "响应失败", e.getMsg());
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResultVO<String> ExpiredJwtExceptionHandler(ExpiredJwtException e) {
        return new ResultVO<>(e.getCode(), "登录信息已失效,请重新登录");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultVO<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResultVO<>(1001, "参数校验失败", errors);
    }
}