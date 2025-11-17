package com.jh.cavy.manage.dto;

import com.jh.cavy.common.mybatisPlus.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class DictItemDTO extends BaseEntity {
    private Long id;

    /**
     * 主表id
     */
    private Long dicId;

    /**
     * key
     */
    private String item;

    /**
     * value
     */
    private String label;
}
