package com.jh.favour.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 人情管理-人情记录表
 * @TableName t_bus_favour_record
 */
@TableName(value ="t_bus_favour_record")
@Data
public class FavourRecord implements Serializable {
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