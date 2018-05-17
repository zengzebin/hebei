package com.ssxt.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ssxt.common.enums.ConstParam;
import com.ssxt.common.enums.LoginParam;
import com.ssxt.common.web.SessionHelper;
import com.ssxt.web.bean.SymRole;
import com.ssxt.web.bean.SymUser;

public class CtxHelper {
	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(CtxHelper.class);
	public static final String FILE_SEP = ConstParam.FILE_SEP;
	public static final String Line_SEP = ConstParam.Line_SEP;
	public static final String WEB_LINE_SEP = "<br/>";
	public static final String WEB_NEW_LINE = "\n";
	public static final String SYS_NEW_LINE = Line_SEP;

	/**
	 * 获取本地url
	 * 
	 * @param request
	 * @return
	 */
	public static String getUrl(HttpServletRequest request) {
		String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/";
		return url;
	}

	/**
	 * 获取本地服务器路径
	 * 
	 * @param request
	 * @return
	 */
	public static String getPath(HttpServletRequest request) {
		String path = request.getSession().getServletContext().getRealPath("");
		return path.substring(0, path.lastIndexOf("\\") + 1);// tmocat路径
	}

	/**
	 * 取消 缓存
	 * 
	 * @param response
	 */
	public static void setNoCache(HttpServletResponse response) {
		response.setDateHeader("expries", -1);
		response.setHeader("pragma", "no-cache");
		response.setHeader("cache-control", "");
	}

	/**
	 * 
	 * 获取当前用户ID<br/>
	 * 获取不到返回-1
	 * 
	 * @param request
	 * @return long
	 * @exception @since
	 *                1.0.0
	 */
	public static Integer getCurUserId(HttpServletRequest request) {
		Integer userId = -1;

		SymUser user = getCurUser(request);
		if (user != null && user.getId() != null) {
			userId = user.getId();
		}
		if (userId == -1) {
			DataUtil.showMsgException("会话已经失效，请重新登陆!");
		}

		return userId;
	}

	/**
	 * 
	 * 获取当前用户Name<br/>
	 * 获取不到返回defaultName
	 * 
	 * @param request
	 * @param defaultName
	 * @return String
	 * @exception @since
	 *                1.0.0
	 */
	public static String getCurUserName(HttpServletRequest request, String defaultName) {
		String userName = defaultName;

		SymUser user = getCurUser(request);
		if (user != null && !DataUtil.isNull(user.getName())) {
			userName = user.getName();
		}
		return userName;
	}

	/**
	 * 
	 * 获取当前用户<br/>
	 * 
	 * @return SysUser
	 * @exception @since
	 *                1.0.0
	 */
	public static SymUser getCurUser(HttpServletRequest request) {
		SymUser user = null;
		try {
			request.getAttribute(LoginParam.SESSION_USER_NAME);
			user = (SymUser) SessionHelper.getSession(request).getAttribute(LoginParam.SESSION_USER_NAME);
			// if (user == null) {
			// user = new SymUser();
			// user.setId(100);
			// user.setName("測試的賬號呢");
			// }

		} catch (Throwable t) {
			log.error("获取当前用户出错", t);
		}
		return user;
	}

	/**
	 * 
	 * @Title: getCurrentRequest
	 * @author：zzb
	 * @date：2016年1月13日下午6:14:43
	 * @Description: 获取当前request
	 * @return
	 * @throws IllegalStateException
	 *             当前线程不是web请求抛出此异常.
	 */
	public static HttpServletRequest getCurrentRequest() throws IllegalStateException {
		ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if (attrs == null) {
			throw new IllegalStateException("当前线程中不存在 Request 上下文");
		}
		return attrs.getRequest();
	}

	/**
	 * 
	 * 设置当前用户<br/>
	 * 
	 * @return SysUser
	 * @exception @since
	 *                1.0.0
	 */
	public static SymUser setCurUser(HttpServletRequest request, SymUser user) {
		try {
			request.getSession().setAttribute(LoginParam.SESSION_USER_NAME, user);
			
		} catch (Throwable t) {
			log.error("保存当前用户出错", t);
		}
		return user;
	}

	/**
	 * 获取Spring上下文
	 * 
	 * @return
	 */
	public static ApplicationContext getApplicationContext() {
		return SpringBeanUtil.getInstance().getApplicationContext();
	}

	/**
	 * 获取某个Spring注入的Bean
	 * 
	 * @param id
	 * @param clasz
	 * @return
	 */
	public static <T> T getSpringBean(String id, Class<T> clasz) {
		return SpringBeanUtil.getBean(id, clasz);
	}
	//
	// public static SymRole currentRole(HttpServletRequest request) {
	// SymRole role = null;
	// try {
	// role = (SymRole)
	// request.getSession().getAttribute(LoginParam.SESSION_ROLE_MODULE);
	//
	// if (role == null) {
	//
	// }
	// } catch (Exception e) {
	//
	// }
	// return role;
	// }

	/**
	 * 保存当前角色
	 * 
	 * @param request
	 * @param role
	 * @return
	 */
	public static SymRole saveUserRole(HttpServletRequest request, SymRole role) {
		try {
			request.getSession().setAttribute(LoginParam.SESSION_ROLE_MODULE, role);
		} catch (Throwable t) {
			log.error("保存当前用户出错", t);
		}
		return role;
	}

	/**
	 * 
	 * 设置当前用户所有状态<br/>
	 * 
	 * @return SysUser
	 * @exception @since
	 *                1.0.0
	 */
	public static SymUser saveUserStatus(SymUser user, HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = null;
			session = request.getSession();
			if (session != null) {
				session.invalidate();
				session = request.getSession(true);
			}
			CtxHelper.setCurUser(request, user);
			// UserService userSerevice=CtxHelper.getSpringBean("userService",
			// UserService.class);
			// DeptService deptService=CtxHelper.getSpringBean("deptService",
			// DeptService.class);
			// session.setAttribute(ConstParam.LoginParam.SESSION_USER_NAME,
			// user);
			// Dept dept=deptService.getDept(user.getDeptCode());
			// session.setAttribute(LoginParam.SESSION_DEPT, dept);
			//
			// session.setAttribute(LoginParam.SESSION_USER_MODULE,
			// userSerevice.getUserModule(user));
			// session.setAttribute(LoginParam.SESSION_ROLE_MODULE,
			// userSerevice.getRoleModule(user.getRoleId()));
			//
			// // session.setAttribute(LoginParam.SESSION_USER_PERMITSSION,
			// userSerevice.getUserPermission(user));
			// session.setAttribute(LoginParam.SESSION_ROLE_PERMITSSION,
			// userSerevice.getRolePermission(user.getRoleId()));
			// session.setAttribute(LoginParam.SESSION_PERMITSSION,
			// userSerevice.getPermission());

		} catch (Throwable t) {
			throw new RuntimeException("保存用户状态出错", t);
		}
		return user;
	}

	/**
	 * 
	 * 设置当前用户所有状态<br/>
	 * 
	 * @return SysUser
	 * @exception @since
	 *                1.0.0
	 */
	public static SymUser removeUserStatus(SymUser user, HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();
			CtxHelper.setCurUser(request, null);
			request.getSession().setAttribute(LoginParam.SESSION_USER_MODULE, null);
			request.getSession().setAttribute(LoginParam.SESSION_ROLE_MODULE, null);
			request.getSession().invalidate();
			response.addHeader("pragma", "no-cache");
			response.addHeader("cache-control", "");
			response.setDateHeader("Expires", 0);

		} catch (Throwable t) {
			throw new RuntimeException("保存用户状态出错", t);
		}
		return user;
	}

//	public static HashMap getSessionRoleModule(HttpServletRequest request) {
//		return (HashMap) request.getSession().getAttribute(LoginParam.SESSION_ROLE_MODULE);
//
//	}

	public static SymRole getSymRole(HttpServletRequest request) {
		SymRole role = (SymRole) request.getSession().getAttribute(LoginParam.SESSION_ROLE_MODULE);
//		if (role == null) {
//			role = new SymRole();
//			role.setIsOperation(1);
//		}

		return role;
	}

	public static HashMap getSessionRolePermitssion(HttpServletRequest request) {
		return (HashMap) request.getSession().getAttribute(LoginParam.SESSION_ROLE_PERMITSSION);
	}

	// public static Dept getSessionDept(HttpServletRequest request){
	// return (Dept)
	// request.getSession().getAttribute(ConstParam.LoginParam.SESSION_DEPT);
	// }
	/**
	 * 校验token
	 * 
	 * @param reset
	 * @return
	 */
	public static boolean validateToken(boolean reset) {
		HttpServletRequest request = getRequest();
		return TokenProcessor.getInstance().isTokenValid(request, reset);
	}

	/**
	 * 
	 * @return
	 */
	public static AccData createFailAccData() {
		return new AccData(false, "no", "");
	}

	/**
	 * 
	 * @return
	 */
	public static AccData createSuccAccData() {
		return new AccData(true, "ok", "");
	}

	/**
	 * 
	 * @return
	 */
	public static AccData toSuccAccData(AccData ad) {
		ad.setMsg("操作成功");
		ad.setSuccess(true);
		return ad;
	}

	/**
	 * 
	 * 把AccData打印到JSON(使用toJson)
	 * 
	 * @param ad
	 * @return
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 * @exception @since
	 *                1.0.0
	 */
	public static void writeToJson(HttpServletResponse response, AccData ad) {
		responseResult(response, ad.toJson(), "UTF-8");
	}

	/**
	 * 
	 * 把AccData打印到JSON(使用toJson)
	 * 
	 * @param ad
	 * @return
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 * @exception @since
	 *                1.0.0
	 */
	public static void writeToJson(HttpServletResponse response, AccData ad, String callback) {

		CtxHelper.responseResult(response, DataUtil.addCallback(ad.toJson(), callback));

	}

	/**
	 * 处理警告信息，不记录日志
	 * 
	 * @param request
	 * @param response
	 * @param errType
	 * @param msg
	 * @param e
	 *            void
	 * @exception @since
	 *                1.0.0
	 */
	public static void doWarn(HttpServletRequest request, HttpServletResponse response, int errType, String msg,
			Exception e) {

		AccData ad = CtxHelper.createFailAccData();
		ad.setErrcode(errType);
		ad.setMsg(DataUtil.getRootCauseMsg(e));
		ad.setSuccess(false);
		CtxHelper.writeToJson(response, ad);
	}

	/**
	 * 处理异常信息，记录日志
	 * 
	 * @param request
	 * @param response
	 * @param errType
	 * @param msg
	 * @param e
	 *            void
	 * @exception @since
	 *                1.0.0
	 */
	public static void doError(HttpServletRequest request, HttpServletResponse response, int errType, String msg,
			Exception e) {

		log.error("操作失败！", e);
		AccData ad = CtxHelper.createFailAccData();
		ad.setErrcode(errType);
		ad.setMsg(DataUtil.getRootCauseMsg(e));
		ad.setSuccess(false);
		CtxHelper.writeToJson(response, ad);
	}

	/**
	 * 
	 * 把ad打印到JSON
	 * 
	 * @param ad
	 * @return
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 * @exception @since
	 *                1.0.0
	 */
	public static void writeToJsonWithToString(HttpServletResponse response, AccData ad) {
		responseResult(response, ad.toString(), "UTF-8");
	}

	/**
	 * 判断菜单有无url权限 checkUrlRight(作用)<br/>
	 * (适用条件):<br/>
	 * (执行流程):<br/>
	 * (使用方法):<br/>
	 * (注意事项):<br/>
	 * 
	 * @param urlRightMap
	 * @param url
	 * @return boolean
	 * @exception @since
	 *                1.0.0
	 */
	public static boolean checkUrlRight(Map<String, Object> urlRightMap, String url) {
		boolean flag = false;
		if (!ConstParam.URL_CHECK_ENABLE) {
			flag = true;
		} else {
			flag = urlRightMap != null && urlRightMap.containsKey(url);
		}
		return flag;

	}

	/**
	 * 响应 ajax提交的请求，返回字符串
	 * 
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	public static void responseResult(HttpServletResponse response, String result) {
		responseResult(response, result, "UTF-8");
	}

	/**
	 * 响应 ajax提交的请求，返回字符串
	 * 
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	public static void responseResult(HttpServletResponse response, String result, String charset) {

		try {
			// response.setContentType("application/json");
			// response.getOutputStream().write(result.getBytes(charset));
			// response.getOutputStream().flush();

			response.setCharacterEncoding(charset);
			response.setContentType("application/json; charset=utf-8");
			PrintWriter out = null;
			out = response.getWriter();
			out.append(result);
			out.flush();
			out.close();

		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("不支持的编码!", e);
		} catch (IOException e) {
			throw new RuntimeException("IO异常!", e);
		}
	}

	/**
	 * 获取当前的request 该方法可能会有问题，不过一般没问题
	 * 
	 * @return
	 */
	public static HttpServletRequest getRequest() {
		return getRequestAttributes().getRequest();
	}

	// public static HttpServletResponse getResponse() {
	// return ((ServletRequestAttributes)getRequestAttributes()).getRequest()
	// }
	// public static ServletRequestAttributes getServletRequestAttributes(){
	// return (ServletRequestAttributes)
	// RequestContextHolder.currentRequestAttributes();
	// }
	public static ServletRequestAttributes getRequestAttributes() {
		return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
	}

	/**
	 * 从小到大排序请求参数
	 * 
	 * @author 杨培新
	 * @return String
	 * @param params
	 * @param hreq
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String accessRequestParaStr(Enumeration params, HttpServletRequest hreq) {
		StringBuffer sb = new StringBuffer();
		ArrayList aList = Collections.list(params);
		Collections.sort(aList, acc); // Comparator用来实现排序规则
		String value = "";
		String param = "";
		String prefix = "";
		for (int i = 0; i < aList.size(); i++) {
			param = aList.get(i).toString();
			if ("randnum".equals(param))
				continue;
			value = hreq.getParameter(param);
			sb.append(prefix + param + "=" + value);
			prefix = "&";
		}
		return sb.toString();
	}

	/**
	 * 组装web信息参数
	 * 
	 * @param request
	 * @return String
	 * @exception @since
	 *                1.0.0
	 */
	public static String accessWebPara(HttpServletRequest request) {
		StringBuffer sb = new StringBuffer();
		String urlExt = CtxHelper.accessRequestParaStr(request.getParameterNames(), request);
		sb.append("IP:" + getIpAddr(request));
		sb.append("\tURL:" + request.getServletPath());
		sb.append("  Param:" + urlExt);
		sb.append(CtxHelper.Line_SEP);
		return sb.toString();
	}

	/**
	 * 通过reuqest参数获取地址
	 * 
	 * @param request
	 * @return String
	 * @exception @since
	 *                1.0.0
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (DataUtil.isNull(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (DataUtil.isNull(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (DataUtil.isNull(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (!DataUtil.isNull(ip) && ip.length() >= 20 && ip.contains(","))
			return ip.split(",")[0];
		return ip;
	}

	public static String markIpStr(HttpServletRequest request, String str) {
		return "IP:" + getIpAddr(request) + " -- " + str;
	}

	private static final AsciiComparator acc = new AsciiComparator();

	public static <T> List<T> copyList(List<T> src) throws IOException, ClassNotFoundException {
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(byteOut);
		out.writeObject(src);

		ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
		ObjectInputStream in = new ObjectInputStream(byteIn);
		@SuppressWarnings("unchecked")
		List<T> dest = (List<T>) in.readObject();
		return dest;
	}
}
