package com.jh.cavy.manage.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.jh.cavy.manage.excel.ExcelDictConverter;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @ExcelProperty(value = "id",index = 0)
    private Integer id;

    /**
     * 账号
     */
    @Schema(name = "账号")
    @ExcelProperty(value = "账号",index = 1)
    private String userName;

    /**
     * 密码
     */
    @Schema(name = "密码")
    @ExcelProperty(value = "密码",index = 2)
    private String pwd;

    /**
     * 真实姓名
     */
    @Schema(name = "真实姓名")
    @ExcelProperty(value = "真实姓名",index = 3)
    private String realName;

    /**
     * 性别
     */
    @Schema(name = "性别")
    @ExcelProperty(value = "性别",index = 4,converter = ExcelDictConverter.class)
    private String gender;

    /**
     * 电话
     */
    @Schema(name = "电话")
    @ExcelProperty(value = "电话",index = 5)
    private String phone;

    /**
     * 邮件
     */
    @Schema(name = "邮件")
    @ExcelProperty(value = "邮件",index = 6)
    private String email;
    /**
     * 状态 0已新建;1已使用;2已禁用;3已冻结
     */
    @Schema(name = "状态")
    @ExcelProperty(value = "状态",index = 7,converter = ExcelDictConverter.class)
    private String status;
    /**
     * 默认用户
     */
    @Schema(name = "默认用户")
    @ExcelProperty(value = "默认用户",index = 8,converter = ExcelDictConverter.class)
    private String defaultUser;

    /**
     * 创建时间
     */
    @Schema(name = "创建时间")
    @ExcelIgnore
    private Date addTime;

    /**
     * 创建人
     */
    @Schema(name = "创建人")
    @ExcelIgnore
    private String addUser;

    /**
     * 修改时间
     */
    @Schema(name = "修改时间")
    @ExcelIgnore
    private Date updateTime;

    /**
     * 修改人
     */
    @Schema(name = "修改人")
    @ExcelIgnore
    private String updateUser;

    /**
     * 地址
     */
    @Schema(name = "地址")
    @ExcelProperty(value = "地址",index = 9)
    private String address;
}