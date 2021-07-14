package com.jh.cavymanage.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 用户菜单表
 */
@ApiModel(value = "com-jh-manage-domain-UserMenu")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_sys_user_menu")
public class UserMenu {
    @ApiModelProperty(value = "")
    private Integer id;

    @ApiModelProperty(value = "")
    private Integer userId;

    @ApiModelProperty(value = "")
    private Integer menuId;

    @ApiModelProperty(value = "")
    private Date addtime;

    @ApiModelProperty(value = "")
    private String adduser;

    @ApiModelProperty(value = "")
    private Date updatetime;

    @ApiModelProperty(value = "")
    private String updateuser;
}