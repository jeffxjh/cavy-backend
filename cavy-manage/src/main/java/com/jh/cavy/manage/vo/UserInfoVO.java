package com.jh.cavy.manage.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Schema
public class UserInfoVO implements Serializable {
    @Schema(name = "")
    private Integer id;

    @Schema(name = "")
    private String userName;
    @Schema(name = "")
    private String openid;

    @Schema(name = "")
    private String realName;

    @Schema(name = "")
    private Integer gender;
    @Schema(name = "")
    private String pwd;

    @Schema(name = "")
    private String phone;

    @Schema(name = "")
    private String email;

    @Schema(name = "")
    private String token;

    @Schema(name = "")
    private String avatar;

    @Schema(name = "status")
    private String status;
    @Schema(name = "defaultUser")
    private String defaultUser;
    @Schema(name = "roleList")
    private List<RoleVO> roleList;
}
