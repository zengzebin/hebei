package com.ssxt.web.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssxt.common.dao.GenericDao;
import com.ssxt.common.service.GenericServiceImpl;
import com.ssxt.web.bean.BasDeviceInfo;
import com.ssxt.web.bean.BasStinfoB;
import com.ssxt.web.bean.BasStinfoSttp;
 
import com.ssxt.web.dao.StcdSttpDao;
import com.ssxt.web.dao.StinfoDao;
import com.ssxt.web.bean.SymMenu;
import com.ssxt.web.bean.SysAddvcdB;
import com.ssxt.web.dao.AddvcdDao;
import com.ssxt.web.dao.MenuDao;

@Service(value = "stcdSttpService")
public class StcdSttpService extends GenericServiceImpl<BasStinfoSttp, Integer> {
	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(StcdSttpService.class);

	protected StcdSttpDao stcdSttpDao;

	@Override
	public GenericDao<BasStinfoSttp, Integer> getDao() {
		return stcdSttpDao;
	}

	@Autowired
	public void setDao(StcdSttpDao dao) {
		this.stcdSttpDao = dao;
	}

	/*
	 * public void delete(int id){ stcdSttpDao.bulkUpdate(
	 * "delete BasStinfoSttp where stcd='"+stcd+"'"); }
	 * 
	 * public void add(int id,String sttps){ delete(id); String
	 * []sttp=sttps.split(","); List <BasStinfoSttp>list=new
	 * ArrayList<BasStinfoSttp>(); for (int i = 0; i < sttp.length; i++) {
	 * BasStinfoSttp info=new BasStinfoSttp(); info.setStcd(stcd);
	 * info.setSttp(sttp[i]); list.add(info); }
	 * stcdSttpDao.saveOrUpdateAll(list); }
	 */
}
