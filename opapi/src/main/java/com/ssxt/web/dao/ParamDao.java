package com.ssxt.web.dao;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

 
import org.springframework.stereotype.Repository;

import com.ssxt.common.dao.GenericDaoImpl;
 
import com.ssxt.common.page.SqlCondGroup;
 
import com.ssxt.web.bean.BasParam;
 

@Repository
public class ParamDao extends GenericDaoImpl<BasParam, Integer> {

	 
	public void commonConList(List<SqlCondGroup> conList, BasParam bean) {
		// TODO Auto-generated method stub
		
	}

}