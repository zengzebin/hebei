package com.ssxt.web.controller;

import static com.ssxt.common.util.DataUtil.notNullString;
import static com.ssxt.common.util.DataUtil.showMsgException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.Destination;
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

import com.ssxt.activeMQ.DmzProducerService;
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
import com.ssxt.web.bean.BasMaintGps;
import com.ssxt.web.bean.SymRole;
import com.ssxt.web.bean.SymUser;
import com.ssxt.web.bean.SysAddvcdB;
import com.ssxt.web.service.AddvcdService;
import com.ssxt.web.service.MaintGpsLastService;
import com.ssxt.web.service.MaintGpsService;
import com.ssxt.web.service.RoleService;
import com.ssxt.web.service.UserService;

@Controller
@RequestMapping(value = "/api/maintGps/")
public class GpsController extends SpringBaseController<BasMaintGps, Integer> {

	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(GpsController.class);

	@Autowired
	MaintGpsService maintGpsService;

	@Autowired
	MaintGpsLastService last;

	@Comment("上传gps")
	@RequestMapping(value = "addGps", method = RequestMethod.POST)
	public void addGps(@ModelAttribute("bean") BasMaintGps bean, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	 
		maintGpsService.addGps(bean);
		toJson(null, 1, true, "gps添加成功", request, response);
	}

	@Comment("历史gps")
	@RequestMapping(value = "loadAll", method = RequestMethod.GET)
	public void loadAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
		SqlBuffer sbf = SqlBuffer.begin();
		sbf.add("maintUserId", DataUtil.getParameterInt(request.getParameter("maintUserId")));
		super.loadAll(request, response, sbf, null, null);
	}

	@Comment("最新gps")
	@RequestMapping(value = "last", method = RequestMethod.GET)
	public void last(HttpServletRequest request, HttpServletResponse response) throws Exception {
		SqlBuffer sbf = SqlBuffer.begin();
		sbf.add("maintUserId", DataUtil.getParameterInt(request.getParameter("maintUserId")));
		List lsit = last.findByCondition(PageControl.getQueryOnlyInstance(), sbf.end());
		super.toJson(lsit, lsit.size(), true, "查询成功", request, response);
	}

	@Comment("测站运维人员距离")
	@RequestMapping(value = "distance", method = RequestMethod.GET)
	public void distance(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PageControl p = maintGpsService.getDistance(request, response);
		super.toJson(p.getList(), p.getList(), true, "查询成功", request, response);
	}

	@Comment("维修里程")
	@RequestMapping(value = "RepairMileage", method = RequestMethod.GET)
	public void RepairMileage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		double mileage = maintGpsService.RepairMileage2(request, response);
		super.toJson(mileage, 1, true, "查询成功", request, response);
	}

	@Comment("维修轨迹")
	@RequestMapping(value = "historyRoute", method = RequestMethod.GET)
	public void historyRoute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PageControl p = maintGpsService.historyRoute(request, response);
		super.toJson(p.getList(), p.getRowCount(), true, "查询成功", request, response);
	}

	@Override
	public boolean getCommentByHbm() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getEntityName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GenericServiceImpl<BasMaintGps, Integer> getService() {
		// TODO Auto-generated method stub
		return maintGpsService;
	}

}
