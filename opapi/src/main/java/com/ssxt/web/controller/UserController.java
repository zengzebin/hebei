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
import com.ssxt.common.util.AonotaionAnalysis;
import com.ssxt.common.util.CachePool;
import com.ssxt.common.util.CtxHelper;
import com.ssxt.common.util.DataUtil;
import com.ssxt.common.util.FileUploadUtils;
import com.ssxt.common.util.MD5Tools;
import com.ssxt.common.util.MDCUtil;
import com.ssxt.common.util.SpringUtil;
import com.ssxt.common.web.SpringBaseController;
import com.ssxt.reflect.ReflectSql;
import com.ssxt.task.AutomaticService;
import com.ssxt.web.bean.BasQuit;
import com.ssxt.web.bean.BasStinfoImage;
import com.ssxt.web.bean.BasTaskInfo;
import com.ssxt.web.bean.SymRole;
import com.ssxt.web.bean.SymUser;
import com.ssxt.web.service.AddvcdService;
import com.ssxt.web.service.RoleService;
import com.ssxt.web.service.UserService;

@Controller
@RequestMapping(value = "/api/users/")
public class UserController extends SpringBaseController<SymUser, Integer> {
	@Autowired
	private UserService service;
	@Autowired
	private RoleService roleservice;

	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(UserController.class);

	@Comment("查询列表")
	@RequestMapping(value = "select", method = RequestMethod.GET)
	public void list(@RequestParam(value = "searchtext", required = false) String searchtext,
			@RequestParam(value = "order", required = false) String sort,
			@RequestParam(value = "orderdir", required = false) String dir,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "length", required = false) Integer pageSize, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SqlBuffer sbf = SqlBuffer.begin();
		SymRole role = CtxHelper.getSymRole(request);

		StringBuffer sql = new StringBuffer(ReflectSql.selectSql(new SymUser(), "user", null));
		sql.append(" from ").append(ReflectSql.getTblaeName(new SymUser())).append(" user");
		sql.append(" inner join sym_role role on(role.id=user.roleId)");
		sbf.addLike("user.loginId", request.getParameter("loginId"));
		sbf.addLike("user.name", request.getParameter("name"));

		sbf.addLike("phone", request.getParameter("phone"));

		sbf.addEndWith("addvcd", AddvcdService.likeStart(MDCUtil.getAddvcd()));
		// 市管理人员
		// if (role.getDistinguish().equals("municipal")) {
		sbf.addText("(role.distinguish!='" + role.getDistinguish() + "' or user.Id=" + MDCUtil.getUserId() + ")",
				" and");
		// }

		PageControl p = super.initPage(searchtext, sort, dir, start, pageSize, request, response);
		super.commonFind(request, response, p, sbf, sql.toString(), Map.class);
		super.toJson(p.getList(), p.getRowCount(), true, ActionMsgParam.ACM_LIS_SUC, request, response);
		AutomaticService automaticService = (AutomaticService) SpringUtil.getContext().getBean("automaticService");

		try {
			automaticService.addTask();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Comment("维修人员列表")
	@RequestMapping(value = "repairPersonnel", method = RequestMethod.GET)
	public void RepairPersonnel(@RequestParam(value = "searchtext", required = false) String searchtext,
			@RequestParam(value = "order", required = false) String sort,
			@RequestParam(value = "orderdir", required = false) String dir,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "length", required = false) Integer pageSize, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SqlBuffer where = SqlBuffer.begin();

		StringBuffer sql = new StringBuffer(ReflectSql.selectSql(new SymUser(), "u", "password"));
		sql.append(" from " + ReflectSql.getTblaeName(new SymUser()) + " u");
		sql.append("  inner join sym_role b on(u.roleId=b.id)");
		// sql.append(
		// " INNER JOIN sym_addvcd_user c ON(u.directlyUnder=c.addvcd AND
		// c.userId=" + MDCUtil.getUserId() + ")");

		where.addLike("u.loginId", request.getParameter("loginId"));

		where.addLike("u.name", request.getParameter("name"));

		where.addLike("u.phone", request.getParameter("phone"));

		where.add("b.isOperation", 1);
		where.addEndWith("u.directlyUnder", AddvcdService.likeStart(MDCUtil.getAddvcd()));

		where.add("u.directlyUnder", request.getParameter("directlyUnder"));

		PageControl p = super.selectSql(searchtext, sort, dir, start, pageSize, request, response, null, where,
				sql.toString());
		super.toJson(p.getList(), p.getRowCount(), true, ActionMsgParam.ACM_LIS_SUC, request, response);
	}

	@Comment("提交离职")
	@RequestMapping(value = "quit", method = RequestMethod.POST)
	public void addQuit(@ModelAttribute("bean") BasQuit bean, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		service.addQuit(bean);
		toJson(bean, 1, true, ActionMsgParam.ACM_MOD_SUC, request, response);
	}

	@Comment("修改离职申请")
	@RequestMapping(value = "quit/{id}", method = RequestMethod.PUT)
	public void updateQuit(@PathVariable Integer id, @ModelAttribute("bean") BasQuit bean, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		service.updateQuit(id, bean);
		toJson(bean, 1, true, ActionMsgParam.ACM_MOD_SUC, request, response);
	}

	@Comment("离职申请列表")
	@RequestMapping(value = "quit", method = RequestMethod.GET)
	public void selectQuit(@RequestParam(value = "searchtext", required = false) String searchtext,
			@RequestParam(value = "order", required = false) String sort,
			@RequestParam(value = "orderdir", required = false) String dir,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "length", required = false) Integer pageSize, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SymRole role = CtxHelper.getSymRole(request);
		SqlBuffer where = SqlBuffer.begin();
		StringBuffer sql = new StringBuffer("SELECT a.id,a.userId,b.name,b.position,b.directlyUnder,");
		sql.append(" b.phone,a.content,a.createTime,a.reason, a.quitTime,a.state");
		sql.append(" FROM bas_quit a");
		sql.append(" INNER JOIN sym_user  b ON(a.userId=b.Id)");

		if (request.getParameter("userId") != null) {
			where.add("a.userId", DataUtil.getParameterInt(request.getParameter("userId")));
		} else {
			where.addEndWith("b.directlyUnder", AddvcdService.likeStart(MDCUtil.getAddvcd()));
		}

		where.addListInteger("a.state", request.getParameter("state"));

		PageControl p = super.selectSql(searchtext, sort, dir, start, pageSize, request, response, null, where,
				sql.toString());
		super.toJson(p.getList(), p.getRowCount(), true, ActionMsgParam.ACM_LIS_SUC, request, response);
	}

	@Comment("获取全部人员")
	@RequestMapping(value = "loadAll", method = RequestMethod.GET)
	public void appUserAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AonotaionAnalysis bean = AonotaionAnalysis.createSql(SymUser.class, "passWord", "user");
		StringBuffer sql = new StringBuffer("select user.id");
		sql.append(bean.getField()).append(" from ").append(bean.getTable());
		sql.append(" inner join sym_role role on(role.id=user.roleId) ");
		SqlBuffer sbf = SqlBuffer.begin();
		log.info(sql.toString());
		sbf.add("user.isApp", DataUtil.getParameterInt(request.getParameter("isApp")), "=", "and");
		sbf.add("user.roleId", DataUtil.getParameterInt(request.getParameter("roleId")), "=", "and");
		sbf.add("role.isOperation", DataUtil.getParameterInt(request.getParameter("isOperation")), "=", "and");

		super.loadAll(request, response, sbf, sql.toString(), null);
	}

	@Comment("新增数据")
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public void add(@ModelAttribute("bean") SymUser bean, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// bean.setCreateTime(new Date());
		AccData ad = CtxHelper.createFailAccData();

		if (service.exist(bean)) {
			bean.setCreateTime(new Date());
			service.addUser(bean);
			ad.setMsg("用户添加成功");
			ad.setSuccess(true);
		} else {
			ad.setMsg("用户已经存在");
			ad.setSuccess(false);
		}

		CtxHelper.setNoCache(response);
		CtxHelper.writeToJson(response, ad);
	}

	@Comment("新增数据")
	@RequestMapping(value = "load/{id}", method = RequestMethod.GET)
	public void add(@PathVariable Integer id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.load(id, request, response);

	}

	@Comment("修改密码")
	@RequestMapping(value = "updatePass/{id}", method = RequestMethod.POST)
	public void updatePass(@PathVariable Integer id, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		SymUser u = CtxHelper.getCurUser(request);
		String currentPass = request.getParameter("currentPass");
		log.info(currentPass);
		log.info(u.getPassWord());
		// if (MD5Tools.MD5(currentPass).equals(u.getPassWord())) {
		if (currentPass.toLowerCase().equals(u.getPassWord().toLowerCase())) {
			SqlBuffer sbf = SqlBuffer.begin();
			// String newPass = MD5Tools.MD5(request.getParameter("newPass"));
			String newPass = request.getParameter("newPass");
			String sql = "update SymUser set passWord='" + newPass + "' where id=" + id;
			service.updateByCondition(sql, sbf.end());
			super.toJson(null, 1, true, "密码修改完成", request, response);
		} else {
			super.toJson(null, 0, false, "当前密码不对", request, response);
		}

	}

	@Comment("修改数据数据")
	@RequestMapping(value = "update", method = RequestMethod.PUT)
	public void update(@ModelAttribute("bean") SymUser bean, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// bean.setCreateTime(new Date());
		AccData ad = CtxHelper.createFailAccData();
		service.updateUser(bean);
		// ReflectSql.updateSql(bean, null, true);

		ad.setMsg("用户修改成功");
		ad.setSuccess(true);
		CtxHelper.setNoCache(response);
		CtxHelper.writeToJson(response, ad);
	}

	@Comment("删除数据数据")
	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
	public void update(@PathVariable Integer id, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// bean.setCreateTime(new Date());
		AccData ad = CtxHelper.createFailAccData();
		service.deleteByKey(id);

		ad.setMsg("删除成功");
		ad.setSuccess(true);
		CtxHelper.setNoCache(response);
		CtxHelper.writeToJson(response, ad);
	}

	@Comment("上传图片")
	@RequestMapping(value = "uploadImage/{id}", method = RequestMethod.POST)
	public void uploadImage(@PathVariable Integer id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		service.uploadImage(id, request);
		toJson(null, 1, true, "上传成功", request, response);
	}

	// @Comment("删除图片")
	// @RequestMapping(value = "deleteImage/{id}", method =
	// RequestMethod.DELETE)
	// public void deleteImage(@PathVariable Integer id, HttpServletRequest
	// request, HttpServletResponse response)
	// throws Exception {
	// SymUser bean = service.get(id);
	// FileUploadUtils.deleteImage(bean.getImagePath());
	//
	// toJson(null, 1, true, ActionMsgParam.ACM_DEL_SUC, request, response);
	// }

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
	public GenericServiceImpl<SymUser, Integer> getService() {
		return service;
	}

}
