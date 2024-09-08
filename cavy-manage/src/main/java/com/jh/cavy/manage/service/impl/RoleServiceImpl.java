package com.jh.cavy.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jh.cavy.common.Result.ResultPage;
import com.jh.cavy.common.mybatisPlus.PageUtil;
import com.jh.cavy.manage.domain.Role;
import com.jh.cavy.manage.domain.User;
import com.jh.cavy.manage.mapper.RoleMapper;
import com.jh.cavy.manage.param.RoleParam;
import com.jh.cavy.manage.service.RoleService;
import com.jh.cavy.manage.vo.RoleVO;
import com.jh.cavy.manage.vo.UserVO;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return roleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Role record) {
        return roleMapper.insert(record);
    }

    @Override
    public int insertSelective(Role record) {
        return roleMapper.insertSelective(record);
    }

    @Override
    public Role selectByPrimaryKey(Integer id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Role record) {
        return roleMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Role record) {
        return roleMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<RoleVO> getRoleByUserName(String userName) {
        List<RoleVO> roleVOList = roleMapper.getRoleByUserId(userName);
        return roleVOList;
    }

    @Override
    public List<RoleVO> getRoleByMenuId(String menuId) {
        LambdaQueryWrapper<Role> queryWrapper = Wrappers.lambdaQuery();
        return roleMapper.getRoleByMenuId(queryWrapper);
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

}

