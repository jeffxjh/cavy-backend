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
 * 用户表
 */
@ApiModel(value = "com-jh-cavy-manage-domain-User")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_sys_user")
public class User extends BaseEntity {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private Integer id;

    /**
     * 账号
     */
    @ApiModelProperty(value = "账号")
    private String userName;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    private String password;

    /**
     * 真实姓名
     */
    @ApiModelProperty(value = "真实姓名")
    private String realName;

    /**
     * 性别
     */
    @ApiModelProperty(value = "性别")
    private Integer gender;

    /**
     * 电话
     */
    @ApiModelProperty(value = "电话")
    private String phone;

    /**
     * 邮件
     */
    @ApiModelProperty(value = "邮件")
    private String email;
    /**
     * 邮件
     */
    @ApiModelProperty(value = "状态 0已新建;1已使用;2已禁用;3已冻结")
    private String status;
    /**
     * 默认用户
     */
    @ApiModelProperty(value = "默认用户 0否;1是")
    private String defaultUser;

}