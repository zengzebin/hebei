package com.ssxt.task.warning.TorrentialFlood;

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
import com.ssxt.web.bean.warning.GwRsvrR;
import com.ssxt.web.service.warning.SettingService;
import com.ssxt.web.service.warning.WarningService;

public class TorrentialFloodPptnJob extends CommonWarning implements Job {

	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(RainfallPptnJob.class);

	// 山洪雨量站雨量 gw_pptn_r DRP
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		JobDetail detail = context.getJobDetail();
		int interval = DataUtil.parseInteger(detail.getJobDataMap().getString("interval"), 0);
		// int interval = 5;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:00");
		SimpleDateFormat timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		WarningService warningService = (WarningService) SpringUtil.getContext().getBean("warningService");
		SettingService settingService = (SettingService) SpringUtil.getContext().getBean("settingService");

		String sql = "select * from gw_pptn_r";
		SqlBuffer where = SqlBuffer.begin();

		Calendar nowTime = Calendar.getInstance();
		Date endTime = nowTime.getTime();// 当前时间
		nowTime.add(Calendar.MINUTE, -interval);
		Date startTime = nowTime.getTime();// 间隔

		System.out.println("开始时间：" + sdf.format(startTime));
		System.out.println("结束时间：" + sdf.format(endTime));

		where.add("TM", sdf.format(startTime), ">");
		where.add("TM", sdf.format(endTime), "<=");

		List<GwPptnR> list = warningService.findByNativeCondition(PageControl.getQueryOnlyInstance(), where.end(), sql,
				GwPptnR.class);
		dealWarning(list, "TorrentialFloodDRP", "drp");

		// for (GwPptnR bean : list) {
		// boolean qualified = true;// 合格
		// if (bean.getDrp() == null) {
		// qualified = false;
		// } else {
		// double bd = bean.getDrp();
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
		// warning.setValue(bean.getDrp());
		// warning.setStatus(0);
		// warning.setRemark("不符合" + WaterWT.getRemark());
		// warningService.warningAdd(warning);
		// }
		//
		// }

	}
}
