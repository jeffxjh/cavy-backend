package com.jh.cavy.manage.api;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping("/business")
@RestController
@FeignClient(name = "cavy-manage", contextId = "businessClient", path = "business")
public interface BusinessClient {


    @GetMapping("/")
    Map<String, String> get(@RequestParam("uuid") String uuid, HttpServletRequest request) throws InterruptedException;


}
