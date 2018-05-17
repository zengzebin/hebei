package com.ssxt.web.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import com.ssxt.common.util.CachePool;
import com.ssxt.common.util.MDCUtil;
import com.ssxt.web.bean.BasProblem;
import com.ssxt.web.bean.BasProblemAnswer;
import com.ssxt.web.bean.BasProblemCommon;
import com.ssxt.web.bean.BasProblemCommonId;
import com.ssxt.web.bean.BasProblemGrade;
import com.ssxt.web.bean.SymAddvcdUser;
import com.ssxt.web.bean.SymMenu;
import com.ssxt.web.bean.SysAddvcdB;
import com.ssxt.web.dao.AddvcdDao;
import com.ssxt.web.dao.GradeDao;
import com.ssxt.web.dao.MenuDao;
import com.ssxt.web.dao.ProblemAnswerDao;
import com.ssxt.web.dao.ProblemCommonDao;
import com.ssxt.web.dao.ProblemDao;

@Service(value = "problemService")
public class ProblemService extends GenericServiceImpl<BasProblem, Integer> {

	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ProblemService.class);

	@Autowired
	protected ProblemDao dao;

	@Autowired
	private ProblemAnswerDao problemanswerDao;

	@Autowired
	private ProblemCommonDao problemCommonDao;

	@Autowired
	private GradeDao gradeDao;

	@Override
	public GenericDao<BasProblem, Integer> getDao() {
		return dao;
	}

	/**
	 * 添加问题
	 * 
	 * @param bean
	 */
	@Transactional
	public void addProblem(BasProblem bean) {
		bean.setClickNum(0);
		bean.setCreateUserId(MDCUtil.getUserId());
		bean.setCreateTime(new Date());
		dao.save(bean);
		updateOrAdd(MDCUtil.getUserId());// 加分
	}

	public BasProblem clickProblem(int problemId) {
		BasProblem bean = dao.get(problemId);
		// 增加点击数
		bean.setClickNum(bean.getClickNum() + 1);
		dao.update(bean);

		// 添加与我相关
		BasProblemCommonId id = new BasProblemCommonId();
		id.setProblemId(problemId);
		id.setUserId(MDCUtil.getUserId());
		BasProblemCommon common = problemCommonDao.get(id);
		if (common == null) {
			common = new BasProblemCommon();
			common.setClickTime(new Date());
			common.setId(id);
			problemCommonDao.save(common);
		} else {
			// 已经有记录更新点击时间
			common.setClickTime(new Date());
			problemCommonDao.update(common);
		}
		return bean;
	}

	/**
	 * 回答问题加分
	 * 
	 * @param id
	 * @param request
	 * @param response
	 */
	@Transactional
	public void answer(BasProblemAnswer bean, HttpServletRequest request, HttpServletResponse response) {
		bean.setCreateTime(new Date());
		bean.setCreateUserId(MDCUtil.getUserId());
		problemanswerDao.save(bean);
		updateOrAdd(MDCUtil.getUserId());// 加分

	}

	/**
	 * 加分及更新等级
	 * 
	 * @param userId
	 */
	public void updateOrAdd(int userId) {
		BasProblemGrade gradeBean = gradeDao.get(userId);// 获取当前用户的等级及积分
		if (gradeBean == null) {
			// 添加新的用户等级
			gradeBean = new BasProblemGrade();
			gradeBean.setScore(1);
			gradeBean.setUpdateTime(new Date());
			gradeBean.setUserId(userId);
			gradeBean.setGrade(1);
		} else {
			// 加分及算等级
			int score = gradeBean.getScore() + 1;
			int grade = score / 10 + 1;
			gradeBean.setUpdateTime(new Date());
			gradeBean.setScore(score);
			gradeBean.setGrade(grade);
		}
		gradeDao.saveOrUpdate(gradeBean);
	}

	public static void main(String[] args) {

	}

}
