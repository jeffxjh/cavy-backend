package com.jh.cavy.workflow.api.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class FormData<T> {
    /**
     * 业务主键
     */
    private String businessKey;

    /**
     * 表单类型
     */
    private String formType;

    /**
     * 表单数据（JSON格式）
     */
    private T formJson;

    /**
     * 表单字段映射
     */
    private Map<String, Object> formFields;

    /**
     * 附件列表
     */
    private List<Attachment> attachments;
}