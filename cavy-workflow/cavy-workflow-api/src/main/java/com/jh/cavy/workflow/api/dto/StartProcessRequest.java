package com.jh.cavy.workflow.api.dto;

import lombok.Data;

import java.util.Map;

@Data
public class StartProcessRequest {
    private String processKey;
    private String businessKey;
    private Map<String, Object> variables;
    private Map<String, Object> formData;
}