package com.ssxt.common.enums;

/**
 * 
 * 
 * <b>类名称：</b>ReportParam<br/>
 * <b>类描述：</b>报表HrReportAction的基本参数信息<br/>

 * <b>创建人：</b>杨培新<br/>

 * <b>修改人：</b>杨培新<br/>

 * <b>修改时间：</b>Sep 7, 2011 9:17:31 PM<br/>
 * <b>修改备注：</b><br/>
 * @version 1.0.0<br/>
 *
 */
public class ReportParam{
	
	public static final int SERIES_TYPE_DPT=0;
	public static final int SERIES_TYPE_LABEL=1;
	public static final int SERIES_TYPE_DPT_LABEL=2;

	public static final int LIST_SECTION_USER=0;
	public static final int LIST_SECTION_DPT=1;
	public static final int LIST_SECTION_LABEL=2;
	
	public static final int BY_SECTION=0;
	public static final int BY_SECTION_TIME=1;
	public static final int BY_TIME=2;
	public static final int BY_TIME_SECTION=3;
	/**
	 * 先用户/部门分组，再LABEL分列
	 */
	public static final int BY_USERDPT_LABEL=4;
	/**
	 * 先LABEL分组，再用户/部门分列
	 */
	public static final int BY_LABEL_USERDPT=5;
}