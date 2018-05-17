package com.ssxt.web.dao;

import com.ssxt.common.dao.GenericDaoImpl;
import com.ssxt.common.page.SqlCondGroup;
import com.ssxt.web.bean.BasDeviceInfo;
import com.ssxt.web.bean.BasStinfoB;
import com.ssxt.web.bean.BasStinfoSttp;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository(value = "stcdSttpDao")
public   class StcdSttpDao extends GenericDaoImpl<BasStinfoSttp, Integer> {

   
	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(StcdSttpDao.class);
 

	public void commonConList(List<SqlCondGroup> conList, BasStinfoB bean) {
		// TODO Auto-generated method stub
		
	}
 
	 
	  
	 
	 
 
}
