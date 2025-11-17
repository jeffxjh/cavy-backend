package com.jh.cavy.workflow.api.dto;

import lombok.Data;

import java.util.Map;

@Data
public class TaskCompleteRequest {
    private String taskId;
    private String processInstanceId;
    private Map<String, Object> variables;
    private String comment;
}