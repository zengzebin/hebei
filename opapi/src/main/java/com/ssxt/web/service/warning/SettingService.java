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
import com.ssxt.web.dao.AddvcdDao;
import com.ssxt.web.dao.MenuDao;
import com.ssxt.web.dao.warning.SettingDao;

@Service(value = "settingService")
public class SettingService extends GenericServiceImpl<BasSetting, Integer> {

	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(SettingService.class);

	@Autowired
	protected SettingDao dao;

	@Override
	public GenericDao<BasSetting, Integer> getDao() {
		return dao;
	}

	public void init() {
		List<BasSetting> list = dao.loadAll();
		for (BasSetting bean : list) {
			CachePool.getInstance().putCacheItem(bean.getName(), bean);
		}
	}

	public static void main(String[] args) {
		String sql = "dwd/dwd/dwd";
		String[] sqls = sql.split("/");
		System.out.println(sqls);
	}

}
