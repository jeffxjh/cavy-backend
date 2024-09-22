package com.jh.cavy.favour.ao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.jh.cavy.common.Resquest.BaseParam;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
@Getter
@Setter
public class FavourBookAO extends BaseParam {
    /**
     * 主键
     */
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
     * 金额
     */
    private BigDecimal amt;

}
