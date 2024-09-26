package com.jh.cavy.favour.vo;

import com.jh.cavy.common.Resquest.BaseParam;
import com.jh.cavy.common.mybatisPlus.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class FavourRelativeVO extends BaseEntity {
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
    private String relateTypeName;
    /**
     * 关联当前用户表主键(是谁的亲友)
     */
    @Schema(name = "关联当前用户表主键(是谁的亲友)")
    private Integer userId;

    @Schema(name = "关联当前用户表主键(是谁的亲友)")
    private String userName;

    /**
     * 亲友关联的用户主键(该亲友在系统中的用户主键)
     */
    @Schema(name = " 亲友关联的用户主键(该亲友在系统中的用户主键)")
    private Integer relateUserId;

    @Schema(name = " 亲友关联的用户主键(该亲友在系统中的用户主键)")
    private String relateUserName;
    private String relateUserNameFirstChar;
}
