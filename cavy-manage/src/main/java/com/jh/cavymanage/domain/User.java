package com.jh.cavymanage.domain;

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
 * 用户表
 */
@ApiModel(value = "com-jh-manage-domain-User")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_sys_user")
public class User {
    @TableId(value = "id",type = IdType.AUTO)
    @ApiModelProperty(value = "")
    private Integer id;

    @ApiModelProperty(value = "")
    private String userName;

    @ApiModelProperty(value = "")
    private String password;

    @ApiModelProperty(value = "")
    private String realName;

    @ApiModelProperty(value = "")
    private Integer gender;

    @ApiModelProperty(value = "")
    private String phone;

    @ApiModelProperty(value = "")
    private String email;

    @ApiModelProperty(value = "")
    private Date addtime;

    @ApiModelProperty(value = "")
    private String adduser;

    @ApiModelProperty(value = "")
    private Date updatetime;

    @ApiModelProperty(value = "")
    private String updateuser;
}