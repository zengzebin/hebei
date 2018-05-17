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
import com.ssxt.web.bean.BasServer;
import com.ssxt.web.bean.SymMenu;
import com.ssxt.web.bean.SymRole;
import com.ssxt.web.bean.SymUser;
import com.ssxt.web.bean.SysAddvcdB;
import com.ssxt.web.service.AddvcdService;
import com.ssxt.web.service.RoleService;
import com.ssxt.web.service.ServerService;
import com.ssxt.web.service.UserService;

@Controller
@RequestMapping(value = "/api/server/")

public class ServerController extends SpringBaseController<BasServer, Integer> {

	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ServerController.class);
	@Autowired
	ServerService serverService;

	@Comment("查询列表")
	@RequestMapping(value = "select", method = RequestMethod.GET)
	public void list(@RequestParam(value = "searchtext", required = false) String searchtext,
			@RequestParam(value = "order", required = false) String sort,
			@RequestParam(value = "orderdir", required = false) String dir,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "length", required = false) Integer pageSize, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		SqlBuffer sbf = SqlBuffer.begin();
//		sbf.add("parentId", DataUtil.parseInteger(request.getParameter("parentId")));
//		sbf.addLike("menuText", request.getParameter("menuText"));
		PageControl p = super.selectHql(searchtext, sort, dir, start, pageSize, request, response, sbf);
		super.toJson(p.getList(), p.getRowCount(), true, ActionMsgParam.ACM_LIS_SUC, request, response);

	}

	@Comment("新增数据")
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public void add(@ModelAttribute("bean") BasServer bean, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		serverService.save(bean);
		super.toJson(null, 1, true, ActionMsgParam.ACM_SAV_SUC, request, response);
	}

	@Comment("修改数据数据")
	@RequestMapping(value = "update", method = RequestMethod.PUT)
	public void update(@ModelAttribute("bean") BasServer bean, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// bean.setCreateTime(new Date());

		BasServer old = serverService.get(bean.getId());
		serverService.updateDomain("修改服务器", old, bean);
		super.toJson(null, 1, true, ActionMsgParam.ACM_MOD_SUC, request, response);

	}

	@Comment("刪除")
	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable Integer id, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// bean.setCreateTime(new Date());
		serverService.deleteByKey(id);
		super.toJson(null, 1, true, ActionMsgParam.ACM_DEL_SUC, request, response);
	}

	@Comment("查询列表")
	@RequestMapping(value = "synchronization", method = RequestMethod.GET)
	public void synchronization(@RequestParam(value = "searchtext", required = false) String searchtext,
			@RequestParam(value = "order", required = false) String sort,
			@RequestParam(value = "orderdir", required = false) String dir,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "length", required = false) Integer pageSize, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
 
		int day = DataUtil.daysBetween(request.getParameter("startTime"), request.getParameter("endTime")) + 1;
		SqlBuffer where = SqlBuffer.begin();
//		serverService.serverTask();
		PageControl p = this.initPage(searchtext, sort, dir, start, pageSize, request, response);

		StringBuffer sql = new StringBuffer("SELECT a.name,c.addvnm,a.addvcd,");

		sql.append(" FORMAT(SUM(CASE WHEN  b.state = 1 THEN 0 ELSE 1 END)/").append(day).append(",2) rate");
		sql.append(" FROM  bas_server  a  ");
		sql.append(" LEFT JOIN  bas_server_situation b ON(a.id=b.serverId) ");
		sql.append(" LEFT JOIN sys_addvcd_b  c ON (a.addvcd=c.addvcd) ");
		p.setGroupBy("GROUP BY a.id");
		where.add("b.createTime", request.getParameter("startTime")+" 00:00:00", ">=");
		where.add("b.createTime", request.getParameter("endTime")+" 23:59:59", "<=");
		this.commonFind(request, response, p, where, sql.toString(), Map.class);
		super.toJson(p.getList(), p.getRowCount(), true, ActionMsgParam.ACM_LIS_SUC, request, response);

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
	public GenericServiceImpl<BasServer, Integer> getService() {
		return serverService;
	}

}
