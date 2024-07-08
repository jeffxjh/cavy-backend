package com.jh.cavy.manage.param;

import com.jh.cavy.common.Resquest.BaseParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@ApiModel
public class UserParam extends BaseParam {
    @ApiModelProperty(name = "userName")
    private String userName;
    @ApiModelProperty(name = "realName")
    private String realName;
    @ApiModelProperty(name = "password")
    private String password;
    @ApiModelProperty(name = "email")
    private String email;
    @ApiModelProperty(name = "phone")
    private String phone;
    @ApiModelProperty(name = "gender")
    private String gender;
    @ApiModelProperty(name = "status")
    private String status;
    @ApiModelProperty(name = "defaultUser")
    private String defaultUser;

}
