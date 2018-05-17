package com.ssxt.web.dao;

import com.ssxt.common.dao.GenericDaoImpl;
 
import com.ssxt.common.page.SqlCondGroup;
import com.ssxt.web.bean.BasDeviceInfo;
import com.ssxt.web.bean.BasTaskInfo;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository(value = "taskInfoDao")
public   class TaskInfoDao extends GenericDaoImpl<BasTaskInfo,String> {

   
	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TaskInfoDao.class);

	public void commonConList(List<SqlCondGroup> conList, BasDeviceInfo bean) {
		// TODO Auto-generated method stub
		
	}
 
	 
	  
	 
	 
 
}
