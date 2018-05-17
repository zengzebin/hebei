package com.ssxt.activiti.controller;

import java.io.InputStream;

import java.util.List;

import javax.annotation.Resource;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.cmd.GetBpmnModelCmd;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

 
 public class ProcessInstanceDiagramCmd implements Command<InputStream> {

	protected String processInstanceId;
	
	
	 
	 
	ProcessEngine processEngine;

    public ProcessInstanceDiagramCmd(String processInstanceId,ProcessEngine processEngine) {
        this.processInstanceId = processInstanceId;
        this.processEngine=processEngine;
    }

    
    public InputStream execute(CommandContext commandContext) {
    	
    	 
        ExecutionEntityManager executionEntityManager = commandContext
            .getExecutionEntityManager();
        ExecutionEntity executionEntity = executionEntityManager
            .findExecutionById(processInstanceId);
        List<String> activiityIds = executionEntity.findActiveActivityIds();
        String processDefinitionId = executionEntity.getProcessDefinitionId();

        GetBpmnModelCmd getBpmnModelCmd = new GetBpmnModelCmd(
                processDefinitionId);
        BpmnModel bpmnModel = getBpmnModelCmd.execute(commandContext);

      /*  InputStream is = ProcessDiagramGenerator.generateDiagram(bpmnModel,
                "png", activiityIds);

        return is;*/
        
        Command<InputStream> cmd = new ProcessInstanceDiagramCmd(processInstanceId,processEngine);
        return processEngine.getManagementService().executeCommand(cmd);
    }

}
