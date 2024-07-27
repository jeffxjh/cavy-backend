package com.jh.cavy.manage.api;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jh.cavy.common.Resquest.RequestHeadHolder;
import com.jh.cavy.common.Result.ResultPage;
import com.jh.cavy.manage.domain.Message;
import com.jh.cavy.manage.domain.UserMenu;
import com.jh.cavy.manage.param.RoleParam;
import com.jh.cavy.manage.service.MessageService;
import com.jh.cavy.manage.service.RoleService;
import com.jh.cavy.manage.vo.MessageVO;
import com.jh.cavy.manage.vo.RoleVO;
import com.jh.cavy.message.websocket.advanced.MessageWebSocketHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Wrapper;
import java.util.List;

@Slf4j
@RequestMapping("/message")
@RestController
public class MessageApi {
    @Resource
    private MessageService messageService;

    @PostMapping
    public List<MessageVO> messageList() {
        LambdaQueryWrapper<Message> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Message::getReceiver, RequestHeadHolder.getAccount());
        List<Message> list = messageService.list(queryWrapper);
        MessageWebSocketHandler.sendMessage("messageList发送消息");
        return BeanUtil.copyToList(list, MessageVO.class);
    }


    @GetMapping("/{id}")
    public MessageVO getById(@PathVariable String id) {
        return BeanUtil.toBean(messageService.getById(id), MessageVO.class);
    }

    @PutMapping("/readed")
    public void readedMessage(String id) {
        messageService.readedMessage(id);
    }

    @PutMapping("/readedAll")
    public void readedAll() {
        messageService.readedAll(RequestHeadHolder.getAccount());
    }
}
