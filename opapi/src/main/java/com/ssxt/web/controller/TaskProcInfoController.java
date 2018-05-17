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
import com.ssxt.common.web.SpringBaseController;
import com.ssxt.web.bean.BasDeviceInfo;
import com.ssxt.web.bean.BasTaskDealNode;
import com.ssxt.web.bean.BasTaskInfo;
import com.ssxt.web.bean.BasTaskProcInfo;
import com.ssxt.web.service.DeviceInfoService;
import com.ssxt.web.service.TaskProcInfoService;
import com.ssxt.web.bean.SymMenu;
import com.ssxt.web.bean.SymUser;
import com.ssxt.web.service.MenuService;
import com.ssxt.web.service.RoleService;
import com.ssxt.web.service.TaskDealNodeService;
import com.ssxt.web.service.TaskInfoService;
import com.ssxt.web.service.UserService;

@Controller
@RequestMapping(value = "/api/taskProcess/")
public class TaskProcInfoController extends SpringBaseController<BasTaskProcInfo, Integer> {

	@Autowired
	private TaskProcInfoService service;

	@Autowired
	private TaskDealNodeService taskDealNodeService;

	@Autowired
	private TaskInfoService taskInfoService;

	@Comment("查询列表")
	@RequestMapping(value = "select", method = RequestMethod.GET)
	public void select(@RequestParam(value = "searchtext", required = false) String searchtext,
			@RequestParam(value = "order", required = false) String sort,
			@RequestParam(value = "orderdir", required = false) String dir,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "length", required = false) Integer pageSize, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		SqlBuffer sbf = SqlBuffer.begin();
		sbf.add("taskNo", request.getParameter("taskNo"));
		PageControl p = super.selectHql(searchtext, sort, dir, start, pageSize, request, response, sbf);
		super.toJson(p.getList(), p.getRowCount(), true, ActionMsgParam.ACM_LIS_SUC, request, response);

	}

	

	@Comment("获取任务单全部流程")
	@RequestMapping(value = "loadAll/{taskNo}", method = RequestMethod.GET)
	public void add(@PathVariable String taskNo, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SqlBuffer sbf = SqlBuffer.begin();
		sbf.add("taskNo", taskNo);
		super.loadAll(request, response,sbf,null,null);
	}

	@Comment("当前可执行环节")
	@RequestMapping(value = "getSelect", method = RequestMethod.GET)
	public void getSelect(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String taskNo = request.getParameter("taskNo");
		AccData ad = CtxHelper.createFailAccData();
		Map map = service.getSelect(taskNo);
		ad.setData(map);
		ad.setMsg(ActionMsgParam.ACM_LIS_SUC);
		ad.setSuccess(true);

		CtxHelper.setNoCache(response);
		CtxHelper.writeToJson(response, ad);

	}

	@Comment("操作")
	@RequestMapping(value = "taskOperate", method = RequestMethod.POST)
	public void taskOperate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AccData ad = CtxHelper.createFailAccData();
		String taskNo = request.getParameter("taskNo");
		String operate = request.getParameter("operate");
		service.taskOperate(taskNo, operate);

		ad.setMsg(ActionMsgParam.ACM_LIS_SUC);
		ad.setSuccess(true);

		CtxHelper.setNoCache(response);
		CtxHelper.writeToJson(response, ad);

	}

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
	public GenericServiceImpl<BasTaskProcInfo, Integer> getService() {
		return service;
	}

}
