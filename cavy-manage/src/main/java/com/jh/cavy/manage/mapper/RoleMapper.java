package com.jh.cavy.manage.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jh.cavy.manage.domain.Role;
import com.jh.cavy.manage.param.RoleParam;
import com.jh.cavy.manage.vo.RoleVO;import org.apache.ibatis.annotations.Param;import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    List<RoleVO> getRoleByUserId(@Param("userName") String userName);

    List<RoleVO> getRoleByMenuId(@Param(Constants.WRAPPER) Wrapper<Role> queryWrapper);

    List<RoleVO> roleList(@Param(Constants.WRAPPER)  Wrapper<Role> queryWrapper);

    Page<RoleVO> findByPage(Page<RoleParam> roleParamPage, QueryWrapper<Role> queryWrapper);
}