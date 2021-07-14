package com.jh.cavymanage.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class UserInfoVO implements Serializable {
    @ApiModelProperty(value = "")
    private Integer id;

    @ApiModelProperty(value = "")
    private String userName;

    @ApiModelProperty(value = "")
    private String realName;

    @ApiModelProperty(value = "")
    private Integer gender;

    @ApiModelProperty(value = "")
    private String phone;

    @ApiModelProperty(value = "")
    private String email;

    @ApiModelProperty(value = "")
    private String token;

    @ApiModelProperty(value = "")
    private String avatar;
}
