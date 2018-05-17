package com.ssxt.web.service;

import static com.ssxt.common.util.DataUtil.notNullString;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.xmlbeans.impl.jam.annotation.WhitespaceDelimitedTagParser;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.mysql.jdbc.log.Log;
import com.ssxt.common.dao.GenericDao;
import com.ssxt.common.enums.ConstParam;
import com.ssxt.common.page.PageControl;
import com.ssxt.common.page.SqlBuffer;
import com.ssxt.common.service.GenericServiceImpl;
import com.ssxt.common.util.AonotaionAnalysis;
import com.ssxt.common.util.DataUtil;
import com.ssxt.common.util.MDCUtil;
import com.ssxt.common.util.StatusCode;
import com.ssxt.web.bean.SysAddvcdB;
import com.ssxt.web.controller.TaskInfoController;
import com.ssxt.web.dao.MasterDao;

@Service(value = "masterService")
public class MasterService extends GenericServiceImpl<SysAddvcdB, String> {
	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(MasterService.class);

	protected MasterDao dao;

	@Override
	public GenericDao<SysAddvcdB, String> getDao() {
		return dao;
	}

	@Autowired
	public void setDao(MasterDao dao) {
		this.dao = dao;
	}

}
