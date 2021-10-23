package com.jh.cavy.manage.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jh.cavy.common.mybatisPlus.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 回答表
 */
@ApiModel(value = "com-jh-cavymanage-domain-Answer")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_bus_answer")
public class Answer extends BaseEntity {
    @TableId(value = "id",type = IdType.AUTO)
    @ApiModelProperty(value = "")
    private Integer id;

    /**
     * 问题id
     */
    @ApiModelProperty(value = "问题id")
    private Integer questionId;

    /**
     * 内容
     */
    @ApiModelProperty(value = "内容")
    private String content;


}