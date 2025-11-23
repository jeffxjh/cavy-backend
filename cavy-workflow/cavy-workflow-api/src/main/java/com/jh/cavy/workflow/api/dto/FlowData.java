package com.jh.cavy.workflow.api.dto;

import lombok.Data;

import java.util.Map;

@Data
public class FlowData {
    /**
     * 流程实例ID
     */
    private String flowInstanceId;

    /**
     * 当前节点ID
     */
    private String currentNodeId;

    /**
     * 上一节点ID
     */
    private String previousNodeId;

    /**
     * 流程状态
     */
    private String flowStatus;

    /**
     * 审批意见
     */
    private String approveComment;

    /**
     * 扩展流程数据
     */
    private Map<String, Object> extFlowData;
}
