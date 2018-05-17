package com.ssxt.web.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssxt.common.dao.GenericDao;
import com.ssxt.common.service.GenericServiceImpl;
import com.ssxt.web.bean.BasOwnerUnits;
import com.ssxt.web.dao.OwnerUnitsDao;

@Service(value = "ownerUnitsService")
public class OwnerUnitsService extends GenericServiceImpl<BasOwnerUnits, String> {

	@Resource(name = "ownerUnitsDao")
	private OwnerUnitsDao ownerUnitsDao;

	@Override
	public GenericDao<BasOwnerUnits, String> getDao() {
		return ownerUnitsDao;
	}

}
