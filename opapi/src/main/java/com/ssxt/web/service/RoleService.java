package com.ssxt.web.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssxt.common.dao.GenericDao;
import com.ssxt.common.service.GenericServiceImpl;
import com.ssxt.common.util.DataUtil;
import com.ssxt.web.bean.SymRole;
import com.ssxt.web.bean.SymRolePermission;
import com.ssxt.web.dao.RoleDao;

@Service(value = "roleService")
public class RoleService extends GenericServiceImpl<SymRole, Integer> {

	@Autowired
	private RoleDao roleDao;

	@Override
	public GenericDao<SymRole, Integer> getDao() {
		// TODO Auto-generated method stub
		return roleDao;
	}

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	@Autowired
	private SymRolePermissionService symRolePermissionService;

}
