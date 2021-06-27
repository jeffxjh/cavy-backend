package com.jh.cavybackend.domain;

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
@ApiModel(value = "com-jh-cavybackend-domain-Config")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_sys_config")
public class Config {
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