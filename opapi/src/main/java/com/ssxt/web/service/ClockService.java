package com.ssxt.web.service;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.TableGenerator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.poi.xssf.usermodel.BaseXSSFEvaluationWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssxt.common.dao.GenericDao;
import com.ssxt.common.page.PageControl;
import com.ssxt.common.page.SqlBuffer;
import com.ssxt.common.service.GenericServiceImpl;
import com.ssxt.common.util.CachePool;
import com.ssxt.common.util.DataUtil;
import com.ssxt.common.util.FastJsonUtils;
import com.ssxt.common.util.MDCUtil;
import com.ssxt.common.web.HttpProvider;
import com.ssxt.web.bean.BasClock;
import com.ssxt.web.bean.BasClockId;
import com.ssxt.web.bean.BasVacationTravel;
import com.ssxt.web.bean.BasWorkDate;
import com.ssxt.web.bean.SymAddvcdUser;
import com.ssxt.web.bean.SymMenu;
import com.ssxt.web.bean.SymUser;
import com.ssxt.web.bean.SysAddvcdB;
import com.ssxt.web.controller.WorkController;
import com.ssxt.web.dao.AddvcdDao;
import com.ssxt.web.dao.AddvcdUserDao;
import com.ssxt.web.dao.ClockDao;
import com.ssxt.web.dao.MenuDao;
import com.ssxt.web.dao.VacationTravelDao;
import com.ssxt.web.dao.WordDateDao;
import com.ssxt.web.vo.Assessment;

@Service(value = "clockService")
public class ClockService extends GenericServiceImpl<BasClock, BasClockId> {
	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ClockService.class);

	@Autowired
	protected ClockDao dao;

	@Autowired
	WordDateDao wordDateDao;

	@Autowired
	UserService userService;

	@Autowired
	VacationTravelDao vacationTravelDao;

	@Override
	public GenericDao<BasClock, BasClockId> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}

	/**
	 * 生产日历年
	 * 
	 * @throws ParseException
	 */

	public void createYearDate() throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		for (int i = 0; i < 800; i++) {

			try {

				BasWorkDate date = new BasWorkDate();

				Calendar calendar = Calendar.getInstance();
 				calendar.add(Calendar.DAY_OF_YEAR, i);
				Date date1 = calendar.getTime();
				System.out.println(sdf.format(date1));

				String[] weekOfDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };

				if (date1 != null) {
					calendar.setTime(date1);
				}
				int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
				if (w < 0) {
					w = 0;
				}
				System.out.print(weekOfDays[w]);
				date.setDate(sdf.format(date1));
				date.setName(weekOfDays[w]);
    			wordDateDao.save(date);
				// date.setRemark(job.get("remark").toString());
				// date.setWorkmk(Integer.parseInt(job.get("workmk").toString()));
				// if (wordDateDao.get(date.getDate()) == null) {

				// }

			} catch (Exception e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				log.error(e.getMessage());
				break;
			}

		}
		// wordDateDao.saveOrUpdateAll(list);

	}

	/**
	 * 生成打卡时间14天
	 */
	@Transactional
	public void autoDate() {
		Calendar c = Calendar.getInstance();

		Format format = new SimpleDateFormat("yyyy-MM-dd");
		Date today = new Date();
		System.out.println("今天是:" + format.format(today));

		String sql = "SELECT a.* FROM   sym_user a INNER JOIN  sym_role  b ON(a.roleId=b.id)";
		sql += " WHERE b.isOperation=1 AND a.isEnable=1 ";
		List<SymUser> list = userService.findByNativeCondition(PageControl.getQueryOnlyInstance(), null, sql,
				SymUser.class);
		for (int i = 0; i < 14; i++) {
			c.setTime(today);
			c.add(Calendar.DAY_OF_MONTH, i);// 今天+1天
			Date tomorrow = c.getTime();
			System.out.println(i + "天后是：" + format.format(tomorrow));

			for (SymUser u : list) {
				BasClockId id = new BasClockId();
				id.setDate(format.format(tomorrow));
				id.setUserId(u.getId());
				BasClock clock = new BasClock();
				clock.setId(id);
				clock.setState(0);
				if (dao.get(id) == null) {
					dao.save(clock);
				}
			}
		}

	}

	/**
	 * 签到
	 */
	public void toWord() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		BasClockId id = new BasClockId();
		id.setDate(formatter.format(new Date()));
		id.setUserId(MDCUtil.getUserId());
		BasClock clock = new BasClock();
		clock.setToWork(new Date());
		clock.setId(id);
		BasClock old = dao.get(id);
		if (old == null) {
			clock.setState(0);
			dao.save(clock);
		} else {
			super.updateDomain("修改上班时间", old, clock);
		}
	}

	/**
	 * 休假及出差申请
	 */
	public void addVacationTravel(BasVacationTravel bean) {
		if (bean.getType() == 1) {
			bean.setContent("休假");
		} else {
			bean.setContent("出差");
		}
		// bean.setUserId(MDCUtil.getUserId());
		bean.setCreateTime(new Date());
		bean.setModify(new Date());
		bean.setModifyUserId(MDCUtil.getUserId());
		vacationTravelDao.save(bean);
	}

	/**
	 * 休假及出差审批结果
	 */
	public void ReplyVacationTravel(int id, BasVacationTravel bean) {
		BasVacationTravel oldBean = vacationTravelDao.get(id);
		String schoolId = MDCUtil.getTenantId();
		Integer userId = MDCUtil.getUserId().intValue();
		String userName = MDCUtil.getUserName();
		bean.setModify(new Date());
		bean.setModifyUserId(MDCUtil.getUserId());
		vacationTravelDao.updateDomain(schoolId, userId, userName, "用户修改数据", oldBean, bean);
		// 同意
		if (bean.getState() == 20) {
			SqlBuffer where = SqlBuffer.begin();
			List<String> list = Differ(oldBean.getStartTime(), oldBean.getEndTime());
			for (String date : list) {

				BasClockId ClockId = new BasClockId();
				ClockId.setDate(date);
				ClockId.setUserId(oldBean.getUserId());
				BasClock clock = new BasClock();
				clock.setId(ClockId);
				clock.setState(oldBean.getType());
				if (dao.get(ClockId) != null) {
					where.remove();
					StringBuffer updateSql = new StringBuffer("update BasClock set state=").append(oldBean.getType())
							.append(" where userId=").append(clock.getId().getUserId()).append(" and  date='")
							.append(date).append("'");

					dao.updateByCondition(updateSql.toString(), where.end());
				} else {
					dao.save(clock);
				}

			}
		}

	}

	/**
	 * 通过时间秒毫秒数判断两个时间的间隔
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public List<String> Differ(Date date1, Date date2) {
		int days = (int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));
		List<String> lsit = new ArrayList<String>();
		Calendar c = Calendar.getInstance();
		Format format = new SimpleDateFormat("yyyy-MM-dd");
		// Date today = new Date();
		System.out.println("开始休假及出差:" + format.format(date1));

		for (int i = 0; i <= days; i++) {
			c.setTime(date1);// 设置开始时间
			c.add(Calendar.DAY_OF_MONTH, i);
			Date tomorrow = c.getTime();
			System.out.println(1 + "天后是：" + format.format(tomorrow));
			lsit.add(format.format(tomorrow));
		}

		return lsit;
	}

}
