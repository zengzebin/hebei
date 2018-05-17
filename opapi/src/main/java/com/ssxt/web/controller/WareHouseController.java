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
import com.ssxt.common.util.MDCUtil;
import com.ssxt.common.web.SpringBaseController;
import com.ssxt.reflect.ReflectSql;
import com.ssxt.web.bean.BasTaskInfo;
import com.ssxt.web.bean.BasWarehouse;
import com.ssxt.web.bean.SymRole;
import com.ssxt.web.bean.SymUser;
import com.ssxt.web.bean.SysAddvcdB;
import com.ssxt.web.service.AddvcdService;
import com.ssxt.web.service.RoleService;
import com.ssxt.web.service.UserService;
import com.ssxt.web.service.WareHouseService;

@Controller
@RequestMapping(value = "/api/WareHouse/")

public class WareHouseController extends SpringBaseController<BasWarehouse, Integer> {

	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(WareHouseController.class);

	@Autowired
	WareHouseService wareHouseService;

	@Comment("仓库查询列表")
	@RequestMapping(value = "select", method = RequestMethod.GET)
	public void select(@RequestParam(value = "searchtext", required = false) String searchtext,
			@RequestParam(value = "order", required = false) String sort,
			@RequestParam(value = "orderdir", required = false) String dir,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "length", required = false) Integer pageSize, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		StringBuffer sql = new StringBuffer("SELECT a.*,c.phone FROM bas_warehouse  a ");
		// sql.append(" INNER JOIN sym_addvcd_user b ON(a.addvcd=b.addvcd AND
		// b.userid=" + MDCUtil.getUserId() + ")");
		sql.append(" left JOIN sym_user c ON(a.responsibilityId=c.id)");

		SqlBuffer where = SqlBuffer.begin();
		where.addLike("a.name", request.getParameter("name"));
		where.addLike("a.address", request.getParameter("address"));
		where.addEndWith("a.addvcd", AddvcdService.likeStart(MDCUtil.getAddvcd()));
		where.addEndWith("a.addvcd", request.getParameter("addvcd"));

		PageControl p = super.initPage(searchtext, sort, dir, start, pageSize, request, response);
		super.commonFind(request, response, p, where, sql.toString(), Map.class);

		super.toJson(p.getList(), p.getRowCount(), true, ActionMsgParam.ACM_LIS_SUC, request, response);

	}

	@Comment("仓库剩余")
	@RequestMapping(value = "surplus", method = RequestMethod.GET)
	public void surplus(HttpServletRequest request, HttpServletResponse response) throws Exception {
		SqlBuffer where = SqlBuffer.begin();
		PageControl p = PageControl.getQueryOnlyInstance();
		String sql = "SELECT * FROM bas_warehouse_surplus ";
		where.add("wareHouseId", request.getParameter("wareHouseId"));
		where.add("deviceTypeId", request.getParameter("deviceTypeId"));
		where.add("model", request.getParameter("model"));
		// p.setGroupBy("GROUP BY wareHouseId,model,state,deviceTypeId");
		this.commonFind(request, response, p, where, sql, Map.class);
		super.toJson(p.getList(), p.getPageRowCount(), true, ActionMsgParam.ACM_LIS_SUC, request, response);
	}

	@Comment("运维仓库剩余")
	@RequestMapping(value = "repairSurplus", method = RequestMethod.GET)
	public void repairSurplus(HttpServletRequest request, HttpServletResponse response) throws Exception {
		SqlBuffer where = SqlBuffer.begin();
		PageControl p = PageControl.getQueryOnlyInstance();
		String sql = "SELECT * FROM bas_repair_surplus ";
		where.add("userId", DataUtil.getParameterInt(request.getParameter("userId")));
		where.add("deviceTypeId", request.getParameter("deviceTypeId"));
		where.add("model", request.getParameter("model"));

		this.commonFind(request, response, p, where, sql, Map.class);
		super.toJson(p.getList(), p.getRowCount(), true, ActionMsgParam.ACM_LIS_SUC, request, response);
	}

	@Comment("获取全部库房")
	@RequestMapping(value = "loadAll", method = RequestMethod.GET)
	public void loadAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
		SqlBuffer where = SqlBuffer.begin();
		StringBuffer sql = new StringBuffer("SELECT a.*,c.phone FROM bas_warehouse  a ");
		// sql.append(" INNER JOIN sym_addvcd_user b ON(a.addvcd=b.addvcd AND
		// b.userid=" + MDCUtil.getUserId() + ")");
		sql.append(" left JOIN sym_user c ON(a.responsibilityId=c.id)");
		PageControl p = PageControl.getQueryOnlyInstance();
		where.addLike("a.name", request.getParameter("name"));
		where.addLike("a.addvcd", request.getParameter("addvcd"));
		where.addLike("a.address", request.getParameter("address"));
		where.addEndWith("a.addvcd", AddvcdService.likeStart(MDCUtil.getAddvcd()));

		super.commonFind(request, response, p, where, sql.toString(), Map.class);
		super.toJson(p.getList(), p.getRowCount(), true, ActionMsgParam.ACM_LIS_SUC, request, response);

	}

	@Comment("获取指定库房")
	@RequestMapping(value = "load/{id}", method = RequestMethod.GET)
	public void load(@PathVariable Integer id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.load(id, request, response);
	}

	@Comment("新增数据")
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public void add(@ModelAttribute("bean") BasWarehouse bean, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		bean.setCreateTime(new Date());
		wareHouseService.save(bean);
		toJson(bean, 1, true, ActionMsgParam.ACM_SAV_SUC, request, response);
	}

	@Comment("修改数据数据")
	@RequestMapping(value = "update/{id}", method = RequestMethod.PUT)
	public void update(@PathVariable Integer id, @ModelAttribute("bean") BasWarehouse bean, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// bean.setCreateTime(new Date());

		BasWarehouse oldBean = wareHouseService.get(id);
		wareHouseService.updateDomain("修改", oldBean, bean);

		super.toJson(bean, 1, true, ActionMsgParam.ACM_MOD_SUC, request, response);
	}

	@Comment("修改数据数据")
	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable Integer id, @ModelAttribute("bean") BasWarehouse bean, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		wareHouseService.deleteByKey(id);
		super.toJson(null, 1, true, ActionMsgParam.ACM_DEL_SUC, request, response);
	}

	@Comment("物品质量评估")
	@RequestMapping(value = "assessment", method = RequestMethod.GET)
	public void assessment(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List list = wareHouseService.assessment(request, response);
		toJson(list, list.size(), true, ActionMsgParam.ACM_LIS_SUC, request, response);
	}

	@Comment("采购分析")
	@RequestMapping(value = "analysis", method = RequestMethod.GET)
	public void analysis(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List list = wareHouseService.analysis(request, response);
		toJson(list, list.size(), true, ActionMsgParam.ACM_LIS_SUC, request, response);
	}

	@Comment("统计")
	@RequestMapping(value = "statistics", method = RequestMethod.GET)
	public void statistics(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List list = wareHouseService.statistics(request, response);
		toJson(list, list.size(), true, ActionMsgParam.ACM_LIS_SUC, request, response);
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
	public GenericServiceImpl<BasWarehouse, Integer> getService() {
		return wareHouseService;
	}

}
