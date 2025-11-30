package com.jh.cavy.workflow.api.dto;

import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class TaskDTOOld {
    private String id;
    private String name;
    private String assignee;
    private String processInstanceId;
    private Date createTime;
    private Map<String, Object> variables;
}