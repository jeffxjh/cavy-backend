package com.jh.cavy.task.business.checkin;

import lombok.Data;

@Data
public class Plant {
    private String loginUrl;
    private String checkInUrl;
    private String email;
    private String passwd;
    private String remember_me;
    private String code;
}
