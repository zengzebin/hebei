package com.ssxt.web.dao;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.identity.User;
import org.springframework.stereotype.Repository;

import com.ssxt.common.dao.GenericDaoImpl;
import com.ssxt.common.page.PageControl;
import com.ssxt.common.page.SqlBuffer;
import com.ssxt.common.page.SqlCondGroup;
import com.ssxt.common.util.DataUtil;
import com.ssxt.common.util.EnCryptTool;
import com.ssxt.web.bean.BasWarehouseSurplus;
import com.ssxt.web.bean.BasWarehouseSurplusId;
import com.ssxt.web.bean.SymUser;
import com.ssxt.web.bean.SysAddvcdB;
import com.ssxt.web.service.AddvcdService;

@Repository
public class WarehouseSurplusDao extends GenericDaoImpl<BasWarehouseSurplus, BasWarehouseSurplusId> {
	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(WarehouseSurplusDao.class);

}