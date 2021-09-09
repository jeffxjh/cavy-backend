package com.jh.cavy.manage.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 问题表
 */
@ApiModel(value = "com-jh-cavymanage-domain-Question")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionVO {
    @ApiModelProperty(value = "")
    private Integer id;

    @ApiModelProperty(value = "")
    private String title;

    @ApiModelProperty(value = "")
    private String content;

    @ApiModelProperty(value = "")
    private String addUser;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "")
    private Date addTime;
    @ApiModelProperty(value = "")
    private String hasAnswer;

}