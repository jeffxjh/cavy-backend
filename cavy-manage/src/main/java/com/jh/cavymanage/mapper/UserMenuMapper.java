package com.jh.cavymanage.mapper;

import com.jh.cavymanage.domain.UserMenu;

public interface UserMenuMapper {
    int insert(UserMenu record);

    int insertSelective(UserMenu record);
}