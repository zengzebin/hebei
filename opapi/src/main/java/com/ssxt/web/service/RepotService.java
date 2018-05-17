package com.ssxt.web.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
import com.ssxt.web.bean.SymAddvcdUser;
import com.ssxt.web.bean.SymMenu;
import com.ssxt.web.bean.SysAddvcdB;
import com.ssxt.web.dao.AddvcdDao;
import com.ssxt.web.dao.MenuDao;
import com.ssxt.web.vo.Assessment;

@Service(value = "repotService")
public class RepotService extends GenericServiceImpl<SysAddvcdB, String> {

	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(RepotService.class);

	@Autowired
	protected AddvcdDao dao;

	@Override
	public GenericDao<SysAddvcdB, String> getDao() {
		return dao;
	}

	/**
	 * 各市故障站点分析
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public PageControl cityError(HttpServletRequest request, HttpServletResponse response) {
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		SqlBuffer where = SqlBuffer.begin();
		PageControl p = PageControl.getQueryOnlyInstance();

		StringBuffer citySql = new StringBuffer(" SELECT a.addvcd,a.addvnm,0 normal, 0 bad  FROM  sys_addvcd_b a ");
		where.add("a.LEVEL", 2);
		where.addEndWith("a.addvcd", AddvcdService.likeStart(MDCUtil.getAddvcd()));
		where.remove();
		List listcity = this.findByNativeCondition(p, where.end(), citySql.toString(), Map.class);

		StringBuffer errorSql = new StringBuffer();

		errorSql.append("SELECT ");
		// errorSql.append(
		// "SUM(CASE WHEN TIMESTAMPDIFF(HOUR, sendTime, NOW())>=24 OR sendTime
		// IS NULL THEN 1 ELSE 0 END) bad,");
		// errorSql.append(
		// "SUM(CASE WHEN TIMESTAMPDIFF(HOUR, sendTime, NOW())<24 AND sendTime
		// IS NOT NULL THEN 1 ELSE 0 END) normal,");
		errorSql.append("	COUNT(DISTINCT b.stcd,b.serverType,b.sttp)  bad,");
		errorSql.append("   f.stcdNum-COUNT(DISTINCT b.stcd,b.serverType,b.sttp)  normal,");
		errorSql.append(" e.addvnm");
		errorSql.append(" FROM bas_stinfo_b  a");
		// errorSql.append(" LEFT JOIN faultSite b ON (a.stcd = b.stcd)");
		errorSql.append(
				" left JOIN bas_statistics_unobstructed b  ON (a.stcd = b.stcd AND a.sttp=b.sttp AND a.serviceType=b.serverType ");
		if (startTime != null) {
			errorSql.append("and b.time>='").append(startTime).append(" 00:00:00' and  b.time<='").append(endTime)
					.append(" 23:59:59'");
		}
		errorSql.append(")");
		errorSql.append(" INNER JOIN sys_addvcd_b d  ON(d.addvcd=a.addvcd)");
		errorSql.append(" INNER JOIN sys_addvcd_b e  ON(e.addvcd=d.parentId)");
		errorSql.append(" INNER JOIN addvcdrangenum f ON(f.addvcd=e.addvcd)");

		where.add("a.valid", 1);
		where.addEndWith("a.addvcd", AddvcdService.likeStart(MDCUtil.getAddvcd()));

		p.setGroupBy("GROUP BY e.parentId");
		p.setList(this.findByNativeCondition(p, where.end(), errorSql.toString(), Map.class));

		return p;
	}

	/**
	 * 各市维修任务及维修人数分析表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public PageControl analysis(HttpServletRequest request, HttpServletResponse response) {
		SqlBuffer where = SqlBuffer.begin();
		PageControl p = PageControl.getQueryOnlyInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd 00:00:00");// 设置日期格式
		Date time = new Date();

		// 权限区域
		StringBuffer citySql = new StringBuffer(
				" SELECT a.addvcd,a.addvnm,0 taskNum,0 normal, 0 bad,0 maintNum  FROM  sys_addvcd_b a ");
		where.add("a.LEVEL", 2);
		where.addEndWith("a.addvcd", AddvcdService.likeStart(MDCUtil.getAddvcd()));

		List listcity = this.findByNativeCondition(p, where.end(), citySql.toString(), Map.class);
		where.remove();
		// 今天任务单数量故障及正常数量
		StringBuffer taskSql = new StringBuffer("SELECT c.addvnm,c.addvcd,COUNT(a.taskNo) taskNum,");
		taskSql.append(
				" SUM(CASE  WHEN TIMESTAMPDIFF(HOUR, sendTime, NOW())>=24 OR sendTime IS NULL  THEN 1 ELSE 0 END) bad,");
		taskSql.append(
				" SUM(CASE  WHEN TIMESTAMPDIFF(HOUR, sendTime, NOW())<24 AND sendTime IS NOT NULL  THEN 1 ELSE 0 END) normal");
		taskSql.append(" FROM bas_task_info a");
		taskSql.append(" INNER JOIN bas_stinfo_b  f ON(f.stionId=a.stionId)");
		taskSql.append(" INNER JOIN sys_addvcd_b  b ON(a.addvcd=b.addvcd)");
		
		taskSql.append(" INNER JOIN sys_addvcd_b c ON(b.parentId=c.addvcd)");
		taskSql.append(" LEFT JOIN  faultsite d ON(f.stcd=d.stcd and f.serviceType=d.serviceType)");
		p.setGroupBy("GROUP BY c.addvcd");
		where.add("a.createTime", df.format(time), ">=");
		List taskList = this.findByNativeCondition(p, where.end(), taskSql.toString(), Map.class);

		where.remove();

		// 今天维修人员数量
		StringBuffer dealSql = new StringBuffer(" SELECT c.addvcd,IFNULL(COUNT(distinct a.maintUserId),0)maintNum");
		dealSql.append(" FROM bas_task_info a");
		dealSql.append(" INNER JOIN sys_addvcd_b  b ON(a.addvcd=b.addvcd)");
		dealSql.append(" INNER JOIN sys_addvcd_b c ON(b.parentId=c.addvcd)");
		p.setGroupBy("GROUP BY c.addvcd");
		where.add("a.reciveTime", df.format(time), ">=");
		List dealList = this.findByNativeCondition(p, where.end(), dealSql.toString(), Map.class);

		// 故障正常任务单数量
		for (int i = 0; i < listcity.size(); i++) {
			Map map = (Map) listcity.get(i);
			for (int j = 0; j < taskList.size(); j++) {
				Map info = (Map) taskList.get(j);
				if (map.get("addvcd").equals(info.get("addvcd"))) {
					map.put("taskNum", info.get("taskNum"));
					map.put("bad", info.get("bad"));
					map.put("normal", info.get("normal"));

				}
			}

		}

		// 维修人数
		for (int i = 0; i < listcity.size(); i++) {
			Map map = (Map) listcity.get(i);
			for (int j = 0; j < dealList.size(); j++) {
				Map info = (Map) dealList.get(j);
				if (map.get("addvcd").equals(info.get("addvcd"))) {
					map.put("maintNum", info.get("maintNum"));

				}
			}

		}
		p.setList(listcity);
		return p;
	}

	/**
	 * 各市维修人员考勤表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public PageControl Assessment(HttpServletRequest request, HttpServletResponse response) {
		SqlBuffer where = SqlBuffer.begin();
		PageControl p = PageControl.getQueryOnlyInstance();
		String startTime = request.getParameter("startTime") + " 00:00:00";
		String endTime = request.getParameter("endTime") + " 23:59:59";
		;

		// vacation: 请假天数,wordDays: 应上班天数，toWord: 签到天数,
		StringBuffer sql = new StringBuffer("SELECT a.userId,c.name,c.position,f.addvcd,f.addvnm,");
		// 请假次数
		sql.append("SUM(CASE WHEN  a.state=1 AND b.workmk=1 THEN 1 ELSE 0 END) vacation,");
		// 签到次数
		sql.append("SUM(CASE WHEN  towork IS NOT NULL AND a.state!=1 THEN 1 ELSE 0 END) toWord,");
		// 应上班天数
		sql.append("SUM(CASE  WHEN   workmk=1 THEN 1 ELSE 0 END)  wordDays,");
		// sql.append("d.stcdNum stcdNum");
		// 管辖站总数
		sql.append(
				" (SELECT COUNT(1)  FROM bas_stinfo_b  stin WHERE stin.addvcd LIKE  CONCAT(LEFT(c.addvcd,4),'%') AND stin.valid=1 ) stcdNum");
		sql.append(" FROM bas_clock a ");
		sql.append(" INNER JOIN bas_work_date b ON(a.date=b.date)");
		sql.append(" INNER JOIN  sym_user c ON(a.userId=c.id)");
		// sql.append(" LEFT JOIN rangenum d ON(a.userId=d.id)");
		sql.append(" LEFT  JOIN  sym_role e ON(c.roleId=e.id)");
		sql.append(" INNER  JOIN  sys_addvcd_b f ON(f.addvcd=c.addvcd)");
		p.setGroupBy("GROUP BY a.userId");
		where.add("b.date", startTime, ">=");
		where.add("b.date", endTime, "<=");
		// 权限
		where.addEndWith("c.directlyUnder", AddvcdService.likeStart(MDCUtil.getAddvcd()));
		where.addEndWith("c.addvcd", request.getParameter("addvcd"));
		where.add("c.isEnable", 1);
		where.add("e.isOperation", 1);
		where.add("a.userId", DataUtil.getParameterInt(request.getParameter("userId")));
		List list = this.findByNativeCondition(p, where.end(), sql.toString(), Map.class);
		List<Assessment> assessmentList = new ArrayList<Assessment>();
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);
			Assessment bean = new Assessment();
			bean.setName(map.get("name").toString());
			bean.setUserId(DataUtil.parseInt(map.get("userId").toString(), 0));
			bean.setVacation(DataUtil.parseInt(map.get("vacation").toString(), 0));
			bean.setToWord(DataUtil.parseInt(map.get("toWord").toString(), 0));
			bean.setWordDays(DataUtil.parseInt(map.get("wordDays").toString(), 1));
			bean.setStcdNum(DataUtil.parseInt(map.get("stcdNum").toString(), 0));
			bean.setAddvcd(map.get("addvcd").toString());
			bean.setAddvnm(map.get("addvnm").toString());
			bean.setPosition(map.get("position") == null ? "" : map.get("position").toString());

			assessmentList.add(bean);

		}

		// 维修数量 时间范围
		where.remove();
		sql.delete(0, sql.length());
		sql.append("SELECT b.id,b.name,");
		// 维修数量
		sql.append("SUM(CASE WHEN a.taskStatus=60 THEN   1   ELSE 0 END)dealNum,");
		// 维修时长工作时长
		sql.append(
				"SUM(CASE WHEN a.taskStatus=60 THEN  TIMESTAMPDIFF(HOUR,dealTime,finishTime) ELSE 0 END )  dealTime");

		// sql.append(
		// "SUM(CASE WHEN a.taskStatus=60 THEN ROUND((
		// UNIX_TIMESTAMP(finishTime)-UNIX_TIMESTAMP(reciveTime))/60)ELSE 0
		// END)dealTime");
		sql.append(" FROM bas_task_info a ");
		sql.append(" INNER JOIN  sym_user b ON(a.maintUserId=b.Id)");
		p.setGroupBy("GROUP BY  b.id");
		where.addEndWith("a.addvcd", AddvcdService.likeStart(MDCUtil.getAddvcd()));// 权限
		where.addEndWith("b.addvcd", request.getParameter("addvcd"));// 地区筛选
		where.add("a.createTime", startTime, ">=");
		where.add("a.createTime", endTime, "<=");
		where.add("b.id", DataUtil.getParameterInt(request.getParameter("userId")));

		list = this.findByNativeCondition(p, where.end(), sql.toString(), Map.class);
		for (int i = 0; i < assessmentList.size(); i++) {
			Assessment bean = assessmentList.get(i);
			for (int j = 0; j < list.size(); j++) {
				Map map = (Map) list.get(j);
				if (bean.getUserId() == DataUtil.parseInt(map.get("id").toString(), 0)) {
					bean.setDealNum(DataUtil.parseInt(map.get("dealNum").toString(), 0));
					bean.setWordTime(DataUtil.parseInt(map.get("dealTime").toString(), 0));
				}
			}
		}

		// 维修超过2次的站点个数
		where.remove();
		sql.delete(0, sql.length());
		// sql.append(" SELECT info.id,COUNT(info.id) num FROM (");
		// sql.append(" SELECT b.id,b.name,stcd,COUNT(a.taskNo)num FROM
		// bas_task_info a");
		// sql.append(" INNER JOIN sym_user b ON(a.maintUserId=b.id)");
		// sql.append(" where a.createTime>='").append(startTime).append("'");
		// sql.append(" and a.createTime<='").append(endTime).append("'");
		// sql.append(" GROUP BY b.id,stcd");
		// sql.append(" HAVING num>=2");
		// sql.append(") info ");
		// p.setGroupBy(" GROUP BY info.id");
		sql.append(" SELECT COUNT(DISTINCT a.stionId) num,b.maintUserId id FROM  bas_task_info a");
		sql.append(" INNER JOIN (");
		sql.append(" SELECT MIN(createTime) createTime,stionId,maintUserId  FROM bas_task_info ");
		sql.append(" where createTime>='").append(startTime).append("'");
		sql.append(" and createTime<='").append(endTime).append("'");
		sql.append(" GROUP BY stionId,maintUserId");
		sql.append(" ) b ON(a.stionId=b.stionId AND a.createTime>b.createTime)");
		p.setGroupBy(" GROUP BY b.maintUserId");
		list = this.findByNativeCondition(p, where.end(), sql.toString(), Map.class);

		for (int i = 0; i < assessmentList.size(); i++) {
			Assessment bean = assessmentList.get(i);
			for (int j = 0; j < list.size(); j++) {
				Map map = (Map) list.get(j);
				if (bean.getUserId() == DataUtil.parseInt(map.get("id").toString(), 0)) {
					bean.setRepeat(DataUtil.parseInt(map.get("num").toString(), 0));
				}
			}
		}

		DecimalFormat df = new DecimalFormat("0.00");// 格式化小数

		for (int i = 0; i < assessmentList.size(); i++) {
			// 出勤率
			Assessment a = assessmentList.get(i);
			a.setAttendance(DataUtil.deciMal(a.getToWord(), a.getWordDays() == 0 ? 1 : a.getWordDays()));
			a.setMaintenanceRate(DataUtil.deciMal(a.getStcdNum() - a.getRepeat(), a.getStcdNum()));
			// 排名 维修数量*维修质量（维修率）*出勤率-请假次数
			double score = a.getDealNum() * a.getMaintenanceRate() * a.getAttendance() - a.getVacation();
			a.setScore(score);
			// Random random = new Random();
			// int s = random.nextInt(10) % (10 - 1 + 1) + 1;
			// a.setScore(s);
		}

		p.setList(assessmentList);
		Assessment.sortIntMethod(assessmentList);
		int ranking = 1;

		for (int i = 0; i < assessmentList.size(); i++) {
			Assessment assessment = assessmentList.get(i);
			if (i == 0)
				// 默认第一个第一名
				assessment.setRanking(ranking);
			if (i != 0) {
				if (assessment.getScore() == assessmentList.get(i - 1).getScore()) {
					// 第n个跟上一个分数一样所以排名一样
					assessment.setRanking(assessmentList.get(i - 1).getRanking());
				} else {
					// 分数低于下一个排名往后
					ranking++;
					assessment.setRanking(ranking);
				}
			}

		}

		return p;
	}

	public static void main(String[] args) {

		System.out.println(DataUtil.deciMal(0, 0));

	}

}
