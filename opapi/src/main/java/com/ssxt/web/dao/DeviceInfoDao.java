package com.ssxt.web.dao;

import com.ssxt.common.dao.GenericDaoImpl;
import com.ssxt.common.page.SqlCondGroup;
import com.ssxt.web.bean.BasDeviceInfo;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository(value = "deviceInfoDao")
public class DeviceInfoDao extends GenericDaoImpl<BasDeviceInfo, Integer> {

	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(DeviceInfoDao.class);

}
