package com.jh.cavy.manage.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 菜单表
 */
@ApiModel(value = "com-jh-manage-domain-Menu")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_sys_menu")
public class Menu {
    @TableId(value = "id",type = IdType.AUTO)
    @ApiModelProperty(value = "")
    private Integer id;

    @ApiModelProperty(value = "菜单编号")
    private String menuCode;

    @ApiModelProperty(value = "菜单名称")
    private String menuName;

    @ApiModelProperty(value = "菜单图标")
    private String icon;

    @ApiModelProperty(value = "菜单地址")
    private String url;

    @ApiModelProperty(value = "")
    private Integer parentId;

    @ApiModelProperty(value = "")
    private Integer sort;

    @ApiModelProperty(value = "")
    private Integer weight;

    @ApiModelProperty(value = "")
    private Integer isDefault;

    @ApiModelProperty(value = "")
    private Date addtime;

    @ApiModelProperty(value = "")
    private String adduser;

    @ApiModelProperty(value = "")
    private Date updatetime;

    @ApiModelProperty(value = "")
    private String updateuser;
}