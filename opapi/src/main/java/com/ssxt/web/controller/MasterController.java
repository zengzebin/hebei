package com.ssxt.web.controller;

import static com.ssxt.common.util.DataUtil.notNullString;
import static com.ssxt.common.util.DataUtil.showMsgException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import com.ssxt.common.web.SpringBaseController;
import com.ssxt.web.bean.BasTaskInfo;
import com.ssxt.web.bean.SymRole;
import com.ssxt.web.bean.SymUser;
import com.ssxt.web.bean.SysAddvcdB;
import com.ssxt.web.service.AddvcdService;
import com.ssxt.web.service.MasterService;
import com.ssxt.web.service.RoleService;
import com.ssxt.web.service.UserService;

@Controller
@RequestMapping(value = "/api/master/")

public class MasterController extends SpringBaseController<SysAddvcdB, String> {

	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(MasterController.class);

	@Autowired
	MasterService masterService;

	// @Comment("查询列表")
	// @RequestMapping(value = "selectReport", method = RequestMethod.GET)
	// public void list(@RequestParam(value = "searchtext", required = false)
	// String searchtext,
	// @RequestParam(value = "order", required = false) String sort,
	// @RequestParam(value = "orderdir", required = false) String dir,
	// @RequestParam(value = "start", required = false) Integer start,
	// @RequestParam(value = "length", required = false) Integer pageSize,
	// HttpServletRequest request,
	// HttpServletResponse response) throws Exception {
	//
	// PageControl p = masterService.reportService(searchtext, sort, dir, start,
	// pageSize, request, response);
	// if (p != null)
	// super.toJson(p.getList(), p.getRowCount(), true,
	// ActionMsgParam.ACM_LIS_SUC, request, response);
	// else
	// super.toJson(null, 0, true, ActionMsgParam.ACM_LIS_SUC, request,
	// response);
	//
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
		return "主表管理";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ssxt.rdbox.common.web.SpringBaseController#getService()
	 */
	@Override
	public GenericServiceImpl<SysAddvcdB, String> getService() {
		return masterService;
	}

}
