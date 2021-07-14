package com.jh.cavytask.params;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class JobParam implements Serializable {
    @ApiModelProperty("ID")
    private String id;
    @ApiModelProperty("cron 表达式")
    private String cronExpression;
    @ApiModelProperty("任务调用的方法名")
    private String methodName;
    @ApiModelProperty("任务是否有状态")
    private Integer isConcurrent;
    @ApiModelProperty("描述")
    private String description;
    @ApiModelProperty("完全限定名")
    private String beanName;
    @ApiModelProperty("触发器名称")
    private String triggerName;
    @ApiModelProperty("任务状态")
    private Integer jobStatus;
    @ApiModelProperty("任务名")
    private String jobName;
}
