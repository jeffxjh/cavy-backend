package com.jh.cavytask.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class QuartzRunner implements ApplicationRunner {
    //@Autowired
    //private ScheduleJobService scheduleJobService;

    @Override
    //项目启动时重新激活启用的定时任务
    public void run(ApplicationArguments applicationArguments) throws Exception {
        //ScheduleJobForm scheduleJobForm = new ScheduleJobForm();
        //scheduleJobForm.setDeleteFlag(DataStatus.DEFAULT.getStatus());
        //scheduleJobForm.setJobStatus(1);
        //List<ScheduleJobForm> scheduleJobList = scheduleJobService.selectList(scheduleJobForm);
        //log.info("##########################"+scheduleJobList.size());
        //if (CollectionUtils.isNotEmpty(scheduleJobList)){
        //    scheduleJobService.initScheduleJob(scheduleJobList);
        //}
    }
}