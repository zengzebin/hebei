package com.ssxt.common.util;

public class autoSql {

	//自动报障统计
	public static String autoErrSql ="SELECT a.id id,a.deviceAddr deviceAddr,a.errTypeCode errTypeCode,a.createTime createTime,"
			+" task.startTime startTime,task.endTime endTime,task.num,task.taskNo,task.doStatus,"
			+" device.device_sn deviceSn,device.id deviceId, device.device_name deviceName,stinfo.stnm stnm,stinfo.stcd stcd,"
			+" errType.name errTypeName,addvcd.ADDVNM addvnm,param.arg_name  deviceType,b.deviceAddr isObserver  "
			+" FROM core_err_report  a"
			+" INNER    JOIN  autotask task ON (a.createTime=task.createTime AND a.deviceAddr=task.addr AND a.errTypeCode=task.errType)"
			+" INNER  JOIN  bas_device_info  device ON (device.DEVICE_ADDR=a.deviceAddr)"
			+" LEFT JOIN gw_stinfo_b stinfo ON(stinfo.stcd=device.stcd)"
			+" INNER JOIN   core_err_type errType  ON(errType.code=a.errTypeCode)"
  			+" LEFT JOIN gw_addvcd_b  addvcd ON(addvcd.ADDVCD=device.region_id)"
			+" LEFT JOIN  bas_param param  ON(device.device_type=param.arg_value)"
			+" LEFT JOIN core_device_observer b  ON(a.createTime =b.createTime AND a.deviceAddr = b.deviceAddr  AND a.errTypeCode = b.errTypeCode)";
          //  +" inner JOIN sym_addvcd_user addvcdUser on(addvcdUser.ADDVCD=addvcd.ADDVCD)";
		 

	public static String autoDate ="SELECT a.id id,a.createTime createTime,"
			+" device.DEVICE_ADDR deviceAddr,device.device_sn deviceSn,device.id deviceId, device.device_name deviceName,stinfo.stnm,"
			+" errType.name errTypeName,addvcd.ADDVNM addvnm,param.arg_name  deviceType,b.deviceAddr isObserver  "
			+" FROM core_err_report  a"
		    +" INNER  JOIN  bas_device_info  device ON (device.DEVICE_ADDR=a.deviceAddr)"
			+" LEFT JOIN gw_stinfo_b stinfo ON(stinfo.stcd=device.stcd)"
			+" INNER JOIN   core_err_type errType  ON(errType.code=a.errTypeCode)"
 			+" LEFT JOIN gw_addvcd_b  addvcd ON(addvcd.ADDVCD=device.region_id)"
			+" LEFT JOIN  bas_param param  ON(device.device_type=param.arg_value)"
			+" LEFT JOIN core_device_observer b  ON(a.createTime =b.createTime AND a.deviceAddr = b.deviceAddr  AND a.errTypeCode = b.errTypeCode)";

	 
	 
	// 设备自动报障web问题列表
	public static String autoProblemSql = " SELECT  device.id deviceId,device.DEVICE_ADDR addr,device.device_name deviceName, "
			+ " param.arg_name  deviceType,GROUP_CONCAT(errType.name) errTypeName,addvcd.ADDVNM addvnm"
			+ " FROM core_err_report task" 
			+ " INNER   JOIN  bas_device_info  device ON (device.DEVICE_ADDR=task.deviceAddr)"
			+ " INNER   JOIN   core_err_type errType  ON(errType.code=task.errTypeCode) "
			+ " LEFT    JOIN   bas_param param     ON(device.device_type=param.arg_value)"
			+ " LEFT    JOIN  gw_addvcd_b  addvcd ON(addvcd.ADDVCD=device.region_id)";


	// 人工问题
	public static String peopleProblemSql = "SELECT  provider,providerDeptname,sourcePos,"
			+ "souceTel,content   FROM  core_err_report" ;

	//维护计划
	public static String planSql = " SELECT reportContent,reportTime,u.name,"
			+ "CASE  isNormal WHEN 1  THEN '正常' ELSE '异常' END error "
			+ "FROM  core_operation_plan_dtl  plan "
			+ "LEFT JOIN  sym_user u ON(u.id=plan.reportUserID) ";
 

}