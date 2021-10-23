package com.jh.cavy.task.service;

import com.jh.cavy.common.Result.ResultPage;
import com.jh.cavy.task.domain.Task;
import com.jh.cavy.task.params.JobPageParam;
import com.jh.cavy.task.params.JobParam;

public interface QuartzBusService {
    void addJob(JobParam jobParam);

    void stopJob(String id);

    void resumeJob(String id);

    void runJob(String id);

    void delJob(String id);

    void updateJob(JobParam jobParam);

    ResultPage<Task> page(JobPageParam jobPageParam);

}
