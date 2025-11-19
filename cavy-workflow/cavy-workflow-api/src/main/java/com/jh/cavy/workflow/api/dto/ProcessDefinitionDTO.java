package com.jh.cavy.workflow.api.dto;

import lombok.Data;

@Data
public class ProcessDefinitionDTO {
    private String id;
    private String name;
    private String processId;
    private String defKey;
    private String bpmnXml;
    private Integer version;
    private String createdBy;
    private String status;
}
