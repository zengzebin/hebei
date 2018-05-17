package com.ssxt.web.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.ssxt.common.dao.GenericDao;
import com.ssxt.common.page.PageControl;
import com.ssxt.common.page.SqlBuffer;
import com.ssxt.common.service.GenericServiceImpl;
import com.ssxt.common.util.CachePool;
import com.ssxt.common.util.DataUtil;
import com.ssxt.common.util.MDCUtil;
import com.ssxt.web.bean.BasDeviceType;
import com.ssxt.web.bean.BasWarehouse;
import com.ssxt.web.bean.BasWarehouseDevice;
import com.ssxt.web.bean.SymAddvcdUser;
import com.ssxt.web.bean.SymMenu;
import com.ssxt.web.bean.SysAddvcdB;
import com.ssxt.web.dao.AddvcdDao;
import com.ssxt.web.dao.MenuDao;
import com.ssxt.web.dao.WareHouseDao;
import com.ssxt.web.dao.WareHouseDeviceDao;
import com.ssxt.web.vo.Analysis;

@Service(value = "wareHouseService")
public class WareHouseService extends GenericServiceImpl<BasWarehouse, Integer> {

	@Autowired
	protected WareHouseDao dao;

	@Autowired
	private WareHouseDeviceDao deviceDao;

	@Override
	public GenericDao<BasWarehouse, Integer> getDao() {
		return dao;
	}

	/**
	 * 统计
	 * 
	 * @param request
	 * @param response
	 */
	public List statistics(HttpServletRequest request, HttpServletResponse response) {
		String sql = "select * from bas_device_type";
		SqlBuffer where = SqlBuffer.begin();
		List<BasDeviceType> deviceTypes = deviceDao.findByNativeCondition(PageControl.getQueryOnlyInstance(),
				where.end(), sql, BasDeviceType.class);
		StringBuffer sqlStr = new StringBuffer(" SELECT a.* FROM bas_warehouse_device a");
		sqlStr.append(" INNER JOIN bas_warehouse b ON(a.wareHouseId=b.id)");
		// sqlStr.append("INNER JOIN sym_addvcd_user c ON(b.addvcd=c.addvcd AND
		// c.userId=105)");
		where.add("deviceTypeId", DataUtil.getParameterInt(request.getParameter("deviceTypeId")));
		where.add("wareHouseId", DataUtil.getParameterInt(request.getParameter("wareHouseId")));
		List<BasWarehouseDevice> devices = deviceDao.findByNativeCondition(PageControl.getQueryOnlyInstance(),
				where.end(), sqlStr.toString(), BasWarehouseDevice.class);
		Iterator<BasWarehouseDevice> it = devices.iterator();
		List list = new ArrayList();
		for (int i = 0; i < deviceTypes.size(); i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			BasDeviceType type = deviceTypes.get(i);
			map.put("name", type.getName());
			List<BasWarehouseDevice> listDevices = new ArrayList<BasWarehouseDevice>();
			for (int j = 0; j < devices.size(); j++) {
				BasWarehouseDevice device = devices.get(j);
				if (device.getDeviceTypeId() == type.getId()) {
					// System.out.println(type.getName() + ":" +
					// device.getDeviceName());
					listDevices.add(device);

				}
			}
			map.put("total", listDevices.size());
			map.put("data", listDevices);
			list.add(map);
			// while (it.hasNext()) {
			// BasWarehouseDevice device = it.next();
			// if (device.getDeviceTypeId() == type.getId()) {
			// System.out.println(type.getName() + ":" +
			// device.getDeviceName());
			// it.remove();
			// }
			// }

		}
		return list;
	}

	/**
	 * 评估
	 * 
	 * @return
	 */
	public List assessment(HttpServletRequest request, HttpServletResponse response) {
		PageControl p = PageControl.getQueryOnlyInstance();
		SqlBuffer where = SqlBuffer.begin();
		StringBuffer sql = new StringBuffer("SELECT b.name,");
		sql.append("SUM(CASE WHEN  state=10 THEN num ELSE 0 END) new, ");
		sql.append("SUM(CASE WHEN  state=20 THEN num ELSE 0 END) good,");
		sql.append("SUM(CASE WHEN  state=30 THEN num ELSE 0 END) bad,");
		sql.append("SUM(CASE WHEN  state=40 THEN num ELSE 0 END)scrap");
		sql.append(" from  bas_warehouse_surplus a");
		sql.append(" INNER JOIN bas_device_type b ON (a.deviceTypeId=b.id) ");
		p.setGroupBy("GROUP BY deviceTypeId");
		where.add("wareHouseId", DataUtil.getParameterInt(request.getParameter("wareHouseId")));
		List list = findByNativeCondition(p, where.end(), sql.toString(), Map.class);
		return list;
	}

	/**
	 * 物品采购分析
	 * 
	 * @return
	 */
	public List analysis(HttpServletRequest request, HttpServletResponse response) {
		PageControl p = PageControl.getQueryOnlyInstance();
		SqlBuffer where = SqlBuffer.begin();
		StringBuffer sql = new StringBuffer("SELECT a.deviceTypeId,b.name,SUM(a.num) num FROM bas_warehouse_surplus a");
		sql.append(" INNER JOIN bas_device_type b ON(a.deviceTypeId=b.id)");
		p.setGroupBy("GROUP BY deviceTypeId");
		where.add("wareHouseId", DataUtil.getParameterInt(request.getParameter("wareHouseId")));
		List list = findByNativeCondition(p, where.end(), sql.toString(), Map.class);
		Map<String, Analysis> map = new HashMap<String, Analysis>();
		for (int i = 0; i < list.size(); i++) {
			Map o = (Map) list.get(i);
			Analysis bean = new Analysis();
			bean.setDeviceTypeId(DataUtil.parseInt(o.get("deviceTypeId").toString(), 0));
			bean.setName(o.get("name").toString());
			bean.setSurplusNum(DataUtil.parseInt(o.get("num").toString(), 0));
			map.put(o.get("name").toString(), bean);
		}

		where.remove();
		sql.delete(0, sql.length());
		sql.append("SELECT b.deviceTypeId,c.name demandName,SUM(b.num) demandNum  ");
		sql.append(" FROM bas_device_order a");
		sql.append(" INNER JOIN bas_device_order_details b ON(a.orderId=b.orderId)");
		sql.append(" INNER JOIN bas_device_type c ON(b.deviceTypeId=c.id)");
		sql.append(" INNER JOIN sym_user d ON(d.id=a.applyUserId)");
		// sql.append(" INNER JOIN sym_addvcd_user e ON(e.addvcd=d.directlyUnder
		// AND e.userId=");
		// sql.append(MDCUtil.getUserId()).append(")");

		where.add("a.examine", 10);
		where.addEndWith("d.directlyUnder", AddvcdService.likeStart(MDCUtil.getAddvcd()));

		p.setGroupBy("GROUP BY b.deviceTypeId");
		list = findByNativeCondition(p, where.end(), sql.toString(), Map.class);
		for (int i = 0; i < list.size(); i++) {
			Map o = (Map) list.get(i);
			String name = o.get("demandName").toString();

			if (map.get(name) != null) {
				Analysis bean = map.get(name);
				bean.setDemandNum(DataUtil.parseInt(o.get("demandNum").toString(), 0));
			} else {
				Analysis bean = new Analysis();
				bean.setDeviceTypeId(DataUtil.parseInt(o.get("deviceTypeId").toString(), 0));
				bean.setName(o.get("demandName").toString());
				bean.setDemandNum(DataUtil.parseInt(o.get("demandNum").toString(), 0));
				map.put(name, bean);
			}

		}
		List<Analysis> AnalysisList = new ArrayList<Analysis>();
		for (String name : map.keySet()) {
			// map.keySet()返回的是所有key的值
			Analysis bean = map.get(name);// 得到每个key多对用value的值
			AnalysisList.add(bean);
		}
		return AnalysisList;
	}

}
