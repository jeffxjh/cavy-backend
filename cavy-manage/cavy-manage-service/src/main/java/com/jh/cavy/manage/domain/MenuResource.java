package com.jh.cavy.manage.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jh.cavy.common.mybatisPlus.BaseEntity;
import lombok.Data;

/**
 * 菜单资源关联表
 * @TableName t_sys_menu_resource
 */
@TableName(value ="t_sys_menu_resource")
@Data
public class MenuResource extends BaseEntity {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 菜单表主键
     */
    private Long menuId;

    /**
     * 资源表主键
     */
    private Long resourceId;



    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}