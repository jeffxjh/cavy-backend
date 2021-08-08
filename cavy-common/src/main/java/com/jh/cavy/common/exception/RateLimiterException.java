package com.jh.cavy.common.exception;

public class RateLimiterException extends RuntimeException{
    private int code;
    private String msg;

    public RateLimiterException() {
        this(5000, "服务器繁忙,请稍后再试");
    }

    public RateLimiterException(String msg) {
        this(4003, msg);
    }

    public RateLimiterException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }
}
