package com.jh.cavy.manage.param;

import com.jh.cavy.common.Resquest.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MenuAO extends BaseParam {
    /**
     * 菜单编号
     */
    @ApiModelProperty(value = "菜单编号")
    private String menuCode;

    /**
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单名称")
    private String menuName;
}
