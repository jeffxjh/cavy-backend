package com.jh.cavy.manage.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.jh.cavy.common.mybatisPlus.BaseEntity;
import lombok.*;

/**
 * 数据字典项
 *
 * @TableName t_sys_dict_item
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value = "t_sys_dict_item")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DictItem extends BaseEntity {
    /**
     *
     */
    @TableId(value = "id", type = IdType.AUTO)
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
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}