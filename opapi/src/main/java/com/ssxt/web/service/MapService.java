package com.ssxt.web.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.ssxt.file.Analysis;
import com.ssxt.web.bean.SymMenu;
import com.ssxt.web.bean.SysAddvcdB;
import com.ssxt.web.dao.AddvcdDao;
import com.ssxt.web.dao.MenuDao;

@Service(value = "mapService")
public class MapService extends GenericServiceImpl<SysAddvcdB, String> {

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
	 * 故障站点统计
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	public Map<String, Object> provinceSituation(HttpServletRequest request, HttpServletResponse response)
			throws ParseException {
		Map<String, Object> map = new HashMap<String, Object>();
		SqlBuffer where = SqlBuffer.begin();
		PageControl p = PageControl.getQueryOnlyInstance();
		StringBuffer errorSql = new StringBuffer();

		errorSql.append("SELECT ");
		errorSql.append(
				"SUM(CASE  WHEN TIMESTAMPDIFF(HOUR, sendTime, NOW())>=24 OR sendTime IS NULL  THEN 1 ELSE 0 END) error,");
		errorSql.append(
				"SUM(CASE  WHEN TIMESTAMPDIFF(HOUR, sendTime, NOW())<24 AND sendTime IS NOT NULL  THEN 1 ELSE 0 END) normal,");
		// errorSql.append(" c.stcdNum");
		errorSql.append(" (SELECT COUNT(1)  FROM bas_stinfo_b  stin WHERE stin.addvcd LIKE  '"
				+ AddvcdService.likeStart(MDCUtil.getAddvcd()) + "%' AND stin.valid=1 ) stcdNum");
		errorSql.append(" FROM bas_stinfo_b  a");
		errorSql.append(" LEFT JOIN faultSite b  ON (a.stcd = b.stcd)");
		errorSql.append(" INNER JOIN sys_addvcd_b d  ON(d.addvcd=a.addvcd)");

		// 权限
		where.addEndWith("a.addvcd", AddvcdService.likeStart(MDCUtil.getAddvcd()));
		where.addEndWith("a.serviceType", request.getParameter("serviceType"));
		// where.add("a.serviceType ", request.getParameter("serviceType"));
		where.add("a.valid", 1);
		// p.setGroupBy("GROUP BY e.parentId");
		List list = this.findByNativeCondition(p, where.end(), errorSql.toString(), Map.class);

		for (int i = 0; i < list.size(); i++) {
			Map m = (Map) list.get(i);
			map.put("error", m.get("error"));
			map.put("normal", m.get("normal"));
			map.put("stcdNum", m.get("stcdNum"));
		}

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

 
		where.remove();
		// 畅通率sql
		String sql = unobstructedSql(request, response);
		list = this.findByNativeCondition(p, where.end(), sql.toString(), Map.class);
		int stcdNum = 0;
		int badTimeTotal = 0;
		int day = DataUtil.daysBetween(request.getParameter("startTime"), request.getParameter("endTime")) + 1;
		for (int i = 0; i < list.size(); i++) {
			Map m = (Map) list.get(i);
			// map.put("unobstructed", m.get("unobstructed"));
			stcdNum += DataUtil.parseInt(m.get("stcdNum").toString(), 0);

			badTimeTotal += DataUtil.parseInt(m.get("badTimeTotal").toString(), 0);
		}
		map.put("unobstructed", (1 - DataUtil.deciMal(badTimeTotal, stcdNum * 24)));
		return map;
	}

	/**
	 * 指定市故障情况
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public PageControl cityRunSituation(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		SqlBuffer where = SqlBuffer.begin();
		PageControl p = PageControl.getQueryOnlyInstance();

		StringBuffer errorSql = new StringBuffer();

		errorSql.append("SELECT ");
		errorSql.append(
				"SUM(CASE  WHEN TIMESTAMPDIFF(HOUR, sendTime, NOW())>=24 OR sendTime IS NULL  THEN 1 ELSE 0 END) error,");
		errorSql.append(
				"SUM(CASE  WHEN TIMESTAMPDIFF(HOUR, sendTime, NOW())<24 AND sendTime IS NOT NULL  THEN 1 ELSE 0 END) normal,");
		errorSql.append(" d.addvnm");
		errorSql.append(" FROM bas_stinfo_b  a");
		errorSql.append(" LEFT JOIN faultSite b  ON (a.stcd = b.stcd and a.serviceType=b.serviceType)");
		errorSql.append(" INNER JOIN sys_addvcd_b d  ON(d.addvcd=a.addvcd)");
		// errorSql.append(" INNER JOIN sys_addvcd_b e
		// ON(e.addvcd=d.parentId)");

		// p.setGroupBy("GROUP BY e.parentId");
		where.add("a.serviceType", DataUtil.getParameterInt(request.getParameter("serviceType")));
		where.add("a.valid ", 1);
		// where.add("d.level ", 3);
		where.addEndWith("a.addvcd", AddvcdService.likeStart(request.getParameter("addvcd")));
		p.setList(this.findByNativeCondition(p, where.end(), errorSql.toString(), Map.class));

		return p;
	}

	/**
	 * 全省任务及人员信息统计
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public Map RepairStatistics(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		SqlBuffer where = SqlBuffer.begin();
		PageControl p = PageControl.getQueryOnlyInstance();

		StringBuffer citySql = new StringBuffer(
				" SELECT a.addvcd,a.addvnm,0 urgent, 0 ordinary,0 handle  FROM  sys_addvcd_b a ");
		// citySql.append(" INNER JOIN sym_addvcd_user z ON(z.addvcd=a.addvcd
		// AND z.userId=" + MDCUtil.getUserId() + ")");
		where.add("a.LEVEL", 2);
		where.addEndWith("a.addvcd", AddvcdService.likeStart(MDCUtil.getAddvcd()));
		List cityList = this.findByNativeCondition(p, where.end(), citySql.toString(), Map.class);
		where.remove();

		StringBuffer repairSql = new StringBuffer("SELECT  0 urgent,");
		repairSql.append(" SUM(CASE  WHEN     a.taskStatus<20 THEN 1 ELSE 0 END) ordinary,");
		repairSql.append(
				" SUM(CASE  WHEN a.taskStatus>=20 AND  a.taskStatus<60   THEN 1 ELSE 0 END) handle ,c.addvnm,c.addvcd");
		repairSql.append(" FROM bas_task_info  a");
		repairSql.append(" INNER JOIN sys_addvcd_b b ON(a.addvcd=b.addvcd)");
		repairSql.append(" INNER JOIN sys_addvcd_b  c ON(c.addvcd=b.parentId)");
		repairSql.append(" INNER JOIN bas_stinfo_b  d ON(a.stionId=d.stionId)");

		p.setGroupBy(" GROUP BY  c.addvcd");
		where.add("d.serviceType", request.getParameter("serviceType"));
		List repairList = this.findByNativeCondition(p, where.end(), repairSql.toString(), Map.class);

		for (int j = 0; j < cityList.size(); j++) {
			Map city = (Map) cityList.get(j);
			for (int i = 0; i < repairList.size(); i++) {
				Map info = (Map) repairList.get(i);
				if (city.get("addvcd").equals(info.get("addvcd"))) {
					city.put("urgent", info.get("urgent"));
					city.put("ordinary", info.get("ordinary"));
					city.put("handle", info.get("handle"));
					break;
				}
			}
		}

		StringBuffer personnelSql = new StringBuffer("");

		personnelSql.append("SELECT info1.addvcd,info1.addvnm,info1.total,");
		personnelSql.append("IFNULL(execution,0) execution,total-IFNULL(execution,0) wait FROM");
		personnelSql.append("(");
		personnelSql.append(" SELECT c.addvcd,c.addvnm ,COUNT(DISTINCT a.Id) total");
		personnelSql.append(" FROM sym_user a");
		personnelSql.append(" INNER JOIN sym_role b ON(a.roleId=b.id)");
		personnelSql.append(" INNER JOIN  sys_addvcd_b c ON(c.addvcd=a.addvcd) ");
		personnelSql.append(" WHERE b.isOperation=1 AND a.isEnable=1");
		personnelSql.append(" GROUP BY a.addvcd");
		personnelSql.append(" )info1");

		personnelSql.append(" LEFT  JOIN ");
		personnelSql.append("(");
		personnelSql.append(" SELECT c.addvcd,c.addvnm,");
		personnelSql.append("COUNT(DISTINCT task.maintUserId) execution FROM bas_task_info task");
		personnelSql.append(" INNER JOIN   sym_user u ON(task.maintUserId=u.id)");
		personnelSql.append(" INNER JOIN  sys_addvcd_b c ON(u.addvcd=c.addvcd) ");
		personnelSql.append(" INNER JOIN sym_role d   ON (u.roleId = d.id)");
		personnelSql.append(" where task.taskStatus < 60 AND d.isOperation=1 AND u.isEnable = 1");
		personnelSql.append(" GROUP BY  c.addvcd");
		personnelSql.append(" )info2 ON(info1.addvcd=info2.addvcd)");
		p.setGroupBy("");
		List personnelList = this.findByNativeCondition(p, null, personnelSql.toString(), Map.class);

		List cityList2 = CtxHelper.copyList(cityList);

		for (int j = 0; j < cityList2.size(); j++) {
			Map city = (Map) cityList2.get(j);

			city.remove("urgent");
			city.remove("ordinary");
			city.remove("handle");
			// city.put("total", 0);
			city.put("execution", 0);
			city.put("wait", 0);
			for (int i = 0; i < personnelList.size(); i++) {
				Map info = (Map) personnelList.get(i);
				if (city.get("addvcd").equals(info.get("addvcd"))) {
					// city.put("total", info.get("total"));
					city.put("execution", info.get("execution"));
					city.put("wait", info.get("wait"));
					break;
				}
			}

		}
		map.put("taskInfo", cityList);
		map.put("personneInfo", cityList2);

		return map;
	}

	/**
	 * 通畅率
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	public PageControl unobstructed(HttpServletRequest request, HttpServletResponse response) throws ParseException {
		PageControl p = PageControl.getQueryOnlyInstance();
		SqlBuffer where = SqlBuffer.begin();
		// StringBuffer sql = new StringBuffer("SELECT c.addvnm,c.parentId,");
		// sql.append(" COUNT(a.stcd) stcdNum,");
		// sql.append(" COUNT(distinct e.stcd) bad,");
		// sql.append(" COUNT(a.stcd)-COUNT(distinct e.stcd) normal,");
		// sql.append(" SUM(e.faultTime) badTimeTotal,");
		// sql.append(" COUNT(a.stcd)*" + day * 24 + " stcdTimeTotal");
		// sql.append(" ,(1-SUM(e.faultTime)/(COUNT(a.stcd)*" + day * 24 + "))
		// unobstructed ");
		// sql.append(" FROM bas_stinfo_b a");
		// sql.append(" INNER JOIN sys_addvcd_b b ON(a.addvcd=b.addvcd) ");
		// sql.append(" INNER JOIN sys_addvcd_b c ON(b.parentId=c.addvcd)");
		// sql.append(" LEFT JOIN bas_statistics_unobstructed e
		// ON(a.stcd=e.stcd");
		//
		// sql.append(" and time>='" + request.getParameter("startTime") + "'");
		// sql.append(" and time<='" + request.getParameter("endTime") + "' )");

		// PageControl p = PageControl.getQueryOnlyInstance();
		// SqlBuffer where = SqlBuffer.begin();

		// 权限开通
		// sql.append(" INNER JOIN sym_addvcd_user f ON(f.addvcd=c.addvcd AND
		// f.userId=").append(MDCUtil.getUserId())
		// .append(")");
		// where.addEndWith("a.addvcd",
		// AddvcdService.likeStart(MDCUtil.getAddvcd()));
		//
		// p.setGroupBy(" GROUP BY c.parentId ");
		// where.add("a.valid", 1);
		// where.add("e.time", request.getParameter("startTime"), ">=");
		// where.add("e.time", request.getParameter("endTime"), "<=");
		String sql = unobstructedSql(request, response);
		List list = this.findByNativeCondition(p, where.end(), sql.toString(), Map.class);

		return p;
	}

	public String unobstructedSql(HttpServletRequest request, HttpServletResponse response) throws ParseException {
		int day = DataUtil.daysBetween(request.getParameter("startTime"), request.getParameter("endTime")) + 1;
		String serviceType = request.getParameter("serviceType");
		StringBuffer sql = new StringBuffer(
				"SELECT a.addvnm,a.parentId,b.stcdNum,a.bad,b.stcdNum-a.bad normal,  IFNULL(a.badTimeTotal,0) badTimeTotal,");
		sql.append(" b.stcdNum * " + day * 24 + " stcdTimeTotal,IFNULL((1- badTimeTotal /  (b.stcdNum * " + day * 24
				+ "))  ,0)unobstructed FROM ");
		sql.append(" (");
		sql.append(" SELECT c.parentId,c.addvnm,");
		sql.append(" COUNT(DISTINCT e.stcd, e.serverType,e.sttp) bad,SUM(e.faultTime) badTimeTotal");
		sql.append(" FROM bas_stinfo_b a");
		sql.append(" INNER JOIN   sys_addvcd_b b ON(a.addvcd=b.addvcd)  ");
		sql.append(" INNER JOIN   sys_addvcd_b c ON(b.parentId=c.addvcd)");
		sql.append(
				" LEFT JOIN bas_statistics_unobstructed e ON(a.stcd = e.stcd AND e.serverType=a.serviceType  AND e.sttp=a.sttp");
		sql.append(" and time>='" + request.getParameter("startTime") + " 00:00:00'");
		sql.append(" and time<='" + request.getParameter("endTime") + " 59:59:59' )");

		sql.append(" WHERE  a.valid = 1 AND a.addvcd LIKE '").append(AddvcdService.likeStart(MDCUtil.getAddvcd()))
				.append("%'");
		if (serviceType != null)
			sql.append(" and serviceType=" + serviceType);
		sql.append(" GROUP BY c.parentId");
		sql.append(" ) a ");
		sql.append(" INNER JOIN addvcdrangenum b  ON(a.parentId=b.addvcd) ");
		return sql.toString();
	}

	/**
	 * 地图及表格故障站点24小时未上报不适合山洪不适用这个接口
	 * 
	 * @param searchtext
	 * @param sort
	 * @param dir
	 * @param start
	 * @param pageSize
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public PageControl faultSite24(PageControl p, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String type = request.getParameter("type");
		SqlBuffer where = SqlBuffer.begin();

		List stinfoList = null;
		// 各个站点信息
		if (type.equals("stinfo")) {

			StringBuffer stinfoSql = new StringBuffer(
					"SELECT  a.stionId,a.longitude,a.latitude,a.serviceType,a.repairId,a.stcd,a.stlc,a.stnm,a.sttp,a.addvcd,a.createTime,");
			stinfoSql.append("  b.observationTime,b.sendTime,b.functionCode,b.insertTime,b.vt,");
			stinfoSql.append("  TIMESTAMPDIFF(HOUR,sendTime,NOW()) difference,");
			stinfoSql.append("  c.taskNo,c.maintUserId,c.name,c.taskStatusName,c.taskStatus,c.nowTime,c.dealTime");
			stinfoSql.append("  from bas_stinfo_b  a");
			stinfoSql.append(" LEFT JOIN faultsite b ON(a.stcd=b.stcd and a.serviceType = b.serviceType)");
			stinfoSql.append(" LEFT  JOIN stcdtaskinfo c ON(c.stionId=a.stionId)");
			where.add("a.valid", 1);
			where.add("a.serviceType", request.getParameter("serviceType"));
			where.addEndWith("a.addvcd", AddvcdService.likeStart(request.getParameter("addvcd")));
			where.addText("(TIMESTAMPDIFF(HOUR, b.sendTime, NOW())>=24 or b.sendTime is null)", "and");

		 
			stinfoList = this.findByNativeCondition(p, where.end(), stinfoSql.toString(), Map.class);
			p.setList(stinfoList);
		}

		where.remove();
		List cityInfoList = null;
		// 各个城市故障站点的数量
		if (type.equals("cityInfo")) {
			// 市
			where.remove();
			StringBuffer citySql = new StringBuffer(" SELECT a.*,0 num  FROM  sys_addvcd_b a ");
			// citySql.append(
			// " INNER JOIN sym_addvcd_user z ON(z.addvcd=a.addvcd AND
			// z.userId=" + MDCUtil.getUserId() + ")");
			where.add("a.LEVEL", 2);
			where.addEndWith("a.addvcd", AddvcdService.likeStart(request.getParameter("addvcd")));
			where.addEndWith("a.addvcd", AddvcdService.likeStart(MDCUtil.getAddvcd()));

			List city = this.findByNativeCondition(p, where.end(), citySql.toString(), Map.class);
			where.remove();
			StringBuffer cityCount = new StringBuffer("select COUNT(a.stcd) num,d.* FROM bas_stinfo_b  a ");
			cityCount.append("LEFT  JOIN   faultsite b ON(a.stcd=b.stcd and a.serviceType = b.serviceType)");
			cityCount.append("INNER JOIN sys_addvcd_b c ON(a.addvcd=c.addvcd)");
			cityCount.append(" INNER JOIN sys_addvcd_b d ON(d.addvcd=c.parentId)");

			// cityCount.append(
			// " INNER JOIN sym_addvcd_user z ON(z.addvcd=a.addvcd AND
			// z.userId=" + MDCUtil.getUserId() + ")");

			where.addText("(TIMESTAMPDIFF(HOUR, b.sendTime, NOW())>=24 or b.sendTime is null)", "and");
			where.add("a.valid", 1);
			where.add("a.serviceType", DataUtil.getParameterInt(request.getParameter("serviceType")));
			where.addEndWith("a.addvcd", AddvcdService.likeStart(request.getParameter("addvcd")));
			where.addEndWith("a.addvcd", AddvcdService.likeStart(MDCUtil.getAddvcd()));

			p.setGroupBy(" 	GROUP BY d.addvcd   ");

			cityInfoList = this.findByNativeCondition(p, where.end(), cityCount.toString(), Map.class);

			if (cityInfoList.size() > 0) {
				for (int i = 0; i < cityInfoList.size(); i++) {
					Map num = (Map) cityInfoList.get(i);
					for (int j = 0; j < city.size(); j++) {
						Map cityInfo = (Map) city.get(j);
						if (cityInfo.get("addvcd").equals(num.get("addvcd")))
							cityInfo.put("num", num.get("num"));
					}

				}
			}

			p.setList(city);
		}

		return p;
	}

	/**
	 * 适用于山洪
	 * 
	 * @param searchtext
	 * @param sort
	 * @param dir
	 * @param start
	 * @param pageSize
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public PageControl ReportCounty24(String searchtext, String sort, String dir, Integer start, Integer pageSize,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map map = new HashMap();
		PageControl p = PageControl.getQueryOnlyInstance();
		SqlBuffer where = SqlBuffer.begin();
		int serviceType = Integer.parseInt(request.getParameter("serviceType"));
		String type = request.getParameter("type");

		if (type.equals("city")) {

			StringBuffer citySql = new StringBuffer(" SELECT a.*,0 num  FROM  sys_addvcd_b a ");
			where.add("a.LEVEL", 2);
			where.addEndWith("a.addvcd", AddvcdService.likeStart(request.getParameter("addvcd")));
			where.addEndWith("a.addvcd", AddvcdService.likeStart(MDCUtil.getAddvcd()));

			List city = this.findByNativeCondition(p, where.end(), citySql.toString(), Map.class);
			where.remove();

			// 统计市下有几个故障县
			// StringBuffer sqlCount = new StringBuffer(
			// "SELECT COUNT(DISTINCT a.addvcd) num,c.parentId FROM bas_stinfo_b
			// a ");
			// sqlCount.append(" LEFT JOIN faultsite b ON(a.stcd=b.stcd)");
			// sqlCount.append(" INNER JOIN sys_addvcd_b c
			// ON(a.addvcd=c.addvcd)");
			//
			// where.add("a.serviceType", serviceType);
			// where.addText("(TIMESTAMPDIFF(HOUR, b.sendTime, NOW())>=24 or
			// b.sendTime is null)", "and");
			// where.add("a.valid", 1);
			// where.add("LEVEL", 3);

			StringBuffer sqlCount = new StringBuffer("SELECT d.addvcd, COUNT(DISTINCT a.id)num FROM bas_server a ");
			sqlCount.append(" INNER JOIN  bas_server_situation b ON(a.id=b.serverId) ");
			sqlCount.append(" INNER JOIN sys_addvcd_b c ON(a.addvcd=c.addvcd) ");
			sqlCount.append(" INNER JOIN sys_addvcd_b d ON(c.parentId=d.addvcd) ");

			where.addEndWith("b.state", 0);
			where.addEndWith("d.addvcd", AddvcdService.likeStart(request.getParameter("addvcd")));
			where.addEndWith("d.addvcd", AddvcdService.likeStart(MDCUtil.getAddvcd()));

			p.setGroupBy(" GROUP BY d.addvcd ");

			List num = this.findByNativeCondition(p, where.end(), sqlCount.toString(), Map.class);

			if (num.size() > 0) {
				for (int i = 0; i < num.size(); i++) {
					Map count = (Map) num.get(i);
					for (int j = 0; j < city.size(); j++) {
						Map county = (Map) city.get(j);
						if (count.get("addvcd").equals(county.get("addvcd")))
							county.put("num", count.get("num"));
					}

				}
			}
			where.remove();
			p.setList(city);
		}

		// 各县故障站点数目
		if (type.equals("countyfault")) {

			// StringBuffer dataSql = new StringBuffer("select c.*, COUNT(1) num
			// FROM bas_stinfo_b a ");
			// dataSql.append(" LEFT JOIN faultsite b ON(a.stcd=b.stcd)");
			// dataSql.append(" INNER JOIN sys_addvcd_b c ON(a.addvcd=c.addvcd)
			// ");
			// dataSql.append("INNER JOIN sys_addvcd_b d ON(d.addvcd=c.parentId)
			// ");
			// where.add("a.valid", 1);
			// where.add("a.serviceType", serviceType);
			// where.addText("(TIMESTAMPDIFF(HOUR, b.sendTime, NOW())>=24 or
			// b.sendTime is null)", "and");
			//
			// where.add("c.level", 3);
			// where.addEndWith("a.addvcd",
			// AddvcdService.likeStart(request.getParameter("addvcd")));
			// where.addEndWith("a.addvcd",
			// AddvcdService.likeStart(MDCUtil.getAddvcd()));
			//
			// p.setGroupBy(" GROUP BY a.addvcd ");

			StringBuffer dataSql = new StringBuffer("  SELECT  a.*,c.* FROM bas_server a  ");
			dataSql.append("   INNER JOIN (");
			dataSql.append("   SELECT MAX(id) id ,b.serverId FROM bas_server_situation b");
			dataSql.append("   WHERE state=0");
			dataSql.append("   GROUP BY serverId");
			dataSql.append("   )b  ON(a.id=b.serverId)");
			dataSql.append("   INNER JOIN sys_addvcd_b c ON(a.addvcd=c.addvcd)");

			where.addEndWith("a.addvcd", AddvcdService.likeStart(request.getParameter("addvcd")));
			where.addEndWith("a.addvcd", AddvcdService.likeStart(MDCUtil.getAddvcd()));
			List list = this.findByNativeCondition(p, where.end(), dataSql.toString(), Map.class);

			p.setList(list);
			where.remove();
		}

		// 故障站点
		// if (type.equals("faultSiteInfo")) {
		// StringBuffer dataSql = new StringBuffer("select a.*,c.addvfull FROM
		// bas_stinfo_b a ");
		// dataSql.append(" LEFT JOIN faultsite b ON(a.stcd=b.stcd)");
		// dataSql.append(" INNER JOIN sys_addvcd_b c ON(a.addvcd=c.addvcd) ");
		// dataSql.append(
		// " INNER JOIN sym_addvcd_user z ON(z.addvcd=a.addvcd AND z.userId=" +
		// MDCUtil.getUserId() + ")");
		//
		// where.add("a.valid", 1);
		// where.add("a.serviceType", serviceType);
		// where.addText("(TIMESTAMPDIFF(HOUR, b.sendTime, NOW())>=24 or
		// b.sendTime is null)", "and");
		//
		// where.add("LEVEL", 3);
		// where.addEndWith("z.addvcd",
		// AddvcdService.likeStart(request.getParameter("addvcd")));
		//
		// List list = this.findByNativeCondition(p, where.end(),
		// dataSql.toString(), Map.class);
		//
		// p.setList(list);
		// where.remove();
		//
		// }

		return p;
	}

	public Map<String, Object> stationState(HttpServletRequest request, HttpServletResponse response) {
		String stcd = request.getParameter("stcd");
		String serviceType = request.getParameter("serviceType");
		SqlBuffer where = SqlBuffer.begin();
		where.add("stcd", stcd);
		String sql = "select * from {table}";
		String fileName = "";
		if (serviceType.equals("1")) {
			// 地下水
			fileName = "GwWaterInfo";
			sql = sql.replaceAll("\\{table\\}", "gw_water_info");
		}
		if (serviceType.equals("2")) {
			// 山洪
			fileName = "GwTorrentialfloodInfo";
			sql = sql.replaceAll("\\{table\\}", "gw_torrentialflood_info");
		}
		if (serviceType.equals("3")) {
			// 中小河流站
			fileName = "GwStreamInfo";
			sql = sql.replaceAll("\\{table\\}", "gw_stream_info");
		}
		if (serviceType.equals("4")) {
			// 雨量基本站
			fileName = "GwRainfallInfo";
			sql = sql.replaceAll("\\{table\\}", "gw_rainfall_info");

		}

		List<?> dataList = this.findByNativeCondition(PageControl.getQueryOnlyInstance(), where.end(), sql, Map.class);
		Map<String, Object> txtMap = null;
		// String file =
		// request.getSession().getServletContext().getRealPath("WEB-INF/classes");
		if (dataList.size() > 0) {
			// file += "/com/ssxt/file/GwTorrentialfloodInfo.hbm.xml";
			Map<String, Object> map = (Map) dataList.get(0);
			// txtMap = Analysis.rename(file, map);
			map.put("WTH", Analysis.setWTH(map.get("WTH")));
			map.put("FLWCHRCD", Analysis.setFLWCHRCD(map.get("FLWCHRCD")));
			map.put("WPTN", Analysis.setWPTN(map.get("WPTN")));
			txtMap = Analysis.rename2(request, fileName, map);
		}
		return txtMap;
	}

}
