package com.jh.cavy.manage.domain;

import com.baomidou.mybatisplus.annotation.*;
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
@TableName(value = "t_bus_question")
public class Question {
    @TableId(value = "id",type = IdType.AUTO)
    @ApiModelProperty(value = "")
    private Integer id;

    @ApiModelProperty(value = "")
    private String title;

    @ApiModelProperty(value = "")
    private String content;
    @TableField(value = "addUser", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "")
    private String adduser;
    @TableField(value = "addTime", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "")
    private Date addtime;
    @TableField(value = "updateUser", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "")
    private String updateuser;
    @TableField(value = "updateTime", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "")
    private Date updatetime;
}