package com.jh.cavy.manage.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @TableName t_sys_message
 */
@TableName(value = "t_sys_message")
@Data
public class Message implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 接收人账号
     */
    private String receiver;
    /**
     * 编号名称
     */
    private String content;

    /**
     * 消息等级 1：提醒；2：一般；3：重要；
     */
    private String msgLevel;

    /**
     * 创建时间
     */
    private Date addTime;

    /**
     * 创建人
     */
    private String addUser;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 修改人
     */
    private String updateUser;

    /**
     * 状态 1已读；0未读
     */
    private String status;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}