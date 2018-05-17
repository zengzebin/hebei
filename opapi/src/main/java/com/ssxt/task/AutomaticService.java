package com.ssxt.task;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.ssxt.common.enums.ConstParam;
import com.ssxt.common.page.PageControl;
import com.ssxt.common.page.SqlBuffer;
import com.ssxt.common.util.DataUtil;
import com.ssxt.web.bean.BasStinfoB;
import com.ssxt.web.bean.BasTaskInfo;
import com.ssxt.web.controller.TaskInfoController;
import com.ssxt.web.service.TaskInfoService;

@Service("automaticService")
public class AutomaticService {
	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(AutomaticService.class);

	@Autowired
	private TaskInfoService taskInfoService;

	/**
	 * 自动生产任务
	 * 
	 * @throws Exception
	 */
	public void addTask() throws Exception {
		// String sql = getSql("gw_water_info");
		StringBuffer sql = new StringBuffer("SELECT  a.stionId,a.stcd, TIMESTAMPDIFF(HOUR,B.sendTime,NOW()) difference");
		sql.append("  from bas_stinfo_b  a");
		sql.append(" LEFT JOIN faultsite b ON(a.stcd=b.stcd and a.serviceType=b.serviceType)");

		log.info(sql.toString());
		SqlBuffer where = SqlBuffer.begin();
		where.add(" TIMESTAMPDIFF(HOUR, b.sendTime, NOW())", 24, ">=");
		where.add(" a.valid", 1);
		PageControl p = PageControl.getQueryOnlyInstance();
		p.setGroupBy("GROUP BY a.stcd");
		List<Map<String, String>> list = taskInfoService.findByNativeCondition(PageControl.getQueryOnlyInstance(),
				where.end(), sql.toString(), Map.class);
		for (int i = 0; i < list.size(); i++) {
			Map  map = list.get(i);
			log.info(map.get("stionId").toString());
			Integer stionId = DataUtil.parseInt(map.get("stionId").toString(), 0);
			BasTaskInfo bean = new BasTaskInfo();
			bean.setStionId(stionId);
			bean.setPriorityStatus(1);
			bean.setContent("自动生成任务单,24小时未上报数据");
			taskInfoService.addTask(bean, "automatic");
		}
		log.info("自动生成任务单,24小时未上报数据条数:" + list.size());
	}

}
