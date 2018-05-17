package com.ssxt.web.controller;

import static com.ssxt.common.enums.LoginParam.USE_LOGIN_PWD_CHECK;
import static com.ssxt.common.util.DataUtil.isNull;
import static com.ssxt.common.util.DataUtil.showMsgException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.cfg.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.ssxt.common.enums.ActionMsgParam;
import com.ssxt.common.enums.ConstParam;
import com.ssxt.common.json.JsonHelper;
import com.ssxt.common.service.GenericServiceImpl;
import com.ssxt.common.util.AccData;
import com.ssxt.common.util.CtxHelper;
import com.ssxt.common.util.DataUtil;
import com.ssxt.common.util.Err;
import com.ssxt.common.util.JsonDataAA;
import com.ssxt.common.util.MD5Tools;
import com.ssxt.common.util.MDCUtil;
import com.ssxt.common.web.SessionHelper;
import com.ssxt.common.web.SpringBaseController;
import com.ssxt.web.bean.SymRole;
import com.ssxt.web.bean.SymUser;
import com.ssxt.web.service.RoleService;
import com.ssxt.web.service.UserService;

@Controller
@RequestMapping(value = "/api/me/")
public class LoginController extends SpringBaseController<SymUser, Integer> {
	@Autowired
	private UserService service;

	@Autowired
	private RoleService roleService;

	public void setUserService(UserService service) {
		this.service = service;
	}

	public static final String SQL_LIST = "select t.ID as ID,t.loginID as loginID,t.name as name,t.department as department,t.position as position,t.who_create as whoCreate,t.create_time as createTime,t.superID as superID,t.uiseq as uiseq   "

	+ " from sym_user t ";

	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(LoginController.class);

	/**
	 * 登录接口（web和app） 返回http状态201成功，401用户名或密码错误！，500登录出错！
	 * 
	 * @param loginid
	 * @param password
	 * @param imei
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@Comment("登录")
	@RequestMapping(value = "login")
	public void login(@RequestParam(required = false) String loginid, @RequestParam(required = false) String password,
			@RequestParam(value = "randImg", required = false) String randImg, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		log.info("用户登录");
		// if(log.isDebugEnabled())
		// log.debug("内容:",JsonHelper.toJSONString(bean));
		AccData ad = CtxHelper.createFailAccData();
		ad.setMsg(ActionMsgParam.ACM_LOGIN_ERR);
		// HttpSession session=request.getSession();
		// String sessionImg=(String) session.getAttribute(LOGIN_RAND_IMG);
		String sessionImg = SessionHelper.getSessionValue(request, LOGIN_RAND_IMG, String.class);
		String name = "";
		try {

			String ipAddr = CtxHelper.getIpAddr(request);
			SymUser bean = new SymUser();
			bean.setLoginId(loginid);
			// bean.setPassWord(MD5Tools.MD5(password));
			bean.setPassWord(password);
			SymUser user = null;
			// 校验输入格式,用户密码为空
			checkLoginFormat(bean);

			log.debug("*****密码登录 用户 LOGINID:{},PWD:{},IP:{} 进行登录******", loginid, password, ipAddr);

			// 密码验证
			if (USE_LOGIN_PWD_CHECK) {
				user = checkPwdWithUpdate(bean);
			}
			// 非密码验证,直接验证是否存在该用户名的唯一用户
			else {
				checkExistWithUpdate(bean);
			}

			// 检验用户是否可用
			checkUserStatus(user);
			SymRole role = roleService.get(user.getRoleId());
			ad.setData(role);
			CtxHelper.saveUserStatus(user, request, response);
			CtxHelper.saveUserRole(request, role);
			MDCUtil.putMDCbyRequest(user, request, response);
			log.info("*****用户 LOGINID:{},NAME:{},IP:{} 登录成功******", loginid, name, ipAddr);
			ad.setMsg(ActionMsgParam.ACM_LOGIN_SUC);
			ad.setSuccess(true);

		} catch (RuntimeException e) {
			// log.info( "登录有误！用户
			// LOGINID:{},PWD:{},NAME:{}",loginid,password,name, e);
			ad.setMsg(DataUtil.getRootCauseMsg(e));
			e.printStackTrace();
			ad.setSuccess(false);
		} catch (Exception e) {
			// log.error( "登录失败！用户
			// LOGINID:{},PWD:{},NAME:{}",loginid,password,name, e);
			ad.setMsg(DataUtil.getRootCauseMsg(e));
			ad.setData(e.toString());
			ad.setSuccess(false);
		}
		CtxHelper.setNoCache(response);
		CtxHelper.writeToJson(response, ad);
	}

	/**
	 * 登录接口（web和app） 返回http状态201成功，401用户名或密码错误！，500登录出错！
	 * 
	 * @param loginid
	 * @param password
	 * @param imei
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@Comment("获取微信openid登录")
	@RequestMapping(value = "loginByOpenid")
	public void loginByOpenid(HttpServletRequest request, HttpServletResponse response) throws IOException {

		log.info("用户登录by openid");
		// if(log.isDebugEnabled())
		// log.debug("内容:",JsonHelper.toJSONString(bean));
		AccData ad = CtxHelper.createFailAccData();
		ad.setMsg(ActionMsgParam.ACM_LOGIN_ERR);
		// HttpSession session=request.getSession();
		// String openId=(String) session.getAttribute(LOGIN_OPENID);
		String openId = SessionHelper.getSessionValue(request, LOGIN_OPENID, String.class);
		String name = "";
		try {

			String ipAddr = CtxHelper.getIpAddr(request);
			SymUser bean = new SymUser();

			log.debug("*****用户 OpenId:{},IP:{} 进行登录******", openId, ipAddr);

			// 检验wx用户是否绑定了账号
			// checkWxExistWithUpdate( bean);
			// 检验用户是否可用
			checkUserStatus(bean);

			// 检验用户是否有登录
			// checkUserIsUnLogging(bean);
			// bean.setCurrentIp(ipAddr);
			CtxHelper.saveUserStatus(bean, request, response);

			log.info("*****用户OpenId:{},IP:{},LOGINID:{},NAME:{} 登录成功******", openId, bean.getLoginId(), name, ipAddr);
			ad.setMsg(ActionMsgParam.ACM_LOGIN_SUC);
			ad.setSuccess(true);

			// ad.setOtherStr(CtxHelper.getCurUser(request).getSymRole().getIndexUrl());

		} catch (RuntimeException e) {
			// log.info( "登录有误！用户
			// LOGINID:{},PWD:{},NAME:{}",loginid,password,name, e);
			ad.setMsg(DataUtil.getRootCauseMsg(e));
			ad.setSuccess(false);
		} catch (Exception e) {
			// log.error( "登录失败！用户
			// LOGINID:{},PWD:{},NAME:{}",loginid,password,name, e);
			ad.setMsg(DataUtil.getRootCauseMsg(e));
			ad.setData(e.toString());
			ad.setSuccess(false);
		}
		CtxHelper.setNoCache(response);
		CtxHelper.writeToJson(response, ad);
	}

	@Comment("退出登录")
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {

		log.info("用户退出");
		CtxHelper.setNoCache(response);
		CtxHelper.removeUserStatus(CtxHelper.getCurUser(request), request, response);
		// return new ModelAndView("redirect:/login.html");

		AccData ad = CtxHelper.createFailAccData();
		ad.setSuccess(true);
		ad.setMsg("退出成功");
		CtxHelper.setNoCache(response);
		CtxHelper.writeToJson(response, ad);
	}

	@Comment("获取当前用户")
	@RequestMapping(value = "curuser", method = RequestMethod.GET)
	public void curuser(HttpServletRequest request, HttpServletResponse response) throws IOException {

		AccData ad = CtxHelper.createFailAccData();
		String msg = ActionMsgParam.ACM_MOD_SUC;
		// String ac=ActionMsgParam.ACM_MOD_SUC;
		ad.setSuccess(true);
		try {
			SymUser user = CtxHelper.getCurUser(request);
			ad.setMsg(msg);
			ad.setData(user);
			// user.setPassWord(null);
			if (user == null) {
				ad.setErrcode(Err.not_login);
				showMsgException("用户未登录!");
				ad.setSuccess(false);
				response.sendRedirect("/login.html");

			}
			// user.setSalt(0);
			// user.setWaist(0);

			PropertyFilter profilter = new PropertyFilter() {

				public boolean apply(Object object, String name, Object value) {
					if (name.equalsIgnoreCase("passWord")) {
						// false表示last字段将被排除在外
						return false;
					}
					return true;
				}

			};

			SimplePropertyPreFilter filter = new SimplePropertyPreFilter(SymUser.class, "passWord");
			String json = JSONObject.toJSONString(user, profilter);

			SymUser user1 = JSON.parseObject(json, SymUser.class);
			ad.setData(user1);

		} catch (RuntimeException e) {
			log.info("获取当前用户有误！", e);
			ad.setMsg(DataUtil.getRootCauseMsg(e));
			ad.setSuccess(false);
		} catch (Exception e) {
			log.error("获取当前用户失败！", e);
			ad.setMsg(DataUtil.getRootCauseMsg(e));
			ad.setData(e.toString());
			ad.setSuccess(false);
		}
		CtxHelper.setNoCache(response);
		CtxHelper.writeToJson(response, ad);

	}

	@Comment("获取当前用户微信端")
	@RequestMapping(value = "curuserWX", method = RequestMethod.GET)
	public void curuserwx(@Comment("获取级别") @RequestParam(value = "info", required = false, defaultValue = "0") int info,
			HttpServletRequest request, HttpServletResponse response) throws IOException {

		AccData ad = CtxHelper.createFailAccData();
		String msg = ActionMsgParam.ACM_MOD_SUC;
		// String ac=ActionMsgParam.ACM_MOD_SUC;
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		Map<String, Object> mapUser = new LinkedHashMap<String, Object>();
		Map<String, Object> mapRole = new LinkedHashMap<String, Object>();
		// JSONObject mapUser=null;
		// JSONObject mapRole=null;

		try {
			SymUser user = CtxHelper.getCurUser(request);
			if (user == null) {
				showMsgException("用户未登录!");
			}
			// user.setSalt(0);
			// user.setWaist(0);
			mapUser.put("id", user.getId());
			mapUser.put("name", user.getName());
			mapUser.put("loginId", user.getLoginId());

			// mapUser=JsonHelper.parseObject(JsonHelper.toJSONString(new
			// String[]{"id","name","loginId","isMaintenance","isOwner","isManager","isCustomer","roleID"},
			// user));
			// mapRole=JsonHelper.parseObject(JsonHelper.toJSONString(JsonHelper.toJSONString(new
			// String[]{"id","name","indexUrl"}, user.getSymRole())));
			map.put("user", mapUser);
			map.put("role", mapRole);
			ad.setData(map);
			// ad.setOtherStr(JsonHelper.toJSONString(new
			// String[]{"id","name","indexUrl"}, user.getSymRole()));

			ad.setMsg(msg);
			ad.setSuccess(true);

		} catch (RuntimeException e) {
			log.info("获取当前用户有误！", e);
			ad.setMsg(DataUtil.getRootCauseMsg(e));
			ad.setSuccess(false);
		} catch (Exception e) {
			log.error("获取当前用户失败！", e);
			ad.setMsg(DataUtil.getRootCauseMsg(e));
			ad.setData(e.toString());
			ad.setSuccess(false);
		}
		CtxHelper.setNoCache(response);
		CtxHelper.writeToJson(response, ad);

	}

	public static final String LOGIN_RAND_IMG = "loginRandImg";
	public static final String LOGIN_OPENID = "loginOpenId";

	/**
	 * 校验验证码
	 * 
	 * @param requestImg
	 * @param sessionImg
	 *            void
	 * @exception @since
	 *                1.0.0
	 */
	public static void checkRandImgCode(String requestImg, String sessionImg) {
		if (isNull(requestImg) || isNull(sessionImg))
			showMsgException("验证码不能为空!");
		if (!sessionImg.equalsIgnoreCase(requestImg))
			showMsgException("验证码错误!");
	}

	/**
	 * 检查登录必填信息
	 * 
	 * @param sysUser
	 *            void
	 * @exception @since
	 *                1.0.0
	 */
	public static void checkLoginFormat(SymUser sysUser) {
		if (sysUser == null)
			showMsgException("用户名和密码不能为空!");
		if (isNull(sysUser.getLoginId()))
			showMsgException("用户名不能为空!");
		// if(isNull(sysUser.getName()))
		// showMsgException("用户名不能为空!");
		if (isNull(sysUser.getPassWord()))
			showMsgException("密码不能为空!");
	}

	/**
	 * 检查用户密码并且更新
	 * 
	 * @param sysUser
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 *             void
	 * @exception @since
	 *                1.0.0
	 */
	public SymUser checkPwdWithUpdate(SymUser sysUser) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		// boolean flag=service.checkUserNotEncryptedWithUpdate(sysUser);
		SymUser isUser = service.checkUserEncryptedWithUpdate(sysUser);
		if (isUser == null)
			showMsgException("登录失败!请检查用户名或者密码是否正确!");

		return isUser;

	}

	/**
	 * 检查sysUser是否在数据库存在，若存在更新为数据库中的信息
	 * 
	 * @param sysUser
	 *            void
	 * @exception @since
	 *                1.0.0
	 */
	@SuppressWarnings("unchecked")
	public void checkExistWithUpdate(SymUser sysUser) {
		boolean flag = false;
		System.out.println("LoginController.checkExistWithUpdate() " + "");
		// List<SymUser>
		// userList=service.findByCondition(PageControl.getQueryOnlyInstance()
		// //获得基础分页条件
		// .setOrder("order by id desc") //设置排序参数
		// ,
		// SqlBuffer.begin() //创建查询条件缓冲区
		// .add("loginID", sysUser.getLoginId()) //like查询，过滤null
		// .end());
		// if(userList!=null&&1==userList.size()){
		// SymUser bean=userList.get(0);
		// org.springframework.beans.BeanUtils.copyProperties(bean, sysUser);
		// flag=true;
		// }

		// SymUser bean = service.getByLoginId(sysUser.getLoginId());
		// if (bean != null) {
		// org.springframework.beans.BeanUtils.copyProperties(bean, sysUser);
		// flag = true;
		// }
		// if (!flag)
		// showMsgException("登录失败!请检查用户名是否存在!");
	}

	/**
	 * 检查sysUser是否在数据库存在，若存在更新为数据库中的信息
	 * 
	 * @param sysUser
	 *            void
	 * @exception @since
	 *                1.0.0
	 */
	@SuppressWarnings("unchecked")
	public void checPhoneExistWithUpdate(String phone, SymUser sysUser) {
		boolean flag = false;
		System.out.println("LoginController.checPhoneExistWithUpdate() " + "");
		// List<SymUser>
		// userList=service.findByCondition(PageControl.getQueryOnlyInstance()
		// //获得基础分页条件
		// .setOrder("order by id desc") //设置排序参数
		// ,
		// SqlBuffer.begin() //创建查询条件缓冲区
		// .add("mbPhone", phone) //like查询，过滤null
		// .end());
		// if(userList!=null&&1==userList.size()){
		// SymUser bean=userList.get(0);
		// org.springframework.beans.BeanUtils.copyProperties(bean, sysUser);
		// flag=true;
		// }

		// SymUser bean = service.getByPhone(phone);
		// if (bean != null) {
		// org.springframework.beans.BeanUtils.copyProperties(bean, sysUser);
		// flag = true;
		// }
		// if (!flag)
		// showMsgException("登录失败!请检查用户名是否存在!");
	}

	/**
	 * 检查帐号是否停用
	 * 
	 * @param sysUser
	 *            void
	 * @exception @since
	 *                1.0.0
	 */
	public static void checkUserStatus(SymUser sysUser) {

		boolean flag = ConstParam.STATUS_ENABLE == sysUser.getIsEnable();
		if (!flag)
			showMsgException("用户密码错误或者账号已经禁用!");// 模糊提示
	}
	// /**
	// * 检查用户是否处于登录状态中
	// * @param sysUser
	// *void
	// * @exception
	// * @since 1.0.0
	// */
	// public void checkUserIsUnLogging(SymUser sysUser){
	// boolean
	// flag=!(isNotNull(comnOnlineUserService.findByUserId(sysUser.getUserId())));
	// if(!flag){
	// showMsgException("该用户登录状态中!");
	// }
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ssxt.rdbox.common.web.SpringBaseController#getCommentByHbm()
	 */
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
		return "登录管理";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ssxt.rdbox.common.web.SpringBaseController#getService()
	 */
	@Override
	public GenericServiceImpl<SymUser, Integer> getService() {
		return service;
	}

}
