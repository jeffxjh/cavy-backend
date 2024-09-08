package com.jh.cavy.manage.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserVO implements Serializable {
    @Schema(name = "")
    private Integer id;

    @Schema(name = "")
    private String userName;

    @Schema(name = "")
    private String realName;

    @Schema(name = "")
    private String password;

    @Schema(name = "")
    private Integer gender;

    @Schema(name = "")
    private String phone;

    @Schema(name = "")
    private String email;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(name = "")
    private Date addTime;

    @Schema(name = "")
    private String addUser;
    @Schema(name = "status")
    private String status;
    @Schema(name = "defaultUser")
    private String defaultUser;
}
