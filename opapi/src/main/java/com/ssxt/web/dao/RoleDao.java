package com.ssxt.web.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.ssxt.common.dao.GenericDaoImpl;
import com.ssxt.common.page.SqlCondGroup;
import com.ssxt.common.util.DataUtil;
import com.ssxt.web.bean.SymRole;

@Repository 
  
public class RoleDao extends GenericDaoImpl<SymRole, Integer>{

	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(RoleDao.class);
	/**
	 * 
	 * 创建数据<br/>
	 * 
	 * @param domain
	 * @param sysUser
	 * @param schoolId
	 * @param reason
	 * @return Serializable
	 * @exception @since
	 *                1.0.0
	 */
	public Serializable saveDomain(String schoolId, Integer userId, String userName, String reason, SymRole domain){
		log.warn(DataUtil.getAddHistory(schoolId, userId, userName, reason, domain));
		return save(domain);
	}

	/**
	 * 修改数据
	 * 
	 * @param oldBean
	 * @param newBean
	 * @param sysUser
	 * @param schoolId
	 * @param reason
	 *            void
	 * @exception @since
	 *                1.0.0
	 */
	public void updateDomain(String schoolId, Integer userId, String userName, String reason, SymRole oldBean, SymRole newBean) {
		String history=DataUtil.getUpdateHistory(schoolId, userId, userName, reason, oldBean, newBean);
		log.warn(history);
		update(oldBean);
	}


	public void commonConList(List<SqlCondGroup> conList, SymRole bean) {
		// TODO Auto-generated method stub
		
	}
 
	 
}