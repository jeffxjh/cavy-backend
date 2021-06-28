package com.jh.cavybackend.api;

import com.jh.cavybackend.domain.User;
import com.jh.cavybackend.jwt.JwtTokenUtil;
import com.jh.cavybackend.jwt.JwtUser;
import com.jh.cavybackend.jwt.JwtUtil;
import com.jh.cavybackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/login")
    public String login(@RequestParam String userName, @RequestParam String passWord, HttpServletResponse response) {
        String token = "";
        User user = userService.getByUserName(userName);
        if (user != null) {
            token = JwtUtil.createToken(user);
            response.addCookie(new Cookie("token", token));
        }
        return token;
    }

    @GetMapping("/login2")
    public String login2(@RequestParam String userName, @RequestParam String passWord, HttpServletRequest request, HttpServletResponse response) {
        String token = "";
        User user = userService.getByUserName(userName);
        if (user != null) {
            JwtUser jwtUser = new JwtUser();
            jwtUser.setAccount("zhangsan");
            jwtUser.setUsername("zhangsna");
            token = jwtTokenUtil.generateToken(jwtUser);
            boolean b = jwtTokenUtil.validateToken(token, jwtUser);
            response.addCookie(new Cookie("token", token));
        }
        return token;
    }
}
