package com.jh.cavy.manage.client;

import com.jh.cavy.common.Result.ResultPage;
import com.jh.cavy.manage.api.RoleClient;
import com.jh.cavy.manage.param.RoleAddParam;
import com.jh.cavy.manage.param.RoleParam;
import com.jh.cavy.manage.service.RoleService;
import com.jh.cavy.manage.vo.RoleVO;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public record RoleClientImpl(RoleService roleService) implements RoleClient {
    @Override
    public List<RoleVO> getRoleByUserName(String userName){
        return roleService.getRoleByUserName(userName);
    }

    @Override
    public RoleVO getRole(String id){
        return roleService.getRole(Integer.valueOf(id));
    }

    @Override
    public List<RoleVO> roleList() {
        return roleService.roleList();
    }

    @Override
    public ResultPage<RoleVO> list(RoleParam roleParam) {
        return roleService.findRolePage(roleParam);
    }

    @Override
    public void add(RoleAddParam roleAddParam) {
        roleService.add(roleAddParam);
    }

    @Override
    public void update(RoleAddParam roleAddParam)  {
        roleService.update(roleAddParam);
    }

    @Override
    public void delete(List<String> ids){
        roleService.delete(ids);
    }
}
