package com.jh.cavy.common.mybatisPlus;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class BaseEntity implements Serializable {
    /**
     * 创建时间
     */
    @TableField(value = "add_time", fill = FieldFill.INSERT)
    private Date addTime;

    /**
     * 创建人
     */
    @TableField(value = "add_user", fill = FieldFill.INSERT)
    private String addUser;

    /**
     * 修改时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 修改人
     */
    @TableField(value = "update_user", fill = FieldFill.INSERT_UPDATE)
    private String updateUser;

    ///**
    // * 逻辑删除
    // */
    //@TableLogic(delval = "1", value = "0")
    //private String delete;
}
