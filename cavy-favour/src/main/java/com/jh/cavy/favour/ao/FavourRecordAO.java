package com.jh.cavy.favour.ao;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jh.cavy.common.Resquest.BaseParam;
import com.jh.cavy.common.mybatisPlus.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class FavourRecordAO extends BaseParam {
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
    private String relateType;
    private String relateTypeName;
    private String remarks;

    /**
     * 备注
     */
    private String remark;

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
}
