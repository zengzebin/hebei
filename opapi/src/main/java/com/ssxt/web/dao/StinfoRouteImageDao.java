package com.ssxt.web.dao;

import org.springframework.stereotype.Repository;

import com.ssxt.common.dao.GenericDaoImpl;
import com.ssxt.web.bean.BasStinfoB;
import com.ssxt.web.bean.BasStinfoRoute;
import com.ssxt.web.bean.BasStinfoRouteImage;

@Repository(value = "stinfoRouteImageDao")
public class StinfoRouteImageDao extends GenericDaoImpl<BasStinfoRouteImage, Integer> {

	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(StinfoRouteImageDao.class);

}
