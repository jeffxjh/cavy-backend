package com.jh.cavy.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jh.cavy.manage.domain.Role;
import com.jh.cavy.manage.mapper.RoleMapper;
import com.jh.cavy.manage.service.RoleService;
import com.jh.cavy.manage.vo.RoleVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

}

