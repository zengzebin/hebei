package com.ssxt.task.warning.Water;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.sql.Timestamp;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.ssxt.common.page.PageControl;
import com.ssxt.common.page.SqlBuffer;
import com.ssxt.common.util.CachePool;
import com.ssxt.common.util.DataUtil;
import com.ssxt.common.util.SpringUtil;
import com.ssxt.reflect.ReflectSql;
import com.ssxt.task.AutomaticService;
import com.ssxt.task.warning.CommonWarning;
import com.ssxt.task.warning.Rainfall.RainfallPptnJob;
import com.ssxt.web.bean.warning.BasSetting;
import com.ssxt.web.bean.warning.BasWarning;
import com.ssxt.web.bean.warning.GwWtR;
import com.ssxt.web.service.warning.SettingService;
import com.ssxt.web.service.warning.WarningService;

public class WaterWtJob extends CommonWarning implements Job {

	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(RainfallPptnJob.class);

	// 地下水水温gw_wt_r WT
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub

		JobDetail detail = context.getJobDetail();
		int interval = DataUtil.parseInteger(detail.getJobDataMap().getString("interval"), 0);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:00");
		SimpleDateFormat timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Calendar nowTime = Calendar.getInstance();
		Date endTime  = nowTime.getTime();// 当前时间
		nowTime.add(Calendar.MINUTE, -interval);
		Date startTime = nowTime.getTime();// 间隔

		System.out.println("开始时间：" + sdf.format(startTime));
		System.out.println("结束时间：" + sdf.format(endTime));

		WarningService warningService = (WarningService) SpringUtil.getContext().getBean("warningService");
		SettingService settingService = (SettingService) SpringUtil.getContext().getBean("settingService");

		SqlBuffer where = SqlBuffer.begin();

		// where.add("TM", "2018-01-08 20:00:00", ">");

		where.add("TM", sdf.format(startTime), ">");
		where.add("TM", sdf.format(endTime), "<=");

//		BasSetting WaterWT = (BasSetting) CachePool.getInstance().getCacheItem("WaterWT");
//
//		if (WaterWT == null) {
//			settingService.init();
//			WaterWT = (BasSetting) CachePool.getInstance().getCacheItem("WaterWT");
//		}
//		int minValue = DataUtil.parseInteger(WaterWT.getValue().split("-")[0], 0);
//		int maxValue = DataUtil.parseInteger(WaterWT.getValue().split("-")[1], 0);

		String sql = "select * from gw_wt_r";

		List<GwWtR> list = warningService.findByNativeCondition(PageControl.getQueryOnlyInstance(), where.end(), sql,
				GwWtR.class);
		dealWarning(list, "WaterWT", "wt");

		// for (GwWtR bean : list) {
		// double wt = bean.getWt();
		// if (wt >= minValue && wt <= maxValue) {
		// // log.info("合格");
		// } else {
		// // log.info("不合格");
		// BasWarning warning = new BasWarning();
		// String stcd = bean.getId().getStcd();
		// Timestamp time = bean.getId().getTm();
		// warning.setCode(stcd);
		// warning.setTime(time);
		// warning.setType(WaterWT.getName());
		// warning.setValue(wt);
		// warning.setStatus(0);
		// warning.setRemark("不符合" + WaterWT.getRemark());
		// warningService.warningAdd(warning);
		// // String delete = "delete from gw_wt_r where ";
		// // where.remove();
		// // where.add("stcd", stcd);
		// // where.add("tm", timestamp.format(time));
		// // warningService.deleteSql(delete, where.end());
		// }
		// }

	}
}
