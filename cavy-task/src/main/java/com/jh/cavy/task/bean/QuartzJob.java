package com.jh.cavy.task.bean;

import lombok.Data;

@Data
public class QuartzJob {

    public static final Integer STATUS_RUNNING = 1;
    public static final Integer STATUS_NOT_RUNNING = 0;
    public static final Integer CONCURRENT_IS = 1;
    public static final Integer CONCURRENT_NOT = 0;

    private Integer id;
    /**
     * cron 表达式
     */
    private String cronExpression;
    /**
     * 任务调用的方法名
     */
    private String methodName;
    /**
     * 任务是否有状态
     */
    private Integer isConcurrent;
    /**
     * 描述
     */
    private String description;
    /**
     * 任务执行时调用哪个类的方法 包名+类名，完全限定名
     */
    private String beanName;
    /**
     * 触发器名称
     */
    private String triggerName;

    /**
     * 任务状态
     */
    private Integer jobStatus;

    private String springBean;
    /**
     * 任务名
     */
    private String jobName;

}