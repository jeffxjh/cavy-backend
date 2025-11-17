package com.jh.cavy.manage.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jh.cavy.manage.domain.Role;
import com.jh.cavy.manage.param.RoleParam;
import com.jh.cavy.manage.vo.RoleVO;import org.apache.ibatis.annotations.Param;import java.util.List;

public interface RoleMapper extends BaseMapper<Role> {

    List<RoleVO> getRoleByUserId(@Param("userName") String userName);

    List<RoleVO> roleList(@Param(Constants.WRAPPER)  Wrapper<Role> queryWrapper);

    Page<RoleVO> findByPage(Page<RoleParam> roleParamPage, QueryWrapper<Role> queryWrapper);
}