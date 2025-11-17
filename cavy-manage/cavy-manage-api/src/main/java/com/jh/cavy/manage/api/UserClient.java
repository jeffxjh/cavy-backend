package com.jh.cavy.manage.api;

import com.jh.cavy.common.Result.ResultPage;
import com.jh.cavy.common.Result.ResultVO;
import com.jh.cavy.manage.param.LoginParam;
import com.jh.cavy.manage.param.UserParam;
import com.jh.cavy.manage.vo.UserInfoVO;
import com.jh.cavy.manage.vo.UserVO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RequestMapping("/user")
@RestController
@FeignClient(name = "cavy-manage", contextId = "userClient", path = "user")
public interface UserClient {

    @PostMapping("/login")
    ResultVO<UserInfoVO> jjwtLogin(@RequestBody LoginParam loginParam, HttpServletResponse response);

    String getOpenId(String code);

    @PostMapping("/wechat/doLogin")
    ResultVO<UserInfoVO> wechatDoLogin(@RequestBody LoginParam loginParam, HttpServletResponse response);

    @GetMapping()
    ResultPage<UserVO> list(@ModelAttribute UserParam userParam);

    @PostMapping()
    void addUser(@RequestBody UserParam userParam);

    @GetMapping("getById")
    UserInfoVO getUser(@RequestParam("id") String id);

    @DeleteMapping()
    void deleteUser(@RequestParam("ids") List<String> ids);

    @PutMapping()
    void updateUser(@RequestBody UserParam userParam);

    @GetMapping("/export")
    void export(@ModelAttribute UserParam userParam, HttpServletResponse response) throws IOException;

    @PostMapping("/import")
    Map<String, Integer> importUser(@RequestParam("file") MultipartFile file);
}
