package com.ssxt.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssxt.common.dao.GenericDao;
import com.ssxt.common.service.GenericServiceImpl;
import com.ssxt.web.bean.SymAddvcdUser;
import com.ssxt.web.bean.SymMenu;
import com.ssxt.web.bean.SysAddvcdB;
import com.ssxt.web.dao.AddvcdDao;
import com.ssxt.web.dao.AddvcdUserDao;
import com.ssxt.web.dao.MenuDao;

@Service(value = "addvcdUserService")
public class AddvcdUserService extends GenericServiceImpl<SymAddvcdUser, Integer> {

	protected AddvcdUserDao dao;

	@Override
	public GenericDao<SymAddvcdUser, Integer> getDao() {
		return dao;
	}

	@Autowired
	public void setDao(AddvcdUserDao dao) {
		this.dao = dao;
	}

	public String deleteAddvcdUser(Integer userId) {
		dao.bulkUpdate("delete SymAddvcdUser where userId=" + userId);
		return "delete SymAddvcdUser where userId=" + userId;
	}

}
