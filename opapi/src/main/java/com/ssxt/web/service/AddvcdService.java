package com.ssxt.web.service;

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
import com.ssxt.web.bean.SymAddvcdUser;
import com.ssxt.web.bean.SymMenu;
import com.ssxt.web.bean.SysAddvcdB;
import com.ssxt.web.dao.AddvcdDao;
import com.ssxt.web.dao.MenuDao;

@Service(value = "addvcdService")
public class AddvcdService extends GenericServiceImpl<SysAddvcdB, String> {

	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(AddvcdService.class);

	@Autowired
	protected AddvcdDao dao;

	@Override
	public GenericDao<SysAddvcdB, String> getDao() {
		return dao;
	}

	/*
	 * @Cacheable(value = "common") public List<SysAddvcdB> loadAll() { return
	 * dao.loadAll(); }
	 */

	public static String likeStart(String addvcd) {
		if (addvcd == null) {
			return null;
		} else {
			int code = Integer.parseInt(addvcd);
			if (code % 10000 == 0) {
				code = code / 10000;
			} else if (code % 100 == 0) {
				code = code / 100;
			}
			return String.valueOf(code);

		}

	}

	// SELECT a.addvcd FROM bas_stinfo_b a
	// INNER JOIN sym_addvcd_user b ON(a.addvcd=b.addvcd AND userId=1)
	// WHERE a.`addvcd` LIKE '1302%'

	// public String getRange(){
	// this.dao.findByCondition(pageControl, conList);
	// return "";
	// }
	public List<SymAddvcdUser> addvcdUser(String[] addvcd, int userId) {
		SqlBuffer where = SqlBuffer.begin();
		Map<String, Boolean> map = new HashMap<String, Boolean>();

		List<SysAddvcdB> list = new ArrayList<SysAddvcdB>();
		int code = 0;
		boolean province = false;
		for (int i = 0; i < addvcd.length; i++) {
			code = Integer.parseInt(addvcd[i]) % 10000;
			if (code == 0) {
				Object o = (List<SysAddvcdB>) CachePool.getInstance().getCacheItem("all");
				if (o == null) {
					list.addAll(this.loadAll());
					// 放入缓存
					CachePool.getInstance().putCacheItem("all", list);
				} else {
					list.addAll((List<SysAddvcdB>) o);
				}

				province = true;
				break;
			}

		}
		// 没有省
		if (!province) {
			for (int i = 0; i < addvcd.length; i++) {
				Object o = CachePool.getInstance().getCacheItem(addvcd[i]);
				if (o == null) {
					where.remove();
					where.add("parentId", addvcd[i]);
					List<SysAddvcdB> newList = this.findByCondition(PageControl.getQueryOnlyInstance(), where.end());
					CachePool.getInstance().putCacheItem(addvcd[i], newList);
					list.addAll(newList);
				} else {
					list.addAll((List<SysAddvcdB>) o);
				}
			}
		}

		List<SymAddvcdUser> addvcdUser = new ArrayList<SymAddvcdUser>();
		for (int i = 0; i < list.size(); i++) {
			SymAddvcdUser city = new SymAddvcdUser();
			city.setAddvcd(list.get(i).getAddvcd());
			city.setUserId(userId);
			addvcdUser.add(city);
		}

		// for (int i = 0; i < addvcd.length; i++) {
		// int code = Integer.parseInt(addvcd[i]) % 100;
		// if (code == 0)
		// map.put(addvcd[i], true);
		// }
		//
		// for (int i = 0; i < addvcd.length; i++) {
		// int code = Integer.parseInt(addvcd[i]) % 100;
		// if (code != 0) {
		// String id = addvcd[i].substring(0, 4) + "00";
		// Boolean boole = map.get(id);
		// if (boole != null)
		// map.put(id, false);
		// }
		//
		// }
		//
		// System.out.println(map);
		//
		// for (int i = 0; i < addvcd.length; i++) {
		// System.out.println("添加县编码" + addvcd[i]);
		// }
		// for (String key : map.keySet()) {
		// boolean bool = map.get(key);
		// if (bool) {
		// System.out.println("添加市编码编码" + key);
		// }
		// }

		return addvcdUser;
	}

	public static void main(String[] args) {
		String[] addvcd = { "130000", "130200", "130300", "130800", "130810" };
		new AddvcdService().addvcdUser(addvcd, 100);
		System.out.println("" + 130000 % 10000);

	}

}
