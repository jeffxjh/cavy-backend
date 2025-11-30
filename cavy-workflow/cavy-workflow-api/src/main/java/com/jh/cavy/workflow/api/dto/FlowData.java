package com.jh.cavy.workflow.api.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

@Data
@FieldNameConstants
public class FlowData implements Serializable {
    /**
     * 交易编号 流程key
     */
    private String txnCode= UUID.randomUUID().toString();
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
     * 上个节点处理人
     */
    private String previousAssignee;

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
