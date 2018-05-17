package com.ssxt.web.controller;

import static com.ssxt.common.util.DataUtil.notNullString;
import static com.ssxt.common.util.DataUtil.showMsgException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.cfg.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ssxt.common.enums.ActionMsgParam;
import com.ssxt.common.page.PageControl;
import com.ssxt.common.page.SqlBuffer;
import com.ssxt.common.page.SqlCondGroup;
import com.ssxt.common.page.WebSearchBuffer;
import com.ssxt.common.service.GenericServiceImpl;
import com.ssxt.common.util.AccData;
import com.ssxt.common.util.CachePool;
import com.ssxt.common.util.CtxHelper;
import com.ssxt.common.util.DataUtil;
import com.ssxt.common.util.FileUploadUtils;
import com.ssxt.common.util.MDCUtil;
import com.ssxt.common.web.SpringBaseController;
import com.ssxt.web.bean.BasDeviceInfo;
import com.ssxt.web.bean.BasStinfoB;
import com.ssxt.web.bean.BasTaskDealNode;
import com.ssxt.web.service.DeviceInfoService;
import com.ssxt.web.service.TaskDealNodeService;
import com.ssxt.web.bean.SymMenu;
import com.ssxt.web.bean.SymUser;
import com.ssxt.web.service.MenuService;
import com.ssxt.web.service.RoleService;
import com.ssxt.web.service.UserService;

@Controller
@RequestMapping(value = "/api/taskDealNode/")
public class TaskDealNodeController extends SpringBaseController<BasTaskDealNode, Integer> {

	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TaskDealNodeController.class);

	@Autowired
	private TaskDealNodeService service;

	@Comment("添加日志")
	@RequestMapping(value = "addJournal", method = RequestMethod.POST)
	public void addJournal(@ModelAttribute("bean") BasTaskDealNode node, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		node.setOperaterId(MDCUtil.getUserId());
		node.setOperateTime(new Date());

		service.save(node);
		super.toJson(null, 1, true, ActionMsgParam.ACM_SAV_SUC, request, response);
	}

	@Comment("添加图片文件视频等")
	@RequestMapping(value = "addMedia", method = RequestMethod.POST)
	public void addFile(@ModelAttribute("bean") BasTaskDealNode node, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int count = service.addMedia(node, request);
		super.toJson(null, count, true, "上传数量完成：" + count, request, response);
	}

	@Override
	public boolean getCommentByHbm() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ssxt.rdbox.common.web.SpringBaseController#getEntityName()
	 */
	@Override
	public String getEntityName() {
		return "用户管理";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ssxt.rdbox.common.web.SpringBaseController#getService()
	 */
	@Override
	public GenericServiceImpl<BasTaskDealNode, Integer> getService() {
		return service;
	}

}
