package com.jh;

import com.jh.cavy.feign.Command;
import com.jh.cavy.feign.CommandTarget;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;

@CommandTarget
public interface TestService {
    @Command(type = "")
    void test();
}
