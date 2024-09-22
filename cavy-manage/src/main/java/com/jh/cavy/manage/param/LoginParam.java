package com.jh.cavy.manage.param;

import lombok.Data;

import java.io.Serializable;
@Data
public class LoginParam implements Serializable {
    private String username;
    private String password;
    private String openid;
    private String token;
}
