package com.jh.cavycore.common.exception;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ExceptionController {
    @GetMapping("/expiredjwtexception")
    public void expiredJwtException(HttpServletRequest request) {
        throw new ExpiredJwtException();
    }
}
