package com.jh.cavy.manage.vo;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class RoleVO {
    /**
     * 角色主键
     */
    @Schema(name = "角色主键")
    private Integer id;
    /**
     * 角色名称
     */
    @Schema(name = "角色名称")
    private String roleName;
    /**
     * 创建时间
     */
    @Schema(name = "创建时间")
    private Date addTime;

    /**
     * 创建人
     */
    @Schema(name = "创建人")
    private String addUser;

    /**
     * 修改时间
     */
    @Schema(name = "修改时间")
    private Date updateTime;

    /**
     * 修改人
     */
    @Schema(name = "修改人")
    private String updateUser;

    @Schema(name = "")
    private List<MenuVO> children;

    @Schema(name = "选中的菜单ID")
    private List<Long> selectKeys;

}
