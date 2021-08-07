package com.jh.cavy.task.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jh.cavy.task.domain.Task;

public interface TaskMapper extends BaseMapper<Task> {
    int insert(Task record);

    int insertSelective(Task record);
}