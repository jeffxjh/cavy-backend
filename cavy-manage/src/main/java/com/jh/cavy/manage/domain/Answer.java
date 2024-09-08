package com.jh.cavy.manage.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jh.cavy.common.mybatisPlus.BaseEntity;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 回答表
 */
@Schema(name = "com-jh-cavymanage-domain-Answer")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_bus_answer")
public class Answer extends BaseEntity {
    @TableId(value = "id",type = IdType.AUTO)
    @Schema(name = "")
    private Integer id;

    /**
     * 问题id
     */
    @Schema(name = "问题id")
    private Integer questionId;

    /**
     * 内容
     */
    @Schema(name = "内容")
    private String content;


}