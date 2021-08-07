package com.jh.cavy.task.domain;


import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "com.jh.cavytask.domain.SchedulTask")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_sys_task")
public class Task {
    private  int id;
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
    /**
     * spring bean名称
     */
    private String springBean;
    /**
     * 任务名
     */
    private String jobName;
}
