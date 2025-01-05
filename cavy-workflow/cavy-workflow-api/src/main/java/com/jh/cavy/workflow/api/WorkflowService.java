package com.jh.cavy.workflow.api;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
//,url = "http://localhost:8012/cavy/workflowService"
@FeignClient(name = "cavy-workflow", contextId = "workflowService", path = "workflow")
public interface WorkflowService {
    @GetMapping("testget")
    void testget(@RequestParam("id") String id, @RequestParam("name") String name);

    @PostMapping("testpost")
    void testpost(@RequestBody Map<String, Object> body);

    @GetMapping("createDeploy")
    void createDeploy();

    @GetMapping("startTask")
    void startTask();

    @GetMapping("completeTask")
    void completeTask(String taskId);

    @GetMapping("queryTask")
    void queryTask(HttpServletResponse httpServletResponse, @RequestParam("processId") String processId);
}
