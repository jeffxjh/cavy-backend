package com.jh.cavy.message.rabbitmq.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jh.cavy.message.rabbitmq.core.MsgLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MsgLogMapper extends BaseMapper<MsgLog> {
}
