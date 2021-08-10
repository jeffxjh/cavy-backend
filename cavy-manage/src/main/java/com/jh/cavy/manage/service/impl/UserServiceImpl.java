package com.jh.cavy.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jh.cavy.manage.domain.User;
import com.jh.cavy.manage.mapper.UserMapper;
import com.jh.cavy.manage.service.UserService;
import com.jh.cavy.manage.vo.UserVO;
import com.jh.cavy.common.Result.ResultPage;
import com.jh.cavy.manage.param.UserParam;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(User record) {
        return userMapper.insert(record);
    }

    @Override
    public int insertSelective(User record) {
        return userMapper.insertSelective(record);
    }

    @Override
    public User selectByPrimaryKey(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(User record) {
        return userMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(User record) {
        return userMapper.updateByPrimaryKey(record);
    }

    @Override
    public User getByUserName(String userName) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", userName);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public ResultPage<UserVO> findUserPage(UserParam userParam) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //Page<User> page = new Page<>(userParam.getPageIndex(),userParam.getPageSize());
        //Page<User> userPage = userMapper.selectPage(page, queryWrapper);
        queryWrapper.like("user_name", userParam.getUserName());
        Page<UserVO> userVOPage = userMapper.findByPage(new Page<>(userParam.getPageIndex(), userParam.getPageSize()), queryWrapper);
        return new ResultPage<>(userVOPage);
    }

}
