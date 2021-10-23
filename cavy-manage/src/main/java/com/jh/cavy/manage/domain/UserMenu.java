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
 * 用户菜单表
 */
@ApiModel(value = "com-jh-cavy-manage-domain-UserMenu")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_sys_user_menu")
public class UserMenu extends BaseEntity {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private Integer id;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Integer userId;

    /**
     * 菜单id
     */
    @ApiModelProperty(value = "菜单id")
    private Integer menuId;

}