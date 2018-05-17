package com.ssxt.task.warning.TorrentialFlood;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.ssxt.common.page.PageControl;
import com.ssxt.common.page.SqlBuffer;
import com.ssxt.common.util.CachePool;
import com.ssxt.common.util.DataUtil;
import com.ssxt.common.util.SpringUtil;
import com.ssxt.task.warning.CommonWarning;
import com.ssxt.task.warning.Rainfall.RainfallPptnJob;
import com.ssxt.web.bean.warning.BasSetting;
import com.ssxt.web.bean.warning.BasWarning;
import com.ssxt.web.bean.warning.GwPptnR;
import com.ssxt.web.bean.warning.GwRiverR;
import com.ssxt.web.bean.warning.GwRsvrR;
import com.ssxt.web.service.warning.SettingService;
import com.ssxt.web.service.warning.WarningService;
import com.ssxt.web.vo.Analysis;

public class TorrentialFloodRiverJob extends CommonWarning implements Job {

	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(RainfallPptnJob.class);

	// 山洪河道流量 gw_river_r Q
	// 山洪河道水位 gw_river_r Z
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		JobDetail detail = context.getJobDetail();
		int interval = DataUtil.parseInteger(detail.getJobDataMap().getString("interval"), 0);
		WarningService warningService = (WarningService) SpringUtil.getContext().getBean("warningService");

		// int interval = 5;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:00");
		SimpleDateFormat timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String sql = "select * from gw_river_r";
		SqlBuffer where = SqlBuffer.begin();

		Calendar nowTime = Calendar.getInstance();
		Date endTime = nowTime.getTime();// 当前时间
		nowTime.add(Calendar.MINUTE, -interval);
		Date startTime = nowTime.getTime();// 间隔

		System.out.println("开始时间：" + sdf.format(startTime));
		System.out.println("结束时间：" + sdf.format(endTime));
		
		where.add("TM", sdf.format(startTime), ">");
		where.add("TM", sdf.format(endTime), "<=");

		List<GwRiverR> list = warningService.findByNativeCondition(PageControl.getQueryOnlyInstance(), where.end(), sql,
				GwRiverR.class);
		dealWarning(list, "TorrentialFloodQ", "q");
		dealWarning(list, "TorrentialFloodZ", "z");

	}

}
