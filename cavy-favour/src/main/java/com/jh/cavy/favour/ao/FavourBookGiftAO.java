package com.jh.cavy.favour.ao;

import com.baomidou.mybatisplus.annotation.TableField;
import com.jh.cavy.common.Resquest.BaseParam;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 人情管理-礼薄明细表
 * @TableName t_bus_favour_book_gift
 */
@Getter
@Setter
public class FavourBookGiftAO extends BaseParam {
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
    /**
     * 礼薄事项 数据字典(生日,结婚,乔迁)
     */
    private String bussType;
    private String remarks;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}