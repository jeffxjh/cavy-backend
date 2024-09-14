package com.jh.cavy.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jh.cavy.common.Result.ResultPage;
import com.jh.cavy.manage.domain.Message;
import com.jh.cavy.manage.domain.Role;
import com.jh.cavy.manage.param.RoleAddParam;
import com.jh.cavy.manage.param.RoleParam;
import com.jh.cavy.manage.vo.RoleVO;
import com.jh.cavy.manage.vo.UserVO;
import jakarta.validation.Valid;

import java.util.List;

public interface RoleService extends IService<Role> {
    List<RoleVO> getRoleByUserName(String userName);

    List<RoleVO> roleList();

    ResultPage<RoleVO> findRolePage(RoleParam roleParam);

    void add(@Valid RoleAddParam roleAddParam);

    void update(@Valid RoleAddParam roleAddParam);

    RoleVO getRole(Integer id);

    void delete(List<String> ids);
}

