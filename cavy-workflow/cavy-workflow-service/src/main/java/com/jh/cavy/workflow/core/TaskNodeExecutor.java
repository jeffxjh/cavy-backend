package com.jh.cavy.workflow.core;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jh.cavy.common.Resquest.RequestHeadHolder;
import com.jh.cavy.workflow.api.dto.FlowData;
import com.jh.cavy.workflow.api.dto.TaskContext;
import com.jh.cavy.workflow.api.dto.TaskResult;
import com.jh.cavy.workflow.api.dto.TradeData;
import com.jh.cavy.workflow.domain.ProcessDef;
import com.jh.cavy.workflow.mapper.ProcessDefMapper;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
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
    public TaskResult execute(TradeData<?> tradeData) {
        TaskContext<?> context = new TaskContext<>(tradeData);
        TaskResult result = new TaskResult();
        result.setData(new ObjectMapper().convertValue(tradeData, Map.class));
        try {
            // 根据步骤号查找对应的节点实现
            TaskNode<?> taskNode = findTaskNodeByStepNo(tradeData.getStepNo());
            if (taskNode == null) {
                result.setSuccess(false);
                result.setErrorMsg("未找到步骤号对应的处理节点: " + tradeData.getStepNo());
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
        result.setData(new ObjectMapper().convertValue(tradeData, Map.class));
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
        // todo 每次执行节点时加载流程配置（添加缓存） 将当前配置信息写入
        // 模拟数据
        context.setFlowConfig(new HashMap<>() {
            {
                put("N0001", new HashMap<>() {{
                    // 这个key后续换为对象属性字面量
                    put("processPath", "demo");
                    put("status", "created");
                }});
            }
        });
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
            log.error("流程节点执行异常", e);
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

    // todo 判断是否已经发起任务
    private boolean executeCommitFlow(TaskNode node, TaskContext context) {
        context.getTradeData().getFlowData().setPreviousAssignee(RequestHeadHolder.getAccount());
        // 启动流程实例
        Map<String, Object> variables = new HashMap<>();
        variables.put("tradeData", JSONUtil.toJsonStr(context.getTradeData()));
        variables.put("flowConfig", JSONUtil.toJsonStr(context.getFlowConfig()));
        variables.put("txnCode", JSONUtil.toJsonStr(context.getTxnCode()));
        new ObjectMapper().convertValue(context.getTradeData().getFormData(), Map.class);
        ProcessDef processDef = processDefMapper.selectOne(Wrappers.<ProcessDef>lambdaQuery()
                                                                   .eq(true, ProcessDef::getProcessId, context.getTxnType()));
        this.safeStartProcessInstance(context.getTxnType(), processDef.getDefKey(), variables);

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
                                         Map<String, Object> variables)
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
        // 查询任务并设置Owner
        Task task = taskService.createTaskQuery()
                            .processInstanceId(processInstance.getId())
                            .active()
                            .singleResult();

        if (task != null) {
            // todo 每次提交设置状态
            // 设置Owner（通常设置为任务的原创建人或委托人）
            taskService.setOwner(task.getId(), RequestHeadHolder.getAccount());
            // 设置assignee 任务当前处理人
            taskService.setAssignee(task.getId(), RequestHeadHolder.getAccount());
            log.info("任务{}分配给{}", variables.get(FlowData.Fields.txnCode), RequestHeadHolder.getAccount());
        }
    }
}