package com.ssxt.task;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.apache.xbean.spring.context.FileSystemXmlApplicationContext;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ssxt.common.util.SpringBeanUtil;
import com.ssxt.common.util.SpringUtil;
import com.ssxt.web.service.ClockService;

import java.sql.Timestamp;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class StatisticsUnobstructedJob implements Job {
	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(StatisticsUnobstructedJob.class);

	/**
	 * 统计全省故障数
	 */
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		ClockService clockService = (ClockService) SpringUtil.getContext().getBean("clockService");

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		Date time = cal.getTime();
		SimpleDateFormat start = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		SimpleDateFormat end = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
		StringBuffer name = new StringBuffer("StatisticsUnobstructed('").append(start.format(time)).append("','")
				.append(end.format(time)).append("')");
		// 调用存储过程
		clockService.prepareCall("StatisticsUnobstructed()");

	}

	public static void main(String[] args) {
		// ApplicationContext ctx = new FileSystemXmlApplicationContext(
		// "classpath:applicationContext.xml,classpath:activiti.cfg.xml");

		// ApplicationContext ctx = new ClassPathXmlApplicationContext(
		// new String[] { "classpath:applicationContext.xml",
		// "classpath:activiti.cfg.xml" });
		// AutomaticService automaticService = (AutomaticService)
		// SpringUtil.getInstance().getBeanByClass(AutomaticService.class);
		//
		// automaticService.addTask();
	}

}