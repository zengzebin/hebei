/**
 * <b>项目名：</b>DMS+运维系统平台<br/>
 * <b>包名：</b>com.ssxt.rdbox.common.util<br/>
 * <b>文件名：</b>SessionHelper.java<br/>

 * <b>版本信息：</b><br/>
 * <b>日期：</b>2016年12月16日-上午9:06:26<br/>
 * <b>Copyright (c)</b> 2016圣世信通-版权所有<br/>
 * 
 */
package com.ssxt.common.util;

import javax.servlet.http.HttpServletRequest;

/**
 * <b>类名称：</b>SessionHelper<br/>
 * <b>类描述：</b><br/>
 * <b>创建人：</b>杨培新<br/>
 * <b>修改人：</b>杨培新<br/>
 * 
 * <b>修改时间：</b>2016年12月16日 上午9:06:26<br/>
 * <b>修改备注：</b><br/>
 * @version 1.0.0<br/>
 */
public class SessionHelper {


	public static <T> T  getSessionValue(HttpServletRequest request,String name,Class<T> clz){
		return clz.cast(getSessionValue(request,name));
	}
	public static Object getSessionValue(HttpServletRequest request,String name){
		return request.getSession().getAttribute(name);
	}
	public static void setSessionValue(HttpServletRequest request,String name,Object value){
		if(value==null){
			request.getSession().removeAttribute(name);
		}
		else request.getSession().setAttribute(name,value);
	}
	public static String getSendPhone(HttpServletRequest request){
		return getSessionValue(request, "send_phone", String.class);
	}
	public static String setSendPhone(HttpServletRequest request,String phone){
		return (String) request.getSession().getAttribute("send_phone");
	}
	

	public static String getSendCode(HttpServletRequest request){
		return getSessionValue(request, "send_code", String.class);
	}
	public static String setSendCode(HttpServletRequest request,String code){
		return (String) request.getSession().getAttribute("send_code");
	}
}
