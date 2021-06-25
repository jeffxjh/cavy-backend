package com.jh.cavybackend.mapper;

import com.jh.cavybackend.domain.UserMenu;

public interface UserMenuMapper {
    int insert(UserMenu record);

    int insertSelective(UserMenu record);
}