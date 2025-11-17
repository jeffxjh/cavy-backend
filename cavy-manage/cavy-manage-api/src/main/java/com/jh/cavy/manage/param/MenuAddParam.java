package com.jh.cavy.manage.param;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

/**
 * 菜单新增参数
 */
@Data
@Validated
@Schema(name = "com-jh-manage-domain-Menu")
public class MenuAddParam {
    @Schema(name = "id")
    private Integer id;
    @Schema(name = "menuId")
    private Integer menuId;
    @NotBlank(message = "menuCode 不能为空")
    @Schema(name = "菜单编号")
    private String menuCode;

    @NotBlank(message = "menuName 不能为空")
    @Schema(name = "菜单名称")
    private String menuName;

    @NotBlank(message = "menuType 不能为空")
    @Schema(name = "菜单类型")
    private String menuType;

    @Schema(name = "菜单图标")
    private String icon;

    @NotBlank(message = "url 不能为空")
    @Schema(name = "菜单地址")
    private String url;

    @Min(message = "长度大于0", value = 0)
    @NotNull(message = "parentId 不能为空")
    @Schema(name = "")
    private Integer parentId;

    @Schema(name = "是否隐藏")
    private Integer hidden;

    @Schema(name = "")
    private Integer sort;

    @Schema(name = "")
    private Integer weight;

    @Schema(name = "")
    private Integer isDefault;
    @Schema(name = "status")
    private Integer status;

}