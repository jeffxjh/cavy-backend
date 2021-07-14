package com.jh.cavymanage.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jh.cavymanage.domain.User;
import com.jh.cavymanage.vo.UserVO;
import org.apache.ibatis.annotations.Param;

public interface UserMapper extends BaseMapper<User> {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    Page<UserVO> findByPage(Page<UserVO> page,  @Param(Constants.WRAPPER)QueryWrapper<User> queryWrapper);
}