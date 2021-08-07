package com.jh.cavy.task.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jh.cavy.task.bean.QuartzJob;
import com.jh.cavy.task.core.QuartzManage;
import com.jh.cavy.task.domain.Task;
import com.jh.cavy.task.mapper.TaskMapper;
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

    //@PostConstruct
    //public void init() {
    //    workerId = NetUtil.ipv4ToLong(NetUtil.getLocalhostStr());
    //    log.info("当前机器的workId:{}", workerId);
    //}

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
}
