package com.ssxt.web.controller.warning;

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

import com.fasterxml.jackson.databind.cfg.BaseSettings;
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

import com.ssxt.web.bean.SymRole;
import com.ssxt.web.bean.SymUser;
import com.ssxt.web.bean.SysAddvcdB;
import com.ssxt.web.bean.warning.BasSetting;
import com.ssxt.web.bean.warning.BasWarning;
import com.ssxt.web.service.AddvcdService;
import com.ssxt.web.service.RoleService;
import com.ssxt.web.service.UserService;
import com.ssxt.web.service.warning.SettingService;
import com.ssxt.web.service.warning.WarningService;

@Controller
@RequestMapping(value = "/api/warning/")

public class WarningController extends SpringBaseController<BasWarning, Integer> {

	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(WarningController.class);

	@Autowired
	private WarningService warningService;

	@Comment("查询列表")
	@RequestMapping(value = "select", method = RequestMethod.GET)
	public void list(@RequestParam(value = "searchtext", required = false) String searchtext,
			@RequestParam(value = "order", required = false) String sort,
			@RequestParam(value = "orderdir", required = false) String dir,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "length", required = false) Integer pageSize, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SqlBuffer where = SqlBuffer.begin();

		StringBuffer sql = new StringBuffer(
				" SELECT  a.time,a.code,a.value,a.status,b.stnm,b.stlc,e.argName,d.name,d.remark,c.addvnm ");
		sql.append(" FROM bas_warning  a");
		sql.append(" INNER JOIN bas_stinfo_b b ON(a.code=b.stcd)");
		sql.append(" INNER JOIN sys_addvcd_b c ON(b.addvcd=c.addvcd)");
		sql.append(" INNER JOIN bas_setting d ON(a.Type=d.name)");
		sql.append(" INNER JOIN  bas_param e ON(b.sttp=e.argValue AND e.paramType='com-ssxt-op-stcdType')");
		 

		where.add("a.time", request.getParameter("startTime"), ">=");
		where.add("a.time", request.getParameter("endTime"), "<=");
		where.add("a.status", request.getParameter("status"));
		where.add("d.id", request.getParameter("type"));
		where.addEndWith("b.addvcd", AddvcdService.likeStart(MDCUtil.getAddvcd()));
		PageControl p = super.initPage(searchtext, sort, dir, start, pageSize, request, response);
		super.commonFind(request, response, p, where, sql.toString(), Map.class);
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
	public GenericServiceImpl<BasWarning, Integer> getService() {
		return warningService;
	}

}
