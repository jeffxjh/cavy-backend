package com.jh.cavy.manage.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.jh.cavy.common.mybatisPlus.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AnswerDTO extends BaseEntity {
    private Integer id;

    /**
     * 问题id
     */
    private Integer questionId;

    /**
     * 内容
     */
    private String content;
}
