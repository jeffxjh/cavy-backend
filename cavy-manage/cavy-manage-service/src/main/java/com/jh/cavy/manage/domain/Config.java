package com.jh.cavy.manage.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jh.cavy.common.mybatisPlus.BaseEntity;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 系统配置表
 */
@Schema(name = "com-jh-cavy-manage-domain-Config")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_sys_config")
public class Config extends BaseEntity {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @Schema(name = "")
    private Integer id;

    /**
     * 编号
     */
    @Schema(name = "编号")
    private String code;

    /**
     * 编号名称
     */
    @Schema(name = "编号名称")
    private String codeValue;

    /**
     * 名称
     */
    @Schema(name = "名称")
    private String name;

}