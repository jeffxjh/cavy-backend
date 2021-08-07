package com.jh.cavy.manage.service;

import com.jh.cavy.manage.domain.User;
import com.jh.cavy.manage.vo.UserVO;
import com.jh.cavy.jwt.common.Result.ResultPage;
import com.jh.cavy.manage.param.UserParam;

public interface UserService{


    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User getByUserName(String userName);

    ResultPage<UserVO> findUserPage(UserParam userParam);
}
