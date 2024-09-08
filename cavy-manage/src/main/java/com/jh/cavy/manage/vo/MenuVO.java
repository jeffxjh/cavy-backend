package com.jh.cavy.manage.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class MenuVO implements Serializable {
    /**
     * id
     */
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
     * 父name
     */
    @Schema(name = "父name")
    private String parentName;

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

    @Schema(name = "拆分的url前部")
    private String parentUrl;

    @Schema(name = "拆分的url后部")
    private String curUrl;

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
     * 创建时间
     */
    @Schema(name = "创建时间")
    private Date addTime;

    /**
     * 创建人
     */
    @Schema(name = "创建人")
    private String addUser;

    /**
     * 修改人
     */
    @Schema(name = "修改人")
    private Date updateTime;

    /**
     * 修改时间
     */
    @Schema(name = "修改时间")
    private String updateUser;

    @Schema(name = "status")
    private Integer status;

    /**
     * 子菜单
     */
    @Schema(name = "子菜单")
    private List<MenuVO> children;
}
