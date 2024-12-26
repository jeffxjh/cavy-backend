package com.jh.cavy.workflow.api;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping
@RestController
public interface WorkflowService {
    @GetMapping("createDeploy")
    void createDeploy();
    @GetMapping("startTask")
    void startTask();
    @GetMapping("completeTask")
    void completeTask(String taskId);
    @GetMapping("queryTask")
    void queryTask(HttpServletResponse httpServletResponse, String processId);
}
