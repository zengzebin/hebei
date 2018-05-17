package com.ssxt.web.dao;

import com.ssxt.common.dao.GenericDaoImpl;
import com.ssxt.common.page.SqlCondGroup;
import com.ssxt.web.bean.BasDeviceInfo;
import com.ssxt.web.bean.BasStinfoB;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository(value = "stinfoDao")
public class StinfoDao extends GenericDaoImpl<BasStinfoB, Integer> {

	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(StinfoDao.class);

	public void commonConList(List<SqlCondGroup> conList, BasStinfoB bean) {
		// TODO Auto-generated method stub

	}

}
