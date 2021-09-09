package com.jh.cavy.manage.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 系统配置表
 */
@ApiModel(value = "com-jh-cavy-manage-domain-Config")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_sys_config")
public class Config {
    /**
     * id
     */
    @TableId(value = "id",type = IdType.AUTO)
    @ApiModelProperty(value = "")
    private Integer id;

    /**
     * 编号
     */
    @ApiModelProperty(value = "编号")
    private String code;

    /**
     * 编号名称
     */
    @ApiModelProperty(value = "编号名称")
    private String codeValue;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String name;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date addTime;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String addUser;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    /**
     * 修改人
     */
    @ApiModelProperty(value = "修改人")
    private String updateUser;
}