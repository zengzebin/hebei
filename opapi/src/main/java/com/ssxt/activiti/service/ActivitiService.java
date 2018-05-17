package com.ssxt.activiti.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.ssxt.activiti.controller.Util;
 
@Service
public class ActivitiService {

	@Autowired
	RepositoryService repositoryService;
	
	@Autowired
	RuntimeService runtimeService;
	
	@Autowired
	TaskService taskService;
	
	@Autowired
	HistoryService historyService;
	
	@Transactional
	public void  deploy(){
    //repositoryService.createDeployment().addClasspathResource("diagrams/" + processName).deploy();
   
	  String  []list=Util.list();
	}

	 
	@Transactional 
   public void start(){
	/*  System.out.println("repositoryService=="+repositoryService);
	  System.out.println("runtimeService==="+runtimeService);
	  System.out.println("taskService==="+taskService);
	  System.out.println("historyService==="+historyService);*/
	 
     System.out.println(repositoryService.createDeployment().addClasspathResource("diagrams/taskProcess.bpmn").deploy().getName());
     Map<String, Object> vars1 = new HashMap<String, Object>();
	    
	String procId = runtimeService.startProcessInstanceByKey("myProcess", vars1).getId();
 
     System.out.println("启动流程流程id="+procId);
     
     // 获得第一个任务
     List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("bin").list();
     //List<Task> tasks = taskService.createTaskQuery().taskCandidateUser("test").list();
     for (Task task : tasks) {
         System.out.println("要分配的任名称 " + task.getName());
        //分配的用户
         taskService.claim(task.getId(), "bin");
     }
     
     tasks = taskService.createTaskQuery().taskAssignee("bin").list();
     for (Task task : tasks) {
         System.out.println("接收到的任务 " + task.getName());
         // 执行(完成)任务
         taskService.complete(task.getId());
     } 
 
      tasks  = taskService.createTaskQuery().taskCandidateGroup("management").list();
       for (Task task : tasks) {
         System.out.println("分配任务" + task.getName());
         // 分配给的人
         taskService.claim(task.getId(), "bin");
         
     }
     
     
     tasks = taskService.createTaskQuery().taskAssignee("bin").list();
    
     for (Task task : tasks) {
    	 System.out.println("收到的任务" + task.getName());
         taskService.complete(task.getId());
     }
   
    
 }
	
	
	@Transactional 
	   public void start2(){
	     System.out.println(repositoryService.createDeployment().addClasspathResource("diagrams/test.bpmn").deploy().getName());
	 	
	     String procId = runtimeService.startProcessInstanceByKey("myProcess").getId();
	    // String procId2 = runtimeService.startProcessInstanceByKey("myProcess").getId();

	     Map<String, Object> vars = new HashMap<String, Object>();
	   
	     vars.put("operate", "staff");
	     complete(vars,procId);//提交订单
	     complete(vars,procId);//审核
	    
	     complete(vars,procId);//分发
	     vars.put("operate", "deal");
	     complete(vars,procId);//同意
	     complete(vars,procId);//不同意
	    
	    
	}
	
	
	  public void complete(Map<String, Object> vars,String procId){
		 
		 	List<Task> tasks = taskService.createTaskQuery().processInstanceId(procId).list();
	     
	     for(Task task : tasks){
	    	  System.out.println(task.getName());
	    	  taskService.complete(task.getId(), vars);
	     }
	 }
	  
	  
	  public void task(String procId){
			   List<Task> tasks = taskService.createTaskQuery().processInstanceId(procId).list();
	           System.out.println(tasks.get(0).getName());
	  }
	  
	  public void Mytask(String assignee,String group){
		   List<Task> tasks = taskService.createTaskQuery().
				  taskCandidateGroup(group).list();
				   
          for(Task task:tasks){
        	  System.out.println(task.getName());
          }
 }
}
