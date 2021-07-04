package com.jh.cavybackend.service;

import com.jh.cavybackend.domain.User;
import com.jh.cavybackend.vo.UserVO;
import com.jh.cavybackend.web.Result.ResultPage;
import com.jh.cavybackend.web.param.UserParam;

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
