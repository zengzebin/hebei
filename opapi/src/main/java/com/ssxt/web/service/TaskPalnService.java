package com.ssxt.web.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssxt.common.bean.FileInfo;
import com.ssxt.common.dao.GenericDao;
import com.ssxt.common.page.PageControl;
import com.ssxt.common.page.SqlBuffer;
import com.ssxt.common.service.GenericServiceImpl;
import com.ssxt.common.util.CtxHelper;
import com.ssxt.common.util.DataUtil;
import com.ssxt.common.util.FileUploadUtils;
import com.ssxt.common.util.MDCUtil;
import com.ssxt.common.util.PropertiesUtil;
import com.ssxt.web.bean.BasClassRoom;
import com.ssxt.web.bean.BasDeviceInfo;
import com.ssxt.web.bean.BasStinfoB;
import com.ssxt.web.bean.BasStinfoImage;
import com.ssxt.web.bean.BasTaskPlan;
import com.ssxt.web.bean.BasTaskPlanDevice;
import com.ssxt.web.bean.BasTaskPlanStcd;
import com.ssxt.web.bean.ModelPalnDevice;
import com.ssxt.web.dao.StinfoDao;
import com.ssxt.web.dao.StinfoImageDao;
import com.ssxt.web.dao.TaskPalnDao;
import com.ssxt.web.dao.TaskPalnDeviceDao;
import com.ssxt.web.dao.TaskPalnStcdDao;
import com.ssxt.web.bean.SymMenu;
import com.ssxt.web.bean.SysAddvcdB;
import com.ssxt.web.dao.AddvcdDao;
import com.ssxt.web.dao.MenuDao;

@Service(value = "taskPalnService")
public class TaskPalnService extends GenericServiceImpl<BasTaskPlan, Integer> {
	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TaskPalnService.class);

	@Autowired
	private TaskPalnDao dao;

	@Autowired
	private TaskPalnStcdDao taskPalnStcdDao;

	@Autowired
	private TaskPalnDeviceDao taskPalnDeviceDao;

	@Autowired
	private DeviceInfoService deviceInfo;

	@Override
	public GenericDao<BasTaskPlan, Integer> getDao() {
		return dao;
	}

	/**
	 * 生产测站设备等信息
	 * 
	 * @param stcds
	 * @param planId
	 */
	public void createStcdPaln(String[] stcds, int planId) {
		for (int i = 0; i < stcds.length; i++) {
			// 保存测站
			BasTaskPlanStcd planStcd = new BasTaskPlanStcd();
			planStcd.setPlanId(planId);
			planStcd.setStcd(stcds[i]);
			taskPalnStcdDao.save(planStcd);
			// 获取该测站下面的设备
			SqlBuffer where = SqlBuffer.begin();
			where.add("stcd", stcds[i]);
			List<BasDeviceInfo> list = deviceInfo.findByCondition(PageControl.getQueryOnlyInstance(), where.end());
			for (int j = 0; j < list.size(); j++) {
				BasDeviceInfo device = list.get(j);
				BasTaskPlanDevice planDevice = new BasTaskPlanDevice();
				planDevice.setParentId(planStcd.getId());
				planDevice.setDeviceId(device.getId());
				planDevice.setState(0);
				planDevice.setModifyTime(new Date());
				// 保存测站设备
				taskPalnDeviceDao.save(planDevice);
			}
		}
	}

	/**
	 * 添加计划任务
	 * 
	 * @param plan
	 * @param request
	 * @param response
	 */
	@Transactional
	public void add(BasTaskPlan plan, HttpServletRequest request, HttpServletResponse response) {
		if (plan.getStcds() == null)
			DataUtil.showMsgException("请选择计划测站");
		String[] stcds = plan.getStcds().split(",");
		// 保存计划
		dao.save(plan);
		// 分析计划中有几个要检查的测站
		createStcdPaln(stcds, plan.getId());

	}

	/**
	 * 修改任务计划
	 * 
	 * @param plan
	 * @param request
	 * @param response
	 */
	@Transactional
	public BasTaskPlan update(BasTaskPlan plan, HttpServletRequest request, HttpServletResponse response) {
		// if (plan.getStcds() == null)
		// DataUtil.showMsgException("请选择计划测站");
		BasTaskPlan oldBean = this.get(plan.getId());

		// 判断是否测站有改变
		// if (plan.getStcds().equals(plan.getStcds())) {
		//
		// // 查询原来的测站
		// SqlBuffer where = SqlBuffer.begin();
		// where.add("planId", plan.getId());
		// List<BasTaskPlanStcd> list =
		// taskPalnStcdDao.findByCondition(PageControl.getQueryOnlyInstance(),
		// where.end());
		// // 删除原来的测站及设备等信息
		// taskPalnStcdDao.deleteAll(list);
		//
		// String[] stcds = plan.getStcds().split(",");
		// createStcdPaln(stcds, plan.getId());
		// }
		plan.setModifyTime(new Date());
		this.updateDomain("修改", oldBean, plan);

		return null;
	}

	/**
	 * 运维修改设备
	 * 
	 * @param planDevice
	 * @param request
	 * @param response
	 */
	@Transactional
	public void updateDevice(ModelPalnDevice devices, HttpServletRequest request, HttpServletResponse response) {
		if (devices.getDevices() != null) {
			for (int i = 0; i < devices.getDevices().size(); i++) {
				int id = devices.getDevices().get(i).getId();
				BasTaskPlanDevice oldBeandeive = taskPalnDeviceDao.get(id);
				devices.getDevices().get(i).setModifyTime(new Date());
				taskPalnDeviceDao.updateDomain("1", MDCUtil.getUserId(), MDCUtil.getUserName(), "修改", oldBeandeive,
						devices.getDevices().get(i));
			}
		}

	}

}
