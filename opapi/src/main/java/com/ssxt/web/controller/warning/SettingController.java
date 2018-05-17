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

import com.ssxt.common.web.SpringBaseController;
import com.ssxt.web.bean.SymMenu;
import com.ssxt.web.bean.SymRole;
import com.ssxt.web.bean.SymUser;
import com.ssxt.web.bean.SysAddvcdB;
import com.ssxt.web.bean.warning.BasSetting;
import com.ssxt.web.service.AddvcdService;
import com.ssxt.web.service.RoleService;
import com.ssxt.web.service.UserService;
import com.ssxt.web.service.warning.SettingService;

@Controller
@RequestMapping(value = "/api/setting/")
public class SettingController extends SpringBaseController<BasSetting, Integer> {

	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(SettingController.class);

	@Autowired
	private SettingService settingService;

	@Comment("查询列表")
	@RequestMapping(value = "select", method = RequestMethod.GET)
	public void list(@RequestParam(value = "searchtext", required = false) String searchtext,
			@RequestParam(value = "order", required = false) String sort,
			@RequestParam(value = "orderdir", required = false) String dir,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "length", required = false) Integer pageSize, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SqlBuffer where = SqlBuffer.begin();
		PageControl p = super.initPage(searchtext, sort, dir, start, pageSize, request, response);
		super.commonFind(request, response, p, where, null, Map.class);
		super.toJson(p.getList(), p.getRowCount(), true, ActionMsgParam.ACM_LIS_SUC, request, response);
	}

	@Comment("添加")
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public void add(@ModelAttribute("bean") BasSetting bean, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		settingService.save(bean);
		toJson(bean, 1, true, ActionMsgParam.ACM_SAV_SUC, request, response);
	}

	@Comment("获取单条记录")
	@RequestMapping(value = "load/{id}", method = RequestMethod.GET)
	public void load(@PathVariable Integer id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.load(id, request, response);
	}

	@Comment("修改")
	@RequestMapping(value = "update/{id}", method = RequestMethod.PUT)
	public void update(@ModelAttribute("bean") BasSetting bean, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BasSetting oldBean = settingService.get(bean.getId());
		settingService.updateDomain("修改", oldBean, bean);
		CachePool.getInstance().putCacheItem(oldBean.getName(), oldBean);
		toJson(oldBean, 1, true, ActionMsgParam.ACM_MOD_SUC, request, response);
	}

	@Comment("删除记录")
	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable Integer id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.delete(id, request, response);
	}

	@Comment("获取全部信息")
	@RequestMapping(value = "loadAll", method = RequestMethod.GET)
	public void loadAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List lsit = settingService.loadAll();
		toJson(lsit, lsit.size(), true, ActionMsgParam.ACM_LIS_SUC, request, response);
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
	public GenericServiceImpl<BasSetting, Integer> getService() {
		return settingService;
	}

}
