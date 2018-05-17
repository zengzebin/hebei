package com.ssxt.web.service;

import java.util.ArrayList;
import java.util.Date;
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
import com.ssxt.common.util.TelnetConnection;
import com.ssxt.web.bean.BasServer;
import com.ssxt.web.bean.BasServerSituation;
import com.ssxt.web.bean.SymAddvcdUser;
import com.ssxt.web.bean.SymMenu;
import com.ssxt.web.bean.SysAddvcdB;
import com.ssxt.web.dao.AddvcdDao;
import com.ssxt.web.dao.MenuDao;
import com.ssxt.web.dao.ServerDao;
import com.ssxt.web.dao.ServerSituationDao;

@Service(value = "serverService")
public class ServerService extends GenericServiceImpl<BasServer, Integer> {

	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ServerService.class);

	@Autowired
	protected ServerDao dao;

	@Autowired
	protected ServerSituationDao serverSituationDao;

	@Override
	public GenericDao<BasServer, Integer> getDao() {
		return dao;
	}

	public void serverTask() {
		Date nowTime = new Date();
		List<BasServerSituation> saveList = new ArrayList<BasServerSituation>();
		List<BasServer> list = this.findByCondition(PageControl.getQueryOnlyInstance(), null);
		for (int i = 0; i < list.size(); i++) {
			BasServer server = list.get(i);
			TelnetConnection connection = new TelnetConnection();
			boolean bool = connection.ping(server.getIp(), server.getPort());
			BasServerSituation bean = new BasServerSituation();
			bean.setCreateTime(nowTime);
			bean.setServerId(server.getId());
			if (bool) {
				bean.setState(1);

			} else {
				bean.setState(0);
			}
			// saveList.add(bean);
			try {
				serverSituationDao.save(bean);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		// serverSituationDao.saveOrUpdateAll(saveList);
	}

}
