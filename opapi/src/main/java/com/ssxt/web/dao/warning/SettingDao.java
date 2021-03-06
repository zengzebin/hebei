package com.ssxt.web.dao.warning;

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
import com.ssxt.web.bean.SymUser;
import com.ssxt.web.bean.SysAddvcdB;
import com.ssxt.web.bean.warning.BasSetting;
import com.ssxt.web.service.AddvcdService;

@Repository
public class SettingDao extends GenericDaoImpl<BasSetting, Integer> {
	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(SettingDao.class);

}