package com.jh.cavy.manage.dto;

import com.jh.cavy.common.mybatisPlus.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RoleDTO extends BaseEntity {
    private Integer id;

    /**
     * 角色名称
     */
    private String roleName;


}
