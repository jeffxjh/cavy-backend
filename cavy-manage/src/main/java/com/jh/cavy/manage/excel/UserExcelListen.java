package com.jh.cavy.manage.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.jh.cavy.manage.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;


@Slf4j
public class UserExcelListen extends AnalysisEventListener<UserDTO> {
    List<UserDTO> list = new ArrayList<UserDTO>();
    public UserExcelListen() {
    }

    @Override
    public void invoke(UserDTO userDTO, AnalysisContext analysisContext) {
        log.info("解析到一条数据:{}", JSON.toJSONString(userDTO));
        list.add(userDTO);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info(JSON.toJSONString(list));
    }
}
