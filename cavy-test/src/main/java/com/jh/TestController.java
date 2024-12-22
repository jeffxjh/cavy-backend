package com.jh;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @RequestMapping(path = {"test"})
    String test() {
        return "aaaaaaa";
    }

    ;
}
