/**
 * <b>项目名：</b>DMS+运维系统平台<br/>
 * <b>包名：</b>com.ssxt.rdbox.common.util<br/>
 * <b>文件名：</b>MDCUtil.java<br/>

 * <b>版本信息：</b><br/>
 * <b>日期：</b>2017年1月4日-下午2:30:47<br/>
 * <b>Copyright (c)</b> 2017圣世信通-版权所有<br/>
 * 
 */
package com.ssxt.common.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.MDC;

import com.ssxt.web.bean.SymUser;

/**
 * <b>类名称：</b>MDCUtil<br/>
 * <b>类描述：</b><br/>
 * <b>创建人：</b>杨培新<br/>
 * <b>修改人：</b>杨培新<br/>
 * 
 * <b>修改时间：</b>2017年1月4日 下午2:30:47<br/>
 * <b>修改备注：</b><br/>
 * 
 * @version 1.0.0<br/>
 */
public class MDCUtil {

	public static void putMDCbyRequest(SymUser user, HttpServletRequest request, HttpServletResponse response) {
		MDC.put("rAddr", request.getRemoteAddr());
		MDC.put("rHost", request.getRemoteHost());
		if (user != null) {
			MDC.put("tenantId", "未知");
			MDC.put("userId", user.getId());
			MDC.put("userName", user.getName());
			MDC.put("addvcd", user.getAddvcd());
		}
	}

	public static String getAddvcd() {
		return MDC.get("addvcd").toString();
	}

	/**
	 * 从MDC保存值
	 * 
	 * @param key
	 * @param o
	 *            void
	 * @exception @since
	 *                1.0.0
	 */
	public static void put(String key, Object o) {
		MDC.put(key, o);
	}

	/**
	 * 从MDC获取值
	 * 
	 * @param key
	 * @return Object
	 * @exception @since
	 *                1.0.0
	 */
	public static Object get(String key) {
		return MDC.get(key);
	}

	public static Object get(String key, Object defaultValue) {
		Object o = get(key);
		return DataUtil.isNull(o) ? defaultValue : o;
	}

	public static void clearMDC() {
		MDC.clear();
	}

	/**
	 * 
	 * 获取当前用户id (注意事项):仅在http连接中、且用户已经登录时有用<br/>
	 * 
	 * @return long
	 * @exception @since
	 *                1.0.0
	 */
	public static Integer getUserId() {
		return DataUtil.notNullInt(MDC.get("userId"), 0);
	}

	/**
	 * 获取当前用户名 (注意事项):仅在http连接中、且用户已经登录时有用<br/>
	 * 
	 * @return String
	 * @exception @since
	 *                1.0.0
	 */
	public static String getUserName() {
		return (String) get("userName", "未知人员");

	}

	/**
	 * 获取当前租户id (注意事项):仅在http连接中有用<br/>
	 * 
	 * @return String
	 * @exception @since
	 *                1.0.0
	 */
	public static String getTenantId() {
		return (String) get("tenantId", "通用");

	}

	/**
	 * 移除当前用户的某个键 (注意事项):仅在http连接中有用<br/>
	 * 
	 * @param key
	 *            void
	 * @exception @since
	 *                1.0.0
	 */
	public static void remove(String key) {
		MDC.remove(key);

	}

}
