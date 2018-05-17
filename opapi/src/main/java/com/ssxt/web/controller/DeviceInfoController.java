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
import com.ssxt.common.util.MDCUtil;
import com.ssxt.common.web.SpringBaseController;
import com.ssxt.web.bean.BasDeviceInfo;
import com.ssxt.web.service.AddvcdService;
import com.ssxt.web.service.DeviceInfoService;
import com.ssxt.web.bean.SymMenu;
import com.ssxt.web.bean.SymUser;
import com.ssxt.web.service.MenuService;
import com.ssxt.web.service.RoleService;
import com.ssxt.web.service.UserService;

@Controller
@RequestMapping(value = "/api/device/")
public class DeviceInfoController extends SpringBaseController<BasDeviceInfo, Integer> {
	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(DeviceInfoController.class);

	@Autowired
	private DeviceInfoService service;

	@Comment("查询列表")
	@RequestMapping(value = "select", method = RequestMethod.GET)
	public void list(@RequestParam(value = "searchtext", required = false) String searchtext,
			@RequestParam(value = "order", required = false) String sort,
			@RequestParam(value = "orderdir", required = false) String dir,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "length", required = false) Integer pageSize, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SqlBuffer sbf = SqlBuffer.begin();
		sbf.add("addvcd", request.getParameter("addvcd"));
		sbf.add("deviceType", request.getParameter("deviceType"));
		sbf.addEndWith("addvcd", AddvcdService.likeStart(MDCUtil.getAddvcd()));
		sbf.addLike("address", request.getParameter("address"));
		sbf.addLike("deviceName", request.getParameter("deviceName"));
		
		PageControl p = super.selectHql(searchtext, sort, dir, start, pageSize, request, response, sbf);
		super.toJson(p.getList(), p.getRowCount(), true, ActionMsgParam.ACM_LIS_SUC, request, response);
	}

	@Comment("新增数据")
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public void add(@ModelAttribute("bean") BasDeviceInfo bean, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// bean.setCreateTime(new Date());
		AccData ad = CtxHelper.createFailAccData();

		if (bean.getAddress() == null)
			showMsgException("地址不能为空");
		if (service.getDeviceInfo(bean.getAddress()).size() == 0) {
			service.save(bean);
			ad.setMsg("用户添加成功");
			ad.setSuccess(true);
		} else {
			showMsgException("用户已经存在");
		}

		CtxHelper.setNoCache(response);
		CtxHelper.writeToJson(response, ad);
	}

	@Comment("修改数据数据")
	@RequestMapping(value = "update", method = RequestMethod.PUT)
	public void update(@ModelAttribute("bean") BasDeviceInfo bean, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// bean.setCreateTime(new Date());

		BasDeviceInfo old = service.get(bean.getId());
		service.updateDomain("更新设备测站", old, bean);

		AccData ad = CtxHelper.createFailAccData();

		ad.setMsg(ActionMsgParam.ACM_MOD_SUC);
		ad.setSuccess(true);

		CtxHelper.setNoCache(response);
		CtxHelper.writeToJson(response, ad);
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
	public GenericServiceImpl<BasDeviceInfo, Integer> getService() {
		return service;
	}

}
