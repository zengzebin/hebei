package com.ssxt.web.service.warning;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.ssxt.common.dao.GenericDao;
import com.ssxt.common.page.PageControl;
import com.ssxt.common.page.SqlBuffer;
import com.ssxt.common.service.GenericServiceImpl;
import com.ssxt.common.util.CachePool;
 
import com.ssxt.web.bean.SymMenu;
import com.ssxt.web.bean.SysAddvcdB;
import com.ssxt.web.bean.warning.BasSetting;
import com.ssxt.web.bean.warning.BasWarning;
import com.ssxt.web.dao.AddvcdDao;
import com.ssxt.web.dao.MenuDao;
import com.ssxt.web.dao.warning.SettingDao;
import com.ssxt.web.dao.warning.WarningDao;

@Service(value = "warningService")
public class WarningService extends GenericServiceImpl<BasWarning, Integer> {

	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(WarningService.class);

	@Autowired
	protected WarningDao dao;

	@Override
	public GenericDao<BasWarning, Integer> getDao() {
		return dao;
	}

	public void warningAdd(BasWarning bean) {
		try {
			dao.save(bean);
		} catch (Exception e) {
			log.error("报警新增出差" + e.getMessage());
			e.printStackTrace();

		}

	}

	public static void main(String[] args) {

	}

}
