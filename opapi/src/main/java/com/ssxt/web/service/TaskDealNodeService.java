package com.ssxt.web.service;

import java.io.File;
import java.nio.channels.ShutdownChannelGroupException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.dialect.MckoiDialect;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssxt.common.bean.FileInfo;
import com.ssxt.common.dao.GenericDao;
import com.ssxt.common.service.GenericServiceImpl;
import com.ssxt.common.util.CtxHelper;
import com.ssxt.common.util.DataUtil;
import com.ssxt.common.util.FileOperation;
import com.ssxt.common.util.FileUploadUtils;
import com.ssxt.common.util.MDCUtil;
import com.ssxt.common.util.PropertiesUtil;
import com.ssxt.web.bean.BasDeviceInfo;
import com.ssxt.web.bean.BasTaskDealNode;
 import com.ssxt.web.dao.TaskDealNodeDao;
import com.ssxt.web.bean.SymMenu;
import com.ssxt.web.bean.SysAddvcdB;
import com.ssxt.web.dao.AddvcdDao;
import com.ssxt.web.dao.MenuDao;

@Service(value = "taskDealNodeService")
public class TaskDealNodeService extends GenericServiceImpl<BasTaskDealNode, Integer> {
	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TaskDealNodeService.class);

	protected TaskDealNodeDao taskDealNodeDao;

	@Override
	public GenericDao<BasTaskDealNode, Integer> getDao() {
		return taskDealNodeDao;
	}

	@Autowired
	public void setDao(TaskDealNodeDao dao) {
		this.taskDealNodeDao = dao;
	}

	public int addMedia(BasTaskDealNode info, HttpServletRequest request) {
		if (info.getTaskNo() == null) {
			info = new BasTaskDealNode();
			info.setTaskNo("1111");
			info.setProcId(0);
			info.setDealType(0);
		}
 
		List<BasTaskDealNode> DealNodes = new ArrayList<BasTaskDealNode>();

		StringBuffer saveFile = new StringBuffer(CtxHelper.getPath(request));
		StringBuffer saveUrl = new StringBuffer(CtxHelper.getUrl(request));

		String projectName = PropertiesUtil.getProperty("file.saveFile");

		saveFile.append(projectName).append(File.separator).append("task").append(File.separator)
				.append(info.getTaskNo()).append(File.separator);
		saveUrl.delete(0, saveUrl.length()-1);
		saveUrl.append(projectName).append("/").append("task").append("/").append(info.getTaskNo()).append("/");

		log.info(saveFile.toString());
		log.info(saveUrl.toString());
		File fileDir = new File(saveFile.toString());
		if (!fileDir.isDirectory()) {
			// 如果目录不存在，则创建目录
			fileDir.mkdirs();
		}
		List<FileInfo> list = FileUploadUtils.uoloadFile(request, saveFile.toString());
		for (int i = 0; i < list.size(); i++) {
			BasTaskDealNode node = new BasTaskDealNode();
			FileInfo fileInfo = list.get(i);

			node.setUrl(saveUrl.toString() + fileInfo.getName().toString());
			node.setSize(fileInfo.getSize());
			node.setPath(fileInfo.getSaveFile());
			node.setFileName(fileInfo.getName());

			node.setOperaterId(MDCUtil.getUserId());
			node.setOperateName(MDCUtil.getUserName());
			node.setOperateTime(new Date());

			node.setTaskNo(info.getTaskNo());
			node.setProcId(info.getProcId());
			node.setDealType(info.getDealType());

			String fileType = fileInfo.getName();
			if (fileType.contains("jpg") || fileType.contains("png") || fileType.contains("gif")) {
				node.setContent("图片");
				node.setDealType(2);
			} else {
				node.setContent("其他文件");
				node.setDealType(0);
			}

			DealNodes.add(node);

		}

		taskDealNodeDao.saveOrUpdateAll(DealNodes);
		return list.size();

	}

}
