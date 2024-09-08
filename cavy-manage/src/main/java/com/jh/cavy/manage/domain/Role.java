package com.jh.cavy.manage.domain;

import com.jh.cavy.common.mybatisPlus.BaseEntity;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 角色表
 */
@Schema(name = "com-jh-cavy-manage-domain-Role")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Role extends BaseEntity {
    /**
     * id
     */
    @Schema(name = "id")
    private Integer id;

    /**
     * 角色名称
     */
    @Schema(name = "角色名称")
    private String roleName;



}