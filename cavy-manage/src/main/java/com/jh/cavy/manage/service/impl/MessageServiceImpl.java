package com.jh.cavy.manage.service.impl;

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
    implements MessageService{

}




