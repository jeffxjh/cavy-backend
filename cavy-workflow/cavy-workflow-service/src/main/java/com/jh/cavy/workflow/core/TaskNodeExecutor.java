package com.jh.cavy.workflow.core;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jh.cavy.workflow.api.dto.TaskContext;
import com.jh.cavy.workflow.api.dto.TaskResult;
import com.jh.cavy.workflow.api.dto.TradeDTO;
import com.jh.cavy.workflow.domain.ProcessDef;
import com.jh.cavy.workflow.mapper.ProcessDefMapper;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@SuppressWarnings({"rawtypes", "unchecked"})
public class TaskNodeExecutor {
    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private ProcessDefMapper processDefMapper;

    /**
     * 执行任务节点 - 基于步骤号动态查找实现类
     */
    public TaskResult execute(TradeDTO<?> tradeDTO) {
        TaskContext<?> context = new TaskContext<>(tradeDTO);
        TaskResult result = new TaskResult();
        result.setData(new ObjectMapper().convertValue(tradeDTO, Map.class));
        try {
            // 根据步骤号查找对应的节点实现
            TaskNode<?> taskNode = findTaskNodeByStepNo(tradeDTO.getStepNo());
            if (taskNode == null) {
                result.setSuccess(false);
                result.setErrorMsg("未找到步骤号对应的处理节点: " + tradeDTO.getStepNo());
                return result;
            }
            // 检查是否支持当前交易类型和操作类型
            if (!isNodeSupported(taskNode, context)) {
                result.setSuccess(false);
                result.setErrorMsg("节点不支持当前交易类型或操作类型");
                return result;
            }
            // 执行节点流程
            if (!executeNode(taskNode, context)) {
                result.setSuccess(false);
                result.setErrorMsg(context.getErrorMessage());
                return result;
            }
            result.setSuccess(true);
            result.setData(context.getResults());
        }
        catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg("执行异常: " + e.getMessage());
        }
        result.setData(new ObjectMapper().convertValue(tradeDTO, Map.class));
        return result;
    }

    /**
     * 根据步骤号查找对应的任务节点
     */
    private TaskNode<?> findTaskNodeByStepNo(String stepNo) {
        // 方法1: 根据Bean名称查找
        String beanName = NodeNameConverter.getNodeBeanName(stepNo);
        if (applicationContext.containsBean(beanName)) {
            return applicationContext.getBean(beanName, TaskNode.class);
        }

        // 方法2: 根据类名查找
        String className = NodeNameConverter.getNodeClassName(stepNo);
        Map<String, TaskNode> nodeBeans = applicationContext.getBeansOfType(TaskNode.class);
        for (TaskNode<?> node : nodeBeans.values()) {
            if (node.getClass().getSimpleName().equals(className)) {
                return node;
            }
        }

        return null;
    }

    private boolean isNodeSupported(TaskNode<?> node, TaskContext<?> context) {
        return true;
    }

    private boolean executeNode(TaskNode node, TaskContext context) {
        try {
            // 初始化
            node.init(context);
            // 数据加载
            node.load(context);
            // 根据操作类型执行不同流程
            switch (context.getOperateType()) {
                case LOAD:
                    return executeLoadFlow(node, context);
                case COMMIT:
                    return executeCommitFlow(node, context);
                case SAVE:
                    node.commit(context);
                    break;
                case CANCEL:
                    node.cancel(context);
                    break;
                case APPROVE:
                    node.approve(context);
                    break;
                case REJECT:
                    node.reject(context);
                    break;
                default:
                    context.setErrorMessage("不支持的操作类型: " + context.getOperateType());
                    return false;
            }
            return true;
        }
        catch (Exception e) {
            context.setErrorMessage("节点执行异常: " + e.getMessage());
            return false;
        }
    }

    private boolean executeLoadFlow(TaskNode node, TaskContext context) {
        // 提交前处理
        node.init(context);
        // 提交处理
        node.load(context);
        // 完成处理
        node.finish(context);
        return true;
    }

    private boolean executeCommitFlow(TaskNode node, TaskContext context) {
        // 启动流程实例
        Map<String, Object> variables = new HashMap<>();
        variables.put("approved", null);
        new ObjectMapper().convertValue( context.getTradeDTO().getFormData(), Map.class);
        ProcessDef processDef = processDefMapper.selectOne(Wrappers.<ProcessDef>lambdaQuery()
                                                                   .eq(true, ProcessDef::getProcessId, context.getTxnType()));
        this.safeStartProcessInstance(context.getTxnType(),processDef.getDefKey(), variables);

        // 提交前校验
        if (!node.beforeCommitValidate(context)) {
            context.setErrorMessage("提交前校验失败");
            return false;
        }
        // 提交前处理
        node.beforeCommit(context);
        // 提交处理
        node.commit(context);
        // 完成处理
        node.finish(context);
        return true;
    }

    /**
     * 检查流程定义是否已部署
     */
    public boolean isProcessDefinitionDeployed(String processDefinitionKey) {
        try {
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                                                          .deploymentId(processDefinitionKey)
                                                          .latestVersion()
                                                          .singleResult();
            return processDefinition != null;
        }
        catch (Exception e) {
            return false;
        }
    }

    /**
     * 安全的启动流程实例方法
     */
    public void safeStartProcessInstance(String processId,
                                         String processDefinitionKey,
                                         Map<String, Object> variables )
    {
        // 1. 检查流程定义是否已部署
        if (!isProcessDefinitionDeployed(processDefinitionKey)) {
            throw new RuntimeException("流程定义未部署或不存在: " + processDefinitionKey);
        }
        // 2. 获取最新的流程定义信息
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                                                      .deploymentId(processDefinitionKey)
                                                      .latestVersion()
                                                      .singleResult();
        System.out.println("使用流程定义: " + processDefinition.getName() +
                                   ", 版本: " + processDefinition.getVersion());
        // 3. 启动流程实例
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(
                processId, variables);
        System.out.println("流程实例启动成功: " + processInstance.getId());
        processInstance.getId();
    }
}