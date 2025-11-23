package com.jh.cavy.workflow.api.dto;

import lombok.Data;

import java.util.Map;

@Data
public class TaskResult {
    private boolean success;
    private String errorCode;
    private String errorMsg;
    private Map<String, Object> data;
    private long timestamp;
    private String txnType;
    private String stepNo;
    private OperateType operateType;

    public TaskResult() {
        this.timestamp = System.currentTimeMillis();
    }

    public TaskResult(boolean success, String errorMsg) {
        this();
        this.success = success;
        this.errorMsg = errorMsg;
    }
}