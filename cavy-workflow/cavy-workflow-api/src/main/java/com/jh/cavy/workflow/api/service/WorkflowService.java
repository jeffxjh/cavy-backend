package com.jh.cavy.workflow.api.service;

import com.jh.cavy.common.Result.ResultPage;
import com.jh.cavy.workflow.api.dto.ProcessDefinitionDTO;
import com.jh.cavy.workflow.api.dto.ProcessDefinitionVO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/workflow")
//,url = "http://localhost:8012/cavy/workflowService"
@FeignClient(name = "cavy-workflow", contextId = "workflowService", path = "workflow")
public interface WorkflowService {
    @GetMapping("testget")
    void testget(@RequestParam("id") String id, @RequestParam("name") String name);

    @PostMapping("testpost")
    void testpost(@RequestBody Map<String, Object> body);

    @PostMapping("/definition")
    void createDefinition(@RequestBody ProcessDefinitionDTO dto);

    @PostMapping("/definition/queryPage")
    ResultPage<ProcessDefinitionVO> queryPageDefinition(@RequestBody ProcessDefinitionDTO dto);

    @PostMapping("/deploy")
    String deployProcess(@RequestParam("processId") String processId);

    @PostMapping("createDeploy")
    void createDeploy(@RequestBody Map<String, Object> body);

    @GetMapping("startTask")
    void startTask();

    @GetMapping("completeTask")
    void completeTask(@RequestParam("taskId") String taskId);

    @GetMapping("queryTask")
    void queryTask(HttpServletResponse httpServletResponse, @RequestParam("processId") String processId);

    @GetMapping("queryHisTask")
    void queryHisTask(@RequestParam("taskId") String taskId);

    @GetMapping("queryUserTask")
    void queryUserTask(@RequestParam("userName") String userName);
}
