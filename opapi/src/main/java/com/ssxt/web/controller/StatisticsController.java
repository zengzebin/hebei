package com.ssxt.web.controller;

import static com.ssxt.common.util.DataUtil.notNullString;
import static com.ssxt.common.util.DataUtil.showMsgException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.cfg.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ssxt.common.enums.ActionMsgParam;
import com.ssxt.common.enums.ConstParam;
import com.ssxt.common.page.PageControl;
import com.ssxt.common.page.SqlBuffer;
import com.ssxt.common.page.SqlCondGroup;
import com.ssxt.common.page.WebSearchBuffer;
import com.ssxt.common.service.GenericServiceImpl;
import com.ssxt.common.util.AccData;
import com.ssxt.common.util.CachePool;
import com.ssxt.common.util.CtxHelper;
import com.ssxt.common.util.DataUtil;

import com.ssxt.common.web.SpringBaseController;
import com.ssxt.web.bean.BasTaskInfo;
import com.ssxt.web.bean.SymRole;
import com.ssxt.web.bean.SymUser;
import com.ssxt.web.bean.SysAddvcdB;
import com.ssxt.web.service.AddvcdService;
import com.ssxt.web.service.MapService;
import com.ssxt.web.service.RoleService;
import com.ssxt.web.service.StatisticsService;
import com.ssxt.web.service.TaskInfoService;
import com.ssxt.web.service.UserService;

@Controller
@RequestMapping(value = "/api/statistics/")

public class StatisticsController extends SpringBaseController<BasTaskInfo, String> {

	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(StatisticsController.class);

	@Autowired
	TaskInfoService taskInfoService;

	@Autowired
	StatisticsService statisticsService;

	@Autowired
	MapService mapService;

	// 故障站点统计
	@RequestMapping(value = "provinceSituation", method = RequestMethod.GET)
	public void provinceSituation(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map map = mapService.provinceSituation(request, response);
		toJson(map, 0, true, ActionMsgParam.ACM_LIS_SUC, request, response);

	}

	// 市故障正常数量
	@RequestMapping(value = "cityRunSituation", method = RequestMethod.GET)
	public void cityRunSituation(HttpServletRequest request, HttpServletResponse response) throws Exception {

		PageControl p = mapService.cityRunSituation(request, response);
		toJson(p.getList(), p.getList().size(), true, ActionMsgParam.ACM_LIS_SUC, request, response);

	}

	@Comment("各市维修任务及运维人数统计")
	@RequestMapping(value = "RepairStatistics", method = RequestMethod.GET)
	public void RepairStatistics(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map map = mapService.RepairStatistics(request, response);
		toJson(map, 0, true, "统计成功", request, response);
	}

	@Comment("通畅率")
	@RequestMapping(value = "unobstructed", method = RequestMethod.GET)
	public void unobstructed(HttpServletRequest request, HttpServletResponse response) throws Exception {

		PageControl p = mapService.unobstructed(request, response);
		toJson(p.getList(), p.getList().size(), true, ActionMsgParam.ACM_LIS_SUC, request, response);

	}

	@Comment("饼图及故障站点统计")
	@RequestMapping(value = "faultSite", method = RequestMethod.GET)
	public void faultSite(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map map = statisticsService.faultSite(request, response);
		toJson(map, 0, true, ActionMsgParam.ACM_LIS_SUC, request, response);

	}

	@Comment("测站报修趋势")
	@RequestMapping(value = "repairTrend", method = RequestMethod.GET)
	public void repairTrend(HttpServletRequest request, HttpServletResponse response) throws Exception {

//		Map map = statisticsService.repairTrend(request, response);
//		toJson(map, 0, true, "统计成功", request, response);
	}

	@Comment("运维人员信息打卡总数工作休假等信息")
	@RequestMapping(value = "maintenanceInfo", method = RequestMethod.GET)
	public void maintenanceInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map<String, String> map = statisticsService.getMaintenanceInfo(request, response);
		toJson(map, 1, true, ActionMsgParam.ACM_LIS_SUC, request, response);

	}

	@Comment("全省故障站趋势分析")
	@RequestMapping(value = "provinceFault", method = RequestMethod.GET)
	public void provinceFault(HttpServletRequest request, HttpServletResponse response) throws Exception {

		PageControl p = statisticsService.provinceFault(request, response);
		toJson(p.getList(), p.getList().size(), true, ActionMsgParam.ACM_LIS_SUC, request, response);

	}

	@Comment("运维效率评估")
	@RequestMapping(value = "efficiency", method = RequestMethod.GET)
	public void efficiency(HttpServletRequest request, HttpServletResponse response) throws Exception {

		PageControl p = statisticsService.efficiency(request, response);
		toJson(p.getList(), p.getList().size(), true, ActionMsgParam.ACM_LIS_SUC, request, response);

	}


	

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ssxt.rdbox.common.web.SpringBaseController#getCommentByHbm()
	 */
	@Override
	public boolean getCommentByHbm() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ssxt.rdbox.common.web.SpringBaseController#getEntityName()
	 */
	@Override
	public String getEntityName() {
		return "用户管理";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ssxt.rdbox.common.web.SpringBaseController#getService()
	 */
	@Override
	public GenericServiceImpl<BasTaskInfo, String> getService() {
		return taskInfoService;
	}

}
