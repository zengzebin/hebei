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
import com.ssxt.web.bean.BasWarehouseDevice;
import com.ssxt.web.bean.ModelRepairDeviceUse;
import com.ssxt.web.bean.BasDeviceInfo;
import com.ssxt.web.bean.BasDeviceType;
import com.ssxt.web.service.DeviceInfoService;
import com.ssxt.web.service.WareHouseDeviceService;
import com.ssxt.web.bean.SymMenu;
import com.ssxt.web.bean.SymRole;
import com.ssxt.web.bean.SymUser;
import com.ssxt.web.dao.DeviceTypeDao;
import com.ssxt.web.service.MenuService;
import com.ssxt.web.service.RoleService;
import com.ssxt.web.service.UserService;

@Controller
@RequestMapping(value = "/api/warehouseDevice/")
public class WareHouseDeviceController extends SpringBaseController<BasWarehouseDevice, Integer> {
	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(WareHouseDeviceController.class);

	@Autowired
	private WareHouseDeviceService service;

	@Autowired
	private DeviceTypeDao deviceTypeDao;

	@Comment("设备入库")
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public void add(@ModelAttribute("bean") BasWarehouseDevice bean, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// bean.setCreateTime(new Date());

		service.add(bean, request, response);
		toJson(bean, 1, true, ActionMsgParam.ACM_SAV_SUC, request, response);
	}

	@Comment("设备入库查询列表")
	@RequestMapping(value = "select", method = RequestMethod.GET)
	public void list(@RequestParam(value = "searchtext", required = false) String searchtext,
			@RequestParam(value = "order", required = false) String sort,
			@RequestParam(value = "orderdir", required = false) String dir,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "length", required = false) Integer pageSize, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SqlBuffer where = SqlBuffer.begin();

		where.add("wareHouseId", DataUtil.getParameterInt(request.getParameter("wareHouseId")));
		PageControl p = PageControl.getQueryOnlyInstance();

		if (request.getParameter("page") != null)
			p = this.initPage(searchtext, sort, dir, start, pageSize, request, response);
		this.commonFind(request, response, p, where, null, Map.class);

		super.toJson(p.getList(), p.getRowCount(), true, ActionMsgParam.ACM_LIS_SUC, request, response);
	}

	@Comment("设备出库查询列表")
	@RequestMapping(value = "out", method = RequestMethod.GET)
	public void out(@RequestParam(value = "searchtext", required = false) String searchtext,
			@RequestParam(value = "order", required = false) String sort,
			@RequestParam(value = "orderdir", required = false) String dir,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "length", required = false) Integer pageSize, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SqlBuffer where = SqlBuffer.begin();
		String sql = "select * from bas_warehouse_out";
		where.add("wareHouseId", DataUtil.getParameterInt(request.getParameter("wareHouseId")));
		where.add("orderId", DataUtil.getParameterInt(request.getParameter("orderId")));
		PageControl p = PageControl.getQueryOnlyInstance();
		if (request.getParameter("page") != null)
			p = this.initPage(searchtext, sort, dir, start, pageSize, request, response);
		this.commonFind(request, response, p, where, sql.toString(), Map.class);
		super.toJson(p.getList(), p.getRowCount(), true, ActionMsgParam.ACM_LIS_SUC, request, response);
	}

	@Comment("运维人员使用")
	@RequestMapping(value = "repairUse", method = RequestMethod.POST)
	public void repairUse(@ModelAttribute("deviceUse") ModelRepairDeviceUse deviceUse, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		service.repairUse(deviceUse, request, response);
		super.toJson(null, 1, true, ActionMsgParam.ACM_MOD_SUC, request, response);
	}

	@Comment("运维人员使用记录")
	@RequestMapping(value = "repairUseHistory", method = RequestMethod.GET)
	public void repairUseHistory(@RequestParam(value = "searchtext", required = false) String searchtext,
			@RequestParam(value = "order", required = false) String sort,
			@RequestParam(value = "orderdir", required = false) String dir,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "length", required = false) Integer pageSize, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		PageControl p = super.initPage(searchtext, sort, dir, start, pageSize, request, response);
		SqlBuffer wehre = SqlBuffer.begin();
		String sql = "select * from bas_repair_device_use";
		wehre.add("taskNo", request.getParameter("taskNo"));
		wehre.add("userId", request.getParameter("userId"));
		super.commonFind(request, response, p, wehre, sql, Map.class);
		super.toJson(p.getList(), p.getPageRowCount(), true, ActionMsgParam.ACM_LIS_SUC, request, response);
	}

	@Comment("设备类型列表")
	@RequestMapping(value = "/deviceType/select", method = RequestMethod.GET)
	public void deviceType(@RequestParam(value = "searchtext", required = false) String searchtext,
			@RequestParam(value = "order", required = false) String sort,
			@RequestParam(value = "orderdir", required = false) String dir,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "length", required = false) Integer pageSize, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SqlBuffer where = SqlBuffer.begin();

		PageControl p = PageControl.getQueryOnlyInstance();
		String sql = "select * from bas_device_type";

		where.addLike("name", request.getParameter("name"));
		if (request.getParameter("page") != null)
			p = this.initPage(searchtext, sort, dir, start, pageSize, request, response);
		this.commonFind(request, response, p, where, sql, Map.class);

		super.toJson(p.getList(), p.getRowCount(), true, ActionMsgParam.ACM_LIS_SUC, request, response);
	}

	@Comment("添加设备类型")
	@RequestMapping(value = "deviceType/add", method = RequestMethod.POST)
	public void addType(@ModelAttribute("bean") BasDeviceType devieType, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// bean.setCreateTime(new Date());
		AccData ad = CtxHelper.createFailAccData();
		deviceTypeDao.save(devieType);
		ad.setMsg(ActionMsgParam.ACM_SAV_SUC);
		ad.setSuccess(true);
		CtxHelper.setNoCache(response);
		CtxHelper.writeToJson(response, ad);
	}

	@Comment("删除设备类型")
	@RequestMapping(value = "deviceType/delete/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable Integer id, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// bean.setCreateTime(new Date());
		AccData ad = CtxHelper.createFailAccData();
		deviceTypeDao.deleteByKey(id);
		ad.setMsg(ActionMsgParam.ACM_DEL_SUC);
		ad.setSuccess(true);
		CtxHelper.setNoCache(response);
		CtxHelper.writeToJson(response, ad);
	}

	@Comment("修改设备类型")
	@RequestMapping(value = "deviceType/update", method = RequestMethod.PUT)
	public void deviceTypeUpdate(@ModelAttribute("bean") BasDeviceType devieType, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// bean.setCreateTime(new Date());
		AccData ad = CtxHelper.createFailAccData();
		deviceTypeDao.saveOrUpdate(devieType);
		ad.setMsg(ActionMsgParam.ACM_MOD_SUC);
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
	public GenericServiceImpl<BasWarehouseDevice, Integer> getService() {
		return service;
	}

}
