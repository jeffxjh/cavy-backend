package com.jh.cavy.message.rabbitmq.core;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("msg_log")
public class MsgLog {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 消息标识（对应Listen的key） */
    private String msgKey;

    /** 消息内容（JSON格式） */
    private String msgData;

    /** 创建时间 */
    private Date createTime;

    /** 消费时间 */
    private Date consumerTime;

    /** 消费用户 */
    private String consumerUser;

    /** 是否消费（0-未消费，1-已消费） */
    private Integer isConsumed;

    /** 是否需要重发（0-不需要，1-需要） */
    private Integer needResend;

    /** 重发次数 */
    private Integer resendCount;

    /** 是否持久化 */
    private Integer isDurable;
}