package com.jh.cavy.manage.param;

import cn.hutool.core.lang.tree.Tree;
import com.jh.cavy.manage.domain.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Validated
@Schema(name = "com-jh-manage-domain-role")
public class RoleAddParam extends Role {
    @Schema(name = "菜单树列表")
    private List<Tree<Integer>> menuTreeList;
}
