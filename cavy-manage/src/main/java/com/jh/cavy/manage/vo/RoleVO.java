package com.jh.cavy.manage.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class RoleVO {
    @ApiModelProperty(value="")
    private Integer id;

    @ApiModelProperty(value="")
    private String roleName;


    @ApiModelProperty(value="",name = "")
    private List<MenuVO> children;

}
