package com.jh.cavy.manage.vo;

import com.fasterxml.jackson.annotation.JsonFormat;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 问题表
 */
@Schema(name = "com-jh-cavymanage-domain-Question")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionVO {
    @Schema(name = "")
    private Integer id;

    @Schema(name = "")
    private String title;

    @Schema(name = "")
    private String content;

    @Schema(name = "")
    private String addUser;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(name = "")
    private Date addTime;
    @Schema(name = "")
    private String hasAnswer;

}