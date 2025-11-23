package com.jh.cavy.workflow.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jh.cavy.common.Resquest.RequestHeadHolder;
import com.jh.cavy.common.Result.ResultPage;
import com.jh.cavy.common.mybatisPlus.PageUtil;
import com.jh.cavy.workflow.api.dto.*;
import com.jh.cavy.workflow.api.service.ProcessService;
import com.jh.cavy.workflow.api.service.WorkflowService;
import com.jh.cavy.workflow.core.*;
import com.jh.cavy.workflow.domain.ProcessDef;
import com.jh.cavy.workflow.mapper.ProcessDefMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.model.*;
import org.flowable.bpmn.model.Process;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.task.api.Task;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkflowServiceImpl implements WorkflowService {
    final WorkflowHandler workflowHandler;
    final RepositoryService repositoryService;
    final TaskService taskService;
    final RuntimeService runtimeService;
    final ProcessService processService;
    final ProcessDefMapper processDefMapper;
    final TaskNodeExecutor taskNodeExecutor;
    ;

    /**
     * 获取流程定义中的自定义属性
     */
    @GetMapping("/process-definition/{processDefinitionId}/properties")
    public ResponseEntity<?> getProcessDefinitionProperties(
            @PathVariable String processDefinitionId)
    {

        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        Process process = bpmnModel.getProcesses().get(0);
        // 从流程定义中获取版本信息
        ProcessDefinition processDefinition = repositoryService
                                                      .createProcessDefinitionQuery()
                                                      .processDefinitionId(processDefinitionId)
                                                      .singleResult();
        // 获取 Camunda 属性
        String versionTag = process.getAttributeValue("http://camunda.org/schema/1.0/bpmn", "versionTag");
        // 获取流程基本属性
        Map<String, Object> processProperties = new HashMap<>();
        processProperties.put("id", process.getId());
        processProperties.put("name", process.getName());
        processProperties.put("versionTag", versionTag);
        processProperties.put("isExecutable", process.isExecutable());

        // 获取自定义属性
        List<Map<String, String>> customProperties = getCustomProperties(process);

        Map<String, Object> result = new HashMap<>();
        result.put("processProperties", processProperties);
        result.put("customProperties", customProperties);

        return ResponseEntity.ok(result);
    }

    /**
     * 获取用户任务属性
     */
    @GetMapping("/task/{taskId}/properties")
    public ResponseEntity<?> getTaskProperties(@PathVariable String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

        if (task == null) {
            return ResponseEntity.notFound().build();
        }

        // 获取任务基本属性
        Map<String, Object> taskProperties = new HashMap<>();
        taskProperties.put("assignee", task.getAssignee());
        taskProperties.put("priority", task.getPriority());
        taskProperties.put("dueDate", task.getDueDate());

        // 获取流程变量中的业务属性
        Map<String, Object> processVariables = runtimeService.getVariables(task.getExecutionId());

        // 从 BPMN 模型中获取任务定义属性
        BpmnModel bpmnModel = repositoryService.getBpmnModel(task.getProcessDefinitionId());
        FlowElement flowElement = bpmnModel.getFlowElement(task.getTaskDefinitionKey());

        if (flowElement instanceof UserTask) {
            UserTask userTask = (UserTask) flowElement;
            taskProperties.put("candidateUsers", userTask.getCandidateUsers());
            taskProperties.put("candidateGroups", userTask.getCandidateGroups());

            // 获取自定义属性
            List<Map<String, String>> customProperties = getCustomProperties(userTask);
            taskProperties.put("customProperties", customProperties);
        }

        return ResponseEntity.ok(taskProperties);
    }

    /**
     * 获取自定义属性的通用方法
     */
    private List<Map<String, String>> getCustomProperties(BaseElement element) {
        List<Map<String, String>> customProperties = new ArrayList<>();

        if (element.getExtensionElements() != null) {
            List<ExtensionElement> propertiesElements = element.getExtensionElements()
                                                                .get("properties");

            if (propertiesElements != null) {
                for (ExtensionElement propertiesElement : propertiesElements) {
                    List<ExtensionElement> propertyElements = propertiesElement.getChildElements()
                                                                      .get("property");

                    if (propertyElements != null) {
                        for (ExtensionElement propertyElement : propertyElements) {
                            Map<String, String> prop = new HashMap<>();
                            prop.put("name", propertyElement.getAttributeValue(null, "name"));
                            prop.put("value", propertyElement.getAttributeValue(null, "value"));
                            customProperties.add(prop);
                        }
                    }
                }
            }
        }

        return customProperties;
    }

    @Override
    public void testget(String id, String name) {
        log.info("testget");
    }

    @Override
    public void testpost(Map<String, Object> body) {
        log.info("testpost");
    }

    @Override
    public void createDefinition(ProcessDefinitionDTO dto) {
        ProcessDef processDef = processDefMapper.selectOne(Wrappers.<ProcessDef>lambdaQuery()
                                                                   .eq(true, ProcessDef::getDefKey, dto.getDefKey()));
        if (processDef != null) {
            throw new RuntimeException("已存在相同processId");
        }
        ProcessDef definition = new ProcessDef();
        definition.setName(dto.getName());
        definition.setDefKey(dto.getDefKey());
        definition.setProcessId(dto.getProcessId());
        definition.setBpmnXml(dto.getBpmnXml());
        definition.setVersion(dto.getVersion() == null ? 1 : dto.getVersion());
        definition.setStatus(dto.getStatus() == null ? "active" : dto.getStatus());
        processDefMapper.insert(definition);
    }

    @Override
    public ResultPage<ProcessDefinitionVO> queryPageDefinition(ProcessDefinitionDTO dto) {
        Long userId = RequestHeadHolder.getUserId();
        LambdaQueryWrapper<ProcessDef> queryWrapper = Wrappers.lambdaQuery(ProcessDef.class);
        queryWrapper.and(StringUtils.isNotBlank(dto.getName()), wrapper -> wrapper
                                                                                   .like(StringUtils.isNotBlank(dto.getName()), ProcessDef::getName, dto.getName())
                                                                                   .or()
                                                                                   .like(StringUtils.isNotBlank(dto.getName()), ProcessDef::getDefKey, dto.getName()))
                //.eq(ProcessDef::getName, userId)
                .orderByDesc(ProcessDef::getUpdateTime);
        Page<ProcessDefinitionVO> processDefinitionVOPage = processDefMapper.queryPageDefinition(PageUtil.newPage(dto), queryWrapper);
        return new ResultPage<>(processDefinitionVOPage);
    }

    @Override
    public String deployProcess(String processId) {
        ProcessDef processDef = processDefMapper.selectOne(Wrappers.<ProcessDef>lambdaQuery()
                                                                   .eq(true, ProcessDef::getProcessId, processId));
        if (processDef == null) {
            return "";
        }
        return processService.deployProcess(BeanUtil.copyProperties(processDef, ProcessDefinitionDTO.class));
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
    public TaskResult loadTask(TradeDTO tradeDTO) {
        FormData formData = new FormData();
        Map<String, Object> formFields = new HashMap<>();
        formFields.put("orderNo", "ORD20231215001");
        formFields.put("customerId", "CUST001");
        formData.setFormFields(formFields);
        tradeDTO.setFormData(formData);
        return taskNodeExecutor.execute(tradeDTO);
    }

    @Override
    public TaskResult commitTask(TradeDTO tradeDTO) {
        tradeDTO.setTxnType("ORDER_CREATE");
        tradeDTO.setStepNo("N0000");  // 这会自动找到 OrderN0000 类
        tradeDTO.setOperateType(OperateType.COMMIT);

        FormData formData = new FormData();
        Map<String, Object> formFields = new HashMap<>();
        formFields.put("orderNo", "ORD20231215001");
        formFields.put("customerId", "CUST001");
        formData.setFormFields(formFields);
        tradeDTO.setFormData(formData);

        return taskNodeExecutor.execute(tradeDTO);
    }

    @Override
    public void completeTask(String taskId) {
        workflowHandler.completeTask(taskId);
    }

    @SneakyThrows
    @Override
    public void queryTask(HttpServletResponse httpServletResponse, String processId) {
        workflowHandler.genProcessDiagram(httpServletResponse, processId);
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
