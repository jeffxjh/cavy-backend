package com.jh.cavy.manage.param;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 菜单新增参数
 */
@Data
@Validated
@ApiModel(value = "com-jh-manage-domain-Menu")
public class MenuAddParam {
    @ApiModelProperty(value = "id")
    private Integer id;
    @ApiModelProperty(value = "menuId")
    private Integer menuId;
    @NotBlank(message = "menuCode 不能为空")
    @ApiModelProperty(value = "菜单编号")
    private String menuCode;

    @NotBlank(message = "menuName 不能为空")
    @ApiModelProperty(value = "菜单名称")
    private String menuName;

    @NotBlank(message = "menuType 不能为空")
    @ApiModelProperty(value = "菜单类型")
    private String menuType;

    @ApiModelProperty(value = "菜单图标")
    private String icon;

    @NotBlank(message = "url 不能为空")
    @ApiModelProperty(value = "菜单地址")
    private String url;

    @Min(message = "长度大于0", value = 0)
    @NotNull(message = "parentId 不能为空")
    @ApiModelProperty(value = "")
    private Integer parentId;

    @ApiModelProperty(value = "是否隐藏")
    private Integer hidden;

    @ApiModelProperty(value = "")
    private Integer sort;

    @ApiModelProperty(value = "")
    private Integer weight;

    @ApiModelProperty(value = "")
    private Integer isDefault;
    @ApiModelProperty(value = "status")
    private Integer status;

}