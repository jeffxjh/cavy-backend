package com.jh.cavy.workflow.core;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.common.engine.api.history.HistoricData;
import org.flowable.engine.*;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.history.ProcessInstanceHistoryLog;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.DeploymentQuery;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.image.ProcessDiagramGenerator;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.variable.api.history.HistoricVariableInstance;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkflowHandler {
    /**
     * 流程引擎
     */
    private final ProcessEngine processEngine;
    /**
     * 流程仓库服务类
     */
    private final RepositoryService repositoryService;
    /**
     * 查询运行信息
     */
    private final RuntimeService runtimeService;
    /**
     * 查询任务信息
     */
    private final TaskService taskService;
    /**
     * 查询历史信息
     */
    private final HistoryService historyService;


    /**
     * 发起任务
     *
     * @param processDefinitionKey
     * @param businessKey
     * @return {@link Object }
     */
    public ProcessInstance startTask(String processDefinitionKey, String businessKey) {
        Map<String, Object> variables = new HashMap<String, Object>(0);
        variables.put("employee", "工作组");
        variables.put("nrOfHolidays", 8);
        variables.put("description", "请假");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey, variables);
        //Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        //taskService.complete(task.getId());
        return processInstance;
    }

    /**
     * 提交节点
     *
     * @param taskId
     * @return {@link Object }
     */
    public Object completeTask(String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        taskService.complete(task.getId());
        return task;
    }

    /**
     * 查询流程定义
     *
     * @return {@link Object }
     */
    public Object queryWorkflowDeploy() {
        DeploymentQuery deploymentQuery = processEngine.getRepositoryService().createDeploymentQuery();
        List<Deployment> list = deploymentQuery.list();
        //查询单个定义的流程
        /*ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId("5")
                .singleResult();*/
//        System.out.println("Found process definition : " + processDefinition.getName());
        return list;
    }

    public Object createDeploy() {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //文件名必须包含 bpmn20.xml 或者 bpmn.xml
        Deployment deployment = repositoryService.createDeployment()
                                        .addClasspathResource("flowable/test_flowable.bpmn20.xml")
                                        .category("基本类型")
                                        .name("流程审批")
                                        .deploy();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                                                      .deploymentId(deployment.getId())
                                                      .singleResult();
        log.info("Found process definition :key{},name{} " + processDefinition.getKey() + processDefinition.getName());
        return deployment;
    }

    /**
     * 查询待办任务
     *
     * @return {@link Object }
     */
    public Object queryProcessInstance() {
        TaskService taskService = processEngine.getTaskService();
        //通过组查询任务表
//        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("managers").list();
        //通过人查询单个任务
        Task task = taskService.createTaskQuery().taskAssignee("小王").singleResult();
        //通过任务id查询流程变量
        Map<String, Object> processVariables = taskService.getVariables(task.getId());
        return processVariables;
    }

    public Object getHistoryTask(String name) {
        HistoryService historyService = processEngine.getHistoryService();
        //历史任务流程——流程id
        List<HistoricActivityInstance> activities =
                historyService.createHistoricActivityInstanceQuery()
                        .processInstanceId("2501")
                        .finished()
                        .orderByHistoricActivityInstanceEndTime().asc()
                        .list();
        //历史任务
        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery().taskAssignee(name).list();
        return list;
    }

    /**
     * 返回流程图
     *
     * @param httpServletResponse
     * @param processId
     */
    public void genProcessDiagram(HttpServletResponse httpServletResponse, String processId) throws Exception {
        ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult();
        //流程走完的不显示图
        if (pi == null) {
            return;
        }
        Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
        //使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
        String InstanceId = task.getProcessInstanceId();
        List<Execution> executions = runtimeService
                                             .createExecutionQuery()
                                             .processInstanceId(InstanceId)
                                             .list();

        //得到正在执行的Activity的Id
        List<String> activityIds = new ArrayList<>();
        List<String> flows = new ArrayList<>();
        for (Execution exe : executions) {
            List<String> ids = runtimeService.getActiveActivityIds(exe.getId());
            activityIds.addAll(ids);
        }

        //获取流程图
        BpmnModel bpmnModel = repositoryService.getBpmnModel(pi.getProcessDefinitionId());
        ProcessEngineConfiguration engconf = processEngine.getProcessEngineConfiguration();
        ProcessDiagramGenerator diagramGenerator = engconf.getProcessDiagramGenerator();
        InputStream in = diagramGenerator.generateDiagram(bpmnModel, "png", activityIds, flows, engconf.getActivityFontName(), engconf.getLabelFontName(), engconf.getAnnotationFontName(), engconf.getClassLoader(), 1.0, true);
        OutputStream out = null;
        byte[] buf = new byte[1024];
        int legth = 0;
        try {
            out = httpServletResponse.getOutputStream();
            while ((legth = in.read(buf)) != -1) {
                out.write(buf, 0, legth);
            }
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }
    void test10() {
        HistoricProcessInstance pi = historyService.createHistoricProcessInstanceQuery().singleResult();
        ProcessInstanceHistoryLog historyLog = historyService.createProcessInstanceHistoryLogQuery(pi.getId())
                                                       //包括历史活动
                                                       .includeActivities()
                                                       //包括历史任务
                                                       .includeTasks()
                                                       //包括历史变量
                                                       .includeVariables()
                                                       .singleResult();
        log.info("id:{},startTime:{},endTime:{}", historyLog.getId(), historyLog.getStartTime(), historyLog.getEndTime());
        List<HistoricData> historicData = historyLog.getHistoricData();
        for (HistoricData data : historicData) {
            if (data instanceof HistoricActivityInstance) {
                HistoricActivityInstance hai = (HistoricActivityInstance) data;
                log.info("name:{},type:{}", hai.getActivityName(), hai.getActivityType());
            }
            if (data instanceof HistoricTaskInstance) {
                HistoricTaskInstance hti = (HistoricTaskInstance) data;
                log.info("name:{},assignee:{}", hti.getName(), hti.getAssignee());
            }
            if (data instanceof HistoricVariableInstance) {
                HistoricVariableInstance hvi = (HistoricVariableInstance) data;
                log.info("name:{},type:{},value:{}", hvi.getVariableName(), hvi.getVariableTypeName(), hvi.getValue());
            }
        }
    }

    public void queryUserTask(String userName) {
        TaskService taskService = processEngine.getTaskService();
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(userName).list();
        for (int i=0; i<tasks.size(); i++) {
           log.info(tasks.get(i).getId(), tasks.get(i).getAssignee(),tasks.get(i).getName(),tasks.get(i).getDescription());
        }
    }

    public void queryHisTask(String taskId) {
        List<HistoricProcessInstance> list = historyService.createHistoricProcessInstanceQuery().finished().list();
        for (HistoricProcessInstance hpi : list) {
            log.info("name:{},startTime:{},endTime:{}",hpi.getName(),hpi.getStartTime(),hpi.getEndTime());
        }
    }
}
