package com.ssxt.web.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssxt.common.dao.GenericDao;
import com.ssxt.common.page.PageControl;
import com.ssxt.common.page.SqlBuffer;
import com.ssxt.common.service.GenericServiceImpl;
import com.ssxt.common.util.CachePool;
import com.ssxt.common.util.SpringUtil;
import com.ssxt.web.bean.BasDeviceInfo;
import com.ssxt.web.bean.BasStinfoB;
import com.ssxt.web.dao.DeviceInfoDao;
import com.ssxt.web.bean.SymMenu;
import com.ssxt.web.bean.SysAddvcdB;
import com.ssxt.web.controller.CommonController;
import com.ssxt.web.dao.AddvcdDao;
import com.ssxt.web.dao.MenuDao;

@Service(value = "deviceInfoService")
public class DeviceInfoService extends GenericServiceImpl<BasDeviceInfo, Integer> {
	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(DeviceInfoService.class);

	protected DeviceInfoDao deviceInfoDao;

	@Override
	public GenericDao<BasDeviceInfo, Integer> getDao() {
		return deviceInfoDao;
	}

	@Autowired
	public void setDao(DeviceInfoDao dao) {
		this.deviceInfoDao = dao;
	}

	public List<?> getDeviceInfo(String addr) {
		SqlBuffer sbf = SqlBuffer.begin();
		sbf.add("address", addr, "=", "and");

		return deviceInfoDao.findByCondition(PageControl.getQueryOnlyInstance(), sbf.end());
	}

	/**
	 * 设置设备字典缓存
	 * 
	 * @return
	 */
	public Map<Integer, BasDeviceInfo> selectDevice() {
		Map<Integer, BasDeviceInfo> device = new HashMap<Integer, BasDeviceInfo>();

		DeviceInfoService deviceInfoService = (DeviceInfoService) SpringUtil.getContext().getBean("deviceInfoService");
		List<BasDeviceInfo> BasDeviceInfos = deviceInfoService.loadAll();

		for (int i = 0; i < BasDeviceInfos.size(); i++) {
			BasDeviceInfo bean = BasDeviceInfos.get(i);
			device.put(bean.getId(), bean);
		}
		CachePool.getInstance().putCacheItem("device", device);

		return device;
	}
}
