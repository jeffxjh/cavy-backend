package com.jh.cavybackend.service;

import com.jh.cavybackend.domain.UserMenu;
public interface UserMenuService{


    int insert(UserMenu record);

    int insertSelective(UserMenu record);

}
