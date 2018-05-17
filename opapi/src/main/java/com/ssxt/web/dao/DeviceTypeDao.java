package com.ssxt.web.dao;

import org.springframework.stereotype.Repository;

import com.ssxt.common.dao.GenericDaoImpl;
import com.ssxt.web.bean.BasDeviceInfo;
import com.ssxt.web.bean.BasDeviceOrderDetails;
import com.ssxt.web.bean.BasDeviceType;

@Repository(value = "deviceTypeDao")
public class DeviceTypeDao extends GenericDaoImpl<BasDeviceType, Integer> {

	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(DeviceTypeDao.class);

}
