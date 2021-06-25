package com.jh.cavybackend.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.jh.cavybackend.domain.UserMenu;
import com.jh.cavybackend.mapper.UserMenuMapper;
import com.jh.cavybackend.service.UserMenuService;
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
