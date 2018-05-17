/**
 * <b>项目名：</b>DMS+运维系统平台<br/>
 * <b>包名：</b>com.ssxt.rdbox.common.web<br/>
 * <b>文件名：</b>SessionHelper.java<br/>

 * <b>版本信息：</b><br/>
 * <b>日期：</b>2016年12月16日-上午9:06:26<br/>
 * <b>Copyright (c)</b> 2016圣世信通-版权所有<br/>
 * 
 */
package com.ssxt.common.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.ssxt.common.util.DataUtil;

/**
 * <b>类名称：</b>SessionHelper<br/>
 * <b>类描述：</b><br/>
 * <b>创建人：</b>杨培新<br/>
 * <b>修改人：</b>杨培新<br/>
 * 
 * <b>修改时间：</b>2017年1月22日 下午2:36:20<br/>
 * <b>修改备注：</b><br/>
 * 
 * @version 1.0.0<br/>
 */
public class SessionHelper {

	public static <T> T getSessionValue(HttpServletRequest request, String name, Class<T> clz) {
		return clz.cast(getSessionValue(request, name));
	}

	public static HttpSession getSession(HttpServletRequest request) {
		HttpSession session = null;
		String token = null;
		String qstr = request.getQueryString();
		if (!DataUtil.isNull(qstr) && qstr.contains("_ypxtk=")) {
			token = DataUtil.getStringAfter(qstr, "_ypxtk=", false);
			token = DataUtil.getStringBefore(token, "&");
			if (!DataUtil.isNull(token)) {
				session = MySessionContext.getInstance().getSession(token);
			}
		}
		if (session == null) {
			session = request.getSession();
		}
		return session;
	}

	public static Object getSessionValue(HttpServletRequest request, String name) {
		return getSession(request).getAttribute(name);
	}

	public static void setSessionValue(HttpServletRequest request, String name, Object value) {
		HttpSession session = getSession(request);
		if (value == null) {
			session.removeAttribute(name);
		} else
			session.setAttribute(name, value);
	}

	public static String getSendPhone(HttpServletRequest request) {
		return getSessionValue(request, "send_phone", String.class);
	}

	public static String setSendPhone(HttpServletRequest request, String phone) {
		return (String) getSession(request).getAttribute("send_phone");
	}

	public static String getSendCode(HttpServletRequest request) {
		return getSessionValue(request, "send_code", String.class);
	}

	public static void setSendCode(HttpServletRequest request, String code) {
		setSessionValue(request, "send_code",code);
	}

	public static String getPhone(HttpServletRequest request) {
		return getSessionValue(request, "phone", String.class);
	}

	public static void setPhone(HttpServletRequest request, String phone) {
		setSessionValue(request, "phone",phone);
	}
	

	public static String getLoginid(HttpServletRequest request) {
		return getSessionValue(request, "Loginid", String.class);
	}

	public static void setLoginid(HttpServletRequest request, String Loginid) {
		setSessionValue(request, "Loginid",Loginid);
	}
}
