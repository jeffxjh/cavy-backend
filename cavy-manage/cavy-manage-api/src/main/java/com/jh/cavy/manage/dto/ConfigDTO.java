package com.jh.cavy.manage.dto;

import com.jh.cavy.common.mybatisPlus.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ConfigDTO extends BaseEntity {
    private Integer id;

    /**
     * 编号
     */
    private String code;

    /**
     * 编号名称
     */
    private String codeValue;

    /**
     * 名称
     */
    private String name;
}
