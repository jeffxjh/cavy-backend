package com.jh.cavymanage.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserVO implements Serializable {
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
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "")
    private Date addtime;

    @ApiModelProperty(value = "")
    private String adduser;

}
