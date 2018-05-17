package com.ssxt.web.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.ssxt.common.dao.GenericDaoImpl;
import com.ssxt.common.page.SqlCondGroup;
import com.ssxt.common.util.DataUtil;
import com.ssxt.web.bean.SymRole;
import com.ssxt.web.bean.SymRolePermission;
 
@Repository

public class SymRolePermissionDao extends GenericDaoImpl<SymRolePermission, Integer> {

	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(SymRolePermissionDao.class);

	public void delete(Long id) {
		Session session = getSession();
		session.createSQLQuery("delete from sym_role where id=" + id + "").executeUpdate();
		session.close();

	}

	public void commonConList(List<SqlCondGroup> conList, SymRolePermission bean) {
		// TODO Auto-generated method stub

	}

}