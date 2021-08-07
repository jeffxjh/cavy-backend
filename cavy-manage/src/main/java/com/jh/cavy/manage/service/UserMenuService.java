package com.jh.cavy.manage.service;

import com.jh.cavy.manage.domain.UserMenu;
public interface UserMenuService{


    int insert(UserMenu record);

    int insertSelective(UserMenu record);

}
