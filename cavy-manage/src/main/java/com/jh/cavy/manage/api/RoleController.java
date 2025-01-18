package com.jh.cavy.manage.api;


import com.jh.cavy.common.Result.ResultPage;
import com.jh.cavy.manage.param.MenuAddParam;
import com.jh.cavy.manage.param.RoleAddParam;
import com.jh.cavy.manage.param.RoleParam;
import com.jh.cavy.manage.service.RoleService;
import com.jh.cavy.manage.vo.RoleVO;
import com.jh.cavy.manage.vo.UserInfoVO;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;

import java.util.List;

@Slf4j
@Tag(name = "角色相关API")
@RequestMapping("/role")
@RestController
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @Schema(name = "根据用户名称获取角色列表")
    @GetMapping("/getRoleByUserName")
    public List<RoleVO> getRoleByUserName(@RequestParam("userName") String userName) {
        return roleService.getRoleByUserName(userName);
    }

    @GetMapping("getById")
    public RoleVO getRole(@RequestParam("id") String id) {
        return roleService.getRole(Integer.valueOf(id));
    }


    @Schema(name = "获取全量角色列表")
    @PostMapping
    public List<RoleVO> roleList() {
        return roleService.roleList();
    }

    @Schema(name = "获取角色分页列表")
    @GetMapping()
    public ResultPage<RoleVO> list(@ModelAttribute RoleParam roleParam) {
        return roleService.findRolePage(roleParam);
    }

    @Schema(name = "新增角色")
    @PostMapping("add")
    public void add(@Valid @RequestBody RoleAddParam roleAddParam) {
        roleService.add(roleAddParam);
    }

    @Schema(name = "修改角色")
    @PostMapping("update")
    public void update(@Valid @RequestBody RoleAddParam roleAddParam) {
        roleService.update(roleAddParam);
    }
    @DeleteMapping()
    public void delete(@RequestParam("ids") List<String> ids) {
        roleService.delete(ids);
    }

}
