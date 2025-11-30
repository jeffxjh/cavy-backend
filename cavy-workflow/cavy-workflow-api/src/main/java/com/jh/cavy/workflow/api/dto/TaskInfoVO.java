package com.jh.cavy.workflow.api.dto;

import com.jh.cavy.common.Resquest.BaseParam;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@FieldNameConstants
public class TaskInfoVO extends BaseParam {
    private String name;
    private String description;
    private String taskDefinitionKey;
    private String txnCode;
    private String stepNo;
    private String processPath;
    private String owner;
    private String previousAssignee;
    private String parentTaskId;
    private String assignee;
    private int priority = 50;
    private String state;
    private String status;
    private String bussStatus;
    private Date createTime;
    private List<String> roleList;
    private List<String> userList;
    private String localizedName;
    private String localizedDescription;
    private Date inProgressStartTime;
    private String originalAssignee;
    private int assigneeUpdatedCount;
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
