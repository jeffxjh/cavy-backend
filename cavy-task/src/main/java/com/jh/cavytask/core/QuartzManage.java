package com.jh.cavytask.core;

import com.jh.cavytask.bean.QuartzJob;
import org.quartz.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

@Component
public class QuartzManage {

    @Resource(name = "scheduler")
    private Scheduler scheduler;

    public void addJob(QuartzJob job) throws SchedulerException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        //通过类名获取实体类，即要执行的定时任务的类
        Class<?> clazz = Class.forName(job.getBeanName());
        Job jobEntity = (Job)clazz.newInstance();
        //通过实体类和任务名创建 JobDetail
        JobDetail jobDetail = newJob(jobEntity.getClass())
                                      .withIdentity(job.getJobName()).build();
        //通过触发器名和cron 表达式创建 Trigger
        Trigger cronTrigger = newTrigger()
                                      .withIdentity(job.getTriggerName())
                                      .startNow()
                                      .withSchedule(CronScheduleBuilder.cronSchedule(job.getCronExpression()))
                                      .build();
        //执行定时任务
        scheduler.scheduleJob(jobDetail,cronTrigger);
    }

    /**
     * 更新job cron表达式
     * @param quartzJob
     * @throws SchedulerException
     */
    public void updateJobCron(QuartzJob quartzJob) throws SchedulerException {

        TriggerKey triggerKey = TriggerKey.triggerKey(quartzJob.getJobName());
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(quartzJob.getCronExpression());
        trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
        scheduler.rescheduleJob(triggerKey, trigger);
    }
    /**
     * 删除一个job
     * @param quartzJob
     * @throws SchedulerException
     */
    public void deleteJob(QuartzJob quartzJob) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(quartzJob.getJobName());
        scheduler.deleteJob(jobKey);
    }
    /**
     * 恢复一个job
     * @param quartzJob
     * @throws SchedulerException
     */
    public void resumeJob(QuartzJob quartzJob) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(quartzJob.getJobName());
        scheduler.resumeJob(jobKey);
    }
    /**
     * 立即执行job
     * @param quartzJob
     * @throws SchedulerException
     */
    public void runAJobNow(QuartzJob quartzJob) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(quartzJob.getJobName());
        scheduler.triggerJob(jobKey);
    }
    /**
     * 暂停一个job
     * @param quartzJob
     * @throws SchedulerException
     */
    public void pauseJob(QuartzJob quartzJob) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(quartzJob.getJobName());
        scheduler.pauseJob(jobKey);
    }
}
