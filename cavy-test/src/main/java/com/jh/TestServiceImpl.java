package com.jh;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
@Service
public class TestServiceImpl implements TestService,Test1Service {
    @Override
    public String test() {
        System.out.println("哈哈 恭喜你 成功啦");
        return "哈哈 恭喜你 成功啦";
    }
}
