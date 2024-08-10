package com.jh.cavy.manage.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jh.cavy.common.mybatisPlus.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 菜单表
 */
@ApiModel(value = "com-jh-cavy-manage-domain-Menu")
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
    @ApiModelProperty(value = "id")
    private Integer id;

    /**
     * 菜单id
     */
    @ApiModelProperty(value = "菜单id")
    private Integer menuId;

    /**
     * 菜单类型 1：目录；2：菜单；3：按钮
     */
    @ApiModelProperty(value = "菜单id")
    private Integer menuType;

    /**
     * 菜单编号
     */
    @ApiModelProperty(value = "菜单编号")
    private String menuCode;

    /**
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单名称")
    private String menuName;

    /**
     * 父id
     */
    @ApiModelProperty(value = "父id")
    private Integer parentId;

    /**
     * 图标
     */
    @ApiModelProperty(value = "图标")
    private String icon;

    /**
     * 地址
     */
    @ApiModelProperty(value = "地址")
    private String url;

    /**
     * 是否隐藏
     */
    @ApiModelProperty(value = "是否隐藏")
    private Integer hidden;

    /**
     * 权重
     */
    @ApiModelProperty(value = "权重")
    private Integer weight;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer sort;

    /**
     * 是否默认
     */
    @ApiModelProperty(value = "是否默认")
    private Integer isDefault;

}