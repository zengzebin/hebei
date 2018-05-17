package com.ssxt.web.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssxt.common.dao.GenericDao;
import com.ssxt.common.page.PageControl;
import com.ssxt.common.page.SqlBuffer;
import com.ssxt.common.service.GenericServiceImpl;
import com.ssxt.common.util.DataUtil;
import com.ssxt.common.util.MDCUtil;
import com.ssxt.web.bean.BasDeviceInfo;
import com.ssxt.web.bean.BasMessageRemind;
import com.ssxt.web.dao.DeviceInfoDao;
import com.ssxt.web.bean.SymMenu;
import com.ssxt.web.bean.SysAddvcdB;
import com.ssxt.web.controller.CommonController;
import com.ssxt.web.dao.AddvcdDao;
import com.ssxt.web.dao.MenuDao;
import com.ssxt.web.dao.MessageDao;

@Service(value = "messageService")
public class MessageService extends GenericServiceImpl<BasMessageRemind, Integer> {
	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(MessageService.class);

	@Autowired
	protected MessageDao messageDao;

	@Override
	public GenericDao<BasMessageRemind, Integer> getDao() {
		return messageDao;
	}

	/**
	 * 添加消息与更新消息
	 * 
	 * @param bean
	 * @param request
	 * @param response
	 */
	public void saveUpdate(BasMessageRemind bean, HttpServletRequest request, HttpServletResponse response) {
		bean.setCreateTime(new Date());
		bean.setCreateUserId(MDCUtil.getUserId());
		SqlBuffer where = SqlBuffer.begin();
		List<BasMessageRemind> list = null;
		BasMessageRemind old = null;
		if (bean.getType() == 1) {
			// 任务单
			where.add("taskNo", bean.getTaskNo());
			list = this.exist(where);
			if (list.size() > 0) {
				// 更新
				old = list.get(0);
				updateDomain("修改", old, bean);
			} else {
				// 添加
				this.save(bean);
			}
		}
	}

}
