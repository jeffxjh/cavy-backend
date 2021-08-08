package com.jh.cavy.common.exception;

public class ScannerClassException extends RuntimeException{
    private int code;
    private String msg;

    public ScannerClassException() {
        this(5001, "包扫描异常");
    }

    public ScannerClassException(String msg) {
        this(4003, msg);
    }

    public ScannerClassException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }
}
