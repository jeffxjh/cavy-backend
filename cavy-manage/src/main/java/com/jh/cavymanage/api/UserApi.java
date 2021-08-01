package com.jh.cavymanage.api;

import cn.hutool.core.bean.BeanUtil;
import com.jh.cavymanage.domain.User;
import com.jh.cavycore.jwt.JwtProperties;
import com.jh.cavycore.jwt.JwtTokenUtil;
import com.jh.cavycore.jwt.JwtUser;
import com.jh.cavycore.redis.RedisHandle;
import com.jh.cavymanage.service.UserService;
import com.jh.cavymanage.vo.UserInfoVO;
import com.jh.cavymanage.vo.UserVO;
import com.jh.cavycore.common.Result.ResultPage;
import com.jh.cavycore.common.Result.ResultVO;
import com.jh.cavymanage.param.LoginParam;
import com.jh.cavymanage.param.UserParam;
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
    private RedisHandle redisHandle;
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
            token = jwtTokenUtil.generateToken(jwtUser);
            //boolean b = jwtTokenUtil.validateToken(token, jwtUser);
            response.addCookie(new Cookie("token", token));
            redisHandle.hset(jwtProperties.getRedisKey(), token, jwtUser,jwtProperties.getTokenValidityInSeconds());
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
