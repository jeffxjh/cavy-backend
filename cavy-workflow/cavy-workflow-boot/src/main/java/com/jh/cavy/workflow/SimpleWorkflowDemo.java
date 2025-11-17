package com.jh.cavy.workflow;

import org.flowable.engine.*;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class SimpleWorkflowDemo {

    private ProcessEngine processEngine;
    private RepositoryService repositoryService;
    private RuntimeService runtimeService;
    private TaskService taskService;

    public void init() {
        // 配置流程引擎
        ProcessEngineConfiguration cfg = new StandaloneProcessEngineConfiguration()
                                                 .setJdbcUrl("jdbc:postgresql://192.168.2.2:5432/flowable?currentSchema=flowable")
                                                 .setJdbcUsername("postgres")
                                                 .setJdbcPassword("123456")
                                                 .setJdbcDriver("org.postgresql.Driver");

        this.processEngine = cfg.buildProcessEngine();
        this.repositoryService = processEngine.getRepositoryService();
        this.runtimeService = processEngine.getRuntimeService();
        this.taskService = processEngine.getTaskService();
    }

    public void deployProcess() {
        // 部署流程定义
        Deployment deployment = repositoryService.createDeployment()
                                        .addClasspathResource("flowable/simple-approval.bpmn20.xml")
                                        .deploy();

        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                                                      .deploymentId(deployment.getId())
                                                      .singleResult();

        System.out.println("流程部署成功: " + processDefinition.getName());
        System.out.println("流程定义ID: " + processDefinition.getId());
    }

    public String startProcess(String applicant, String manager) {
        // 启动流程实例
        Map<String, Object> variables = new HashMap<>();
        variables.put("applicant", applicant);
        variables.put("manager", manager);
        variables.put("approved", null);

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("simpleApproval", variables);

        System.out.println("流程实例启动成功: " + processInstance.getId());
        return processInstance.getId();
    }

    public void completeTask(String assignee) {
        // 查询并完成任务
        List<Task> tasks = taskService.createTaskQuery()
                                   .taskAssignee(assignee)
                                   .list();

        if (tasks.isEmpty()) {
            System.out.println(assignee + " 没有待处理的任务");
            return;
        }

        for (Task task : tasks) {
            System.out.println("\n=== 当前任务 ===");
            System.out.println("任务ID: " + task.getId());
            System.out.println("任务名称: " + task.getName());
            System.out.println("流程实例ID: " + task.getProcessInstanceId());

            Map<String, Object> variables = new HashMap<>();

            if ("经理审批".equals(task.getName())) {
                // 经理审批任务
                Scanner scanner = new Scanner(System.in);
                System.out.print("请输入审批意见 (1-通过, 2-拒绝): ");
                String decision = scanner.nextLine();

                boolean approved = "1".equals(decision);
                variables.put("approved", approved);
                System.out.println("审批结果: " + (approved ? "通过" : "拒绝"));
            }

            taskService.complete(task.getId(), variables);
            System.out.println("任务已完成: " + task.getName());
        }
    }

    public void displayTasks(String assignee) {
        // 显示用户的任务列表
        List<Task> tasks = taskService.createTaskQuery()
                                   .taskAssignee(assignee)
                                   .list();

        if (tasks.isEmpty()) {
            System.out.println(assignee + " 没有待处理的任务");
            return;
        }

        System.out.println("\n=== " + assignee + " 的任务列表 ===");
        for (Task task : tasks) {
            System.out.println("任务ID: " + task.getId());
            System.out.println("任务名称: " + task.getName());
            System.out.println("创建时间: " + task.getCreateTime());
            System.out.println("流程实例ID: " + task.getProcessInstanceId());
            System.out.println("------------------------");
        }
    }

    public void displayProcessStatus(String processInstanceId) {
        // 显示流程状态
        List<Task> activeTasks = taskService.createTaskQuery()
                                         .processInstanceId(processInstanceId)
                                         .list();

        System.out.println("\n=== 流程状态 ===");
        System.out.println("流程实例ID: " + processInstanceId);
        System.out.println("当前活动任务:");

        if (activeTasks.isEmpty()) {
            System.out.println("流程已结束");
        } else {
            for (Task task : activeTasks) {
                System.out.println(" - " + task.getName() + " (负责人: " + task.getAssignee() + ")");
            }
        }
    }

    public static void main(String[] args) {
        SimpleWorkflowDemo demo = new SimpleWorkflowDemo();
        demo.init();
        demo.deployProcess();

        // 启动流程实例
        String processInstanceId = demo.startProcess("张三", "李经理");

        // 显示初始任务
        demo.displayTasks("张三");
        demo.completeTask("张三");  // 张三提交申请

        // 经理审批
        demo.displayTasks("李经理");
        demo.completeTask("李经理");

        // 显示流程状态
        demo.displayProcessStatus(processInstanceId);

        // 如果有HR任务，完成它
        demo.displayTasks("hr");
        demo.completeTask("hr");

        // 最终状态
        demo.displayProcessStatus(processInstanceId);
    }
}