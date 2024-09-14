package com.jh.cavy.manage.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.jh.cavy.common.mybatisPlus.BaseEntity;
import lombok.Data;

/**
 * 资源表
 * @TableName t_sys_resource
 */
@TableName(value ="t_sys_resource")
@Data
public class Resource extends BaseEntity {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 资源编码
     */
    private String resourceCode;

    /**
     * 资源名称
     */
    private String resourceName;

    /**
     * 资源路径
     */
    private String resourcePath;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}