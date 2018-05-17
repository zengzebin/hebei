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
import com.ssxt.web.bean.BasDeviceInfo;
import com.ssxt.web.bean.BasMessageRemind;
import com.ssxt.web.service.DeviceInfoService;
import com.ssxt.web.bean.SymMenu;
import com.ssxt.web.bean.SymRole;
import com.ssxt.web.bean.SymUser;
import com.ssxt.web.service.MenuService;
import com.ssxt.web.service.MessageService;
import com.ssxt.web.service.RoleService;
import com.ssxt.web.service.UserService;

@Controller
@RequestMapping(value = "/api/message/")
public class MessageController extends SpringBaseController<BasMessageRemind, Integer> {
	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(MessageController.class);

	@Autowired
	private MessageService service;

	@Comment("查询列表")
	@RequestMapping(value = "select", method = RequestMethod.GET)
	public void list(@RequestParam(value = "searchtext", required = false) String searchtext,
			@RequestParam(value = "order", required = false) String sort,
			@RequestParam(value = "orderdir", required = false) String dir,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "length", required = false) Integer pageSize, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		SqlBuffer where = SqlBuffer.begin();
		SymRole role = CtxHelper.getSymRole(request);
		if (role.getIsOperation() == 1)
			where.add("userId", MDCUtil.getUserId());
		else
			where.add("createUserId", MDCUtil.getUserId());
		PageControl p = this.initPage(searchtext, sort, dir, start, pageSize, request, response);

		this.commonFind(request, response, p, where, null, Map.class);
		super.toJson(p.getList(), p.getRowCount(), true, ActionMsgParam.ACM_LIS_SUC, request, response);
	}

	@Comment("新增与更新信息")
	@RequestMapping(value = "addUpdate", method = RequestMethod.POST)
	public void add(@ModelAttribute("bean") BasMessageRemind bean, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		service.saveUpdate(bean, request, response);
		toJson(bean, 1, true, ActionMsgParam.ACM_SAV_SUC, request, response);
	}

	@Comment("已读")
	@RequestMapping(value = "read/{id}", method = RequestMethod.PUT)
	public void read(@PathVariable Integer id, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		SqlBuffer wehre = SqlBuffer.begin();
		wehre.add("id", id);
		service.updateByCondition("update BasMessageRemind set state=" + 1 + " where", wehre.end());
		toJson(null, 1, true, ActionMsgParam.ACM_MOD_SUC, request, response);
	}

	@Comment("加载单个数据")
	@RequestMapping(value = "load/{id}", method = RequestMethod.GET)
	public void update(@PathVariable Integer id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// bean.setCreateTime(new Date());
		super.load(id, request, response);
	}

	@Comment("删除数据")
	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable Integer id, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// bean.setCreateTime(new Date());
		AccData ad = CtxHelper.createFailAccData();
		service.deleteByKey(id);
		ad.setMsg(ActionMsgParam.ACM_DEL_SUC);
		ad.setSuccess(true);

		CtxHelper.setNoCache(response);
		CtxHelper.writeToJson(response, ad);
	}

	@Comment("加载全部数据")
	@RequestMapping(value = "loadAll", method = RequestMethod.GET)
	public void loadAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// bean.setCreateTime(new Date());
		super.loadAll(request, response, null, null, null);
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
	public GenericServiceImpl<BasMessageRemind, Integer> getService() {
		return service;
	}

}
