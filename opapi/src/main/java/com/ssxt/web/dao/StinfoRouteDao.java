package com.ssxt.web.dao;

import org.springframework.stereotype.Repository;

import com.ssxt.common.dao.GenericDaoImpl;
import com.ssxt.web.bean.BasStinfoB;
import com.ssxt.web.bean.BasStinfoRoute;

@Repository(value = "stinfoRouteDao")
public class StinfoRouteDao extends GenericDaoImpl<BasStinfoRoute, Integer> {

	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(StinfoRouteDao.class);

}
