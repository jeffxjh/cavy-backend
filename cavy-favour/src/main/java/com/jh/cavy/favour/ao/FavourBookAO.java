package com.jh.cavy.favour.ao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jh.cavy.common.Resquest.BaseParam;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class FavourBookAO implements Serializable {
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
     * 自定义礼薄名称
     */
    @NotBlank(message = "礼薄名称不能为空")
    private String bussName;
    /**
     * 举办时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "举办时间不能为空")
    private Date bussDate;

    /**
     * 备注
     */
    private String remark;

    /**
     * 金额
     */
    private BigDecimal amt;

}
