package com.jh.cavy.task.business.checkin;

import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@Slf4j
public class TaskCheckin implements Job {
    public void run() {
        BusinessHelper businessHelper = SpringUtil.getBean(BusinessHelper.class);
        businessHelper.doThing();
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        run();
    }
}