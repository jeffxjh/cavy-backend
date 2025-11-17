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
 * 角色菜单关联表
 * @TableName t_sys_role_menu
 */
@TableName(value ="t_sys_role_menu")
@Data
public class RoleMenu extends BaseEntity {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 角色表主键
     */
    private Long roleId;

    /**
     * 菜单表主键
     */
    private Long menuId;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}