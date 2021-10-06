package com.jh.cavy.manage.service;

import com.jh.cavy.manage.domain.User;
import com.jh.cavy.manage.vo.UserVO;
import com.jh.cavy.common.Result.ResultPage;
import com.jh.cavy.manage.param.UserParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public interface UserService {


    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User getByUserName(String userName);

    ResultPage<UserVO> findUserPage(UserParam userParam);

    void export(UserParam userParam, HttpServletResponse response);

    void importUser(MultipartFile multipartFile);
}

