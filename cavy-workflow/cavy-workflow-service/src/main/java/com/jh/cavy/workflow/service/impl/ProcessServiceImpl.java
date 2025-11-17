package com.jh.cavy.workflow.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jh.cavy.workflow.api.dto.*;
import com.jh.cavy.workflow.api.service.ProcessService;
import com.jh.cavy.workflow.domain.LeaveApply;
import com.jh.cavy.workflow.domain.ProcessDef;
import com.jh.cavy.workflow.mapper.LeaveApplyMapper;
import com.jh.cavy.workflow.mapper.ProcessDefMapper;
import org.apache.commons.lang3.StringUtils;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;


@Transactional
@Service
public class ProcessServiceImpl implements ProcessService {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private ProcessDefMapper processDefMapper;

    @Autowired
    private LeaveApplyMapper leaveApplyMapper;


    /**
     * 部署流程定义（从前端保存的BPMN XML）
     */
    @Override
    public String deployProcess(ProcessDefinitionDTO dto) {
        // 保存到自定义表
        ProcessDef definition = new ProcessDef();
        definition.setId(UUID.randomUUID().toString());
        definition.setName(dto.getName());
        definition.setDefKey(dto.getKey());
        definition.setBpmnXml(dto.getBpmnXml());
        definition.setVersion(1);
        definition.setStatus("active");
        processDefMapper.insert(definition);

        // 部署到Flowable引擎
        Deployment deployment = repositoryService.createDeployment()
                                        .addString(dto.getKey() + ".bpmn20.xml", dto.getBpmnXml())
                                        .name(dto.getName())
                                        .key(dto.getKey())
                                        .deploy();

        return deployment.getId();
    }

    /**
     * 启动流程实例
     */
    @Override
    public ProcessInstanceDTO startProcess(StartProcessRequest request) {
        // 保存业务数据
        LeaveApply apply = new LeaveApply();
        apply.setApplicant((String) request.getVariables().get("applicant"));
        apply.setManager((String) request.getVariables().get("manager"));
        apply.setStatus("pending");
        apply.setFormData(JSONUtil.toJsonStr(request.getFormData()));

        leaveApplyMapper.insert(apply);

        // 设置流程变量
        Map<String, Object> variables = new HashMap<>();
        variables.putAll(request.getVariables());
        variables.put("businessKey", apply.getId().toString());
        variables.put("formData", request.getFormData());

        // 启动流程
        ProcessInstance instance = runtimeService.startProcessInstanceByKey(
                request.getProcessKey(),
                apply.getId().toString(),
                variables
        );

        // 更新业务数据
        apply.setProcessInstanceId(instance.getId());
        leaveApplyMapper.updateById(apply);

        return BeanUtil.copyProperties(instance,ProcessInstanceDTO.class);
    }

    /**
     * 查询用户任务
     */
    @Override
    public List<TaskDTO> getUserTasks(String assignee) {
        List<Task> tasks = taskService.createTaskQuery()
                                   .taskAssignee(assignee)
                                   .orderByTaskCreateTime().desc()
                                   .list();

        return tasks.stream().map(task ->
        {
            TaskDTO dto = new TaskDTO();
            dto.setId(task.getId());
            dto.setName(task.getName());
            dto.setAssignee(task.getAssignee());
            dto.setProcessInstanceId(task.getProcessInstanceId());
            dto.setCreateTime(task.getCreateTime());
            dto.setVariables(taskService.getVariables(task.getId()));
            return dto;
        }).collect(Collectors.toList());
    }

    /**
     * 完成任务
     */
    @Override
    public void completeTask(TaskCompleteRequest request) {
        // 添加审批意见
        if (StringUtils.isNotBlank(request.getComment())) {
            taskService.addComment(request.getTaskId(),
                    request.getProcessInstanceId(), request.getComment());
        }

        // 完成任务
        taskService.complete(request.getTaskId(), request.getVariables());

        // 更新业务状态
        ProcessInstance instance = runtimeService.createProcessInstanceQuery()
                                           .processInstanceId(request.getProcessInstanceId())
                                           .singleResult();

        if (instance == null) {
            // 流程已结束
            updateBusinessStatus(request.getProcessInstanceId(), "completed");
        }
    }

    private void updateBusinessStatus(String processInstanceId, String status) {
        LambdaQueryWrapper<LeaveApply> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LeaveApply::getProcessInstanceId, processInstanceId);
        LeaveApply apply = leaveApplyMapper.selectOne(wrapper);
        if (apply != null) {
            apply.setStatus(status);
            leaveApplyMapper.updateById(apply);
        }
    }

    /**
     * 获取流程定义列表
     */
    @Override
    public List<ProcessDefDTO> getProcessDefinitions() {
        LambdaQueryWrapper<ProcessDef> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProcessDef::getStatus, "active");
        return BeanUtil.copyToList(processDefMapper.selectList(wrapper),ProcessDefDTO.class);
    }
}