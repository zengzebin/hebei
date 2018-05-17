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
import com.ssxt.web.bean.BasDeviceOrder;
import com.ssxt.web.bean.SymUser;
import com.ssxt.web.bean.SysAddvcdB;

@Repository
public class OrderDao extends GenericDaoImpl<BasDeviceOrder, Integer> {

	 
}