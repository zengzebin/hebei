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
import com.ssxt.web.bean.BasOwnerUnits;
import com.ssxt.web.bean.BasProblem;
import com.ssxt.web.bean.BasProblemAnswer;
import com.ssxt.web.bean.SymRole;
import com.ssxt.web.bean.SymUser;
import com.ssxt.web.bean.SysAddvcdB;
import com.ssxt.web.service.AddvcdService;
import com.ssxt.web.service.ProblemService;
import com.ssxt.web.service.RoleService;
import com.ssxt.web.service.UserService;

@Controller
@RequestMapping(value = "/api/problem/")

public class ProblemController extends SpringBaseController<BasProblem, Integer> {

	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ProblemController.class);

	@Autowired
	private ProblemService problemService;

	@Comment("新增")
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public void add(@ModelAttribute("bean") BasProblem bean, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		problemService.addProblem(bean);
		toJson(bean, 1, true, ActionMsgParam.ACM_SAV_SUC, request, response);
	}

	@Comment("删除")
	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
	public void add(@PathVariable Integer id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		problemService.deleteByKey(id);
		toJson(null, 1, true, ActionMsgParam.ACM_DEL_SUC, request, response);
	}

	@Comment("修改")
	@RequestMapping(value = "update", method = RequestMethod.PUT)
	public void update(@ModelAttribute("bean") BasProblem bean, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BasProblem old = problemService.get(bean.getId());
		problemService.updateDomain("修改問題", old, bean);
		toJson(null, 1, true, ActionMsgParam.ACM_MOD_SUC, request, response);
	}

	@Comment("问题详情")
	@RequestMapping(value = "load/{id}", method = RequestMethod.GET)
	public void load(@PathVariable Integer id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BasProblem bean = problemService.get(id);
		toJson(bean, 1, true, ActionMsgParam.ACM_LIS_SUC, request, response);
	}

	@Comment("读取加一")
	@RequestMapping(value = "read/{id}", method = RequestMethod.GET)
	public void read(@PathVariable Integer id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BasProblem bean = problemService.clickProblem(id);
		toJson(bean, 1, true, ActionMsgParam.ACM_LIS_SUC, request, response);
	}

	@Comment("回答问题")
	@RequestMapping(value = "answer", method = RequestMethod.POST)
	public void answer(@ModelAttribute("bean") BasProblemAnswer bean, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		problemService.answer(bean, request, response);
		toJson(bean, 1, true, ActionMsgParam.ACM_LIS_SUC, request, response);
	}

	@Comment("等级信息")
	@RequestMapping(value = "info", method = RequestMethod.GET)
	public void title(HttpServletRequest request, HttpServletResponse response) throws Exception {
		SqlBuffer wehre = SqlBuffer.begin();
		// 我的等级
		StringBuffer sql = new StringBuffer("SELECT a.grade,");
		// 回到的问题数目
		sql.append(" (SELECT COUNT(1) FROM bas_problem_answer answer WHERE answer.createUserId=a.userId)answerNum,");
		// 发布的问题数
		sql.append(" (SELECT COUNT(1) FROM bas_problem problem WHERE problem.createUserId=a.userId)problemNum ");
		sql.append(" FROM bas_problem_grade a");
		wehre.add("a.userId", MDCUtil.getUserId());
		PageControl p = this.commonFind(request, response, null, wehre, sql.toString(), Map.class);
		toJson(p.getList(), p.getRowCount(), true, ActionMsgParam.ACM_LIS_SUC, request, response);
	}

	@Comment("常见问题与我相关")
	@RequestMapping(value = "relevant", method = RequestMethod.GET)
	public void relevant(@RequestParam(value = "searchtext", required = false) String searchtext,
			@RequestParam(value = "order", required = false) String sort,
			@RequestParam(value = "orderdir", required = false) String dir,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "length", required = false) Integer pageSize, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SqlBuffer wehre = SqlBuffer.begin();
		StringBuffer sql = new StringBuffer("SELECT b.*,a.userId FROM bas_problem_common a");
		sql.append(" INNER JOIN bas_problem b ON(a.problemId=b.id)");
		wehre.add("a.userId", MDCUtil.getUserId());
		PageControl p = initPage(searchtext, sort, dir, start, pageSize, request, response);
		this.commonFind(request, response, p, wehre, sql.toString(), Map.class);
		toJson(p.getList(), p.getRowCount(), true, ActionMsgParam.ACM_LIS_SUC, request, response);
	}

	@Comment("最新问题及热门话题和我的问题")
	@RequestMapping(value = "select", method = RequestMethod.GET)
	public void select(@RequestParam(value = "searchtext", required = false) String searchtext,
			@RequestParam(value = "order", required = false) String sort,
			@RequestParam(value = "orderdir", required = false) String dir,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "length", required = false) Integer pageSize, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		StringBuffer sql = new StringBuffer("SELECT * ,");
		sql.append(" (SELECT COUNT(1) FROM  bas_problem_answer b WHERE a.id=b.problemId)  answerNum");
		sql.append(" FROM bas_problem a ");
		PageControl p = initPage(searchtext, sort, dir, start, pageSize, request, response);
		SqlBuffer wehre = SqlBuffer.begin();
		wehre.add("CreateUserId", DataUtil.getParameterInt(request.getParameter("CreateUserId")));
		this.commonFind(request, response, p, wehre, sql.toString(), Map.class);
		toJson(p.getList(), p.getRowCount(), true, ActionMsgParam.ACM_LIS_SUC, request, response);
	}

	// @Comment("我的问题")
	// @RequestMapping(value = "myProblem", method = RequestMethod.GET)
	// public void myProblem(@RequestParam(value = "searchtext", required =
	// false) String searchtext,
	// @RequestParam(value = "order", required = false) String sort,
	// @RequestParam(value = "orderdir", required = false) String dir,
	// @RequestParam(value = "start", required = false) Integer start,
	// @RequestParam(value = "length", required = false) Integer pageSize,
	// HttpServletRequest request,
	// HttpServletResponse response) throws Exception {
	// String sql = "select * from bas_problem";
	// PageControl p = initPage(searchtext, sort, dir, start, pageSize, request,
	// response);
	// SqlBuffer wehre = SqlBuffer.begin();
	// wehre.add("CreateUserId",
	// DataUtil.getParameterInt(request.getParameter("CreateUserId")));
	// this.commonFind(request, response, p, wehre, sql, BasProblem.class);
	// toJson(p.getList(), p.getRowCount(), true, ActionMsgParam.ACM_LIS_SUC,
	// request, response);
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
	public GenericServiceImpl<BasProblem, Integer> getService() {
		return problemService;
	}

}
