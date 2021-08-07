package com.jh.cavy.task.business;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

@Slf4j
public class TaskTest implements Job {

    public void run() {
        for (int i = 0; i < 10; i++) {
            log.info(i+" run ################## " + (new Date()));
        }
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        run();
    }
}