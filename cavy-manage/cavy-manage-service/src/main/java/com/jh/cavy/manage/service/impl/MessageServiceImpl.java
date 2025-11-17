package com.jh.cavy.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jh.cavy.manage.domain.Message;
import com.jh.cavy.manage.service.MessageService;
import com.jh.cavy.manage.mapper.MessageMapper;
import org.springframework.stereotype.Service;

/**
 * @author jeffx
 * @description 针对表【t_sys_message】的数据库操作Service实现
 * @createDate 2024-07-27 15:16:18
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message>
        implements MessageService {

    @Override
    public void readedMessage(String id) {
        LambdaUpdateWrapper<Message> eq = Wrappers.lambdaUpdate(Message.class).set(Message::getStatus, "1").eq(Message::getId, id);
        //Message message = this.getById(id);
        //this.update(message,eq);
        this.update(eq);
    }

    @Override
    public void readedAll(String account) {
        LambdaUpdateWrapper<Message> eq = Wrappers.lambdaUpdate(Message.class).set(Message::getStatus, "1").eq(Message::getReceiver, account);
        this.update(eq);
    }
}




