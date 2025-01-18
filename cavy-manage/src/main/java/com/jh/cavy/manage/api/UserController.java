package com.jh.cavy.manage.api;

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
import com.jh.cavy.manage.config.WeixinProperties;
import com.jh.cavy.manage.domain.User;
import com.jh.cavy.manage.param.LoginParam;
import com.jh.cavy.manage.param.UserParam;
import com.jh.cavy.manage.service.RoleService;
import com.jh.cavy.manage.service.UserService;
import com.jh.cavy.manage.vo.RoleVO;
import com.jh.cavy.manage.vo.UserInfoVO;
import com.jh.cavy.manage.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RequestMapping("/user")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;
    private final RoleService roleService;
    private final CacheService cacheService;
    private final JwtProperties jwtProperties;
    private final WeixinProperties weixinProperties;

    @PostMapping("/login")
    public ResultVO<UserInfoVO> jjwtLogin(@RequestBody LoginParam loginParam, HttpServletResponse response) {
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

    public String getOpenId(String code) {
        String requestUrl = weixinProperties.getGetOpenidUrl() + "?appid=" + weixinProperties.getAppId() + "&secret=" + weixinProperties.getSecretId() + "&js_code=" + code + "&grant_type=authorization_code";
        HttpRequest get = HttpUtil.createGet(requestUrl);
        HttpResponse response = get.execute();
        return response.body();
    }

    @PostMapping("/wechat/doLogin")
    public ResultVO<UserInfoVO> wechatDoLogin(@RequestBody LoginParam loginParam, HttpServletResponse response) {
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

    @GetMapping()
    public ResultPage<UserVO> list(@ModelAttribute UserParam userParam) {
        return userService.findUserPage(userParam);
    }

    @PostMapping()
    public void addUser(@RequestBody UserParam userParam) {
        userService.addUser(userParam);
    }

    @GetMapping("getById")
    public UserInfoVO getUser(@RequestParam("id") String id) {
        return userService.getUser(Integer.valueOf(id));
    }

    @DeleteMapping()
    public void deleteUser(@RequestParam("ids") List<String> ids) {
        userService.deleteUser(ids);
    }

    @PutMapping()
    public void updateUser(@RequestBody UserParam userParam) {
        userService.updateUser(userParam);
    }

    @GetMapping("/export")
    public void export(@ModelAttribute UserParam userParam, HttpServletResponse response) throws IOException {
        userService.export(userParam, response);
    }

    @PostMapping("/import")
    public Map<String, Integer> importUser(@RequestParam("file") MultipartFile file) {
        return userService.importUser(file);
    }
}
