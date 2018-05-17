package com.ssxt.web.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
import com.ssxt.common.util.CtxHelper;
import com.ssxt.common.util.DataUtil;
import com.ssxt.common.util.MDCUtil;
import com.ssxt.web.bean.SymMenu;
import com.ssxt.web.bean.SymUser;
import com.ssxt.web.bean.SysAddvcdB;
import com.ssxt.web.controller.StatisticsController;
import com.ssxt.web.dao.AddvcdDao;
import com.ssxt.web.dao.MenuDao;

@Service(value = "statisticsService")
public class StatisticsService extends GenericServiceImpl<SysAddvcdB, String> {
	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(StatisticsService.class);

	protected AddvcdDao dao;

	@Override
	public GenericDao<SysAddvcdB, String> getDao() {
		return dao;
	}

	@Autowired
	public void setDao(AddvcdDao dao) {
		this.dao = dao;
	}

	/**
	 * 运维人员信息打卡总数工作休假等信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public Map getMaintenanceInfo(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> info = new HashMap<String, Object>();
		SqlBuffer where = SqlBuffer.begin();
		// 当天打卡请假和总人数
		StringBuffer sql = new StringBuffer("SELECT  COUNT(1) total,");
		sql.append(" SUM(CASE  WHEN toWork  IS NOT NULL    THEN 1 ELSE  0 END) clockNum, ");
		sql.append(" SUM(CASE  WHEN state=1  THEN 1 ELSE  0 END) vacationNum ");
		sql.append(" FROM sym_user a");
		sql.append(" INNER JOIN  sym_role  b ON(a.roleId=b.id)");
		sql.append(" left JOIN  bas_clock c ON(a.id=c.userId AND c.date=DATE_FORMAT(NOW(),'%Y-%m-%d'))");
		// 权限开通
		// sql.append("INNER JOIN sym_addvcd_user d ON(a.directlyUnder=d.addvcd
		// AND d.userId=").append(MDCUtil.getUserId())
		// .append(")");
		where.addEndWith("a.directlyUnder", AddvcdService.likeStart(MDCUtil.getAddvcd()));
		where.add("b.isOperation", 1);
		where.add("a.isEnable", 1);
		Map map = (Map) this
				.findByNativeCondition(PageControl.getQueryOnlyInstance(), where.end(), sql.toString(), Map.class)
				.get(0);
		info.put("total", map.get("total") == null ? "0" : map.get("total").toString());
		info.put("clockNum", map.get("clockNum") == null ? "0" : map.get("clockNum").toString());
		info.put("vacationNum", map.get("vacationNum") == null ? "0" : map.get("vacationNum").toString());

		// 正在工作人数
		sql.delete(0, sql.length());
		sql.append("SELECT COUNT(DISTINCT a.id) workNum FROM sym_user a ");
		sql.append(" INNER JOIN  sym_role  b ON(a.roleId=b.id) ");
		sql.append(" LEFT JOIN bas_task_info  c ON(a.id=c.maintUserId) ");

		where.remove();
		// 权限
		where.addEndWith("a.directlyUnder", AddvcdService.likeStart(MDCUtil.getAddvcd()));
		where.add("b.isOperation", 1);
		where.add("a.isEnable", 1);
		where.add("c.taskStatus", 60, "!=");
		map = (Map) this
				.findByNativeCondition(PageControl.getQueryOnlyInstance(), where.end(), sql.toString(), Map.class)
				.get(0);
		info.put("workNum", map.get("workNum") == null ? "0" : map.get("workNum").toString());
		info.put("freeNum", DataUtil.parseInt(info.get("total").toString(), 0)
				- DataUtil.parseInt(info.get("workNum").toString(), 0));

		 

		return info;
	}

	/**
	 * 饼图业务
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public Map faultSite(HttpServletRequest request, HttpServletResponse response) {
		SqlBuffer where = SqlBuffer.begin();
		String addvcd = request.getParameter("addvcd");
		PageControl p = PageControl.getQueryOnlyInstance();

		StringBuffer sql2 = new StringBuffer("SELECT c.argName name, COUNT(1) num");
		sql2.append(" FROM bas_stinfo_b  a");
		sql2.append(" LEFT JOIN faultSite b  ON (a.stcd = b.stcd)");
		sql2.append(" INNER JOIN  bas_param c ON(a.serviceType=c.argValue and paramType='com-ssxt-op-serviceType')");
//		sql2.append(" INNER JOIN sym_addvcd_user z ON(z.addvcd=a.addvcd AND z.userId=" + MDCUtil.getUserId() + ")");

		where.addText("(TIMESTAMPDIFF(HOUR, sendTime, NOW())>=24 or sendTime is null)", "and");
		where.add("a.valid", 1);
//		where.addEndWith("z.addvcd", AddvcdService.likeStart(addvcd));
		where.addEndWith("a.addvcd", AddvcdService.likeStart(addvcd));
		where.addEndWith("a.addvcd", AddvcdService.likeStart(MDCUtil.getAddvcd()));
 		p.setGroupBy(" GROUP BY a.serviceType ");
		List pie = findByNativeCondition(p, where.end(), sql2.toString(), Map.class);

		Map map = new HashMap();

		// map.put("pie", list);
		map.put("info", pie);

		return map;
	}

	/**
	 * 全省故障站趋势分析
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public PageControl provinceFault(HttpServletRequest request, HttpServletResponse response) {
		PageControl p = PageControl.getQueryOnlyInstance();
		SqlBuffer where = SqlBuffer.begin();

		StringBuffer sql = new StringBuffer("SELECT FLOOR(COUNT(1)/days) num,date  FROM bas_statistics_fault a ");
		sql.append(" INNER JOIN bas_stinfo_b  b ON(a.stcd=b.stcd)");
		where.add("b.valid", 1);
		where.add("a.date", request.getParameter("startTime"), ">=");
		where.add("a.date", request.getParameter("endTime"), "<=");
		// 权限
		where.addEndWith("b.addvcd", AddvcdService.likeStart(MDCUtil.getAddvcd()));
		p.setGroupBy("GROUP BY  DATE ");
		p.setList(this.findByNativeCondition(p, where.end(), sql.toString(), Map.class));
		return p;
	}

	/**
	 * 运维效率评估
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public PageControl efficiency(HttpServletRequest request, HttpServletResponse response) {
		PageControl p = PageControl.getQueryOnlyInstance();
		SqlBuffer where = SqlBuffer.begin();
		StringBuffer sql = new StringBuffer("SELECT d.addvnm,d.addvcd,");
		sql.append(
				" SUM(CASE WHEN a.taskStatus=60 THEN  ROUND(( UNIX_TIMESTAMP(finishTime)-UNIX_TIMESTAMP(reciveTime))/60)   END) dealTime,");
		sql.append(" SUM(CASE WHEN a.taskStatus=60 THEN 1 END) dealNum,");
		sql.append(
				" SUM(CASE WHEN a.taskStatus>=20 THEN  ROUND((UNIX_TIMESTAMP(reciveTime)-UNIX_TIMESTAMP(a.createTime))/60)   END) responseTime,");

		sql.append(" SUM(CASE WHEN a.taskStatus>=20 THEN 1 END) responseNum,");
		sql.append(" (SELECT COUNT(1) FROM bas_task_info t WHERE t.addvcd=a.addvcd and t.taskStatus=60) cityDealNum,");
		sql.append(" (SELECT COUNT(1) FROM bas_stinfo_b t WHERE t.addvcd=d.addvcd and t.valid=1) cityNum");
		sql.append(" FROM bas_task_info a  ");
		sql.append(" INNER JOIN bas_stinfo_b  b ON(b.stionId=a.stionId)");
		sql.append(" INNER JOIN sys_addvcd_b  c ON(c.addvcd=b.addvcd)");
		sql.append(" INNER JOIN sys_addvcd_b  d ON(c.parentId=d.addvcd)");

		// #返修次数达2次以上
		// SELECT COUNT(1),info.addvcd FROM (
		// SELECT stcd,COUNT(1) num,c.addvcd FROM bas_task_info a
		// INNER JOIN sys_addvcd_b b ON(a.addvcd=b.addvcd)
		// INNER JOIN sys_addvcd_b c ON(c.addvcd=b.parentId)
		// GROUP BY c.addvcd,stcd HAVING num>=2
		// ) info GROUP BY addvcd

		// 权限开通
		// sql.append(" INNER JOIN sym_addvcd_user e ON(e.addvcd=d.addvcd AND
		// e.userId=").append(MDCUtil.getUserId())
		// .append(")");
		where.addEndWith("a.addvcd", AddvcdService.likeStart(MDCUtil.getAddvcd()));
		where.add("b.valid", 1);
		p.setGroupBy("GROUP BY d.addvcd");

		p.setList(this.findByNativeCondition(p, where.end(), sql.toString(), Map.class));
		return p;
	}

	/**
	 * 测站报修趋势
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	// public Map repairTrend(HttpServletRequest request, HttpServletResponse
	// response) {
	//
	// SqlBuffer where = SqlBuffer.begin();
	// PageControl p = PageControl.getQueryOnlyInstance();
	//
	// StringBuffer dateSql = new StringBuffer(
	// "SELECT DATE_FORMAT(a.createTime, '%Y-%m-%d') tm FROM bas_task_info a");
	// dateSql.append(" INNER JOIN bas_stinfo_b b ON(a.stcd=b.stcd)");
	// dateSql.append(" INNER JOIN sym_addvcd_user z ON(z.addvcd=a.addvcd AND
	// z.userId=" + MDCUtil.getUserId() + ")");
	//
	// where.addEndWith("z.addvcd",
	// AddvcdService.likeStart(request.getParameter("addvcd")));
	// where.add("b.valid", 1);
	//
	// p.setGroupBy(" GROUP BY tm ORDER BY tm");
	// // 查处日期
	// List dateList = findByNativeCondition(p, where.end(), dateSql.toString(),
	// Map.class);
	//
	// List datalist = new ArrayList();// 最终数据
	// Map dataMap = new HashMap();
	//
	// for (int i = 1; i < 5; i++) {
	// where.remove();
	// StringBuffer sql = new StringBuffer(
	// "SELECT COUNT(1) num,c.argName, DATE_FORMAT(a.createTime, '%Y-%m-%d') tm
	// FROM bas_task_info a ");
	// sql.append("INNER JOIN bas_stinfo_b b ON(a.stcd=b.stcd)");
	// sql.append(" INNER JOIN bas_param c ON(b.STTP=c.argValue)");
	// sql.append(" INNER JOIN sym_addvcd_user z ON(z.addvcd=a.addvcd AND
	// z.userId=" + MDCUtil.getUserId() + ")");
	//
	// where.add("b.valid", 1);
	// where.add("b.serviceType", i);
	// where.addEndWith("z.addvcd",
	// AddvcdService.likeStart(request.getParameter("addvcd")));
	//
	// p.setGroupBy(" GROUP BY tm");
	// log.info(sql.toString());
	// List list = findByNativeCondition(p, where.end(), sql.toString(),
	// Map.class);
	// List listData = new ArrayList();
	// Map MapData = new HashMap();
	// for (int j = 0; j < dateList.size(); j++) {
	// Map isTm = (Map) dateList.get(j);
	// listData.add(getNum(list, isTm.get("tm").toString()));
	//
	// }
	// if (i == 1) {
	// MapData.put("name", "地下水");
	// MapData.put("data", listData);
	//
	// }
	// if (i == 2) {
	// MapData.put("name", "山洪");
	// MapData.put("data", listData);
	// }
	// if (i == 3) {
	//
	// MapData.put("name", "中小河流站");
	// MapData.put("data", listData);
	// }
	// if (i == 4) {
	//
	// MapData.put("name", "雨量基本站");
	// MapData.put("data", listData);
	// }
	//
	// datalist.add(MapData);
	// }
	// dataMap.put("data", datalist);
	// dataMap.put("date", dateList);
	// return dataMap;
	//
	// }
	//
	// public int getNum(List list, String tm2) {
	// int num = 0;
	// for (int i = 0; i < list.size(); i++) {
	// Map map = (Map) list.get(i);
	// String tm = map.get("tm").toString();
	// if (tm.equals(tm2)) {
	// num = DataUtil.parseInteger(map.get("num").toString(), 0);
	// break;
	// }
	// }
	// return num;
	// }

}
