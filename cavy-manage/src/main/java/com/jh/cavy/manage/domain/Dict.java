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
 * 数据字典表
 * @TableName t_sys_dict
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value ="t_sys_dict")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Dict extends BaseEntity {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}