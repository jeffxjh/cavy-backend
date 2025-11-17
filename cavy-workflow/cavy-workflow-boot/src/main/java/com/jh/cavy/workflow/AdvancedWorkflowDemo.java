package com.jh.cavy.workflow;

import lombok.Data;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
class LeaveApplication {
    private String applicant;
    private String manager;
    private Date startDate;
    private Date endDate;
    private Integer days;
    private String reason;
    private Boolean approved;
    private String comments;
}

public class AdvancedWorkflowDemo {

    private ProcessEngine processEngine;
    private RuntimeService runtimeService;
    private TaskService taskService;

    public void init() {
        //ProcessEngineConfiguration cfg = new StandaloneProcessEngineConfiguration()
        //                                         .setJdbcUrl("jdbc:h2:mem:flowable2;DB_CLOSE_DELAY=-1")
        //                                         .setJdbcUsername("sa")
        //                                         .setJdbcPassword("")
        //                                         .setJdbcDriver("org.h2.Driver")
        //                                         .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        ProcessEngineConfiguration cfg = new StandaloneProcessEngineConfiguration()
                                                 .setJdbcUrl("jdbc:postgresql://192.168.2.2:5432/flowable?currentSchema=flowable")
                                                 .setJdbcUsername("postgres")
                                                 .setJdbcPassword("123456")
                                                 .setJdbcDriver("org.postgresql.Driver");

        this.processEngine = cfg.buildProcessEngine();
        this.runtimeService = processEngine.getRuntimeService();
        this.taskService = processEngine.getTaskService();

        // 部署流程
        processEngine.getRepositoryService().createDeployment()
                .addClasspathResource("simple-approval.bpmn20.xml")
                .deploy();
    }

    public String startLeaveProcess(LeaveApplication application) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("applicant", application.getApplicant());
        variables.put("manager", application.getManager());
        variables.put("application", application);
        variables.put("approved", null);

        ProcessInstance processInstance = runtimeService
                                                  .startProcessInstanceByKey("simpleApproval", variables);

        System.out.println("请假流程启动: " + processInstance.getId());
        return processInstance.getId();
    }

    public void processManagerTask(String manager) {
        Task task = taskService.createTaskQuery()
                            .taskAssignee(manager)
                            .singleResult();

        if (task != null) {
            LeaveApplication application = (LeaveApplication) taskService.getVariable(
                    task.getId(), "application");

            System.out.println("\n=== 经理审批任务 ===");
            System.out.println("申请人: " + application.getApplicant());
            System.out.println("请假时间: " + application.getStartDate() + " 至 " + application.getEndDate());
            System.out.println("天数: " + application.getDays());
            System.out.println("事由: " + application.getReason());

            // 模拟审批逻辑
            boolean approved = application.getDays() <= 5; // 5天以内自动批准

            Map<String, Object> variables = new HashMap<>();
            variables.put("approved", approved);
            application.setApproved(approved);
            application.setComments(approved ? "批准" : "请假天数过长，请重新申请");

            taskService.complete(task.getId(), variables);
            System.out.println("经理审批完成: " + (approved ? "批准" : "拒绝"));
        }
    }
}