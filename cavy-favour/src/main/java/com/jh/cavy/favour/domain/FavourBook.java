package com.jh.cavy.favour.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jh.cavy.common.mybatisPlus.BaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 人情管理-礼薄表
 *
 * @TableName t_bus_favour_book
 */
@Setter
@Getter
@TableName(value = "t_bus_favour_book")
public class FavourBook extends BaseEntity  {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 主办人用户主键
     */
    private Integer currentUserId;

    /**
     * 礼薄事项 数据字典(生日,结婚,乔迁)
     */
    private String bussType;
    /**
     * 自定义礼薄名称
     */
    private String bussName;


    /**
     * 举办时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date bussDate;


    /**
     * 金额
     */
    private BigDecimal amt;

    /**
     * 备注
     */
    private String remark;


    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}