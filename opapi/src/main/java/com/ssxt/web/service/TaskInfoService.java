package com.ssxt.web.service;

import static com.ssxt.common.util.DataUtil.showMsgException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.log4j.MDC;
import org.hibernate.dialect.MckoiDialect;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssxt.common.dao.GenericDao;
import com.ssxt.common.page.SqlBuffer;
import com.ssxt.common.service.GenericServiceImpl;
import com.ssxt.common.util.AccData;
import com.ssxt.common.util.CtxHelper;
import com.ssxt.common.util.DataUtil;
import com.ssxt.common.util.MDCUtil;
import com.ssxt.common.util.OpParam;
import com.ssxt.common.util.StatusCode;
import com.ssxt.reflect.ReflectSql;
import com.ssxt.web.bean.BasDeviceInfo;
import com.ssxt.web.bean.BasMessageRemind;
import com.ssxt.web.bean.BasStinfoB;
import com.ssxt.web.bean.BasTaskHelp;
import com.ssxt.web.bean.BasTaskInfo;
import com.ssxt.web.bean.BasTaskProcInfo;

import com.ssxt.web.dao.TaskInfoDao;
import com.ssxt.web.bean.SymMenu;
import com.ssxt.web.bean.SysAddvcdB;
import com.ssxt.web.dao.AddvcdDao;
import com.ssxt.web.dao.MenuDao;

@Service(value = "taskInfoService")
public class TaskInfoService extends GenericServiceImpl<BasTaskInfo, String> {
	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TaskInfoService.class);

	protected TaskInfoDao taskInfoDao;

	@Override
	public GenericDao<BasTaskInfo, String> getDao() {
		return taskInfoDao;
	}

	@Autowired
	public void setDao(TaskInfoDao taskInfoDao) {
		this.taskInfoDao = taskInfoDao;
	}

	// 过程处理
	@Autowired
	TaskProcInfoService taskProcInfoService;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;
	@Autowired
	private StinfoService stinfoService;

	@Autowired
	private MessageService MessageService;

	/**
	 * 添加任务单
	 * 
	 * @param stcd
	 * @param content
	 * @throws Exception
	 */

	@Transactional
	public void addTask(BasTaskInfo bean, String type) throws Exception {
		SqlBuffer sbf = SqlBuffer.begin();
		sbf.add("taskStatus", StatusCode.owners, "<");
		sbf.add("stionId", bean.getStionId());
		int count = this.exist(sbf).size();
		if (count > 0) {
			if (type.equals("automatic")) {
				log.info("该测站已经有任务单在处理了");
				return;
			} else {
				showMsgException("该测站已经有任务单在处理了");
			}

		}

		Date nowTime = new Date();
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("heibie20171215");
		SqlBuffer sfb = SqlBuffer.begin();
		sfb.add("stionId", bean.getStionId());
		BasStinfoB stinfo = stinfoService.exist(sfb).get(0);
		BasTaskInfo task = new BasTaskInfo();
		BasTaskProcInfo procInfo = new BasTaskProcInfo();

		BasMessageRemind message = new BasMessageRemind();

		message.setCreateUserId(MDCUtil.getUserId());
		message.setMessage("你有新的任务单" + stinfo.getStnm() + "测站24小时未上报");
		message.setTaskNo(processInstance.getId());
		message.setType(1);
		message.setState(0);
		message.setLevel(1);

		if (type.equals("automatic")) {
			// 自动任务单的直接派单给维修人员状态带接收
			bean.setMaintUserId(stinfo.getRepairId());
			message.setUserId(stinfo.getRepairId());
		}

		task.setTaskNo(processInstance.getId());// 任务单号

		task.setStionId(bean.getStionId());// 维修站点
		task.setAddvcd(stinfo.getAddvcd());// 维修地区
		task.setContent(bean.getContent());// 内容

		task.setCreateTime(nowTime);// 任务单创建时间
		task.setModifyTime(nowTime);
		task.setCreateUserId(MDCUtil.getUserId());

		task.setCurrentNode("report");// 当前节点

		task.setPriorityStatus(bean.getPriorityStatus());// 任务单级别
		task.setPriorityName(OpParam.priorityMap.get(bean.getPriorityStatus()));

		task.setTaskStatus(StatusCode.Report);// 当前状态
		task.setTaskStatusName(StatusCode.ReportText);

		task.setUpdateCheck(0);
		task.setUpdateMaint(1);

		procInfo.setTaskNo(processInstance.getId());
		procInfo.setCurrentNode("report");// 当前节点
		procInfo.setContent(StatusCode.ReportText);// 内容
		procInfo.setOperateTime(nowTime);
		procInfo.setOperaterId(MDCUtil.getUserId());
		procInfo.setOperaterUserName(MDCUtil.getUserName());

		// 如果有指派
		if (bean.getMaintUserId() != null) {
			message.setUserId(bean.getMaintUserId());
			task.setMaintUserId(bean.getMaintUserId());
			task.setTaskStatus(StatusCode.send);
			task.setTaskStatusName(StatusCode.sendText);
			task.setCurrentNode("send");
			// task.setMaintUserId(stinfo.getRepairId());
			procInfo.setCurrentNode("send");
			procInfo.setContent(StatusCode.sendText);
		}

		if (bean.getHelpPersonnel() != null) {
			String[] userIds = bean.getHelpPersonnel().split(",");
			for (int i = 0; i < userIds.length; i++) {
				BasTaskHelp personnel = new BasTaskHelp();
				personnel.setTaskNo(task.getTaskNo());
				personnel.setUserId(DataUtil.parseInteger(userIds[i], 0));
				String sql = ReflectSql.inertSql(personnel);
				this.insert(sql);
			}
		}

		taskInfoDao.save(task);
		taskProcInfoService.save(procInfo);
		MessageService.saveUpdate(message, null, null);
	}

	@Transactional
	public boolean updateTask(String taskNo, BasTaskInfo bean) throws Exception {
		BasTaskInfo old = taskInfoDao.get(taskNo);
		Date nowTime = new Date();
		BasTaskProcInfo procInfo = new BasTaskProcInfo();

		if (old.getTaskStatus() >= StatusCode.WaitDeal)
			showMsgException("该任务已经被接收不能修改了");

		if (bean.getTaskStatus() == 10) {
			bean.setCurrentNode("send");
			bean.setTaskStatusName(StatusCode.sendText);// 待接收
			bean.setModifyTime(nowTime);
			procInfo.setTaskNo(taskNo);
			procInfo.setContent("指派完成，" + StatusCode.sendText);
			procInfo.setOperaterId(MDCUtil.getUserId());
			procInfo.setOperaterUserName(MDCUtil.getUserName());
			procInfo.setOperateTime(nowTime);
			procInfo.setCurrentNode("send");
			if (bean.getHelpPersonnel() == null)
				bean.setHelpPersonnel(",");

			if (!bean.getHelpPersonnel().equals(old.getHelpPersonnel())) {
				this.updateByNativeCondition("delete from bas_task_help where taskNo='" + bean.getTaskNo() + "'", null);
				String deleteMessage = "delete from bas_message_remind where taskNo='" + bean.getTaskNo()
						+ "' and type=2";
				this.updateByNativeCondition(deleteMessage, null);
				String[] userIds = bean.getHelpPersonnel().split(",");
				for (int i = 0; i < userIds.length; i++) {
					BasTaskHelp personnel = new BasTaskHelp();
					personnel.setTaskNo(bean.getTaskNo());
					personnel.setUserId(DataUtil.parseInteger(userIds[i], 0));
					String sql = ReflectSql.inertSql(personnel);
					this.insert(sql);
					BasMessageRemind message = new BasMessageRemind();

					message.setCreateUserId(MDCUtil.getUserId());
					message.setMessage(taskNo + "该任务单需要你协助");
					message.setTaskNo(taskNo);
					message.setCreateTime(nowTime);
					message.setType(2);
					message.setState(0);
					message.setLevel(1);
					message.setUserId(DataUtil.parseInteger(userIds[i], 0));
					MessageService.save(message);
				}

			}
		}
		taskProcInfoService.save(procInfo);
		// BeanUtils.copyProperties(old, bean);
		this.updateDomain("修改任务单", old, bean);
		return true;

	}

}
