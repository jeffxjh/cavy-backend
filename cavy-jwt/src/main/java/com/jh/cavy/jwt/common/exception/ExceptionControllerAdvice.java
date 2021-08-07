package com.jh.cavy.jwt.common.exception;

import com.jh.cavy.jwt.common.Result.ResultVO;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(APIException.class)
    public ResultVO<String> APIExceptionHandler(APIException e) {
        // 注意哦，这里返回类型是自定义响应体
        return new ResultVO<>(e.getCode(), "响应失败", e.getMsg());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultVO<String> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
        // 注意哦，这里返回类型是自定义响应体
        return new ResultVO<>(1001, "参数校验失败", objectError.getDefaultMessage());
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResultVO<String> ExpiredJwtExceptionHandler(ExpiredJwtException e) {
        return new ResultVO<>(e.getCode(), "登录信息已失效,请重新登录");
    }
}