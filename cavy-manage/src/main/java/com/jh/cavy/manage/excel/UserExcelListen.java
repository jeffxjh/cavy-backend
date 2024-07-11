package com.jh.cavy.manage.excel;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.jh.cavy.manage.domain.User;
import com.jh.cavy.manage.dto.UserDTO;
import com.jh.cavy.manage.service.UserService;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class UserExcelListen extends AnalysisEventListener<UserDTO> {
    private final static int pageSize = 1000;
    @Getter
    private int successNum;
    @Getter
    private int errorNum;
    @Getter
    private int totalNum;

    private UserService userService;

    public UserExcelListen(UserService userService) {
        this.userService = userService;
    }

    List<UserDTO> list = new ArrayList<UserDTO>();

    public UserExcelListen() {
    }

    @SneakyThrows
    @Override
    public void invoke(UserDTO userDTO, AnalysisContext analysisContext) {
        totalNum++;
        log.info("解析到一条数据:{}", new ObjectMapper().writeValueAsString(userDTO));
        if (BeanUtil.isNotEmpty(userDTO, "addTime", "addUser", "updateTime", "updateUser")) {
            switch (userDTO.getGender()) {
                case "男":
                    userDTO.setGender("1");
                    break;
                case "女":
                    userDTO.setGender("2");
                    break;
            }
            switch (userDTO.getDefaultUser()) {
                case "是":
                    userDTO.setDefaultUser("1");
                    break;
                case "否":
                    userDTO.setDefaultUser("0");
                    break;
            }
            //0已新建;1已使用;2已禁用;3已冻结
            switch (userDTO.getStatus()) {
                case "已新建":
                    userDTO.setStatus("0");
                    break;
                case "已使用":
                    userDTO.setStatus("1");
                    break;
                case "已禁用":
                    userDTO.setStatus("2");
                    break;
                case "已冻结":
                    userDTO.setStatus("3");
                    break;
            }
            list.add(userDTO);
        } else {
            errorNum++;
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        successNum = list.size();
        if (CollUtil.isNotEmpty(list)) {
            List<List<UserDTO>> partition = Lists.partition(list, pageSize);
            for (List<UserDTO> dtoList : partition) {
                userService.saveBatch(BeanUtil.copyToList(dtoList, User.class));
                log.info("用户 {} 添加成功", dtoList.stream().map(UserDTO::getUserName).collect(Collectors.joining(",")));
            }

        }
    }

    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {
        super.onException(exception, context);
    }
}
