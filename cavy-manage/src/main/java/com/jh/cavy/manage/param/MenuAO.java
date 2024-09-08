package com.jh.cavy.manage.param;

import com.jh.cavy.common.Resquest.BaseParam;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class MenuAO extends BaseParam {
    /**
     * 菜单编号
     */
    @Schema(name = "菜单编号")
    private String menuCode;

    /**
     * 菜单名称
     */
    @Schema(name = "菜单名称")
    private String menuName;
    /**
     * 菜单状态
     */
    @Schema(name = "菜单状态")
    private Integer status;
}
