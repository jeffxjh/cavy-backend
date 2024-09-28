package com.jh.cavy.favour.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.jh.cavy.common.mybatisPlus.BaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 人情管理-人情记录表
 * @TableName t_bus_favour_record
 */
@Setter
@Getter
@TableName(value ="t_bus_favour_record")
public class FavourRecord extends BaseEntity {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 当前用户主键(主体用户)
     */
    private Integer currentUserId;

    /**
     * 关联用户主键
     */
    private Integer relateUserId;

    /**
     * 往来交易类型 1支出 0收入
     */
    private String tradeType;

    /**
     * 交易事项 数据字典(生日,结婚,乔迁)
     */
    private String bussType;

    /**
     * 金额
     */
    private BigDecimal amt;



    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}