package com.jh.cavytask.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jh.cavytask.domain.Task;

public interface TaskMapper extends BaseMapper<Task> {
    int insert(Task record);

    int insertSelective(Task record);
}