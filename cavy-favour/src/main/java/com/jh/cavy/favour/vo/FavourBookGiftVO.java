package com.jh.cavy.favour.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jh.cavy.common.Resquest.BaseParam;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 人情管理-礼薄明细表
 * @TableName t_bus_favour_book_gift
 */
@Getter
@Setter
public class FavourBookGiftVO extends BaseParam {
    /**
     * 主键
     */
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

    private String realName;

    private String nickName;

    private String relateType;
    private String remarks;
    /**
     * 礼薄事项 数据字典(生日,结婚,乔迁)
     */
    private String bussType;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}