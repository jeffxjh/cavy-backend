package com.jh.cavy.workflow.api.dto;

import lombok.Data;

@Data
public class Attachment {
    private String fileId;
    private String fileName;
    private String filePath;
    private Long fileSize;
    private String fileType;
}