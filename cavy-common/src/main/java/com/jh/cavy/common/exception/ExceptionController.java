package com.jh.cavy.common.exception;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class ExceptionController {
    @GetMapping("/expiredjwtexception")
    public void expiredJwtException(HttpServletRequest request) {
        throw new ExpiredJwtException();
    }
}
