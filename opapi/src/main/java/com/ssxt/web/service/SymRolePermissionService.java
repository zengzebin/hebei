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
import com.ssxt.web.bean.SymRolePermission;
 import com.ssxt.web.bean.SymUser;
import com.ssxt.web.dao.SymRolePermissionDao;
import com.ssxt.web.dao.UserDao;

@Service(value = "symRolePermissionService")
public class SymRolePermissionService extends GenericServiceImpl<SymRolePermission, Integer> {

	protected SymRolePermissionDao dao;
	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(SymRolePermissionService.class);

	@Override
	public GenericDao<SymRolePermission, Integer> getDao() {
		return dao;
	}

	@Autowired
	public void setDao(SymRolePermissionDao dao) {
		this.dao = dao;
	}
	
	@Transactional
	public void addPermission(Integer roleId, String[] ids) {
		this.deleteRole(roleId);

		List<SymRolePermission> list = new ArrayList<SymRolePermission>();
		for (int i = 0; i < ids.length; i++) {
			SymRolePermission permission = new SymRolePermission();
			permission.setRoleId(roleId);
			permission.setMenuId(DataUtil.parseInt(ids[i], 0));
			list.add(permission);
		}
		dao.saveOrUpdateAll(list);
	}
	
	public void deleteRole(Integer roleId){
		dao.bulkUpdate("delete SymRolePermission where roleId=" + roleId);
	}

}
