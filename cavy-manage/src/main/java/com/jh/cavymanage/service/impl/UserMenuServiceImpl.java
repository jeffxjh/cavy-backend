package com.jh.cavymanage.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.jh.cavymanage.domain.UserMenu;
import com.jh.cavymanage.mapper.UserMenuMapper;
import com.jh.cavymanage.service.UserMenuService;
@Service
public class UserMenuServiceImpl implements UserMenuService{

    @Resource
    private UserMenuMapper userMenuMapper;

    @Override
    public int insert(UserMenu record) {
        return userMenuMapper.insert(record);
    }

    @Override
    public int insertSelective(UserMenu record) {
        return userMenuMapper.insertSelective(record);
    }

}
