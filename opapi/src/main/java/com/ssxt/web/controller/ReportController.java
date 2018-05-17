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
import com.ssxt.common.util.CachePool;
import com.ssxt.common.util.CtxHelper;
import com.ssxt.common.util.DataUtil;
import com.ssxt.common.web.SpringBaseController;
import com.ssxt.web.bean.BasDeviceInfo;
import com.ssxt.web.service.DeviceInfoService;
import com.ssxt.web.bean.SymMenu;
import com.ssxt.web.bean.SymUser;
import com.ssxt.web.service.MenuService;
import com.ssxt.web.service.RepotService;
import com.ssxt.web.service.RoleService;
import com.ssxt.web.service.UserService;

@Controller
@RequestMapping(value = "/api/report/")
public class ReportController extends SpringBaseController<BasDeviceInfo, Integer> {
	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ReportController.class);

	@Autowired
	private RepotService repotService;
	@Autowired
	private DeviceInfoService deviceService;

	@Comment("各市故障站点分析")
	@RequestMapping(value = "cityError", method = RequestMethod.GET)
	public void cityError(HttpServletRequest request, HttpServletResponse response) throws Exception {

		PageControl p = repotService.cityError(request, response);
		toJson(p.getList(), p.getList().size(), true, ActionMsgParam.ACM_LIS_SUC, request, response);

	}

	@Comment("各市维修人员考勤表")
	@RequestMapping(value = "assessment", method = RequestMethod.GET)
	public void assessment(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PageControl p = repotService.Assessment(request, response);

		super.toJson(p.getList(), p.getList().size(), true, ActionMsgParam.ACM_LIS_SUC, request, response);

	}

	@Comment("各市维修任务及维修人数分析表")
	@RequestMapping(value = "analysis", method = RequestMethod.GET)
	public void analysis(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PageControl p = repotService.analysis(request, response);
		super.toJson(p.getList(), p.getList().size(), true, ActionMsgParam.ACM_DEL_SUC, request, response);

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
	public GenericServiceImpl<BasDeviceInfo, Integer> getService() {
		return deviceService;
	}

}
