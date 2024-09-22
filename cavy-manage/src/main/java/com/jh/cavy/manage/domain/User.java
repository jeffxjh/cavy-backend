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
 * 用户表
 */
@Schema(name = "com-jh-cavy-manage-domain-User")
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
    @Schema(name = "id")
    private Integer id;

    /**
     * 账号
     */
    @Schema(name = "账号")
    private String userName;
    /**
     * 微信openid
     */
    @Schema(name = "微信openid")
    private String openid;
    /**
     * 密码
     */
    @Schema(name = "密码")
    private String password;

    /**
     * 真实姓名
     */
    @Schema(name = "真实姓名")
    private String realName;

    /**
     * 性别
     */
    @Schema(name = "性别")
    private Integer gender;

    /**
     * 电话
     */
    @Schema(name = "电话")
    private String phone;

    /**
     * 邮件
     */
    @Schema(name = "邮件")
    private String email;
    /**
     * 邮件
     */
    @Schema(name = "状态 0已新建;1已使用;2已禁用;3已冻结")
    private String status;
    /**
     * 默认用户
     */
    @Schema(name = "默认用户 0否;1是")
    private String defaultUser;

}