package com.jh.cavy.workflow.api.service;

import com.jh.cavy.workflow.api.dto.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProcessService {
    String deployProcess(ProcessDefinitionDTO dto);
    ProcessInstanceDTO startProcess(StartProcessRequest request) ;
    List<TaskDTO> getUserTasks(String assignee);
    void completeTask(TaskCompleteRequest request);
    List<ProcessDefDTO> getProcessDefinitions() ;
}
