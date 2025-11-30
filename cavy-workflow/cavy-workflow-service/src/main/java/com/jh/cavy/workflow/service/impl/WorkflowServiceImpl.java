package com.jh.cavy.workflow.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jh.cavy.common.Resquest.RequestHeadHolder;
import com.jh.cavy.common.Result.ResultPage;
import com.jh.cavy.common.mybatisPlus.PageUtil;
import com.jh.cavy.workflow.ProcessStatusConstants;
import com.jh.cavy.workflow.api.dto.*;
import com.jh.cavy.workflow.api.service.ProcessService;
import com.jh.cavy.workflow.api.service.WorkflowService;
import com.jh.cavy.workflow.core.TaskNodeExecutor;
import com.jh.cavy.workflow.core.WorkflowHandler;
import com.jh.cavy.workflow.domain.ProcessDef;
import com.jh.cavy.workflow.mapper.ProcessDefMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.model.*;
import org.flowable.bpmn.model.Process;
import org.flowable.engine.HistoryService;
import org.flowable.engine.TaskService;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.flowable.task.api.Task;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.identitylink.api.IdentityLink;
import org.flowable.task.api.TaskQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;
import java.util.stream.Collectors;

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
    final HistoryService historyService;

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
        Deployment deployment = repositoryService.createDeployment()
                                        .addString(definition.getDefKey() + ".bpmn20.xml", definition.getBpmnXml())
                                        .name(definition.getName())
                                        .key(definition.getDefKey())
                                        .deploy();
        definition.setDefKey(deployment.getId());
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

    private static final String CAMUNDA_PROPERTIES_TAG = "camunda:properties";
    private static final String CAMUNDA_PROPERTY_TAG = "camunda:property";
    private static final String CAMUNDA_PROPERTY_NAME_ATTR = "name";
    private static final String CAMUNDA_PROPERTY_VALUE_ATTR = "value";

    public TaskDTO getCustomPropData(TaskDTO taskDTO) {
        // 1. 获取核心参数
        String processDefinitionId = taskDTO.getProcessDefinitionId();
        String currentActivityId = taskDTO.getTaskDefinitionKey();
        // 2. 获取 BpmnModel（流程模型容器）
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        if (bpmnModel == null) {
            throw new RuntimeException("未获取到流程模型：" + processDefinitionId);
        }

        // 3. 解析模型级 Camunda 自定义属性
        Process mainProcess = bpmnModel.getMainProcess();
        System.out.println("=== 模型级 Camunda 自定义属性 ===");
        Map<String, String> processProps = parseCamundaProperties(mainProcess);
        processProps.forEach((key, value) -> System.out.println(key + "：" + value));

        // 4. 解析节点级 Camunda 自定义属性（使用修正后的方法获取 Activity）
        Activity currentActivity = getActivityFromBpmnModel(bpmnModel, currentActivityId);
        if (currentActivity == null) {
            // 打印所有可用节点ID，方便调试
            String allActivityIds = getAllActivityIds(bpmnModel);
            throw new RuntimeException("未找到节点ID：" + currentActivityId + "，所有节点ID：" + allActivityIds);
        }
        System.out.println("\n=== 节点级 Camunda 自定义属性（" + currentActivity.getName() + "） ===");
        Map<String, String> activityProps = parseCamundaProperties(currentActivity);
        activityProps.forEach((key, value) -> System.out.println(key + "：" + value));

        // 5. 单独获取你的自定义属性（role/user）
        String role = activityProps.get("role");
        String user = activityProps.get("user");
        System.out.println("\n目标属性：role=" + role + "，user=" + user);
        taskDTO.setCustomPropData(new CustomPropData(processProps, activityProps));
        return taskDTO;
    }

    /**
     * 核心：解析 Camunda 格式自定义属性（支持 Process/Activity）
     */
    private <T extends org.flowable.bpmn.model.BaseElement> Map<String, String> parseCamundaProperties(T element) {
        Map<String, List<ExtensionElement>> extensionElements = element.getExtensionElements();
        if (extensionElements == null || extensionElements.isEmpty()) {
            return Map.of();
        }

        // 查找 camunda:properties 父元素
        Optional<ExtensionElement> propertiesElement = extensionElements.getOrDefault("properties", List.of())
                                                               .stream()
                                                               .findFirst();
        if (propertiesElement.isEmpty()) {
            return Map.of();
        }

        // 查找所有 camunda:property 子元素
        List<ExtensionElement> propertyElements = propertiesElement.get().getChildElements()
                                                          .getOrDefault("property", List.of());

        // 提取 name 和 value
        return propertyElements.stream()
                       .collect(Collectors.toMap(
                               prop -> prop.getAttributeValue(null, CAMUNDA_PROPERTY_NAME_ATTR),
                               prop -> prop.getAttributeValue(null, CAMUNDA_PROPERTY_VALUE_ATTR),
                               (v1, v2) -> v1 // 同名属性保留第一个
                       ));
    }

    /**
     * 修正版：Flowable 7.0 获取 Activity（遍历 FlowElement）
     */
    private Activity getActivityFromBpmnModel(BpmnModel bpmnModel, String activityId) {
        for (Process process : bpmnModel.getProcesses()) {
            for (FlowElement flowElement : process.getFlowElements()) {
                if (flowElement instanceof Activity && activityId.equals(flowElement.getId())) {
                    return (Activity) flowElement;
                }
            }
        }
        return null;
    }

    /**
     * 辅助方法：获取所有节点ID（调试用）
     */
    private String getAllActivityIds(BpmnModel bpmnModel) {
        return bpmnModel.getProcesses().stream()
                       .flatMap(process -> process.getFlowElements().stream())
                       .filter(flowElement -> flowElement instanceof Activity)
                       .map(FlowElement::getId)
                       .collect(Collectors.joining(","));
    }

    @Override
    public ResultPage<TaskInfoVO> todoTaskPageList(TaskInfoAO dto) {
        TaskQuery taskQuery = taskService.createTaskQuery();
        List<Task> tasks = taskQuery.taskAssignee(RequestHeadHolder.getAccount())
                                   .listPage(dto.getPageIndex().intValue() - 1, dto.getPageSize().intValue());
        // 丰富任务数据
        List<TaskInfoVO> taskInfoVOList = tasks.stream()
                                                  .map(this::convertToBasicDTO)
                                                  .map(this::enrichWithProcessDefinitionData)  // 流程定义数据
                                                  .map(this::enrichWithProcessVariables)       // 流程变量
                                                  .map(this::enrichWithTaskLocalVariables)     // 任务局部变量
                                                  //.map(this::enrichWithFormData)              // 表单数据
                                                  .map(this::enrichWithBusinessData)          // 业务数据
                                                  .map(this::enrichWithCandidateInfo)         // 候选人信息
                                                  .map(this::enrichWithBPMNProperties)        // BPMN配置属性
                                                  .map(this::enrichWithDisplayData)           // 显示数据
                                                  .map(this::getCustomPropData)           // 显示数据
                                                  .map(this::toTaskInfoVO)
                                                  .collect(Collectors.toList());


        Page<TaskInfoVO> taskInfoVOPage = new Page<>(dto.getPageIndex().intValue() - 1, dto.getPageSize(), taskQuery.count());
        taskInfoVOPage.setRecords(taskInfoVOList);
        return new ResultPage<>(taskInfoVOPage);
    }

    @SuppressWarnings("unchecked")
    private TaskInfoVO toTaskInfoVO(TaskDTO task) {
        TaskInfoVO taskInfoVO = new TaskInfoVO();
        taskInfoVO.setAssignee(task.getAssignee());
        taskInfoVO.setName(task.getName());
        taskInfoVO.setDescription(task.getDescription());
        taskInfoVO.setPriority(task.getPriority());
        taskInfoVO.setDueDate(task.getDueDate());
        taskInfoVO.setTxnCode(task.getProcessDefinitionKey());
        taskInfoVO.setProcessDefinitionId(task.getProcessDefinitionId());
        taskInfoVO.setStepNo(task.getTaskDefinitionKey());
        taskInfoVO.setProcessInstanceId(task.getProcessInstanceId());
        taskInfoVO.setRoleList(task.getCandidateGroups());
        taskInfoVO.setOwner(task.getOwner());
        taskInfoVO.setUserList(task.getCandidateUsers());
        taskInfoVO.setCreateTime(task.getCreateTime());
        taskInfoVO.setState(task.getProcessStatus());
        Optional<Object> processVariablesOpt = Optional.ofNullable(task.getProcessVariables());
        // 链式处理：processVariables → flowConfig → stepNode → processPath
        JSONObject flowConfigNode = processVariablesOpt
                                            .map(JSONUtil::parseObj)
                                            .map(json -> json.getBean("flowConfig", Map.class))
                                            .map(flowConfig -> flowConfig.get(taskInfoVO.getStepNo()))
                                            .map(JSONUtil::parseObj)
                                            .orElse(null);
        taskInfoVO.setProcessPath(Optional.ofNullable(flowConfigNode)
                                          .map(flowConfigNodeJson -> flowConfigNodeJson.getStr(TaskInfoVO.Fields.processPath))
                                          .orElse(null));
        taskInfoVO.setStatus(Optional.ofNullable(flowConfigNode)
                                     .map(flowConfigNodeJson -> flowConfigNodeJson.getStr(TaskInfoVO.Fields.status))
                                     .orElse(null));
        return taskInfoVO;
    }

    /**
     * 基础DTO转换
     */
    private TaskDTO convertToBasicDTO(Task task) {
        TaskDTO dto = new TaskDTO();

        // 基础任务属性
        dto.setId(task.getId());
        dto.setName(task.getName());
        dto.setDescription(task.getDescription());
        dto.setTaskDefinitionKey(task.getTaskDefinitionKey());
        dto.setPriority(task.getPriority());
        dto.setCategory(task.getCategory());

        // 流程实例信息
        dto.setProcessInstanceId(task.getProcessInstanceId());
        dto.setProcessDefinitionId(task.getProcessDefinitionId());
        dto.setExecutionId(task.getExecutionId());

        // 时间信息
        dto.setCreateTime(task.getCreateTime());
        dto.setDueDate(task.getDueDate());
        dto.setClaimTime(task.getClaimTime());

        // 处理人信息
        dto.setAssignee(task.getAssignee());
        dto.setOwner(task.getOwner());

        return dto;
    }

    /**
     * 丰富流程定义数据
     */
    private TaskDTO enrichWithProcessDefinitionData(TaskDTO dto) {
        try {
            ProcessDefinition processDefinition = repositoryService
                                                          .createProcessDefinitionQuery()
                                                          .processDefinitionId(dto.getProcessDefinitionId())
                                                          .singleResult();
            if (processDefinition != null) {
                dto.setProcessDefinitionName(processDefinition.getName());
                dto.setProcessDefinitionKey(processDefinition.getKey());
                dto.setProcessDefinitionVersion(processDefinition.getVersion());

                // 获取业务Key
                ProcessInstance processInstance = runtimeService
                                                          .createProcessInstanceQuery()
                                                          .processInstanceId(dto.getProcessInstanceId())
                                                          .singleResult();

                if (processInstance != null) {
                    dto.setBusinessKey(processInstance.getBusinessKey());
                    dto.setProcessStatus(getProcessInstanceStatus(processInstance.getProcessInstanceId()));
                    if (processInstance instanceof ExecutionEntityImpl executionEntity)
                        dto.setOriginalPersistentState(executionEntity.getOriginalPersistentState());
                }
            }
        }
        catch (Exception e) {
            log.warn("获取流程定义数据失败: {}", dto.getProcessDefinitionId(), e);
        }
        return dto;
    }

    public String getProcessInstanceStatus(String processInstanceId) {
        // 检查运行时实例
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                                                  .processInstanceId(processInstanceId)
                                                  .singleResult();
        if (processInstance != null) {
            if (processInstance.isSuspended()) {
                return ProcessStatusConstants.SUSPENDED;
            }
            else {
                return ProcessStatusConstants.ACTIVE;
            }
        }
        // 检查历史实例
        HistoricProcessInstance historicInstance = historyService.createHistoricProcessInstanceQuery()
                                                           .processInstanceId(processInstanceId)
                                                           .singleResult();
        if (historicInstance != null) {
            if (historicInstance.getEndTime() != null) {
                return ProcessStatusConstants.COMPLETED;
            }
            return ProcessStatusConstants.ACTIVE;
        }
        return ProcessStatusConstants.NOT_FOUND;
    }

    /**
     * 获取流程变量
     */
    private TaskDTO enrichWithProcessVariables(TaskDTO dto) {
        try {
            Map<String, Object> variables = runtimeService.getVariables(dto.getProcessInstanceId());
            dto.setProcessVariables(variables);

            // 从流程变量中提取常用业务字段
            extractBusinessFieldsFromVariables(dto, variables);

        }
        catch (Exception e) {
            log.warn("获取流程变量失败: {}", dto.getProcessInstanceId(), e);
        }
        return dto;
    }

    /**
     * 获取任务局部变量
     */
    private TaskDTO enrichWithTaskLocalVariables(TaskDTO dto) {
        try {
            Map<String, Object> localVariables = taskService.getVariablesLocal(dto.getId());
            dto.setTaskLocalVariables(localVariables);
        }
        catch (Exception e) {
            log.warn("获取任务局部变量失败: {}", dto.getId(), e);
        }
        return dto;
    }


    /**
     * 获取业务数据
     */
    private TaskDTO enrichWithBusinessData(TaskDTO dto) {
        try {
            Map<String, Object> businessData = new HashMap<>();

            // 从流程变量中提取业务数据
            if (dto.getProcessVariables() != null) {
                // 提取常见的业务字段
                businessData.put("applicant", dto.getProcessVariables().get("applicant"));
                businessData.put("applyReason", dto.getProcessVariables().get("applyReason"));
                businessData.put("applyDate", dto.getProcessVariables().get("applyDate"));
                businessData.put("amount", dto.getProcessVariables().get("amount"));

                // 根据业务Key查询外部业务数据
                if (dto.getBusinessKey() != null) {
                    // 示例：调用外部服务获取业务数据
                    // BusinessEntity business = businessService.findByBusinessKey(dto.getBusinessKey());
                    // businessData.put("businessDetail", business);
                }
            }

            dto.setBusinessData(businessData);

        }
        catch (Exception e) {
            log.warn("获取业务数据失败: {}", dto.getId(), e);
        }
        return dto;
    }

    /**
     * 获取候选人信息
     */
    private TaskDTO enrichWithCandidateInfo(TaskDTO dto) {
        try {
            List<IdentityLink> identityLinks = taskService.getIdentityLinksForTask(dto.getId());

            List<String> candidateUsers = identityLinks.stream()
                                                  .filter(link -> link.getUserId() != null)
                                                  .map(IdentityLink::getUserId)
                                                  .collect(Collectors.toList());

            List<String> candidateGroups = identityLinks.stream()
                                                   .filter(link -> link.getGroupId() != null)
                                                   .map(IdentityLink::getGroupId)
                                                   .collect(Collectors.toList());

            dto.setCandidateUsers(candidateUsers);
            dto.setCandidateGroups(candidateGroups);

        }
        catch (Exception e) {
            log.warn("获取候选人信息失败: {}", dto.getId(), e);
        }
        return dto;
    }

    /**
     * 获取BPMN配置属性
     */
    private TaskDTO enrichWithBPMNProperties(TaskDTO dto) {
        try {
            BpmnModel bpmnModel = repositoryService.getBpmnModel(dto.getProcessDefinitionId());
            FlowElement flowElement = bpmnModel.getFlowElement(dto.getTaskDefinitionKey());

            if (flowElement instanceof UserTask) {
                UserTask userTask = (UserTask) flowElement;
                Map<String, String> properties = new HashMap<>();

                // 获取扩展属性
                Map<String, List<ExtensionElement>> extensionElements = userTask.getExtensionElements();
                if (extensionElements != null) {
                    // 解析自定义属性
                    properties.put("taskType", userTask.getAttributeValue("http://your-company.com/bpmn", "taskType"));
                    properties.put("businessType", userTask.getAttributeValue("http://your-company.com/bpmn", "businessType"));
                }

                // 获取文档说明
                if (userTask.getDocumentation() != null && !userTask.getDocumentation().isEmpty()) {
                    properties.put("documentation", userTask.getDocumentation());
                }

                dto.setTaskProperties(properties);
            }

        }
        catch (Exception e) {
            log.warn("获取BPMN配置属性失败: {}", dto.getTaskDefinitionKey(), e);
        }
        return dto;
    }

    /**
     * 从流程变量中提取业务字段
     */
    private void extractBusinessFieldsFromVariables(TaskDTO dto, Map<String, Object> variables) {
        // 这里可以根据你的业务需求提取特定的流程变量
        if (variables.containsKey("businessType")) {
            dto.setProcessStatus(variables.get("businessType").toString());
        }

        // 计算任务处理时长
        if (dto.getCreateTime() != null) {
            long duration = System.currentTimeMillis() - dto.getCreateTime().getTime();
            dto.setDuration(formatDuration(duration));
        }

        // 检查是否逾期
        if (dto.getDueDate() != null) {
            dto.setOverdue(dto.getDueDate().before(new Date()));
        }
    }

    /**
     * 丰富显示数据
     */
    private TaskDTO enrichWithDisplayData(TaskDTO dto) {
        // 状态显示文本
        if (dto.getProcessStatus() != null) {
            dto.setProcessStatus(getStatusText(dto.getProcessStatus()));
        }

        // 优先级显示文本
        // if (dto.getPriority() != null) {
        //    dto.setPriority(getPriorityText(dto.getPriority()));
        //}

        return dto;
    }

    private String formatDuration(long durationMs) {
        long days = durationMs / (1000 * 60 * 60 * 24);
        long hours = (durationMs % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        return days + "天" + hours + "小时";
    }

    private String getStatusText(String status) {
        // 状态字典映射
        Map<String, String> statusMap = Map.of(
                "PENDING", "待处理",
                "APPROVING", "审批中",
                "COMPLETED", "已完成",
                "REJECTED", "已拒绝"
        );
        return statusMap.getOrDefault(status, status);
    }

    private String getPriorityText(Integer priority) {
        // 优先级字典映射
        Map<String, String> priorityMap = Map.of(
                "1", "低",
                "2", "中",
                "3", "高",
                "4", "紧急"
        );
        return priorityMap.getOrDefault(priority, "中");
    }

    // public void execute(DelegateExecution execution) {
    //    // 1. 获取当前流程运行时关键ID（与6.x一致）
    //    String processDefinitionId = execution.getProcessDefinitionId(); // 流程定义ID（格式：key:version:deployId）
    //    String currentActivityId = execution.getCurrentActivityId();     // 当前节点ID（如validateOrderTask）
    //
    //    // 2. 步骤1：获取流程定义元数据（仅含基础信息，无活动定义）
    //    ProcessDefinition processDefinition = repositoryService.getProcessDefinition(processDefinitionId);
    //    System.out.println("=== Flowable 7.0 流程模型数据 ===");
    //    System.out.println("流程名称：" + processDefinition.getName());
    //    System.out.println("流程版本：" + processDefinition.getVersion());
    //    System.out.println("当前节点ID：" + currentActivityId);
    //
    //    // 3. 步骤2：获取 BpmnModel（核心：流程模型的完整结构，含所有节点/序列流）
    //    BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
    //
    //    // 4. 步骤3：从 BpmnModel 中获取当前流程（一个 BpmnModel 可能包含多个流程，按流程Key匹配）
    //    Process process = bpmnModel.getProcess(processDefinition.getKey()); // 按流程Key获取对应流程
    //
    //    // 5. 步骤4：获取当前节点的 Activity（即 6.x 的 ActivityDefinition）
    //    // 注意：7.x 中用 org.flowable.bpmn.model.Activity 替代 6.x 的 ActivityDefinition
    //    Activity currentActivity = process.getActivity(currentActivityId);
    //
    //    // 6. 输出当前节点的模型数据（与6.x功能一致）
    //    System.out.println("当前节点名称：" + currentActivity.getName());
    //    System.out.println("当前节点类型：" + currentActivity.getType()); // 如 "serviceTask"、"userTask"
    //    System.out.println("当前节点负责人（assignee）：" + currentActivity.getAssignee()); // 仅用户任务有效
    //    System.out.println("当前节点是否异步：" + currentActivity.isAsynchronous());
    //}
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
        // 定义在流程模型中
        workflowHandler.startTask("Process_2", "TEST");
    }

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public TaskResult loadTask(TradeData tradeData) {
        FormData formData = new FormData();
        Map<String, Object> formFields = new HashMap<>();
        formFields.put("orderNo", "ORD20231215001");
        formFields.put("customerId", "CUST001");
        formData.setFormFields(formFields);
        tradeData.setFormData(formData);
        tradeData.getFlowData().setPreviousAssignee("SYSTEM");
        return taskNodeExecutor.execute(tradeData);
    }

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public TaskResult commitTask(TradeData tradeData) {
        return taskNodeExecutor.execute(tradeData);
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
