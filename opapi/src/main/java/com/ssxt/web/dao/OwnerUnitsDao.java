package com.ssxt.web.dao;

import org.springframework.stereotype.Repository;

import com.ssxt.common.dao.GenericDaoImpl;
import com.ssxt.web.bean.BasOwnerUnits;

@Repository(value = "ownerUnitsDao")
public class OwnerUnitsDao extends GenericDaoImpl<BasOwnerUnits, String> {

	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(OwnerUnitsDao.class);

}
