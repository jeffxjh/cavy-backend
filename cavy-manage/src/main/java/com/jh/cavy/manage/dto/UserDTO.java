package com.jh.cavy.manage.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 用户表
 */
@Data
public class UserDTO {
    /**
     * id
     */
    @ExcelProperty({"id", "id"})
    private Integer id;

    /**
     * 账号
     */
    @ApiModelProperty(value = "账号")
    @ExcelProperty({"账号", "账号"})
    private String userName;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    @ExcelProperty({"密码", "密码"})
    private String password;

    /**
     * 真实姓名
     */
    @ApiModelProperty(value = "真实姓名")
    @ExcelProperty({"真实姓名", "真实姓名"})
    private String realName;

    /**
     * 性别
     */
    @ApiModelProperty(value = "性别")
    @ExcelProperty({"性别", "性别"})
    private Integer gender;

    /**
     * 电话
     */
    @ApiModelProperty(value = "电话")
    @ExcelProperty({"电话", "电话"})
    private String phone;

    /**
     * 邮件
     */
    @ApiModelProperty(value = "邮件")
    @ExcelProperty({"邮件", "邮件"})
    private String email;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @ExcelProperty({"创建时间", "创建时间"})
    private Date addTime;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    @ExcelProperty({"创建人", "创建人"})
    private String addUser;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    @ExcelProperty({"修改时间", "修改时间"})
    private Date updateTime;

    /**
     * 修改人
     */
    @ApiModelProperty(value = "修改人")
    @ExcelProperty({"修改人", "修改人"})
    private String updateUser;
}