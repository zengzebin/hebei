package com.ssxt.web.dao;

 

import java.io.Serializable;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.ssxt.common.dao.GenericDaoImpl;
import com.ssxt.common.page.SqlCondGroup;
import com.ssxt.web.bean.SymMenu;

 

@Repository
public class MenuDao extends GenericDaoImpl<SymMenu, Integer>{
	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(MenuDao.class);

	 

	public void commonConList(List<SqlCondGroup> conList, SymMenu bean) {
		// TODO Auto-generated method stub
		
	}
 
 
}