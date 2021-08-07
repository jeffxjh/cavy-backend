package com.jh.cavy.manage.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.jh.cavy.manage.domain.UserMenu;
import com.jh.cavy.manage.mapper.UserMenuMapper;
import com.jh.cavy.manage.service.UserMenuService;
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
