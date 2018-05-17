package com.ssxt.task.warning.Water;

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
import com.ssxt.web.bean.warning.GwWtR;
import com.ssxt.web.bean.warning.GwZR;
import com.ssxt.web.service.warning.SettingService;
import com.ssxt.web.service.warning.WarningService;

public class WaterZJob extends CommonWarning implements Job {

	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(RainfallPptnJob.class);

	// 地下水埋深 gw_z_r BD
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		JobDetail detail = context.getJobDetail();
		int interval = DataUtil.parseInteger(detail.getJobDataMap().getString("interval"), 0);
		// int interval = 5;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:00");
		SimpleDateFormat timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		WarningService warningService = (WarningService) SpringUtil.getContext().getBean("warningService");

		Calendar nowTime = Calendar.getInstance();

		SqlBuffer where = SqlBuffer.begin();

		Date endTime = nowTime.getTime();// 当前时间
		nowTime.add(Calendar.MINUTE, -interval);
		Date startTime = nowTime.getTime();// 间隔

		where.add("TM", sdf.format(startTime), ">");
		where.add("TM", sdf.format(endTime), "<=");
		
		String sql = "select * from gw_z_r";
		List<GwZR> list = warningService.findByNativeCondition(PageControl.getQueryOnlyInstance(), where.end(), sql,
				GwZR.class);
		dealWarning(list, "WaterBD", "bd");

		// for (GwZR bean : list) {
		// boolean qualified = true;// 合格
		// if (bean.getBd() == null) {
		// qualified = false;
		// } else {
		// double bd = bean.getBd();
		// if (bd >= minValue && bd <= maxValue) {
		// // log.info("合格");
		// } else {
		// // log.info("不合格");
		// qualified = false;
		// }
		// }
		//
		// if (!qualified) {
		// BasWarning warning = new BasWarning();
		// String stcd = bean.getId().getStcd();
		// Timestamp time = bean.getId().getTm();
		// warning.setCode(stcd);
		// warning.setTime(time);
		// warning.setType(WaterWT.getName());
		// warning.setValue(bean.getBd());
		// warning.setStatus(0);
		// warning.setRemark("不符合" + WaterWT.getRemark());
		// warningService.warningAdd(warning);
		// }
		//
		// }

	}
}
