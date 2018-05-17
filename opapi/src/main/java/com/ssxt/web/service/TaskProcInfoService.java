package com.ssxt.web.service;

import static com.ssxt.common.util.DataUtil.showMsgException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssxt.common.dao.GenericDao;
import com.ssxt.common.service.GenericServiceImpl;
import com.ssxt.common.util.AccData;
import com.ssxt.common.util.CtxHelper;
import com.ssxt.common.util.MDCUtil;
import com.ssxt.common.util.StatusCode;
import com.ssxt.web.bean.BasDeviceInfo;
import com.ssxt.web.bean.BasTaskInfo;
import com.ssxt.web.bean.BasTaskProcInfo;

import com.ssxt.web.dao.TaskProcInfoDao;
import com.ssxt.web.bean.SymMenu;
import com.ssxt.web.bean.SysAddvcdB;
import com.ssxt.web.dao.AddvcdDao;
import com.ssxt.web.dao.MenuDao;

@Service(value = "taskProcInfoService")
public class TaskProcInfoService extends GenericServiceImpl<BasTaskProcInfo, Integer> {
	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TaskProcInfoService.class);

	@Autowired
	private TaskProcInfoDao taskProcInfoDao;

	@Autowired
	private TaskInfoService taskInfoService;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;

	@Override
	public GenericDao<BasTaskProcInfo, Integer> getDao() {
		return taskProcInfoDao;
	}

	@Autowired
	public void setDao(TaskProcInfoDao taskProcInfoDao) {
		this.taskProcInfoDao = taskProcInfoDao;
	}

	/**
	 * 任务单操作
	 * 
	 * @param taskNo
	 * @param operate
	 */
	@Transactional
	public void taskOperate(String taskNo, String operate) {
		Map<String, Object> variables = new HashMap<String, Object>();
		Date nowTime = new Date();

		BasTaskInfo task = taskInfoService.get(taskNo);
		BasTaskProcInfo procInfo = new BasTaskProcInfo();
		procInfo.setTaskNo(task.getTaskNo());
		AccData ad = CtxHelper.createFailAccData();
		int status = 0;
		String StatusName = "";
		String currentNode = "";
		String explain = "";

		if (operate.equals("Maintenance")) {
			currentNode = "Maintenance";
			status = StatusCode.WaitDeal;
			StatusName = StatusCode.WaitDealText;
			explain = "接单," + StatusCode.WaitDealText;
			task.setReciveTime(nowTime);
			task.setUpdateCheck(1);
			procInfo.setContent(explain);
		}

		if (operate.equals("execute")) {
			currentNode = "execute";
			status = StatusCode.deal;
			StatusName = StatusCode.dealText;
			explain = StatusCode.dealText + "中";
			task.setDealTime(nowTime);
			task.setUpdateCheck(1);
			procInfo.setContent(explain);
		}

		if (operate.equals("examine")) {
			currentNode = "examine";
			status = StatusCode.reviewed;
			StatusName = StatusCode.reviewedText;
			explain = "维修完成," + StatusCode.reviewedText;
			task.setDealFinished(nowTime);
			task.setUpdateCheck(1);
			procInfo.setContent(explain);
		}

		if (operate.equals("deal")) {
			currentNode = "Maintenance";
			status = StatusCode.reviewedNo;
			StatusName = StatusCode.reviewedNoText;
			explain = "审核不通过," + StatusCode.WaitDealText;
			task.setDealFinished(null);
			task.setUpdateMaint(1);
			procInfo.setContent(explain);
		}

		if (operate.equals("end")) {
			currentNode = "end";
			status = StatusCode.owners;
			StatusName = StatusCode.ownersText;
			explain = StatusCode.ownersText;
			task.setFinishTime(nowTime);
			task.setUpdateMaint(1);
			procInfo.setContent(explain);
		}

		if (operate.equals("cancle")) {
			currentNode = "cancle";
			status = StatusCode.cancle;
			StatusName = StatusCode.cancleText;
			explain = StatusCode.cancleText;
			task.setFinishTime(nowTime);
			task.setUpdateMaint(1);
			procInfo.setContent(explain);
		}

		task.setModifyTime(nowTime);
		task.setCurrentNode(currentNode);
		task.setTaskStatus(status);
		task.setTaskStatusName(StatusName);
		procInfo.setOperaterId(MDCUtil.getUserId());
		procInfo.setOperaterUserName(MDCUtil.getUserName());
		procInfo.setCurrentNode(currentNode);
		procInfo.setOperateTime(nowTime);
		variables.put("operate", operate);// 环节
		// Task task1 =
		// taskService.createTaskQuery().processInstanceId(taskNo).singleResult();
		// taskService.complete(task1.getId(), variables);
		// Task newtask =
		// taskService.createTaskQuery().processInstanceId(taskNo).singleResult();
		// if (!operate.equals("end") && newtask == null)
		// showMsgException("操作有误,请退出重新执行");

		taskInfoService.update(task);
		taskProcInfoDao.save(procInfo);
	}

	/**
	 * 当前可执行环节
	 * 
	 * @param taskNo
	 * @return
	 */
	public Map<String, String> getSelect(String taskNo) {
		Map<String, String> map = new HashMap<String, String>();
		BasTaskInfo task = taskInfoService.get(taskNo);

		if (task.getCurrentNode().equals("report")) {
			map.put("receive", "待派单");
		}
		if (task.getCurrentNode().equals("send")) {
			map.put("Maintenance", "接收");
		}
		if (task.getCurrentNode().equals("Maintenance")) {
			map.put("execute", "执行维修");
		}
		if (task.getCurrentNode().equals("execute")) {
			map.put("examine", "维修完成");
		}

		if (task.getCurrentNode().equals("examine")) {
			map.put("end", "审核通过");
			map.put("deal", "审核不通过，待维修");
		}

		return map;
	}

}
