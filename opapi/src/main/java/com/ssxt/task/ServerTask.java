package com.ssxt.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.ssxt.common.util.SpringUtil;
import com.ssxt.web.service.ClockService;
import com.ssxt.web.service.ServerService;

public class ServerTask implements Job {
	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ServerTask.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		ServerService serverService = (ServerService) SpringUtil.getContext().getBean("serverService");

		serverService.serverTask();

	}

}
