package com.jh.cavy.manage.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class MenuVO {
    /**
     * id
     */
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

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date addTime;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String addUser;

    /**
     * 修改人
     */
    @ApiModelProperty(value = "修改人")
    private Date updateTime;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    private String updateUser;

    /**
     * 子菜单
     */
    @ApiModelProperty(value = "子菜单")
    private List<MenuVO> children;
}
