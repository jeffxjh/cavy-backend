package com.jh.cavybackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jh.cavybackend.domain.User;

public interface UserMapper extends BaseMapper<User> {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}