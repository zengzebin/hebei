package com.ssxt.web.controller;

import static com.ssxt.common.util.DataUtil.notNullString;
import static com.ssxt.common.util.DataUtil.showMsgException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jms.Destination;
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

import com.ssxt.activeMQ.DmzProducerService;
import com.ssxt.common.enums.ActionMsgParam;
import com.ssxt.common.enums.ConstParam;
import com.ssxt.common.page.PageControl;
import com.ssxt.common.page.SqlBuffer;
import com.ssxt.common.page.SqlCondGroup;
import com.ssxt.common.page.WebSearchBuffer;
import com.ssxt.common.service.GenericServiceImpl;
import com.ssxt.common.util.AccData;
import com.ssxt.common.util.AonotaionAnalysis;
import com.ssxt.common.util.CachePool;
import com.ssxt.common.util.CtxHelper;
import com.ssxt.common.util.DataUtil;
import com.ssxt.common.util.MDCUtil;
import com.ssxt.common.web.SpringBaseController;
import com.ssxt.reflect.ReflectSql;
import com.ssxt.web.bean.BasDeviceInfo;
import com.ssxt.web.bean.BasMaintGps;
import com.ssxt.web.bean.SymRole;
import com.ssxt.web.bean.SymUser;
import com.ssxt.web.bean.SysAddvcdB;
import com.ssxt.web.service.AddvcdService;
import com.ssxt.web.service.MaintGpsLastService;
import com.ssxt.web.service.MaintGpsService;
import com.ssxt.web.service.MapService;
import com.ssxt.web.service.MasterService;

import com.ssxt.web.service.RoleService;
import com.ssxt.web.service.UserService;

@Controller
@RequestMapping(value = "/api/map/")
public class MapController extends SpringBaseController<BasMaintGps, Integer> {

	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(MapController.class);

	@Autowired
	MaintGpsService maintGpsService;

	@Autowired
	MasterService masterService;

	@Autowired
	MapService mapService;

	@Comment("查询运维人员最后的坐标")
	@RequestMapping(value = "maintLastPosition", method = RequestMethod.GET)
	public void maintPosition(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// StringBuffer sql = new StringBuffer(ReflectSql.selectSql(new
		// SymUser(), "a", "passWord"));
		PageControl p = PageControl.getQueryOnlyInstance();
		StringBuffer sql = new StringBuffer(ReflectSql.selectSql(new SymUser(), "a", "passWord"));
		SqlBuffer where = SqlBuffer.begin();
		sql.append(" ,b.longitude,b.latitude,b.createTime lastTime, ");
		sql.append(
				"(SELECT GROUP_CONCAT(taskNo) FROM bas_task_info t WHERE t.maintUserId=a.id AND t.taskStatus>0 AND t.taskStatus<60 )taskNo,");
		sql.append(
				"(SELECT state FROM bas_clock clock WHERE clock.userId=a.id  AND  clock.date=DATE_FORMAT(NOW(),'%Y-%m-%d')) state ");

		sql.append(" from ").append(ReflectSql.getTblaeName(new SymUser()) + " a");

		sql.append(" inner join bas_maint_gps_last b on(a.id=b.maintUserId)");

		// sql.append(" INNER JOIN sym_addvcd_user c ON(a.id=c.userId)");
		sql.append(" inner join sym_role d on(a.roleId=d.id)");
		// sql.append(" INNER JOIN sym_addvcd_user z ON(z.addvcd=c.addvcd AND
		// z.userId=" + MDCUtil.getUserId() + ")");

		where.add("d.isOperation", 1);
		where.addEndWith("a.addvcd", AddvcdService.likeStart(request.getParameter("addvcd")));
		where.addEndWith("a.addvcd", AddvcdService.likeStart(MDCUtil.getAddvcd()));
		p.setGroupBy(" GROUP BY a.id");
		log.info(sql.toString());
		super.loadAll(request, response, where, sql.toString(), p);

	}

	@Comment("根据县统计")
	@RequestMapping(value = "24ReportCounty", method = RequestMethod.GET)
	public void ReportCounty24(@RequestParam(value = "searchtext", required = false) String searchtext,
			@RequestParam(value = "order", required = false) String sort,
			@RequestParam(value = "orderdir", required = false) String dir,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "length", required = false) Integer pageSize, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		PageControl p = mapService.ReportCounty24(searchtext, sort, dir, start, pageSize, request, response);

		super.toJson(p.getList(), p.getRowCount(), true, ActionMsgParam.ACM_LIS_SUC, request, response);

	}

	@Comment("地图及表格故障站点24小时未上报")
	@RequestMapping(value = "faultSite24", method = RequestMethod.GET)
	public void faultSite24(@RequestParam(value = "searchtext", required = false) String searchtext,
			@RequestParam(value = "order", required = false) String sort,
			@RequestParam(value = "orderdir", required = false) String dir,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "length", required = false) Integer pageSize, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		PageControl p = PageControl.getQueryOnlyInstance();
		// 是否分页
		if (request.getParameter("page") != null)
			p = super.initPage(searchtext, sort, dir, start, pageSize, request, response);
		mapService.faultSite24(p, request, response);

		super.toJson(p.getList(), p.getRowCount(), true, ActionMsgParam.ACM_LIS_SUC, request, response);

	}

	@Comment("测站信息值")
	@RequestMapping(value = "stationState", method = RequestMethod.GET)
	public void stationState(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// PageControl p =
		Map map = mapService.stationState(request, response);
		super.toJson(map, 1, true, ActionMsgParam.ACM_LIS_SUC, request, response);

	}

	// @Comment("根据站点统计")
	// @RequestMapping(value = "24ReportStinfo", method = RequestMethod.GET)
	// public void ReportStinfo24(@RequestParam(value = "searchtext", required =
	// false) String searchtext,
	// @RequestParam(value = "order", required = false) String sort,
	// @RequestParam(value = "orderdir", required = false) String dir,
	// @RequestParam(value = "start", required = false) Integer start,
	// @RequestParam(value = "length", required = false) Integer pageSize,
	// HttpServletRequest request,
	// HttpServletResponse response) throws Exception {
	//
	// PageControl p = masterService.reportService24(searchtext, sort, dir,
	// start, pageSize, request, response);
	//
	// super.toJson(p.getList(), p.getRowCount(), true,
	// ActionMsgParam.ACM_LIS_SUC, request, response);
	// }

	@Override
	public boolean getCommentByHbm() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getEntityName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GenericServiceImpl<BasMaintGps, Integer> getService() {
		// TODO Auto-generated method stub
		return maintGpsService;
	}

}
