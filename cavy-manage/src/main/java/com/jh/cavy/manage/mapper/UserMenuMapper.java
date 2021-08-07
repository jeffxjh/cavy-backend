package com.jh.cavy.manage.mapper;

import com.jh.cavy.manage.domain.UserMenu;

public interface UserMenuMapper {
    int insert(UserMenu record);

    int insertSelective(UserMenu record);
}