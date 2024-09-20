package com.jh.favour.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 人情管理-亲友表
 * @TableName t_bus_favour_relative
 */
@TableName(value ="t_bus_favour_relative")
@Data
public class FavourRelative implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 亲友名称
     */
    private String realName;

    /**
     * 亲友昵称
     */
    private String nickName;

    /**
     * 关联当前用户表主键(是谁的亲友)
     */
    private Integer userId;

    /**
     * 亲友关联的用户主键(该亲友在系统中的用户主键)
     */
    private Integer relateUserId;

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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}