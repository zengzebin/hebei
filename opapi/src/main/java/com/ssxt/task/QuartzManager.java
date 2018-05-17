package com.ssxt.task;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;

import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;

import org.springframework.stereotype.Service;

import com.ssxt.common.util.CachePool;
import com.ssxt.common.util.PropertiesUtil;
import com.ssxt.common.util.SpringUtil;
import com.ssxt.file.Analysis;
import com.ssxt.web.bean.BasDeviceInfo;
import com.ssxt.web.bean.BasStinfoB;
import com.ssxt.web.service.DeviceInfoService;
import com.ssxt.web.service.StinfoService;
import com.ssxt.web.service.warning.SettingService;

/**
 * @Description: 定时任务管理类
 * 
 * @ClassName: QuartzManager
 * @Copyright: Copyright (c) 2014
 * 
 * @author Comsys-LZP
 * @date 2014-6-26 下午03:15:52
 * @version V2.0
 */

@Service
public class QuartzManager {
	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(QuartzManager.class);

	private static String JOB_GROUP_NAME = "EXTJWEB_JOBGROUP_NAME";
	private static String TRIGGER_GROUP_NAME = "EXTJWEB_TRIGGERGROUP_NAME";
	/*
	 * 当初我初始化的是 SchedulerFactoryBean schedulerFactoryBean； 这样是注入不进去的 报下面的错
	 * nested exception is
	 * org.springframework.beans.factory.BeanNotOfRequiredTypeException: Bean
	 * named 'schedulerFactoryBean' must be of type
	 * [org.springframework.scheduling.quartz.SchedulerFactoryBean], but was
	 * actually of type [org.quartz.impl.StdScheduler>]
	 * 看spring源码可以知道，其实spring得到的是一个工厂bean，得到的不是它本身，而是它负责创建的org.quartz.impl.
	 * StdScheduler对象 所以要使用Scheduler对象
	 */
	@Resource
	private Scheduler scheduler;

	public Scheduler getScheduler() {
		return scheduler;

	}

	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	public void initCachePool() {

		// // 测站缓存
		// StinfoService stinfoService = (StinfoService)
		// SpringUtil.getContext().getBean("stinfoService");
		// List<BasStinfoB> basStinfoBs = stinfoService.loadAll();
		// Map<String, BasStinfoB> stion = new HashMap<String, BasStinfoB>();
		//
		// for (int i = 0; i < basStinfoBs.size(); i++) {
		// BasStinfoB bean = basStinfoBs.get(i);
		// stion.put(bean.getStcd(), bean);
		// }
		// CachePool.getInstance().putCacheItem("stion", stion);
		//
		// // 设备缓存
		// DeviceInfoService deviceInfoService = (DeviceInfoService)
		// SpringUtil.getContext().getBean("deviceInfoService");
		// List<BasDeviceInfo> BasDeviceInfos = deviceInfoService.loadAll();
		//
		// Map<Integer, BasDeviceInfo> device = new HashMap<Integer,
		// BasDeviceInfo>();
		// for (int i = 0; i < BasDeviceInfos.size(); i++) {
		// BasDeviceInfo bean = BasDeviceInfos.get(i);
		// device.put(bean.getId(), bean);
		// }
		// CachePool.getInstance().putCacheItem("device", device);
	}

	public void statisticsOrCreate() {
		try {

			// 创建考勤表
			JobTask job = new JobTask();
			job.setJobName("ClockTimeJob");
			job.setClassName("com.ssxt.task.ClockTimeJob");
			job.setJobTime("0 0 0,12,20 * * ? *");
			job.setInterval("0");
			addJob(job);

			// 创建工作日期
//			JobTask job2 = new JobTask();
//			job2.setJobName("CreateYearDateJob");
//			job2.setClassName("com.ssxt.task.CreateYearDateJob");
//			job2.setJobTime("0 0 0,4 1W 8,9,10,11,12 ? *");
//			job.setInterval("0");
//			addJob(job2);

			// 统计故障数
			JobTask job3 = new JobTask();
			job3.setJobName("StatisticsFaultJob");
			job3.setClassName("com.ssxt.task.StatisticsFaultJob");
			job3.setJobTime("0 58 23 * * ? *");
			job3.setInterval("0");
			addJob(job3);

			// 统计通畅率
			JobTask job4 = new JobTask();
			job4.setJobName("StatisticsUnobstructedJob");
			job4.setClassName("com.ssxt.task.StatisticsUnobstructedJob");
			job4.setJobTime("0 5 0 * * ?");
			job4.setInterval("0");
			addJob(job4);

			// 服务器畅通率
			JobTask job5 = new JobTask();
			job5.setJobName("ServerTask");
			job5.setClassName("com.ssxt.task.ServerTask");
			job5.setJobTime("0 0 0 * * ?");
			job5.setInterval("0");
			addJob(job5);

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 添加一个定时任务
	 * 
	 * @throws ClassNotFoundException
	 */
	public void addJob(JobTask job) throws SchedulerException, ClassNotFoundException {

		// Class classz = getClass(job.getClassName());
		Class classz = Class.forName(job.getClassName());

		// 这里获取任务信息数据
		TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), JOB_GROUP_NAME);
		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
		if (trigger == null) {
			// 不存在，创建一个
			/*
			 * JobDetail jobDetail =
			 * JobBuilder.newJob(classz).withIdentity(job.getJobName(),
			 * JOB_GROUP_NAME).build();
			 */

			JobDetail jobDetail = JobBuilder.newJob(classz).withIdentity(job.getJobName(), JOB_GROUP_NAME)
					.usingJobData("taskName", job.getJobName()).usingJobData("interval", job.getInterval()).build();

			jobDetail.getJobDataMap().put("scheduleJob", job);
			// 表达式调度构建器
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getJobTime());
			// 按新的cronExpression表达式构建一个新的trigger
			trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), JOB_GROUP_NAME)
					.withSchedule(scheduleBuilder).build();
			scheduler.pauseJob(jobDetail.getKey());// 暂停任务
			scheduler.scheduleJob(jobDetail, trigger);
			log.info("=========创建任务:" + job.getJobName());
			// scheduler.shutdown();

		} else {
			// Trigger已存在，那么更新相应的定时设置
			// 表达式调度构建器
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getJobTime());
			// 按新的cronExpression表达式重新构建trigger
			trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
			// 按新的trigger重新设置job执行
			scheduler.rescheduleJob(triggerKey, trigger);
		}
	}

	/**
	 * 启动所有定时任务
	 */
	public void startJobs() {
		try {
			scheduler.start();
			scheduler.resumeAll(); // 恢复全部任务
			log.info("=======启动全部任务=========");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public boolean initTask() {
		boolean is = true;

		PropertiesUtil.getProperty("file.saveFile");
		String[] jobName = PropertiesUtil.getProperty("task.name").split(",");
		String[] jobTime = PropertiesUtil.getProperty("task.cron").split("&");
		String[] jobClass = PropertiesUtil.getProperty("task.class").split(",");
		String[] interval = PropertiesUtil.getProperty("task.interval").split(",");

		if (jobName.length != jobTime.length) {
			log.error("config.properties 配置的数目不对请检查");
			return false;
		}

		for (int i = 0; i < jobTime.length; i++) {
			JobTask job = new JobTask();
			job.setJobTime(jobTime[i].trim());
			job.setJobName(jobName[i].trim());
			job.setClassName(jobClass[i].trim());
			job.setInterval(interval[i].trim());
			try {
				addJob(job);

			} catch (SchedulerException e) {
				// TODO Auto-generated catch block
				log.error(jobName[i] + "任务调度配置错误", e);
				is = false;
				break;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error(jobName[i] + "任务初始化错误", e);
				is = false;
			}
			if (!is)
				break;

		}

		return is;
	}

}