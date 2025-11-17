package com.jh.cavy.manage.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jh.cavy.common.Result.ResultPage;
import com.jh.cavy.common.exception.APIException;
import com.jh.cavy.common.mybatisPlus.PageUtil;
import com.jh.cavy.manage.domain.Role;
import com.jh.cavy.manage.domain.RoleMenu;
import com.jh.cavy.manage.mapper.RoleMapper;
import com.jh.cavy.manage.mapper.RoleMenuMapper;
import com.jh.cavy.manage.param.RoleAddParam;
import com.jh.cavy.manage.param.RoleParam;
import com.jh.cavy.manage.service.RoleService;
import com.jh.cavy.manage.vo.RoleVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    private final RoleMapper roleMapper;
    private final RoleMenuMapper roleMenuMapper;

    @Override
    public List<RoleVO> getRoleByUserName(String userName) {
        List<RoleVO> roleVOList = roleMapper.getRoleByUserId(userName);
        return roleVOList;
    }


    @Override
    public List<RoleVO> roleList() {
        LambdaQueryWrapper<Role> queryWrapper = Wrappers.lambdaQuery();
        return roleMapper.roleList(queryWrapper);
    }

    @Override
    public ResultPage<RoleVO> findRolePage(RoleParam roleParam) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        //Page<User> page = new Page<>(userParam.getPageIndex(),userParam.getPageSize());
        //Page<User> userPage = userMapper.selectPage(page, queryWrapper);
        queryWrapper.like("role_name", roleParam.getRoleName());
        Page<RoleVO> r = roleMapper.findByPage(PageUtil.newPage(roleParam), queryWrapper);
        return new ResultPage<>(r);
    }

    @Override
    public void add(RoleAddParam roleAddParam) {
        Role role = BeanUtil.copyProperties(roleAddParam, Role.class);
        List<Role> roleList = roleMapper.selectList(Wrappers.<Role>lambdaQuery().eq(Role::getRoleName, roleAddParam.getRoleName()));
        if (CollectionUtil.isNotEmpty(roleList)) {
            throw new APIException("角色名称已存在");
        }
        roleMapper.insert(role);
        List<Tree<Integer>> listTree = roleAddParam.getMenuTreeList();
        if (CollectionUtil.isNotEmpty(listTree)) {
            List<RoleMenu> roleMenuList = listTree.stream().map(tree -> {
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setMenuId(Long.valueOf(tree.getId()));
                roleMenu.setRoleId(Long.valueOf(role.getId()));
                return roleMenu;
            }).toList();
            roleMenuMapper.insert(roleMenuList);
        }
    }

    @Override
    public void update(RoleAddParam roleAddParam) {
        Role role = BeanUtil.copyProperties(roleAddParam, Role.class);
        roleMapper.updateById(role);
        List<Tree<Integer>> listTree = roleAddParam.getMenuTreeList();
        if (CollectionUtil.isNotEmpty(listTree)) {
            roleMenuMapper.delete(new LambdaQueryWrapper<RoleMenu>().eq(RoleMenu::getRoleId, role.getId()));
            List<RoleMenu> roleMenuList = listTree.stream().map(tree -> {
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setMenuId(Long.valueOf(tree.getId()));
                roleMenu.setRoleId(Long.valueOf(role.getId()));
                return roleMenu;
            }).toList();
            roleMenuMapper.insert(roleMenuList);
        }
    }

    @Override
    public RoleVO getRole(Integer id) {
        Role role = roleMapper.selectById(id);
        RoleVO roleVO=  BeanUtil.copyProperties(role, RoleVO.class);
        List<RoleMenu> roleMenuList = roleMenuMapper.selectList(Wrappers.<RoleMenu>lambdaQuery().eq(RoleMenu::getRoleId, role.getId()));
        roleVO.setSelectKeys(roleMenuList.stream().map(RoleMenu::getMenuId).collect(Collectors.toList()));
        return roleVO;
    }

    @Override
    public void delete(List<String> ids) {
        roleMapper.deleteByIds(ids);
        roleMenuMapper.delete(Wrappers.<RoleMenu>lambdaQuery().in(RoleMenu::getRoleId, ids));
    }

}

