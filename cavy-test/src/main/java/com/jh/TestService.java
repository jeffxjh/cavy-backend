package com.jh;

import com.jh.cavy.feign.Access;
import com.jh.cavy.feign.RestFeignClient;
import org.springframework.stereotype.Service;


@RestFeignClient(path = "/testService",name = "TestService",isMicro = true)
public interface TestService {

    @Access(type = "test",path = {"test2"})
    String test();
}
