package com.jh.cavy.manage.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RoleVO {
    @ApiModelProperty(value="")
    private Integer id;

    @ApiModelProperty(value="")
    private String roleName;

    @ApiModelProperty(value="")
    private Integer menuId;

    @ApiModelProperty(value="")
    private String realName;

    @ApiModelProperty(value="")
    private String userName;

}
