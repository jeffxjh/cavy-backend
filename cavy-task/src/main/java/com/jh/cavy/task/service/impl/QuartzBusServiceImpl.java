package com.jh.cavy.task.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jh.cavy.common.Result.ResultPage;
import com.jh.cavy.task.bean.QuartzJob;
import com.jh.cavy.task.core.QuartzManage;
import com.jh.cavy.task.domain.Task;
import com.jh.cavy.task.mapper.TaskMapper;
import com.jh.cavy.task.params.JobPageParam;
import com.jh.cavy.task.params.JobParam;
import com.jh.cavy.task.service.QuartzBusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class QuartzBusServiceImpl implements QuartzBusService {
    private final long workerId = 1;//为终端ID
    private final long datacenterId = 1;//数据中心ID

    @Resource
    private QuartzManage quartzManage;
    @Resource
    private TaskMapper taskMapper;

    @Override
    public void addJob(JobParam jobParam) {
        try {
            Snowflake snowflake = IdUtil.createSnowflake(workerId, datacenterId);
            int i = Integer.parseInt(snowflake.nextIdStr().substring(10));
            QuartzJob quartzJob = BeanUtil.toBean(jobParam, QuartzJob.class);
            Task task = BeanUtil.toBean(quartzJob, Task.class);
            task.setId(i);
            taskMapper.insert(task);

            quartzJob.setId(i);
            quartzManage.addJob(quartzJob);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void stopJob(String id) {
        try {
            Task task = taskMapper.selectById(id);
            QuartzJob quartzJob = BeanUtil.toBean(task, QuartzJob.class);
            quartzManage.pauseJob(quartzJob);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void resumeJob(String id) {
        try {
            Task task = taskMapper.selectById(id);
            QuartzJob quartzJob = BeanUtil.toBean(task, QuartzJob.class);
            quartzManage.resumeJob(quartzJob);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void runJob(String id) {
        try {
            Task task = taskMapper.selectById(id);
            QuartzJob quartzJob = BeanUtil.toBean(task, QuartzJob.class);
            quartzManage.resumeJob(quartzJob);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delJob(String id) {
        taskMapper.deleteById(id);
    }

    @Override
    public void updateJob(JobParam jobParam) {
        UpdateWrapper<Task> wrapper = new UpdateWrapper<>();
        taskMapper.update(BeanUtil.toBean(jobParam, Task.class), wrapper);
    }

    @Override
    public ResultPage page(JobPageParam jobPageParam) {
        LambdaQueryWrapper<Task> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.like(Task::getJobName, jobPageParam.getJobName());
        Page<Task> taskPage = taskMapper.selectPage(new Page<Task>(jobPageParam.getPageIndex(), jobPageParam.getPageSize()), lambdaQuery);
        return new ResultPage<>(taskPage);
    }
}
