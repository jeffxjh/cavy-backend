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
 * 人情管理-礼薄明细表
 * @TableName t_bus_favour_book_gift
 */
@Setter
@Getter
@TableName(value ="t_bus_favour_book_gift")
public class FavourBookGift extends BaseEntity {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 礼薄表主键
     */
    private Integer favourBookId;

    /**
     * 送礼用户主键
     */
    private Integer relateUserId;

    /**
     * 金额
     */
    private BigDecimal amt;

    private String remarks;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}