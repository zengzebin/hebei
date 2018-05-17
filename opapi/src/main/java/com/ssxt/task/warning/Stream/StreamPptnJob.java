package com.ssxt.task.warning.Stream;

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
import com.ssxt.common.util.DataUtil;
import com.ssxt.common.util.SpringUtil;
import com.ssxt.task.warning.CommonWarning;
import com.ssxt.task.warning.Rainfall.RainfallPptnJob;
import com.ssxt.web.bean.warning.GwRiverR;
import com.ssxt.web.bean.warning.GwStreamPptnR;
import com.ssxt.web.service.warning.WarningService;

public class StreamPptnJob extends CommonWarning implements Job {

	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(RainfallPptnJob.class);

	// 中小河流雨量站雨量 gw_stream_pptn_r DRP
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		JobDetail detail = context.getJobDetail();
		int interval = DataUtil.parseInteger(detail.getJobDataMap().getString("interval"), 0);
		WarningService warningService = (WarningService) SpringUtil.getContext().getBean("warningService");

		// int interval = 5;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:00");
		SimpleDateFormat timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


		String sql = "select * from gw_stream_pptn_r";
		SqlBuffer where = SqlBuffer.begin();
		
		Calendar nowTime = Calendar.getInstance();

 		Date endTime = nowTime.getTime();// 当前时间
		nowTime.add(Calendar.MINUTE, -interval);
		Date startTime = nowTime.getTime();// 间隔

		// where.add("TM", "2018-01-08 20:00:00", "=");
		where.add("TM", sdf.format(startTime), ">");
		where.add("TM", sdf.format(endTime), "<=");

		List<GwStreamPptnR> list = warningService.findByNativeCondition(PageControl.getQueryOnlyInstance(), where.end(),
				sql, GwStreamPptnR.class);
		dealWarning(list, "StreamDRP", "drp");

	}
}
