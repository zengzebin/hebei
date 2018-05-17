package com.ssxt.web.service;

import java.io.File;
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

import com.ssxt.common.bean.FileInfo;
import com.ssxt.common.dao.GenericDao;
import com.ssxt.common.page.PageControl;
import com.ssxt.common.page.SqlBuffer;
import com.ssxt.common.service.GenericServiceImpl;
import com.ssxt.common.util.CachePool;
import com.ssxt.common.util.CtxHelper;
import com.ssxt.common.util.DataUtil;
import com.ssxt.common.util.FileUploadUtils;
import com.ssxt.common.util.MDCUtil;
import com.ssxt.common.util.PropertiesUtil;
import com.ssxt.web.bean.BasClassRoom;
import com.ssxt.web.bean.SymAddvcdUser;
import com.ssxt.web.bean.SymMenu;
import com.ssxt.web.bean.SysAddvcdB;
import com.ssxt.web.dao.AddvcdDao;
import com.ssxt.web.dao.ClassRoomDao;
import com.ssxt.web.dao.MenuDao;

@Service(value = "classRoomService")
public class ClassRoomService extends GenericServiceImpl<BasClassRoom, Integer> {
	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ClassRoomService.class);

	@Autowired
	protected ClassRoomDao dao;

	@Autowired
	private ProblemService problemService;

	@Override
	public GenericDao<BasClassRoom, Integer> getDao() {
		return dao;
	}

	public static void main(String[] args) {

	}

	/**
	 * 添加数据及图片
	 * 
	 * @param bean
	 * @param request
	 * @param response
	 */
	@Transactional
	public void add(BasClassRoom bean, HttpServletRequest request, HttpServletResponse response) {
		bean.setCreateTime(new Date());
		bean.setCreateUserId(MDCUtil.getUserId());
		bean.setClickNum(0);
		bean.setModifyUserId(MDCUtil.getUserId());
		bean.setModifyTime(new Date());
		dao.save(bean);
		problemService.updateOrAdd(MDCUtil.getUserId());

		// StringBuffer saveFile = new StringBuffer(CtxHelper.getPath(request));
		// StringBuffer saveUrl = new StringBuffer(CtxHelper.getUrl(request));
		//
		// String projectName = PropertiesUtil.getProperty("file.saveFile");
		//
		// saveFile.append(projectName).append(File.separator).append("classroom").append(File.separator);
		// saveUrl.delete(0, saveUrl.length() - 1);
		// saveUrl.append(projectName).append("/").append("classroom").append("/");
		//
		// log.info(saveFile.toString());
		// log.info(saveUrl.toString());
		// File fileDir = new File(saveFile.toString());
		// if (!fileDir.isDirectory()) {
		// // 如果目录不存在，则创建目录
		// fileDir.mkdirs();
		// }
		// List<FileInfo> list = FileUploadUtils.uoloadFile(request,
		// saveFile.toString());
		// // 如果有图片添加图片信息
		// if (list.size() > 0) {
		// FileInfo fileInfo = list.get(0);
		// bean.setFile(fileInfo.getSaveFile());
		// bean.setFileName(fileInfo.getName());
		// bean.setUrl(fileInfo.getSaveUrl());
		// bean.setSize(fileInfo.getSize());
		//
		// }
		// dao.save(bean);

	}

	/**
	 * 删除数据及图片
	 * 
	 * @param id
	 */
	@Transactional
	public void delete(Integer id) {
		BasClassRoom bean = this.get(id);
		FileUploadUtils.deleteImage(bean.getFile());
		this.deleteByKey(id);
	}

	/**
	 * 上传图片
	 * 
	 * @param id
	 * @param request
	 * @param response
	 */
	public void uploadImage(int id, HttpServletRequest request, HttpServletResponse response) {
		// 删除老图片
		BasClassRoom old = this.get(id);
		FileUploadUtils.deleteImage(old.getFile());

		StringBuffer saveFile = new StringBuffer(CtxHelper.getPath(request));
		StringBuffer saveUrl = new StringBuffer(CtxHelper.getUrl(request));

		String projectName = PropertiesUtil.getProperty("file.saveFile");

		saveFile.append(projectName).append(File.separator).append("classroom").append(File.separator)
				.append(old.getId()).append(File.separator);
		saveUrl.delete(0, saveUrl.length() - 1);
		saveUrl.append(projectName).append("/").append("classroom").append("/").append(old.getId()).append("/");

		log.info(saveFile.toString());
		log.info(saveUrl.toString());
		File fileDir = new File(saveFile.toString());
		if (!fileDir.isDirectory()) {
			// 如果目录不存在，则创建目录
			fileDir.mkdirs();
		}
		List<FileInfo> list = FileUploadUtils.uoloadFile(request, saveFile.toString());
		BasClassRoom bean = new BasClassRoom();
		if (list.size() > 0) {
			FileInfo fileInfo = list.get(0);
			bean.setFile(fileInfo.getSaveFile());
			bean.setFileName(fileInfo.getName());
			bean.setUrl(saveUrl + fileInfo.getName());
			bean.setSize(fileInfo.getSize());
			bean.setType(fileInfo.getType());
		}
		this.updateDomain("修改", old, bean);
	}

}
