package com.jh.cavy.manage.management;

import lombok.extern.slf4j.Slf4j;

import jakarta.annotation.PreDestroy;
@Slf4j
public class TerminateBean {
    @PreDestroy
    public void preDestroy() {
        log.info("TerminalBean is destroyed");
    }
}
