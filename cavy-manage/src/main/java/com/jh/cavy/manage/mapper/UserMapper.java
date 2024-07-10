package com.jh.cavy.manage.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jh.cavy.manage.domain.User;
import com.jh.cavy.manage.param.UserParam;
import com.jh.cavy.manage.vo.UserInfoVO;
import com.jh.cavy.manage.vo.UserVO;
import org.apache.ibatis.annotations.Param;

public interface UserMapper extends BaseMapper<User> {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    Page<UserVO> findByPage(Page<UserParam> page, @Param(Constants.WRAPPER) QueryWrapper<User> queryWrapper);

    UserInfoVO getUser(Integer id);

    void insertRoleRelate(@Param("userId") Integer userId,@Param("roleId")Integer roleId);

    void deleteRoleReate(@Param("userId") Integer id);
}