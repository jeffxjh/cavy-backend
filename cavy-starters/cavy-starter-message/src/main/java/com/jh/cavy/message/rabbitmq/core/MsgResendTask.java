package com.jh.cavy.message.rabbitmq.core;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jh.cavy.message.rabbitmq.core.mapper.MsgLogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 消息重发补偿定时任务
 * 扫描未消费且需要重发的消息，进行重发
 */
@Component
@RequiredArgsConstructor
public class MsgResendTask {

    private final MsgLogMapper msgLogMapper;
    private final MsgUtil msgUtil;

    /**
     * 每5分钟执行一次重发
     */
    @Scheduled(cron = "0 0/5 * * * ?")
    public void resendMsg() {
        // 1. 查询需要重发的消息（未消费、需要重发、重发次数<3）
        LambdaQueryWrapper<MsgLog> queryWrapper = new LambdaQueryWrapper<MsgLog>()
                                                          .eq(MsgLog::getIsConsumed, 0)
                                                          .eq(MsgLog::getNeedResend, 1)
                                                          .lt(MsgLog::getResendCount, 3); // 最大重发3次
        List<MsgLog> needResendLogs = msgLogMapper.selectList(queryWrapper);

        // 2. 遍历重发
        for (MsgLog log : needResendLogs) {
            try {
                // 反序列化数据
                Object data = new ObjectMapper().convertValue(log.getMsgData(), Object.class);
                // 调用MsgUtil重发（持久化标识沿用原配置）
                msgUtil.sendMsg( RabbitMqExchangeConfig.MSG_EXCHANGE,log.getMsgKey(), data, log.getIsDurable() == 1);
            } catch (Exception e) {
                // 重发失败：更新重发次数
                MsgLog updateLog = new MsgLog();
                updateLog.setId(log.getId());
                updateLog.setResendCount(log.getResendCount() + 1);
                msgLogMapper.updateById(updateLog);
            }
        }
    }
}