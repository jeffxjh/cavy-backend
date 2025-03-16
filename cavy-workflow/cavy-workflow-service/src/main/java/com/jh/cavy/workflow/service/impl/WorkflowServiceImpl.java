package com.jh.cavy.workflow.service.impl;

import com.jh.cavy.workflow.api.WorkflowService;
import com.jh.cavy.workflow.core.WorkflowHandler;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkflowServiceImpl implements WorkflowService {
    final WorkflowHandler workflowHandler;


    @Override
    public void testget(String id, String name) {
        log.info("testget");
    }

    @Override
    public void testpost(Map<String, Object> body) {
        log.info("testpost");
    }

    @Override
    public void createDeploy(Map<String, Object> body) {
        workflowHandler.createDeploy();
    }
    @SneakyThrows
    @Override
    public void startTask() {
        //定义在流程模型中
        workflowHandler.startTask("Process_2", "TEST");
    }

    @Override
    public void completeTask(String taskId) {
        workflowHandler.completeTask(taskId);
    }

    @SneakyThrows
    @Override
    public void queryTask(HttpServletResponse httpServletResponse, String processId) {
        workflowHandler.genProcessDiagram(httpServletResponse,processId);
    }

    @Override
    public void queryHisTask(String taskId) {
        workflowHandler.queryHisTask(taskId);
    }

    @Override
    public void queryUserTask(String userName) {
        workflowHandler.queryUserTask(userName);
    }
}
