package com.jh.cavy.manage.excel;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.jh.cavy.manage.domain.User;
import com.jh.cavy.manage.dto.UserDTO;
import com.jh.cavy.manage.service.UserService;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;


@Slf4j
public class UserExcelListen extends AnalysisEventListener<UserDTO> {
    private final int pageSize = 1000;
    private UserService userService;

    public UserExcelListen(UserService userService) {
        this.userService = userService;
    }

    List<UserDTO> list = new ArrayList<UserDTO>();

    public UserExcelListen() {
    }

    @Override
    public void invoke(UserDTO userDTO, AnalysisContext analysisContext) {
        log.info("解析到一条数据:{}", JSON.toJSONString(userDTO));
        if (BeanUtil.isNotEmpty(userDTO,"addTime","addUser","updateTime","updateUser")){
            list.add(userDTO);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        if (CollUtil.isNotEmpty(list)) {
            //todo 验证有效性
            List<List<UserDTO>> partition = Lists.partition(list, pageSize);
            userService.saveBatch(BeanUtil.copyToList(list, User.class));
            log.info(JSON.toJSONString(list));
        }
    }
}
