package com.jh.cavy.favour.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jh.cavy.common.mybatisPlus.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class FavourInoutPageVO extends BaseEntity {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 当前用户主键(主体用户)
     */
    private Integer currentUserId;
    private String currentUserName;

    /**
     * 关联用户主键
     */
    private Integer relateUserId;
    private String relateUserName;

    /**
     * 往来交易类型 1支出 0收入
     */
    private String tradeType;
    private String tradeTypeName;

    /**
     * 交易事项 数据字典(生日,结婚,乔迁)
     */
    private String bussType;
    private String bussTypeName;
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
     * 备注
     */
    private String remark;
    /**
     * 来自
     * record || gift
     */
    private String source;

    /**
     * 金额
     */
    private BigDecimal amt;

}