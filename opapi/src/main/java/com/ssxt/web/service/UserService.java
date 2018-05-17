package com.ssxt.web.service;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssxt.common.bean.FileInfo;
import com.ssxt.common.dao.GenericDao;
import com.ssxt.common.page.PageControl;
import com.ssxt.common.page.SqlBuffer;
import com.ssxt.common.page.SqlUtil;
import com.ssxt.common.service.GenericServiceImpl;
import com.ssxt.common.util.CtxHelper;
import com.ssxt.common.util.DataUtil;
import com.ssxt.common.util.EnCryptTool;
import com.ssxt.common.util.FileUploadUtils;
import com.ssxt.common.util.FileUtil;
import com.ssxt.common.util.MD5Tools;
import com.ssxt.common.util.MDCUtil;
import com.ssxt.common.util.PropertiesUtil;
import com.ssxt.common.util.SpringBeanUtil;
import com.ssxt.reflect.ReflectSql;
import com.ssxt.web.bean.BasQuit;
import com.ssxt.web.bean.BasTaskDealNode;
import com.ssxt.web.bean.SymAddvcdUser;
import com.ssxt.web.bean.SymRoleUser;
import com.ssxt.web.bean.SymUser;
import com.ssxt.web.dao.QuitDao;
import com.ssxt.web.dao.UserDao;

@Service(value = "userService")
public class UserService extends GenericServiceImpl<SymUser, Integer> {

	protected UserDao dao;
	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(UserService.class);

	@Override
	public GenericDao<SymUser, Integer> getDao() {
		return dao;
	}

	@Autowired
	public void setDao(UserDao dao) {
		this.dao = dao;
	}

	@Autowired
	AddvcdUserService addvcdUserService;

	@Autowired
	AddvcdService addvcdService;

	@Autowired
	RoleService roleService;

	@Autowired
	UserRoleService userRoleService;

	@Autowired
	private ProblemService problemService;

	@Autowired
	QuitDao quitDao;
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ssxt.rdbox.common.service.GenericServiceImpl#delete(java.io.
	 * Serializable)
	 */

	public SymUser checkUserEncryptedWithUpdate(SymUser sys) {
		SymUser isUser = null;
		SqlBuffer sbf = SqlBuffer.begin(); // 创建查询条件缓冲区
		sbf.add("loginID", sys.getLoginId());
		sbf.add("passWord", sys.getPassWord());
		List<SymUser> list = dao.findByCondition(PageControl.getQueryOnlyInstance() // 获得基础分页条件
				.setOrder("order by id desc") // 设置排序参数
				, sbf.end());
		if (list.size() > 0) {
			isUser = list.get(0);
			return isUser;
		}
		return null;
	}

	public boolean exist(SymUser sys) {

		SqlBuffer sbf = SqlBuffer.begin(); // 创建查询条件缓冲区
		sbf.add("loginId", sys.getLoginId());
		List<SymUser> list = dao.findByCondition(PageControl.getQueryOnlyInstance() // 获得基础分页条件
				.setOrder("order by id desc") // 设置排序参数
				, sbf.end());
		if (list.size() > 0) {
			return false;
		}
		return true;

	}

	@Transactional
	public void addUser(SymUser sys) throws Exception {
		if (sys.getDirectlyUnder() == null)
			sys.setDirectlyUnder(sys.getAddvcd());

		String[] addvcd = sys.getAddvcd().split(",");
		sys.setPassWord(MD5Tools.MD5(sys.getPassWord()));
		if (sys.getPassWord() == null)
			sys.setPassWord(MD5Tools.MD5("123456"));
		dao.save(sys);

		// 返回城市编码
		// List<SymAddvcdUser> addvcdUsers =
		// addvcdService.addvcdUser(sys.getAddvcd().split(","), sys.getId());
		// addvcdUserService.saveOrUpdateAll(addvcdUsers);
		problemService.updateOrAdd(sys.getId());
	}

	@Transactional
	public void updateUser(SymUser sys) throws Exception {
		List<String> sql = new ArrayList<String>();

		SymUser oldBean = this.get(sys.getId());

		super.updateDomain("更新用户", oldBean, sys);

	}

	/**
	 * 申请离职
	 * 
	 * @param bean
	 */
	public void addQuit(BasQuit bean) {
		// bean.setUserId(MDCUtil.getUserId());
		bean.setCreateTime(new Date());
		bean.setModifyTime(new Date());
		bean.setModifyUserId(MDCUtil.getUserId());
		quitDao.save(bean);
	}

	/**
	 * 离职批复
	 * 
	 * @param bean
	 * @throws Exception
	 */
	@Transactional
	public void updateQuit(int id, BasQuit bean) throws Exception {
		BasQuit oldBean = quitDao.get(id);
		String schoolId = MDCUtil.getTenantId();
		Integer userId = MDCUtil.getUserId().intValue();
		String userName = MDCUtil.getUserName();
		bean.setModifyTime(new Date());
		bean.setModifyUserId(MDCUtil.getUserId());
		quitDao.updateDomain(schoolId, userId, userName, "用户修改数据", oldBean, bean);
		if (bean.getState() == 20) {
			SqlBuffer where = SqlBuffer.begin();
			SymUser user = new SymUser();
			user.setId(oldBean.getUserId());
			user.setIsEnable(0);
			String updateSql = ReflectSql.updateSql(user, null, false);
			dao.updateByNativeCondition(updateSql.toString(), where.end());
		}

	}

	/**
	 * 上传图片
	 * 
	 * @param id
	 * @param request
	 */
	public void uploadImage(int id, HttpServletRequest request) {
		SymUser user = this.get(id);
		String oldPath = user.getImagePath() == null ? "" : user.getImagePath();

		StringBuffer saveFile = new StringBuffer(CtxHelper.getPath(request));
		StringBuffer saveUrl = new StringBuffer(CtxHelper.getUrl(request));

		String projectName = PropertiesUtil.getProperty("file.saveFile");

		saveFile.append(projectName).append(File.separator).append("user").append(File.separator)
				.append(user.getLoginId()).append(File.separator);
		saveUrl.delete(0, saveUrl.length() - 1);
		saveUrl.append(projectName).append("/").append("user").append("/").append(user.getLoginId()).append("/");

		log.info(saveFile.toString());
		log.info(saveUrl.toString());
		File fileDir = new File(saveFile.toString());
		if (!fileDir.isDirectory()) {
			// 如果目录不存在，则创建目录
			fileDir.mkdirs();
		}
		List<FileInfo> list = FileUploadUtils.uoloadFile(request, saveFile.toString());
		if (list.size() != 0) {
			user.setImagePath(list.get(0).getSaveFile());
			user.setImageUrl(saveUrl + list.get(0).getName());
			this.update(user);
		}
		FileUtil.deleteFile(oldPath);

	}

}
