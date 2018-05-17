package com.ssxt.web.controller;

import static com.ssxt.common.util.DataUtil.notNullString;
import static com.ssxt.common.util.DataUtil.showMsgException;

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
import com.ssxt.common.util.AonotaionAnalysis;
import com.ssxt.common.util.CachePool;
import com.ssxt.common.util.CtxHelper;
import com.ssxt.common.util.DataUtil;
import com.ssxt.common.util.MD5Tools;
import com.ssxt.common.util.MDCUtil;
import com.ssxt.common.util.StatusCode;
import com.ssxt.common.web.SpringBaseController;
import com.ssxt.reflect.ReflectSql;
import com.ssxt.web.bean.BasTaskInfo;
import com.ssxt.web.service.TaskInfoService;
import com.ssxt.web.bean.SymRole;
import com.ssxt.web.bean.SymUser;
import com.ssxt.web.bean.SysAddvcdB;
import com.ssxt.web.service.AddvcdService;
import com.ssxt.web.service.RoleService;
import com.ssxt.web.service.UserService;

@Controller
@RequestMapping(value = "/api/task/")
public class TaskInfoController extends SpringBaseController<BasTaskInfo, String> {

	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TaskInfoController.class);

	@Autowired
	TaskInfoService taskInfoService;

	@Comment("查询列表")
	@RequestMapping(value = "select", method = RequestMethod.GET)
	public void select(@RequestParam(value = "searchtext", required = false) String searchtext,
			@RequestParam(value = "order", required = false) String sort,
			@RequestParam(value = "orderdir", required = false) String dir,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "length", required = false) Integer pageSize, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SymRole role = CtxHelper.getSymRole(request);
		PageControl p = this.initPage(searchtext, sort, dir, start, pageSize, request, response);
		SqlBuffer where = SqlBuffer.begin();
		StringBuffer sql = new StringBuffer(ReflectSql.selectSql(new BasTaskInfo(), "task", null));
		sql.append(",now() now,b.stnm,b.stlc,b.serviceType,b.repairId ");
		sql.append(" from " + ReflectSql.getTblaeName(new BasTaskInfo()) + " task");
		sql.append(" INNER JOIN bas_stinfo_b b ON(b.stionId=task.stionId)");
		Object related = request.getAttribute("related");
		if (related != null) {
			// 与我相关的
			sql.append(" LEFT JOIN bas_task_help c ON(task.taskNo=c.taskNo)");
 			p.setGroupBy(" GROUP BY taskNo ");
			where.add("c.userId", MDCUtil.getUserId());
		} else {

			// 普通运维人员
			if (role.getDistinguish().equals("maintainer")) {
				where.add("task.maintUserId", MDCUtil.getUserId());
				where.addListInteger("task.taskStatus", request.getParameter("taskStatus"));
				where.addEndWith("task.addvcd", AddvcdService.likeStart(request.getParameter("addvcd")));
				where.add("task.taskStatus", StatusCode.send, ">=");
			} else {
				// 其他人员
				where.add("task.maintUserId", DataUtil.getParameterInt(request.getParameter("maintUserId")));
				where.addEndWith("task.addvcd", AddvcdService.likeStart(MDCUtil.getAddvcd()));
				where.addEndWith("task.addvcd", AddvcdService.likeStart(request.getParameter("addvcd")));
			}

		}

		where.add("b.valid", 1);// 有效站

		where.add("b.stcd", request.getParameter("stcd"));

		// 优先级
		where.add("task.priorityStatus", DataUtil.getParameterInt(request.getParameter("priorityStatus")));

		// 維修人員
		where.add("task.maintUserId", DataUtil.getParameterInt(request.getParameter("maintUserId")));

		where.add("b.serviceType", request.getParameter("serviceType"));

		where.addListInteger("task.taskStatus", request.getParameter("taskStatus"));

		where.add("task.createTime", request.getParameter("startTime"), ">=");
		where.add("task.createTime", request.getParameter("endTime"), "<=");

		this.commonFind(request, response, p, where, sql.toString(), Map.class);
		super.toJson(p.getList(), p.getRowCount(), true, ActionMsgParam.ACM_LIS_SUC, request, response);

	}

	@Comment("查询列表")
	@RequestMapping(value = "related", method = RequestMethod.GET)
	public void related(@RequestParam(value = "searchtext", required = false) String searchtext,
			@RequestParam(value = "order", required = false) String sort,
			@RequestParam(value = "orderdir", required = false) String dir,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "length", required = false) Integer pageSize, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setAttribute("related", "true");
		this.select(searchtext, sort, dir, start, pageSize, request, response);
	}

	@Comment("历史")
	@RequestMapping(value = "history", method = RequestMethod.GET)
	public void history(@RequestParam(value = "searchtext", required = false) String searchtext,
			@RequestParam(value = "order", required = false) String sort,
			@RequestParam(value = "orderdir", required = false) String dir,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "length", required = false) Integer pageSize, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		StringBuffer sql = new StringBuffer(ReflectSql.selectSql(new BasTaskInfo(), "task", null));
		sql.append(",now() now,b.stnm,b.stlc,b.serviceType,b.repairId ");
		sql.append(" from " + ReflectSql.getTblaeName(new BasTaskInfo()) + " task");
		sql.append(" INNER JOIN bas_stinfo_b b ON(b.stionId=task.stionId)");
		SqlBuffer where = SqlBuffer.begin();

		where.add("b.stcd", request.getParameter("stcd"));
		where.addListInteger("task.taskStatus", request.getParameter("taskStatus"));
		where.add("task.maintUserId", DataUtil.getParameterInt(request.getParameter("maintUserId")));
		where.add("task.createTime", request.getParameter("startTime"), ">=");
		where.add("task.createTime", request.getParameter("endTime"), "<=");

		PageControl p = this.initPage(searchtext, sort, dir, start, pageSize, request, response);
		this.commonFind(request, response, p, where, sql.toString(), Map.class);
		super.toJson(p.getList(), p.getRowCount(), true, ActionMsgParam.ACM_LIS_SUC, request, response);

	}

	@Comment("维修处理用于维修人员")
	@RequestMapping(value = "dealTask", method = RequestMethod.GET)
	public void dealTask(@RequestParam(value = "searchtext", required = false) String searchtext,
			@RequestParam(value = "order", required = false) String sort,
			@RequestParam(value = "orderdir", required = false) String dir,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "length", required = false) Integer pageSize, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		this.select(searchtext, sort, dir, start, pageSize, request, response);
	}

	@Comment("添加任务单")
	@RequestMapping(value = "addTask", method = RequestMethod.POST)
	public void addTask(@ModelAttribute("bean") BasTaskInfo bean, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println(response);
		taskInfoService.addTask(bean, "artificial");
		AccData ad = CtxHelper.createFailAccData();
		ad.setMsg(ActionMsgParam.ACM_SAV_SUC);
		ad.setSuccess(true);
		CtxHelper.writeToJson(response, ad);

	}

	@Comment("删除任务单")
	@RequestMapping(value = "delete/{taskNo}", method = RequestMethod.DELETE)
	public void delete(@PathVariable String taskNo, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		super.delete(taskNo, request, response);
	}

	@Comment("修改任务单")
	@RequestMapping(value = "update/{taskNo}", method = RequestMethod.PUT)
	public void update(@PathVariable String taskNo, @ModelAttribute("bean") BasTaskInfo bean,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		boolean bool = taskInfoService.updateTask(taskNo, bean);
		toJson(null, 0, bool, "修改成功", request, response);
	}

	@Comment("获取任务单信息")
	@RequestMapping(value = "load/{taskNo}", method = RequestMethod.GET)
	public void load(@PathVariable String taskNo, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.load(taskNo, request, response);
	}

	@Comment("获取全部任务单")
	@RequestMapping(value = "loadAll", method = RequestMethod.GET)
	public void loadAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AonotaionAnalysis bean = AonotaionAnalysis.createSql(BasTaskInfo.class, "taskNo", "task");
		StringBuffer sql = new StringBuffer("");
		sql.append("select task.taskNo" + bean.getField() + " from " + bean.getTable());
		sql.append(" INNER JOIN bas_stinfo_b b ON(task.stcd=b.stcd)");
		super.loadAll(request, response, null, sql.toString(), null);
	}

	@Comment("统计任务单状态数量")
	@RequestMapping(value = "statisticsState", method = RequestMethod.GET)
	public void statisticsState(HttpServletRequest request, HttpServletResponse response) throws Exception {
		SymRole role = CtxHelper.getSymRole(request);
		SqlBuffer where = SqlBuffer.begin();
		StringBuffer sql = new StringBuffer(
				"SELECT   taskStatus,taskStatusName name,COUNT(1) num FROM  bas_task_info a ");
		sql.append(" INNER JOIN bas_stinfo_b b on(a.stionId=b.stionId)");
		where.add("b.serviceType", request.getParameter("serviceType"));
		where.add("a.priorityStatus", DataUtil.getParameterInt(request.getParameter("priorityStatus")));
		where.addListInteger("a.taskStatus", request.getParameter("taskStatus"));

		if (role.getDistinguish().equals("maintainer")) {
			where.add("a.maintUserId", MDCUtil.getUserId());
			where.addEndWith("a.addvcd", AddvcdService.likeStart(request.getParameter("addvcd")));

		} else {
			where.addEndWith("a.addvcd", AddvcdService.likeStart(MDCUtil.getAddvcd()));
			where.addEndWith("a.addvcd", AddvcdService.likeStart(request.getParameter("addvcd")));
		}

		where.add("b.valid", 1);
		PageControl p = PageControl.getQueryOnlyInstance();
		p.setGroupBy("GROUP BY taskStatus");
		super.loadAll(request, response, where, sql.toString(), p);
	}

	@Comment("与我相关统计任务单状态数量")
	@RequestMapping(value = "relatedState", method = RequestMethod.GET)
	public void relatedState(HttpServletRequest request, HttpServletResponse response) throws Exception {
		SymRole role = CtxHelper.getSymRole(request);
		SqlBuffer where = SqlBuffer.begin();
		StringBuffer sql = new StringBuffer(
				"SELECT  taskStatus,taskStatusName NAME,COUNT(1) num FROM     bas_task_help a ");
		sql.append(" INNER JOIN bas_task_info b ON(a.taskNo=b.taskNo)");
		sql.append(" INNER JOIN bas_stinfo_b c on(b.stcd=c.stcd)");

		where.add("a.userId", MDCUtil.getUserId());

		where.add("c.valid", 1);
		PageControl p = PageControl.getQueryOnlyInstance();
		p.setGroupBy("GROUP BY taskStatus");
		super.loadAll(request, response, where, sql.toString(), p);
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
