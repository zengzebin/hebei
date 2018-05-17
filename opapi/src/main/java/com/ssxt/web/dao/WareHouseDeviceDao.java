package com.ssxt.web.dao;

import com.ssxt.common.dao.GenericDaoImpl;
import com.ssxt.common.page.SqlCondGroup;
import com.ssxt.web.bean.BasWarehouseDevice;
import com.ssxt.web.bean.BasDeviceInfo;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository(value = "wareHouseDeviceDao")
public class WareHouseDeviceDao extends GenericDaoImpl<BasWarehouseDevice, Integer> {

	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(WareHouseDeviceDao.class);

}
