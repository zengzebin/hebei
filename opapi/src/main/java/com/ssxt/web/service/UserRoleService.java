package com.ssxt.web.service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssxt.common.dao.GenericDao;
import com.ssxt.common.page.PageControl;
import com.ssxt.common.page.SqlBuffer;
import com.ssxt.common.page.SqlUtil;
import com.ssxt.common.service.GenericServiceImpl;
import com.ssxt.common.util.DataUtil;
import com.ssxt.common.util.EnCryptTool;
import com.ssxt.common.util.SpringBeanUtil;
import com.ssxt.web.bean.SymAddvcdUser;
import com.ssxt.web.bean.SymRolePermission;
import com.ssxt.web.bean.SymRoleUser;
import com.ssxt.web.bean.SymUser;
import com.ssxt.web.dao.UserDao;
import com.ssxt.web.dao.UserRoleDao;

@Service(value = "userRoleService")
public class UserRoleService extends GenericServiceImpl<SymRoleUser, Integer> {

	protected UserRoleDao dao;
	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(UserRoleService.class);

	@Override
	public GenericDao<SymRoleUser, Integer> getDao() {
		return dao;
	}

	@Autowired
	public void setDao(UserRoleDao dao) {
		this.dao = dao;
	}

	public void deleteUserRole(Integer userId) {
		dao.bulkUpdate("delete SymRoleUser where userId=" + userId);

	}

}
