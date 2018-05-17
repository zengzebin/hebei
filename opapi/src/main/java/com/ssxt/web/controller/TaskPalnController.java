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
import com.ssxt.common.util.StatusCode;
import com.ssxt.common.web.SpringBaseController;
import com.ssxt.web.bean.BasDeviceInfo;
import com.ssxt.web.bean.BasStinfoB;
import com.ssxt.web.bean.BasTaskPlan;
import com.ssxt.web.bean.BasTaskPlanDevice;
import com.ssxt.web.bean.BasTaskPlanStcd;
import com.ssxt.web.bean.ModelPalnDevice;
import com.ssxt.web.service.AddvcdService;
import com.ssxt.web.service.DeviceInfoService;
import com.ssxt.web.bean.SymMenu;
import com.ssxt.web.bean.SymRole;
import com.ssxt.web.bean.SymUser;
import com.ssxt.web.dao.TaskPalnStcdDao;
import com.ssxt.web.service.MenuService;
import com.ssxt.web.service.RoleService;
import com.ssxt.web.service.StinfoService;
import com.ssxt.web.service.TaskPalnService;
import com.ssxt.web.service.UserService;

@Controller
@RequestMapping(value = "/api/taskPaln/")
public class TaskPalnController extends SpringBaseController<BasTaskPlan, Integer> {
	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TaskPalnController.class);

	@Autowired
	private TaskPalnService service;

	@Autowired
	private TaskPalnStcdDao taskPalnStcdDao;

	@Autowired
	private StinfoService stinfoService;

	@Autowired
	private DeviceInfoService deviceInfoService;

	@Comment("任务计划")
	@RequestMapping(value = "select", method = RequestMethod.GET)
	public void list(@RequestParam(value = "searchtext", required = false) String searchtext,
			@RequestParam(value = "order", required = false) String sort,
			@RequestParam(value = "orderdir", required = false) String dir,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "length", required = false) Integer pageSize, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SymRole role = CtxHelper.getSymRole(request);
		String sql = "select * from bas_task_plan";
		SqlBuffer where = SqlBuffer.begin();
		// if (role.getIsOperation() == 1) {
		// where.add("maintUserId", MDCUtil.getUserId());
		// } else {
		// where.addEndWith("addvcd",
		// AddvcdService.likeStart(MDCUtil.getAddvcd()));
		// }

		if (role.getDistinguish().equals("maintainer")) {
			where.add("maintUserId", MDCUtil.getUserId());
		} else {
			// 其他人员
			where.addEndWith("addvcd", AddvcdService.likeStart(MDCUtil.getAddvcd()));
		}

		where.addListInteger("status", request.getParameter("status"));
		PageControl p = this.initPage(searchtext, sort, dir, start, pageSize, request, response);
		this.commonFind(request, response, p, where, sql, Map.class);
		super.toJson(p.getList(), p.getRowCount(), true, ActionMsgParam.ACM_LIS_SUC, request, response);
	}

	@Comment("新增数据")
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public void add(@ModelAttribute("bean") BasTaskPlan plan, HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		service.add(plan, request, response);
		toJson(null, 1, true, ActionMsgParam.ACM_SAV_SUC, request, response);
	}

	@Comment("修改数据数据")
	@RequestMapping(value = "update", method = RequestMethod.PUT)
	public void update(@ModelAttribute("bean") BasTaskPlan bean, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		service.update(bean, request, response);
		toJson(bean, 1, true, ActionMsgParam.ACM_MOD_SUC, request, response);
	}

	@Comment("修改检查设备数据")
	@RequestMapping(value = "updateDevice", method = RequestMethod.POST)
	public void updateDevice(@ModelAttribute("devices") ModelPalnDevice devices, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		service.updateDevice(devices, request, response);
		toJson(null, 1, true, ActionMsgParam.ACM_MOD_SUC, request, response);
	}

	@Comment("加载单个数据")
	@RequestMapping(value = "load/{id}", method = RequestMethod.GET)
	public void update(@PathVariable Integer id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// SqlBuffer where = SqlBuffer.begin();
		// String sql = "select * from bas_task_plan";
		// PageControl p = PageControl.getQueryOnlyInstance();
		// this.commonFind(request, response, p, where, sql, Map.class);
		BasTaskPlan bean = service.get(id);
		Map<String, BasStinfoB> stion = stinfoService.selectStcd();
		Map<Integer, BasDeviceInfo> device = deviceInfoService.selectDevice();

		for (BasTaskPlanStcd station : bean.getBasTaskPlanStcds()) {
			station.setName(stion.get(station.getStcd()).getStnm());
			for (BasTaskPlanDevice planDevice : station.getBasTaskPlanDevices()) {
				// System.out.println(device.get(planDevice.getId()).getDeviceName());
				planDevice.setName(device.get(planDevice.getDeviceId()).getDeviceName());
			}
		}

		super.toJson(bean, 1, true, ActionMsgParam.ACM_LIS_SUC, request, response);

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
	public GenericServiceImpl<BasTaskPlan, Integer> getService() {
		return service;
	}

}
