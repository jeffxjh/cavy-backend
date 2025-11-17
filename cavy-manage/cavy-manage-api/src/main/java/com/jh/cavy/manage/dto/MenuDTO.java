package com.jh.cavy.manage.dto;

import com.jh.cavy.common.mybatisPlus.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class MenuDTO extends BaseEntity {
    private Integer id;

    /**
     * 菜单id
     */
    private Integer menuId;

    /**
     * 菜单类型 M：目录；C：菜单；F：按钮
     */
    private String menuType;

    /**
     * 菜单编号
     */
    private String menuCode;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 父id
     */
    private Integer parentId;

    /**
     * 图标
     */
    private String icon;

    /**
     * 地址
     */
    private String url;

    /**
     * 是否隐藏
     */
    private Integer hidden;

    /**
     * 权重
     */
    private Integer weight;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 是否默认
     */
    private Integer isDefault;
    /**
     * 菜单状态 1启用0禁用
     */
    private Integer status;
}
