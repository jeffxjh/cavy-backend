package com.jh.cavycore.common.exception;

import lombok.Getter;

@Getter
public class ExpiredJwtException extends RuntimeException{
    private int code;
    private String msg;

    public ExpiredJwtException() {
        this(4003, "登录信息过期");
    }

    public ExpiredJwtException(String msg) {
        this(4003, msg);
    }

    public ExpiredJwtException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }
}
