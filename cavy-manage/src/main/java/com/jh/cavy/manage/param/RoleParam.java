package com.jh.cavy.manage.param;

import com.jh.cavy.common.Resquest.BaseParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel
public class RoleParam extends BaseParam {
    @ApiModelProperty(name = "roleName")
    private String roleName;
}
