package com.jh.cavy.workflow.api.dto;

import com.jh.cavy.common.Resquest.BaseParam;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TaskInfoVO extends BaseParam {
    private String owner;
    private int assigneeUpdatedCount;
    private String originalAssignee;
    private String assignee;
    private String parentTaskId;
    private String name;
    private String localizedName;
    private String description;
    private String localizedDescription;
    private int priority = 50;
    private String state;
    private Date createTime;
    private Date inProgressStartTime;
    private String inProgressStartedBy;
    private Date claimTime;
    private String claimedBy;
    private Date suspendedTime;
    private String suspendedBy;
    private Date inProgressStartDueDate;
    private Date dueDate;
    private int suspensionState;
    private String category;
    private boolean isIdentityLinksInitialized;
    private String executionId;
    private String processInstanceId;
    private String processDefinitionId;
    private String taskDefinitionId;
    private String scopeId;
    private String subScopeId;
    private String scopeType;
    private String scopeDefinitionId;
    private String propagatedStageInstanceId;
    private String taskDefinitionKey;
    private String formKey;
    private boolean isCanceled;
    private boolean isCountEnabled;
    private int variableCount;
    private int identityLinkCount;
    private int subTaskCount;
    private String tenantId;
    private String eventName;
    private String eventHandlerId;
    private String tempCompletedBy;
}
