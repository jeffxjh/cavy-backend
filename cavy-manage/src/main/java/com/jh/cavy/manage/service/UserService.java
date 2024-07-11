package com.jh.cavy.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jh.cavy.common.Result.ResultPage;
import com.jh.cavy.manage.domain.User;
import com.jh.cavy.manage.param.UserParam;
import com.jh.cavy.manage.vo.UserInfoVO;
import com.jh.cavy.manage.vo.UserVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface UserService extends IService<User> {


    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User getByUserName(String userName);

    ResultPage<UserVO> findUserPage(UserParam userParam);

    void export(UserParam userParam, HttpServletResponse response) throws IOException;

    Map<String,Integer> importUser(MultipartFile multipartFile);

    void addUser(UserParam userParam);

    void deleteUser(List<String> ids);

    UserInfoVO getUser(Integer id);

    void updateUser(UserParam userParam);
}

