package com.ssxt.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssxt.common.dao.GenericDao;
import com.ssxt.common.service.GenericServiceImpl;
import com.ssxt.web.bean.SymMenu;
import com.ssxt.web.dao.MenuDao;

@Service(value = "menuService")
public class MenuService extends GenericServiceImpl<SymMenu, Integer> {

	protected MenuDao dao;

	@Override
	public GenericDao<SymMenu, Integer> getDao() {
		return dao;
	}

	@Autowired
	public void setDao(MenuDao dao) {
		this.dao = dao;
	}

}
