package com.jh;

import com.jh.cavy.feign.Access;
import com.jh.cavy.feign.RestFeignClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public interface Test1Service  {
    @RequestMapping(path = { "test1" })
    String test();
}
