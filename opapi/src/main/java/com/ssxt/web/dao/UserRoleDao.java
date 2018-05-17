package com.ssxt.web.dao;


import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.ssxt.common.dao.GenericDaoImpl;
import com.ssxt.common.page.SqlCondGroup;
import com.ssxt.common.util.DataUtil;
import com.ssxt.web.bean.SymRole;
import com.ssxt.web.bean.SymRoleUser;

@Repository

public class UserRoleDao extends GenericDaoImpl<SymRoleUser, Integer> {

	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(UserRoleDao.class);

	public void commonConList(List<SqlCondGroup> conList, SymRoleUser bean) {
		// TODO Auto-generated method stub

	}

}