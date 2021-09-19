package com.jh.cavy.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jh.cavy.manage.domain.UserMenu;

public interface UserMenuMapper  extends BaseMapper<UserMenu> {
    int insert(UserMenu record);

    int insertSelective(UserMenu record);
}