package com.jh.cavycore.jwt;

import lombok.Data;

import java.io.Serializable;

@Data
public class JwtUser implements Serializable {
    private Long id;
    private String account;
    private String username;
    private String sex;
    private String email;
    private String phone;
}
