package com.jh.cavy.manage.api;


import com.jh.cavy.common.Result.ResultPage;
import com.jh.cavy.manage.param.RoleParam;
import com.jh.cavy.manage.param.UserParam;
import com.jh.cavy.manage.service.RoleService;
import com.jh.cavy.manage.vo.RoleVO;
import com.jh.cavy.manage.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RequestMapping("/role")
@RestController
public class RoleApi {
    @Resource
    private RoleService roleService;


    @GetMapping("/getRoleByUserName")
    public List<RoleVO> getRoleByUserName(@RequestParam String userName) {
        return roleService.getRoleByUserName(userName);
    }

    @GetMapping("/getRoleByMenuId")
    public List<RoleVO> getRoleByMenuId(@RequestParam String menuId) {
        return roleService.getRoleByMenuId(menuId);
    }

    @PostMapping
    public List<RoleVO> roleList() {
        return roleService.roleList();
    }
    @GetMapping()
    public ResultPage<RoleVO> list(@ModelAttribute RoleParam roleParam) {
        return roleService.findRolePage(roleParam);
    }
}
