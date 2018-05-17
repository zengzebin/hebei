package com.ssxt.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssxt.common.dao.GenericDao;
import com.ssxt.common.service.GenericServiceImpl;
import com.ssxt.web.bean.BasParam;
import com.ssxt.web.bean.SymMenu;
import com.ssxt.web.bean.SysAddvcdB;
import com.ssxt.web.dao.AddvcdDao;
import com.ssxt.web.dao.MenuDao;
import com.ssxt.web.dao.ParamDao;

@Service(value = "paramService")
public class ParamService extends GenericServiceImpl<BasParam, Integer> {

	protected ParamDao dao;

	@Override
	public GenericDao<BasParam, Integer> getDao() {
		return dao;
	}

	@Autowired
	public void setDao(ParamDao dao) {
		this.dao = dao;
	}

}
