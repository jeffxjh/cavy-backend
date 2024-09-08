package com.jh.cavy.manage.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class RoleVO {
    @Schema(name="")
    private Integer id;

    @Schema(name="")
    private String roleName;


    @Schema(name="")
    private List<MenuVO> children;

}
