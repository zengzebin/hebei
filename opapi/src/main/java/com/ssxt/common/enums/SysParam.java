package com.ssxt.common.enums;

/**
 * 
 * 
 * <b>类名称：</b>SysParam<br/>
 * <b>类描述：</b>系统管理的基本参数信息<br/>

 * <b>创建人：</b>杨培新<br/>

 * <b>修改人：</b>杨培新<br/>

 * <b>修改时间：</b>Sep 7, 2011 9:17:17 PM<br/>
 * <b>修改备注：</b><br/>
 * @version 1.0.0<br/>
 *
 */
public class SysParam{
	
	public static final Integer[] USER_NOT_ALLOW_DEL=new Integer[]{3,177};
	
	public static final int BRANCH_NO=0;
	
	public static final int BRANCH_YES=1;
	
	public static final int DPT_ROOT=0;

	
	public static final String GET_USER_TEACHER_SQL="select * from sys_user where  User_ID in (select Tea_ID from teacher_info)";
	
	public static final String GET_USER_ENABLE_SQL="select * from sys_user where Enable=0 ";		
	
	public static final String GET_USER_ROLE_SQL="SELECT ID,status,name,Show_Home,memo FROM sys_role where Status=0 and ID in (SELECT Role_ID FROM sys_user_role where User_ID=?)";
	
	public static final String GET_ROLE_ALLOW_URL_SQL_PRE="select * from sys_power_url where Status=0 and id in (select URL_ID from sys_role_allow_power_url where Role_ID in (";
	
	public static final String GET_ROLE_DENY_URL_SQL_PRE="select * from sys_power_url where Status=0 and id in (select URL_ID from sys_role_deny_power_url where Role_ID in (";

}