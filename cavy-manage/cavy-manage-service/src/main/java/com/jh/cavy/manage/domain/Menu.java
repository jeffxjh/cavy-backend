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
 * 菜单表
 */
@Schema(name = "com-jh-cavy-manage-domain-Menu")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_sys_menu")
public class Menu extends BaseEntity {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @Schema(name = "id")
    private Integer id;

    /**
     * 菜单id
     */
    @Schema(name = "菜单id")
    private Integer menuId;

    /**
     * 菜单类型 M：目录；C：菜单；F：按钮
     */
    @Schema(name = "菜单id")
    private String menuType;

    /**
     * 菜单编号
     */
    @Schema(name = "菜单编号")
    private String menuCode;

    /**
     * 菜单名称
     */
    @Schema(name = "菜单名称")
    private String menuName;

    /**
     * 父id
     */
    @Schema(name = "父id")
    private Integer parentId;

    /**
     * 图标
     */
    @Schema(name = "图标")
    private String icon;

    /**
     * 地址
     */
    @Schema(name = "地址")
    private String url;

    /**
     * 是否隐藏
     */
    @Schema(name = "是否隐藏")
    private Integer hidden;

    /**
     * 权重
     */
    @Schema(name = "权重")
    private Integer weight;

    /**
     * 排序
     */
    @Schema(name = "排序")
    private Integer sort;

    /**
     * 是否默认
     */
    @Schema(name = "是否默认")
    private Integer isDefault;
    /**
     * 菜单状态 1启用0禁用
     */
    @Schema(name = "菜单状态")
    private Integer status;

}