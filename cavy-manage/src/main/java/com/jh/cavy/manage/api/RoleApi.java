package com.jh.cavy.manage.api;


import com.jh.cavy.manage.service.RoleService;
import com.jh.cavy.manage.vo.RoleVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
