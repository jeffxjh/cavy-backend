package com.jh.cavy.workflow.api.service;

import com.jh.cavy.workflow.api.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/workflow")
@CrossOrigin(origins = "*")
public class WorkflowController {

    @Autowired(required = false)
    private ProcessService processService;

    /**
     * 部署流程定义
     */
    @PostMapping("/deploy")
    public ResponseEntity<?> deployProcess(@RequestBody ProcessDefinitionDTO dto) {
        try {
            String deploymentId = processService.deployProcess(dto);
            return ResponseEntity.ok(Map.of("deploymentId", deploymentId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * 启动流程实例
     */
    @PostMapping("/start")
    public ResponseEntity<?> startProcess(@RequestBody StartProcessRequest request) {
        try {
            ProcessInstanceDTO instance = processService.startProcess(request);
            return ResponseEntity.ok(Map.of(
                    "processInstanceId", instance.getId(),
                    "businessKey", instance.getBusinessKey()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * 查询用户任务
     */
    @GetMapping("/tasks")
    public ResponseEntity<List<TaskDTO>> getUserTasks(@RequestParam String assignee) {
        List<TaskDTO> tasks = processService.getUserTasks(assignee);
        return ResponseEntity.ok(tasks);
    }

    /**
     * 完成任务
     */
    @PostMapping("/complete-task")
    public ResponseEntity<?> completeTask(@RequestBody TaskCompleteRequest request) {
        try {
            processService.completeTask(request);
            return ResponseEntity.ok("任务完成成功");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * 获取流程定义列表
     */
    @GetMapping("/process-definitions")
    public ResponseEntity<List<ProcessDefDTO>> getProcessDefinitions() {
        List<ProcessDefDTO> definitions = processService.getProcessDefinitions();
        return ResponseEntity.ok(definitions);
    }
}