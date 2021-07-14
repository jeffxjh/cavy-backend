package com.jh.cavymanage.service;

import com.jh.cavymanage.domain.User;
import com.jh.cavymanage.vo.UserVO;
import com.jh.cavymanage.web.Result.ResultPage;
import com.jh.cavymanage.web.param.UserParam;

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
