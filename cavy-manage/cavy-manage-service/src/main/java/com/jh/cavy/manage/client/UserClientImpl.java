package com.jh.cavy.manage.client;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.jh.cavy.cache.service.CacheService;
import com.jh.cavy.common.Result.ResultPage;
import com.jh.cavy.common.Result.ResultVO;
import com.jh.cavy.jwt.JwtProperties;
import com.jh.cavy.jwt.JwtTokenUtil;
import com.jh.cavy.jwt.JwtUser;
import com.jh.cavy.manage.api.UserClient;
import com.jh.cavy.manage.domain.User;
import com.jh.cavy.manage.param.LoginParam;
import com.jh.cavy.manage.param.UserParam;
import com.jh.cavy.manage.service.RoleService;
import com.jh.cavy.manage.service.UserService;
import com.jh.cavy.manage.vo.RoleVO;
import com.jh.cavy.manage.vo.UserInfoVO;
import com.jh.cavy.manage.vo.UserVO;
import com.jh.cavy.manage.config.WeixinProperties;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public record UserClientImpl(JwtTokenUtil jwtTokenUtil, UserService userService, RoleService roleService, CacheService cacheService,
                             JwtProperties jwtProperties, WeixinProperties weixinProperties) implements UserClient {
    @Override
    public ResultVO<UserInfoVO> jjwtLogin(LoginParam loginParam, HttpServletResponse response) {
        String token;
        User user = userService.getByUserName(loginParam.getUsername());
        if (user != null) {
            if (!loginParam.getPwd().equals(user.getPwd())) {
                return new ResultVO<>(2000, "账号或密码错误", new UserInfoVO());
            }
            JwtUser jwtUser = new JwtUser();
            jwtUser.setAccount(user.getUserName());
            jwtUser.setUsername(user.getRealName());
            jwtUser.setId((Long.valueOf(user.getId())));
            token = jwtTokenUtil.generateToken(jwtUser);
            //boolean b = jwtTokenUtil.validateToken(token, jwtUser);
            response.addCookie(new Cookie("token", token));
            cacheService.hset(jwtProperties.getCacheKey(), token, jwtUser, jwtProperties.getTokenValidityInSeconds());
            UserInfoVO userInfoVO = BeanUtil.copyProperties(user, UserInfoVO.class);
            userInfoVO.setToken(token);
            List<RoleVO> roleVOList = roleService.getRoleByUserName(userInfoVO.getUserName());
            userInfoVO.setRoleList(roleVOList);
            return new ResultVO<>(1000, "登录成功", userInfoVO);
        } else {
            return new ResultVO<>(2000, "用户不存在", new UserInfoVO());
        }
    }

    @Override
    public String getOpenId(String code) {
        String requestUrl = weixinProperties.getGetOpenidUrl() + "?appid=" + weixinProperties.getAppId() + "&secret=" + weixinProperties.getSecretId() + "&js_code=" + code + "&grant_type=authorization_code";
        HttpRequest get = HttpUtil.createGet(requestUrl);
        HttpResponse response = get.execute();
        return response.body();
    }

    @Override
    public ResultVO<UserInfoVO> wechatDoLogin(LoginParam loginParam, HttpServletResponse response)  {
        if (StringUtils.isBlank(loginParam.getCode())) {
            return new ResultVO<>(2000, "认证code为空", new UserInfoVO());
        }
        //并不是真的openid是code
        String openIdJson = getOpenId(loginParam.getCode());
        if (StringUtils.isNotBlank(openIdJson)) {
            Object openid = JSONUtil.parseObj(openIdJson).get("openid");
            if (openid == null) {
                return new ResultVO<>(2000, "认证code无效", new UserInfoVO());
            }
            loginParam.setOpenid(openid.toString());
        } else {
            return new ResultVO<>(2000, "用户登录失败未获取到openid", new UserInfoVO());
        }

        //有token直接返回
        JwtUser jwtUser = jwtTokenUtil.validateToken(loginParam.getToken());
        if (jwtUser != null) {
            UserInfoVO userInfoVO = new UserInfoVO();
            BeanUtil.copyProperties(jwtUser, userInfoVO);
            userInfoVO.setToken(loginParam.getToken());
            return new ResultVO<>(1000, "success", userInfoVO);
        }
        //无token 用户不存在 创建用户
        User user = userService.getByOpenid(loginParam.getOpenid());
        if (user == null) {
            UserParam userParam = new UserParam();
            userParam.setOpenid(loginParam.getOpenid());
            userParam.setUserName(loginParam.getOpenid());
            userParam.setStatus("1");
            user = userService.addUser(userParam);
        }

        jwtUser = new JwtUser();
        jwtUser.setAccount(loginParam.getOpenid());
        jwtUser.setUsername(loginParam.getOpenid());
        jwtUser.setId(Long.valueOf(user.getId()));
        String token = jwtTokenUtil.generateToken(jwtUser);
        response.addCookie(new Cookie("token", token));
        cacheService.hset(jwtProperties.getCacheKey(), token, jwtUser, jwtProperties.getTokenValidityInSeconds());
        UserInfoVO userInfoVO = new UserInfoVO();
        BeanUtil.copyProperties(jwtUser, userInfoVO);
        userInfoVO.setToken(token);
        return new ResultVO<>(userInfoVO);
    }

    @Override
    public ResultPage<UserVO> list(UserParam userParam) {
        return userService.findUserPage(userParam);
    }

    @Override
    public void addUser(UserParam userParam) {
        userService.addUser(userParam);
    }

    @Override
    public UserInfoVO getUser(String id) {
        return userService.getUser(Integer.valueOf(id));
    }

    @Override
    public void deleteUser(List<String> ids)  {
        userService.deleteUser(ids);
    }

    @Override
    public void updateUser(UserParam userParam) {
        userService.updateUser(userParam);
    }

    @Override
    public void export(UserParam userParam, HttpServletResponse response)throws IOException {
        userService.export(userParam, response);
    }

    @Override
    public Map<String, Integer> importUser(MultipartFile file) {
        return userService.importUser(file);
    }
}
