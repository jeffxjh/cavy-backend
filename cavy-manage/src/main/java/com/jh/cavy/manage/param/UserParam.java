package com.jh.cavy.manage.param;

import com.jh.cavy.common.Resquest.BaseParam;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Schema
public class UserParam extends BaseParam {
    @Schema(name = "userName")
    private String id;
    @Schema(name = "userName")
    private String userName;
    @Schema(name = "realName")
    private String realName;
    @Schema(name = "openid")
    private String openid;
    @Schema(name = "pwd")
    private String pwd;
    @Schema(name = "email")
    private String email;
    @Schema(name = "phone")
    private String phone;
    @Schema(name = "gender")
    private String gender;
    @Schema(name = "status")
    private String status;
    @Schema(name = "defaultUser")
    private String defaultUser;
    @Schema(name = "roleList")
    private List<Integer> roleList;
}
