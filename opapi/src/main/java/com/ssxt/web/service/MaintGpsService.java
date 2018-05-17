package com.ssxt.web.service;

import java.nio.channels.ShutdownChannelGroupException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssxt.common.dao.GenericDao;
import com.ssxt.common.page.PageControl;
import com.ssxt.common.page.SqlBuffer;
import com.ssxt.common.service.GenericServiceImpl;
import com.ssxt.common.util.DataUtil;
import com.ssxt.common.util.Distance;
import com.ssxt.common.util.MDCUtil;
import com.ssxt.web.bean.BasMaintGps;
import com.ssxt.web.bean.BasMaintGpsLast;
import com.ssxt.web.bean.BasOwnerUnits;
import com.ssxt.web.bean.BasStinfoB;
import com.ssxt.web.bean.BasTaskInfo;
import com.ssxt.web.bean.SymMenu;
import com.ssxt.web.bean.SysAddvcdB;
import com.ssxt.web.dao.AddvcdDao;
import com.ssxt.web.dao.MaintGpsDao;
import com.ssxt.web.dao.MenuDao;
import com.ssxt.web.vo.MaintDistance;

@Service(value = "maintGpsService")
public class MaintGpsService extends GenericServiceImpl<BasMaintGps, Integer> {

	protected MaintGpsDao dao;

	@Autowired
	private StinfoService stinfoService;

	@Override
	public GenericDao<BasMaintGps, Integer> getDao() {
		return dao;
	}

	@Autowired
	public void setDao(MaintGpsDao dao) {
		this.dao = dao;
	}

	@Autowired
	private MaintGpsLastService maintGpsLastService;

	@Transactional
	public void addGps(BasMaintGps bean) {
		Date time = new Date();
		bean.setCreateTime(time);
		bean.setMaintUserId(MDCUtil.getUserId());
		if (bean.getMaintUserId() == null)
			DataUtil.showMsgException("获取不到当前用户");
		getDao().save(bean);
		BasMaintGpsLast last = new BasMaintGpsLast();
		last.setCreateTime(time);
		last.setMaintUserId(bean.getMaintUserId());
		last.setLatitude(bean.getLatitude());
		last.setLongitude(bean.getLongitude());
		maintGpsLastService.saveOrUpdate(last);
	}

	/**
	 * 该测站区域里面的运维人员
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public PageControl getDistance(HttpServletRequest request, HttpServletResponse response) {

		SqlBuffer where = SqlBuffer.begin();
		where.add("stionId", DataUtil.getParameterInt(request.getParameter("stionId")));
		BasStinfoB stinfo = stinfoService.exist(where).get(0);
		where.remove();

		int addvcd = DataUtil.parseInt(stinfo.getAddvcd(), 10000);
		StringBuffer sql = new StringBuffer("SELECT a.id,a.name,a.addvcd,d.*,");
		sql.append(
				" (SELECT GROUP_CONCAT(taskNo) FROM bas_task_info t WHERE t.maintUserId=a.id AND t.taskStatus>0 AND t.taskStatus<60 )taskNo  ,");
		sql.append(
				" (SELECT state FROM bas_clock clock WHERE clock.userId=a.id  AND  clock.date=DATE_FORMAT(NOW(),'%Y-%m-%d')) state ");
		sql.append(" FROM sym_user a");
		sql.append(" INNER JOIN sym_role b ON(a.roleId=b.Id)");
		// sql.append(" INNER JOIN sym_addvcd_user c ON (c.userId=a.Id)");
		sql.append(" LEFT JOIN bas_maint_gps_last d ON(d.maintUserId=a.Id)");
		where.addEndWith("a.addvcd", addvcd / 100);
		where.add("b.isOperation", 1);
		where.add("a.isEnable", 1);
		PageControl p = PageControl.getQueryOnlyInstance();
		List<?> list = this.findByNativeCondition(p, where.end(), sql.toString(), Map.class);

		double log1 = stinfo.getLongitude();// 经度115.2275611
		double lat1 = stinfo.getLatitude();// 纬度38.9567583

		List<MaintDistance> listDistance = new ArrayList<MaintDistance>();
		for (int i = 0; i < p.getList().size(); i++) {
			Map map = (Map) p.getList().get(i);
			MaintDistance bean = new MaintDistance();
			int state = map.get("state") == null ? 0 : DataUtil.parseInt(map.get("state").toString(), 0);
			bean.setId(DataUtil.parseInt(map.get("id").toString(), 0));
			bean.setName(map.get("name").toString());
			bean.setState(state);
			bean.setTaskNo(map.get("taskNo") == null ? null : map.get("taskNo").toString());
			if (map.get("longitude") != null && stinfo.getLongitude() != null) {
				double log2 = Double.parseDouble(map.get("longitude").toString());
				double lat2 = Double.parseDouble(map.get("latitude").toString());
				double m = Distance.getDistance(lat1, log1, lat2, log2);
				bean.setDistance(m);
				bean.setExist(1);
			} else {
				bean.setExist(0);
			}
			listDistance.add(bean);
		}
		MaintDistance.sortIntMethod(listDistance);
		return p.setList(listDistance);

	}

	/**
	 * 维修里程
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	public double RepairMileage(HttpServletRequest request, HttpServletResponse response) throws ParseException {
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		int repairUserId = DataUtil.parseInt(request.getParameter("repairUserId"), 0);
		String sql = "SELECT  MIN(a.reciveTime) reciveTime,MAX(a.finishTime) finishTime FROM  bas_task_info  a";

		SqlBuffer where = SqlBuffer.begin();
		where.add("finishTime", startTime, ">=");
		where.add("finishTime", endTime, "<=");
		where.add("taskStatus", 60);
		// where.add("maintUserId", repairUserId);
		List list = this.findByNativeCondition(PageControl.getQueryOnlyInstance(), where.end(), sql, Map.class);
		where.remove();
		double mileage = 0;
		if (list.size() > 0) {
			SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Map map = (Map) list.get(0);
			// startTime = map.get("reciveTime").toString();
			// endTime = map.get("finishTime").toString();
			where.add("createTime", map.get("reciveTime"), ">=");
			where.add("createTime", map.get("finishTime"), "<=");
			where.add("maintUserId", 107);

			List<BasMaintGps> listGps = dao.findByCondition(PageControl.getQueryOnlyInstance(), where.end());

			for (int i = 0; i < listGps.size(); i++) {
				if (listGps.size() == 1)
					break;
				int index = i + 1;
				if (index < listGps.size()) {
					BasMaintGps gps1 = listGps.get(i);
					BasMaintGps gps2 = listGps.get(index);
					mileage += Distance.getDistance(gps1.getLatitude(), gps1.getLongitude(), gps2.getLatitude(),
							gps2.getLongitude());
				}

			}
		}
		return mileage;
	}

	/**
	 * 时间段内历史出行公里总数
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	public double RepairMileage2(HttpServletRequest request, HttpServletResponse response) throws ParseException {
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		SqlBuffer where = SqlBuffer.begin();
		int repairUserId = DataUtil.parseInt(request.getParameter("repairUserId"), 0);
		where.add("createTime", startTime, ">=");
		where.add("createTime", endTime, "<=");
		where.add("maintUserId", 107);
		PageControl p = PageControl.getQueryOnlyInstance();
		String sql = "select * from bas_maint_gps";
		List<BasMaintGps> listGps = dao.findByNativeCondition(p, where.end(), sql.toString(), BasMaintGps.class);
		double mileage = 0;
		for (int i = 0; i < listGps.size(); i++) {
			if (listGps.size() == 1)
				break;
			int index = i + 1;
			if (index < listGps.size()) {
				BasMaintGps gps1 = listGps.get(i);
				BasMaintGps gps2 = listGps.get(index);
				mileage += Distance.getDistance(gps1.getLatitude(), gps1.getLongitude(), gps2.getLatitude(),
						gps2.getLongitude());
			}

		}
		return mileage;
	}

	/**
	 * 维修轨迹
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	public PageControl historyRoute(HttpServletRequest request, HttpServletResponse response) throws ParseException {
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		SqlBuffer where = SqlBuffer.begin();
		int repairUserId = DataUtil.parseInt(request.getParameter("repairUserId"), 0);
		where.add("createTime", startTime, ">=");
		where.add("createTime", endTime, "<=");
		where.add("maintUserId", repairUserId);
		PageControl p = PageControl.getQueryOnlyInstance();
		String sql = "select * from bas_maint_gps";
		List<BasMaintGps> listGps = dao.findByNativeCondition(p, where.end(), sql.toString(), BasMaintGps.class);
		p.setList(listGps);
		return p;
	}

}
