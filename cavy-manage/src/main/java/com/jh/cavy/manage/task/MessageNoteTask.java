package com.jh.cavy.manage.task;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jh.cavy.manage.domain.Message;
import com.jh.cavy.manage.domain.User;
import com.jh.cavy.manage.service.MessageService;
import com.jh.cavy.manage.service.UserService;
import com.jh.cavy.message.websocket.advanced.MessageWebSocketHandler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class MessageNoteTask {
    @Resource
    private MessageService messageService;
    @Resource
    private UserService userService;
    static final JSONConfig jsonConfig = new JSONConfig();

    static {
        jsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    @Scheduled(fixedRate = 3000)
    public void messageList() {
        LambdaQueryWrapper<Message> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.ne(Message::getStatus, "1").orderByDesc(Message::getAddTime);
        List<Message> list = messageService.list(queryWrapper);
        Map<String, List<Message>> receiverMap = list.stream().collect(Collectors.groupingBy(Message::getReceiver));
        for (Map.Entry<String, List<Message>> entry : receiverMap.entrySet()) {
            MessageWebSocketHandler.sendMessageToAccount(entry.getKey(), JSONUtil.toJsonStr(entry.getValue(), jsonConfig), "message");
        }
        LambdaQueryWrapper<User> userQueryWrapper = Wrappers.lambdaQuery();
        userQueryWrapper.notIn(CollectionUtil.isNotEmpty(receiverMap.keySet()), User::getUserName, receiverMap.keySet());
        List<User> userList = userService.list(userQueryWrapper);
        for (User user : userList) {
            MessageWebSocketHandler.sendMessageToAccount(user.getUserName(), JSONUtil.toJsonStr(new ArrayList<>()), "message");
        }
    }
}
