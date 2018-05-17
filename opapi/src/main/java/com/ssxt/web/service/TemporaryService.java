package com.ssxt.web.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.ssxt.common.dao.GenericDao;
import com.ssxt.common.page.PageControl;
import com.ssxt.common.page.SqlBuffer;
import com.ssxt.common.service.GenericServiceImpl;
import com.ssxt.common.util.CachePool;
import com.ssxt.common.util.MDCUtil;
import com.ssxt.web.bean.BasDeviceOrder;
import com.ssxt.web.bean.BasTemporary;
import com.ssxt.web.bean.SymAddvcdUser;
import com.ssxt.web.bean.SymMenu;
import com.ssxt.web.bean.SymUser;
import com.ssxt.web.bean.SysAddvcdB;
import com.ssxt.web.dao.AddvcdDao;
import com.ssxt.web.dao.OrderDao;
import com.ssxt.web.dao.MenuDao;
import com.ssxt.web.dao.TemporaryDao;
import com.ssxt.web.vo.Assessment;

@Service(value = "temporaryService")
public class TemporaryService extends GenericServiceImpl<BasTemporary, Integer> {

	protected TemporaryDao dao;

	@Override
	public GenericDao<BasTemporary, Integer> getDao() {
		return dao;
	}

	@Autowired
	public void setDao(TemporaryDao dao) {
		this.dao = dao;
	}

	

}
