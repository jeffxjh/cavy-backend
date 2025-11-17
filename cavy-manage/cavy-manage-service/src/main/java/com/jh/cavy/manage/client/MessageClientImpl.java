package com.jh.cavy.manage.client;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jh.cavy.common.Resquest.RequestHeadHolder;
import com.jh.cavy.manage.api.MessageClient;
import com.jh.cavy.manage.domain.Message;
import com.jh.cavy.manage.service.MessageService;
import com.jh.cavy.manage.vo.MessageVO;
import com.jh.cavy.message.websocket.advanced.MessageWebSocketHandler;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public record MessageClientImpl(MessageService messageService) implements MessageClient {
    @Override
    public List<MessageVO> messageList() {
        LambdaQueryWrapper<Message> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Message::getReceiver, RequestHeadHolder.getAccount());
        List<Message> list = messageService.list(queryWrapper);
        MessageWebSocketHandler.sendMessage("messageList发送消息");
        return BeanUtil.copyToList(list, MessageVO.class);
    }

    @Override
    public MessageVO getById(String id) {
        return BeanUtil.toBean(messageService.getById(id), MessageVO.class);
    }

    @Override
    public void readedMessage(String id) {
        messageService.readedMessage(id);
    }

    @Override
    public void readedAll() {
        messageService.readedAll(RequestHeadHolder.getAccount());
    }
}
