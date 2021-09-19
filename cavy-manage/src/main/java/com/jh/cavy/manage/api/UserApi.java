package com.jh.cavy.manage.api;

import cn.hutool.core.bean.BeanUtil;
import com.jh.cavy.cache.service.CacheService;
import com.jh.cavy.common.Result.ResultPage;
import com.jh.cavy.common.Result.ResultVO;
import com.jh.cavy.jwt.jwt.JwtProperties;
import com.jh.cavy.jwt.jwt.JwtTokenUtil;
import com.jh.cavy.jwt.jwt.JwtUser;
import com.jh.cavy.manage.domain.User;
import com.jh.cavy.manage.param.LoginParam;
import com.jh.cavy.manage.param.UserParam;
import com.jh.cavy.manage.service.UserService;
import com.jh.cavy.manage.vo.UserInfoVO;
import com.jh.cavy.manage.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequestMapping("/user")
@RestController
public class UserApi {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Resource
    private UserService userService;
    @Resource
    private CacheService redisHandle;
    @Resource
    private JwtProperties jwtProperties;

    //@GetMapping("/login")
    //public String auth0Login(@RequestParam String userName, @RequestParam String passWord, HttpServletResponse response) {
    //    String token = "";
    //    User user = userService.getByUserName(userName);
    //    if (user != null) {
    //        token = JwtUtil.createToken(user);
    //        response.addCookie(new Cookie("token", token));
    //    }
    //    return token;
    //}

    @PostMapping("/login")
    public ResultVO<UserInfoVO> jjwtLogin(@RequestBody LoginParam loginParam, HttpServletRequest request, HttpServletResponse response) {
        String token = "";
        User user = userService.getByUserName(loginParam.getUsername());
        if (user != null) {
            JwtUser jwtUser = new JwtUser();
            jwtUser.setAccount(user.getUserName());
            jwtUser.setUsername(user.getRealName());
            jwtUser.setId((Long.valueOf(user.getId())));
            token = jwtTokenUtil.generateToken(jwtUser);
            //boolean b = jwtTokenUtil.validateToken(token, jwtUser);
            response.addCookie(new Cookie("token", token));
            redisHandle.hset(jwtProperties.getRedisKey(), token, jwtUser, jwtProperties.getTokenValidityInSeconds());
            UserInfoVO userInfoVO = BeanUtil.copyProperties(user, UserInfoVO.class);
            userInfoVO.setToken(token);
            return new ResultVO<>(1000, "登录成功", userInfoVO);
        } else {
            return new ResultVO<>(2000, "用户不存在", new UserInfoVO());
        }
    }

    //@GetMapping("/login4")
    //public String login4(@RequestBody Map<String, Object> params) {
    //    String token = "";
    //    String name = StrUtil.toString(params.get("name"));
    //    int length = name.length();
    //    return token;
    //}

    @GetMapping()
    public ResultPage<UserVO> list(@ModelAttribute UserParam userParam) {
        return userService.findUserPage(userParam);
    }
}
