package com.jh.cavy.workflow.api.dto;

import lombok.Data;

@Data
public class ProcessDefinitionDTO {
    private String id;
    private String name;
    private String key;
    private String bpmnXml;
    private Integer version;
    private String createdBy;
}
