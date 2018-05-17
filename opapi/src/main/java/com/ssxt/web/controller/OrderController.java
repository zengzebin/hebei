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
import com.ssxt.web.bean.BasDeviceOrder;
import com.ssxt.web.bean.BasDeviceOrderDetails;
import com.ssxt.web.bean.BasWarehouseDevice;
import com.ssxt.web.bean.ModelDevcieDetails;
import com.ssxt.web.bean.ModelDevcieOutDetails;
import com.ssxt.web.bean.BasDeviceInfo;
import com.ssxt.web.service.OrderService;
import com.ssxt.web.service.AddvcdService;
import com.ssxt.web.service.DeviceInfoService;
import com.ssxt.web.service.WareHouseDeviceService;
import com.ssxt.web.bean.SymMenu;
import com.ssxt.web.bean.SymRole;
import com.ssxt.web.bean.SymUser;
import com.ssxt.web.service.MenuService;
import com.ssxt.web.service.RoleService;
import com.ssxt.web.service.UserService;

@Controller
@RequestMapping(value = "/api/applyDevcie/")
public class OrderController extends SpringBaseController<BasDeviceOrder, Integer> {
	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(OrderController.class);

	@Autowired
	private OrderService service;

	@Comment("申请的设备表单")
	@RequestMapping(value = "select", method = RequestMethod.GET)
	public void list(@RequestParam(value = "searchtext", required = false) String searchtext,
			@RequestParam(value = "order", required = false) String sort,
			@RequestParam(value = "orderdir", required = false) String dir,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "length", required = false) Integer pageSize, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SymRole role = CtxHelper.getSymRole(request);

		SqlBuffer where = SqlBuffer.begin();
		StringBuffer sql = new StringBuffer("SELECT a.* FROM bas_device_order  a");
		sql.append(" INNER JOIN sym_user b ON(a.applyUserId=b.Id)");

		if (request.getParameter("applyUserId") != null) {
			where.add("a.applyUserId", DataUtil.getParameterInt(request.getParameter("applyUserId")));
		} else {
			where.addEndWith("b.directlyUnder", AddvcdService.likeStart(MDCUtil.getAddvcd()));
		}

		where.add("a.examine", DataUtil.getParameterInt(request.getParameter("examine")));

		PageControl p = PageControl.getQueryOnlyInstance();

		if (request.getParameter("page") != null)
			p = this.initPage(searchtext, sort, dir, start, pageSize, request, response);
		this.commonFind(request, response, p, where, sql.toString(), Map.class);

		super.toJson(p.getList(), p.getRowCount(), true, ActionMsgParam.ACM_LIS_SUC, request, response);
	}

	@Comment("清单列表")
	@RequestMapping(value = "load/{orderId}", method = RequestMethod.GET)
	public void detailed(@PathVariable Integer orderId, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.load(orderId, request, response);
	}

	@Comment("申请设备订单")
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public void applyDevice(@ModelAttribute("bean") BasDeviceOrder order,
			@ModelAttribute("detailsList") ModelDevcieDetails detailsList, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		service.applyDevice(order, detailsList, request, response);
		toJson(order, 1, true, ActionMsgParam.ACM_SAV_SUC, request, response);
	}

	@Comment("审批出库")
	@RequestMapping(value = "examine", method = RequestMethod.POST)
	public void examine(@ModelAttribute("deviceOutDetails") ModelDevcieOutDetails deviceOutDetails,
			@ModelAttribute("bean") BasDeviceOrder bean, HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		// bean.setCreateTime(new Date());

		AccData ad = CtxHelper.createFailAccData();
		service.examine(bean, deviceOutDetails, request, response);
		ad.setMsg(ActionMsgParam.ACM_MOD_SUC);
		ad.setSuccess(true);
		CtxHelper.setNoCache(response);
		CtxHelper.writeToJson(response, ad);
	}

	@Comment("删除数据")
	@RequestMapping(value = "delete/{orderId}", method = RequestMethod.DELETE)
	public void delete(@PathVariable Integer orderId, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// bean.setCreateTime(new Date());
		AccData ad = CtxHelper.createFailAccData();
		service.deleteByKey(orderId);
		ad.setMsg(ActionMsgParam.ACM_DEL_SUC);
		ad.setSuccess(true);

		CtxHelper.setNoCache(response);
		CtxHelper.writeToJson(response, ad);
	}

	// @Comment("加载全部数据")
	// @RequestMapping(value = "loadAll", method = RequestMethod.GET)
	// public void loadAll(HttpServletRequest request, HttpServletResponse
	// response) throws Exception {
	// // bean.setCreateTime(new Date());
	// super.loadAll(request, response, null, null, null);
	// }
	// @Comment("订单修改")
	// @RequestMapping(value = "update", method = RequestMethod.PUT)
	// public void update(@ModelAttribute("bean") BasDeviceOrder order,
	// @ModelAttribute("details") ModelDevcieDetails details, HttpServletRequest
	// request,
	// HttpServletResponse response) throws Exception {
	// service.updateApplyDevice(order, details, request, response);
	// toJson(order, 1, true, ActionMsgParam.ACM_MOD_SUC, request, response);
	// }

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
		return "申请设备";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ssxt.rdbox.common.web.SpringBaseController#getService()
	 */
	@Override
	public GenericServiceImpl<BasDeviceOrder, Integer> getService() {
		return service;
	}

}
