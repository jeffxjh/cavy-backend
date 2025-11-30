package com.jh.cavy.workflow.api.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class TaskDTO {
    // 任务基础信息
    private String id;
    private String name;
    private String description;
    private String taskDefinitionKey;
    private Integer priority;
    private String category;

    // 流程实例信息
    private String processInstanceId;
    private String processDefinitionId;
    private String executionId;
    private String businessKey;
    private Object  originalPersistentState;

    // 时间信息
    private Date createTime;
    private Date dueDate;
    private Date claimTime;

    // 处理人信息
    private String assignee;
    private String owner;

    // 流程定义信息
    private String processDefinitionName;
    private String processDefinitionKey;
    private Integer processDefinitionVersion;

    // 流程变量
    private Map<String, Object> processVariables;

    // 任务局部变量
    private Map<String, Object> taskLocalVariables;

    // 业务数据（从流程变量或外部系统获取）
    private Map<String, Object> businessData;

    // 扩展属性（从BPMN XML中获取）
    private Map<String, String> taskProperties;

    // 表单配置
    private String formKey;
    private Map<String, Object> formProperties;

    // 候选人信息
    private List<String> candidateUsers;
    private List<String> candidateGroups;

    // 前端显示字段
    private String processStatus;
    private String duration;
    private Boolean overdue;
    private CustomPropData customPropData;
}