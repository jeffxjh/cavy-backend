package com.jh.cavy.manage.param;

import com.jh.cavy.common.Resquest.BaseParam;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema
public class RoleParam extends BaseParam {
    @Schema(name = "roleName")
    private String roleName;
}
