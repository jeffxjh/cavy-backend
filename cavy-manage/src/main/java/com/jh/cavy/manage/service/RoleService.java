package com.jh.cavy.manage.service;

import com.jh.cavy.common.Result.ResultPage;
import com.jh.cavy.manage.domain.Role;
import com.jh.cavy.manage.param.RoleParam;
import com.jh.cavy.manage.vo.RoleVO;
import com.jh.cavy.manage.vo.UserVO;

import java.util.List;

public interface RoleService {


    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    List<RoleVO> getRoleByUserName(String userName);

    List<RoleVO> getRoleByMenuId(String menuId);

    List<RoleVO> roleList();

    ResultPage<RoleVO> findRolePage(RoleParam roleParam);
}

