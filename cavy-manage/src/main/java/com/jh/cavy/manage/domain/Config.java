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
@ApiModel(value = "com-jh-manage-domain-Config")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_sys_config")
public class Config {
    @TableId(value = "id",type = IdType.AUTO)
    @ApiModelProperty(value = "")
    private Integer id;

    @ApiModelProperty(value = "")
    private String code;

    @ApiModelProperty(value = "")
    private String codeValue;

    @ApiModelProperty(value = "")
    private String name;

    @ApiModelProperty(value = "")
    private Date addtime;

    @ApiModelProperty(value = "")
    private String adduser;

    @ApiModelProperty(value = "")
    private Date updatetime;

    @ApiModelProperty(value = "")
    private String updateuser;
}