package com.jh.cavytask.service;

import com.jh.cavytask.params.JobParam;

public interface QuartzBusService {
    void addJob(JobParam jobParam);

    void stopJob(String id);

    void resumeJob(String id);

    void runJob(String id);

    void delJob(String id);

    void updateJob(JobParam jobParam);
}
