package com.ssxt.web.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssxt.common.dao.GenericDao;
import com.ssxt.common.service.GenericServiceImpl;
import com.ssxt.web.bean.BasMaintGps;
import com.ssxt.web.bean.BasMaintGpsLast;
import com.ssxt.web.bean.SymMenu;
import com.ssxt.web.bean.SysAddvcdB;
import com.ssxt.web.dao.AddvcdDao;
import com.ssxt.web.dao.MaintGpsDao;
import com.ssxt.web.dao.MaintGpsLastDao;
import com.ssxt.web.dao.MenuDao;

@Service(value = "maintGpsLastService")
public class MaintGpsLastService extends GenericServiceImpl<BasMaintGpsLast, Integer> {

	protected MaintGpsLastDao dao;

	@Override
	public GenericDao<BasMaintGpsLast, Integer> getDao() {
		return dao;
	}

	@Autowired
	public void setDao(MaintGpsLastDao dao) {
		this.dao = dao;
	}

}
