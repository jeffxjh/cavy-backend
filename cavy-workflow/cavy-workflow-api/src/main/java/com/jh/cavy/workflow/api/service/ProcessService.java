package com.jh.cavy.workflow.api.service;

import com.jh.cavy.workflow.api.dto.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProcessService {

    /**
     *
     * 部署流程配置
     * @param dto
     * @return {@link String }
     */
    String deployProcess(ProcessDefinitionDTO dto);

    /**
     * 启动流程配置（发起流程）
     * @param request
     * @return
     */
    ProcessInstanceDTO startProcess(StartProcessRequest request) ;

    /**
     * 获取指定用户任务
     * @param assignee
     * @return
     */
    List<TaskDTO> getUserTasks(String assignee);

    /**
     * 提交任务
     * @param request
     */
    void completeTask(TaskCompleteRequest request);

    /**
     * 获取流程定义列表
     * @return
     */
    List<ProcessDefDTO> getProcessDefinitions() ;
}
