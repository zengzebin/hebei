package com.ssxt.web.controller;

import static com.ssxt.common.util.DataUtil.notNullString;
import static com.ssxt.common.util.DataUtil.showMsgException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import com.ssxt.common.util.MDCUtil;
import com.ssxt.common.web.SpringBaseController;
import com.ssxt.task.StatisticsFaultJob;
import com.ssxt.task.StatisticsUnobstructedJob;
import com.ssxt.task.warning.Rainfall.RainfallPptnJob;
import com.ssxt.task.warning.Stream.StreamPptnJob;
import com.ssxt.task.warning.Stream.StreamRiverJob;
import com.ssxt.task.warning.TorrentialFlood.TorrentialFloodPptnJob;
import com.ssxt.task.warning.TorrentialFlood.TorrentialFloodRiverJob;
import com.ssxt.task.warning.TorrentialFlood.TorrentialFloodRsvrJob;
import com.ssxt.task.warning.Water.WaterWtJob;
import com.ssxt.task.warning.Water.WaterZJob;
import com.ssxt.web.bean.BasClock;
import com.ssxt.web.bean.BasClockId;
import com.ssxt.web.bean.BasVacationTravel;
import com.ssxt.web.bean.BasTemporary;
import com.ssxt.web.bean.SymRole;
import com.ssxt.web.bean.SymUser;
import com.ssxt.web.bean.SysAddvcdB;
import com.ssxt.web.dao.WordDateDao;
import com.ssxt.web.service.AddvcdService;
import com.ssxt.web.service.ClockService;
import com.ssxt.web.service.RoleService;
import com.ssxt.web.service.TemporaryService;
import com.ssxt.web.service.UserService;

@Controller
@RequestMapping(value = "/api/work/")

public class WorkController extends SpringBaseController<BasClock, BasClockId> {

	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(WorkController.class);

	@Autowired
	private ClockService clockService;

	@Autowired
	private TemporaryService temporaryService;

	@Comment("休假及出差查询")
	@RequestMapping(value = "vacationTravel", method = RequestMethod.GET)
	public void selectVacationTravel(@RequestParam(value = "searchtext", required = false) String searchtext,
			@RequestParam(value = "order", required = false) String sort,
			@RequestParam(value = "orderdir", required = false) String dir,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "length", required = false) Integer pageSize, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SymRole role = CtxHelper.getSymRole(request);
		SqlBuffer where = SqlBuffer.begin();
	 
		StringBuffer sql = new StringBuffer("SELECT a.id,a.userId,b.name,b.position,b.directlyUnder,");
		sql.append(" b.phone,a.content,a.createTime,a.startTime,a.endTime,a.reason,a.type,a.state");
		sql.append(" FROM bas_vacation_travel a");
		sql.append(" INNER JOIN sym_user  b ON(a.userId=b.Id)");
 
		if (request.getParameter("userId")!=null) {
			where.add("a.userId",  DataUtil.getParameterInt(request.getParameter("userId")));
		}else{
			where.addEndWith("b.directlyUnder", AddvcdService.likeStart(MDCUtil.getAddvcd()));
		}
		
		
		where.addListInteger("a.type", request.getParameter("type"));
		where.addListInteger("a.state", request.getParameter("state"));

		PageControl p = super.selectSql(searchtext, sort, dir, start, pageSize, request, response, null, where,
				sql.toString());
		super.toJson(p.getList(), p.getRowCount(), true, ActionMsgParam.ACM_LIS_SUC, request, response);
	}

	@Comment("考勤查询")
	@RequestMapping(value = "attendance", method = RequestMethod.GET)
	public void attendance(@RequestParam(value = "searchtext", required = false) String searchtext,
			@RequestParam(value = "order", required = false) String sort,
			@RequestParam(value = "orderdir", required = false) String dir,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "length", required = false) Integer pageSize, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		SymRole role = CtxHelper.getSymRole(request);
		SqlBuffer where = SqlBuffer.begin();
		String userId = request.getParameter("userId");

		StringBuffer sql = null;

		if (userId != null) {

			sql = new StringBuffer("SELECT a.date,a.name,b.userId,b.toWork,b.state,a.workmk,a.remark ");
			sql.append("  FROM  bas_work_date  a ");
			sql.append(" LEFT  JOIN bas_clock b ON(a.date=b.date AND userid=").append(userId).append(")");
			PageControl p = PageControl.getQueryOnlyInstance();
			p.setOrder(" ORDER BY a.date ASC ");
			where.add("a.date", request.getParameter("startTime"), ">=", "and");
			where.add("a.date", request.getParameter("endTime"), "<=", "and");
			super.commonFind(request, response, p, where, sql.toString(), Map.class);
			super.toJson(p.getList(), p.getList().size(), true, ActionMsgParam.ACM_LIS_SUC, request, response);

		} else {
			sql = new StringBuffer("SELECT a.* FROM sym_user  a");
			sql.append(" INNER JOIN sym_role b ON(a.roleId=b.id) ");
			where.add("b.isOperation", 1);
			where.add("a.isEnable", 1);
			where.addEndWith("a.directlyUnder", AddvcdService.likeStart(MDCUtil.getAddvcd()));

			PageControl p = super.commonFind(request, response, null, where, sql.toString(), SymUser.class);
			where.remove();

			where.add("a.date", request.getParameter("startTime"), ">=", "and");
			where.add("a.date", request.getParameter("endTime"), "<=", "and");
			List list = new ArrayList();
			for (int i = 0; i < p.getList().size(); i++) {
				SymUser user = (SymUser) p.getList().get(i);
				sql.delete(0, sql.length());

				sql.append("SELECT a.date,a.name,b.userId,b.toWork,b.state,a.workmk,a.remark ");
				sql.append("  FROM  bas_work_date  a ");
				sql.append(" LEFT  JOIN bas_clock b ON(a.date=b.date AND b.userid=").append(user.getId()).append(")");
				PageControl p2 = PageControl.getQueryOnlyInstance();
				p2.setOrder(" ORDER BY a.date ASC ");
				super.commonFind(request, response, p2, where, sql.toString(), Map.class);
				Map map = new HashMap();
				map.put("userId", user.getId());
				map.put("userName", user.getName());
				map.put("data", p2.getList());
				list.add(map);

			}
			super.toJson(list, list.size(), true, ActionMsgParam.ACM_LIS_SUC, request, response);

		}

	}

	@Comment("获取全部区域")
	@RequestMapping(value = "loadAll", method = RequestMethod.GET)
	public void loadAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
		super.loadAll(request, response, null, null, null);

	}

	@Comment("签到")
	@RequestMapping(value = "toWork", method = RequestMethod.POST)
	public void toWork(HttpServletRequest request, HttpServletResponse response) throws Exception {
		clockService.toWord();
		// StreamRiverJob job = new StreamRiverJob();
		// job.execute(null);
		toJson(null, 1, true, ActionMsgParam.ACM_SAV_SUC, request, response);

	}

	@Comment("休假及出差申请")
	@RequestMapping(value = "vacationTravel", method = RequestMethod.POST)
	public void vacationBusinessTyrip(@ModelAttribute("bean") BasVacationTravel bean, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		clockService.addVacationTravel(bean);
		toJson(bean, 1, true, ActionMsgParam.ACM_SAV_SUC, request, response);

	}

	@Comment("休假及出差审批结果")
	@RequestMapping(value = "vacationTravel/{id}", method = RequestMethod.PUT)
	public void ReplyVacationTravel(@PathVariable Integer id, @ModelAttribute("bean") BasVacationTravel bean,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		clockService.ReplyVacationTravel(id, bean);
		super.toJson(null, bean, true, ActionMsgParam.ACM_LIS_SUC, request, response);

	}

	@Comment("代班查询")
	@RequestMapping(value = "temporary", method = RequestMethod.GET)
	public void temporary(@RequestParam(value = "searchtext", required = false) String searchtext,
			@RequestParam(value = "order", required = false) String sort,
			@RequestParam(value = "orderdir", required = false) String dir,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "length", required = false) Integer pageSize, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SymRole role = CtxHelper.getSymRole(request);
		SqlBuffer where = SqlBuffer.begin();
		StringBuffer sql = new StringBuffer("SELECT a.* FROM bas_temporary a");
		sql.append(" INNER JOIN sym_user  b ON(a.createUserId=b.Id)");

//		// 維修人員
//		if (role.getIsOperation() == 1) {
//			where.add("a.createUserId", MDCUtil.getUserId());
//		} else {
//			// 权限
//			// sql.append(" INNER JOIN sym_addvcd_user c
//			// ON(c.addvcd=b.directlyUnder AND c.userId=" + MDCUtil.getUserId()
//			// + ")");
//			where.addEndWith("b.addvcd", AddvcdService.likeStart(MDCUtil.getAddvcd()));
//		}
		
		if (request.getParameter("userId")!=null) {
			where.add("a.createUserId", DataUtil.getParameterInt(request.getParameter("userId")));
		}else{
			where.addEndWith("b.addvcd", AddvcdService.likeStart(MDCUtil.getAddvcd()));
		}
		
		
	
			 
		
		
		// where.addListInteger("a.type", request.getParameter("type"));
		// where.addListInteger("a.state", request.getParameter("state"));
		PageControl p = super.selectSql(searchtext, sort, dir, start, pageSize, request, response, null, where,
				sql.toString());
		super.toJson(p.getList(), p.getRowCount(), true, ActionMsgParam.ACM_LIS_SUC, request, response);
	}

	@Comment("修改代班")
	@RequestMapping(value = "temporary/{id}", method = RequestMethod.PUT)
	public void updateTemporary(@PathVariable Integer id, @ModelAttribute("bean") BasTemporary bean,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		super.toJson(null, bean, true, ActionMsgParam.ACM_LIS_SUC, request, response);
		BasTemporary oldBean = temporaryService.get(id);
		temporaryService.updateDomain("修改代班", oldBean, bean);
		toJson(bean, 1, true, ActionMsgParam.ACM_MOD_SUC, request, response);

	}

	@Comment("添加代班")
	@RequestMapping(value = "temporary", method = RequestMethod.POST)
	public void addTemporary(@ModelAttribute("bean") BasTemporary bean, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		bean.setCreateUserId(MDCUtil.getUserId());
		temporaryService.save(bean);
		super.toJson(null, bean, true, ActionMsgParam.ACM_SAV_SUC, request, response);

	}

	@Comment("删除代班")
	@RequestMapping(value = "temporary/{id}", method = RequestMethod.DELETE)
	public void deleteTemporary(@PathVariable Integer id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		temporaryService.deleteByKey(id);
		super.toJson(null, 0, true, ActionMsgParam.ACM_DEL_SUC, request, response);

	}

	@Comment("单个代班")
	@RequestMapping(value = "temporaryLoad/{id}", method = RequestMethod.GET)
	public void temporaryLoad(@PathVariable Integer id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		super.toJson(temporaryService.get(id), 1, true, ActionMsgParam.ACM_DEL_SUC, request, response);

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
	public GenericServiceImpl<BasClock, BasClockId> getService() {
		return clockService;
	}

}
