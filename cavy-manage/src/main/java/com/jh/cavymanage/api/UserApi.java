package com.jh.cavymanage.api;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.jh.cavymanage.domain.User;
import com.jh.cavymanage.jwt.JwtTokenUtil;
import com.jh.cavymanage.jwt.JwtUser;
import com.jh.cavymanage.service.UserService;
import com.jh.cavymanage.vo.UserInfoVO;
import com.jh.cavymanage.vo.UserVO;
import com.jh.cavymanage.web.Result.ResultPage;
import com.jh.cavymanage.web.Result.ResultVO;
import com.jh.cavymanage.web.param.LoginParam;
import com.jh.cavymanage.web.param.UserParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RequestMapping("/user")
@RestController
public class UserApi {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Resource
    private UserService userService;

    //@GetMapping("/login")
    //public String login(@RequestParam String userName, @RequestParam String passWord, HttpServletResponse response) {
    //    String token = "";
    //    User user = userService.getByUserName(userName);
    //    if (user != null) {
    //        token = JwtUtil.createToken(user);
    //        response.addCookie(new Cookie("token", token));
    //    }
    //    return token;
    //}

    @PostMapping("/login")
    public ResultVO<UserInfoVO> login(@RequestBody LoginParam loginParam, HttpServletRequest request, HttpServletResponse response) {
        String token = "";
        User user = userService.getByUserName(loginParam.getUsername());
        if (user != null) {
            JwtUser jwtUser = new JwtUser();
            jwtUser.setAccount(user.getUserName());
            jwtUser.setUsername(user.getRealName());
            token = jwtTokenUtil.generateToken(jwtUser);
            //boolean b = jwtTokenUtil.validateToken(token, jwtUser);
            response.addCookie(new Cookie("token", token));
            UserInfoVO userInfoVO = BeanUtil.copyProperties(user, UserInfoVO.class);
            userInfoVO.setToken(token);
            return new ResultVO<>(1000, "登录成功", userInfoVO);
        } else {
            return new ResultVO<>(2000, "用户不存在", new UserInfoVO());
        }
    }

    @GetMapping("/login4")
    public String login4(@RequestBody Map<String, Object> params) {
        String token = "";
        String name = StrUtil.toString(params.get("name"));
        int length = name.length();
        return token;
    }

    @GetMapping("")
    public ResultPage<UserVO> list(@ModelAttribute UserParam userParam) {
        return userService.findUserPage(userParam);
    }
}
