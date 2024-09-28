package com.jh.cavy.favour.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import com.jh.cavy.common.mybatisPlus.BaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 人情管理-亲友表
 *
 * @TableName t_bus_favour_relative
 */

@Setter
@Getter
@TableName(value = "t_bus_favour_relative")
public class FavourRelative extends BaseEntity {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 亲友名称
     */
    private String realName;

    /**
     * 亲友昵称
     */
    private String nickName;
    /**
     * 亲友关系
     */
    private Integer relateType;

    /**
     * 关联当前用户表主键(是谁的亲友)
     */
    private Integer userId;

    /**
     * 亲友关联的用户主键(该亲友在系统中的用户主键)
     */
    private Integer relateUserId;


    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}