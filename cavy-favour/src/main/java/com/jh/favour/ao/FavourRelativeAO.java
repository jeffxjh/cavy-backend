package com.jh.favour.ao;

import com.jh.cavy.common.Resquest.BaseParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FavourRelativeAO extends BaseParam {
    @Schema(name = "id")
    private Integer id;

    /**
     * 亲友名称
     */
    @Schema(name = "亲友名称")
    private String realName;

    /**
     * 亲友昵称
     */
    @Schema(name = "亲友昵称")
    private String nickName;
    /**
     * 亲友关系
     */
    @Schema(name = "亲友关系")
    private Integer relateType;
    /**
     * 关联当前用户表主键(是谁的亲友)
     */
    @Schema(name = "关联当前用户表主键(是谁的亲友)")
    private Integer userId;

    /**
     * 亲友关联的用户主键(该亲友在系统中的用户主键)
     */
    @Schema(name = " 亲友关联的用户主键(该亲友在系统中的用户主键)")
    private Integer relateUserId;
}
