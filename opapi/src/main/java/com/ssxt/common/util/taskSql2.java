package com.ssxt.common.util;

public class taskSql2 {

	// web任务单
	public static String webTaskSQL = "SELECT  task.dataType dataType, task.taskNo taskNo, task.addvcd addvcd,task.ADDVNM addvnm, task.taskStatus taskStatus,"
			+ " task.taskStatusName taskStatusName,task.maintUserId maintUserId,maint.name maintName,task.operateCode operateCode, task.operateName operateName,"
			+ " task.priorityName priorityName, task.priorityStatus priorityStatus, task.createTime createTime,task.modifyTime modifyTime, task.reciveTime reciveTime,"
			+ " task.content content, task.updateService updateService, task.soureType soureType,task.plannedId plannedId, createUser.name createUserName,"
			+ " task.deviceId deviceId,device.device_sn deviceSn,device.DEVICE_ADDR addr, device.device_name deviceName,"
			+ " soft.soft_name_cn softName,soft.id softId," 
			+ " hard.hardName hardName,hard.id hardId,view.ownerId" 
			+ " FROM"
			+ " core_device_task_info task " 
			+ " LEFT JOIN sym_user maint  ON (task.maintUserId = maint.ID) "
			+ " LEFT JOIN sym_user createUser ON (task.createId = createUser.ID)"
			+ " LEFT JOIN bas_device_info device ON(device.id=task.deviceId)"
			+ " LEFT JOIN gw_stinfo_b stinfo ON(stinfo.stcd=device.stcd)"
			+ " LEFT JOIN bas_soft_install_info soft ON(task.softId=soft.id)"
			+ " LEFT JOIN bas_hard_info hard ON(task.hardId=hard.id)"
			+ " LEFT JOIN task_owner_view view ON(view.taskNo=task.taskNo)";

	public static String device = "";

	public static String soft = "";

	public static String hash = "";

	
	//微信
	public static String weChatSQL = "SELECT task.dataType dataType,task.taskNo taskNo,task.addvcd addvcd,"
			+" task.maintUserId maintUserId,task.taskStatusName taskStatusName,task.taskStatus taskStatus,"
			+" task.ADDVNM addvnm,task.operateCode operateCode,task.operateName operateName,task.lastOperateName,"
			+" task.priorityStatus priorityStatus,task.priorityName priorityName,"
			+" task.updateCustomer updateCustomer,task.updateMaint updateMaint,"
			+" task.createTime createTime,task.modifyTime  modifyTime,"
			+" task.content content,task.soureType soureType,task.plannedId plannedId, "
		    +" task.isSign isSign,0 taskProblemList,"
		    +" task.deviceId deviceId,task.hardId hardId,task.softId softId,"
		    +" owner.ownerId "
		    +" FROM core_device_task_info  task"
			+" LEFT JOIN sym_user createUser ON (task.createId = createUser.ID)"
			+" LEFT JOIN task_owner_view owner ON (task.taskNo = owner.taskNo)";

	
	//绩效统计
	 public  static String sqlPerformance = "SELECT  task.taskNo,task.createTime createTime,task.finishTime,"
			+ " c.name maintName ,task.content content,"
			+ " TIMESTAMPDIFF(MINUTE,task.starttime,task.reciveTime) responseTime,"
			+ " TIMESTAMPDIFF(MINUTE,task.dealTime,task.dealFinished) restorationTime,owner.ownerId "
			+ " FROM  core_device_task_info task  " 
			+ " LEFT JOIN sym_user c ON(task.maintUserId=c.id)"
			+ " LEFT JOIN bas_device_info device ON(device.Id=task.deviceId)"
			+ " LEFT JOIN bas_hard_info hard ON(task.hardId=hard.id)"
			+ " LEFT JOIN bas_soft_install_info soft ON(task.softId=soft.id)"
			+ " LEFT JOIN task_owner_view owner ON (task.taskNo = owner.taskNo)";

 
	
	//维修人员测站查找
	public static String maintenanceFindStation="SELECT  station.stcd,station.STNM,"
				+ "  LGTD lat , LTTD log "
			    + "  FROM  gw_stinfo_b station"
				+ "  inner join bas_device_info device on(device.stcd=station.stcd)" 
		        + "  inner JOIN sym_addvcd_user  addvcdUser ON(device.region_id=addvcdUser.addvcd)";
	//业主测站测站
	public static String ownerFindStation="SELECT  station.stcd,station.STNM,"
			+ "  LGTD lat , LTTD log "
		    + "  FROM  gw_stinfo_b station"
			+ "  inner join bas_device_info device on(device.stcd=station.stcd)"; 
	    
	
	//机房查找
	public static String maintenanceFindRoom=" SELECT  id,roomName,lat,log "
			+ "FROM  bas_server_room  room" 
			+" inner JOIN sym_addvcd_user  addvcdUser ON(room.addvcd=addvcdUser.addvcd)";

	//测站及设备信息
	public static String stationDeviceInfo="SELECT  a.id deviceId, a.device_name deviceName ,"
			+" a.install_time installTime,a.device_info deviceContent,"
    		+" device_model deviceModel,station.STCD stcd ,station.STNM STNM ,a.DEVICE_ADDR deviceAddr,"
    		+" e.ADDVFULL addvcd ,c.owner_name ownerName,d.arg_name deviceType "
			+" FROM   bas_device_info  a"  
			+" INNER JOIN  gw_stinfo_b   station   ON(a.stcd=station.stcd)"
			+" LEFT  JOIN  gw_addvcd_b   b   ON(a.region_id= b.ADDVCD)"
            +" LEFT JOIN  bas_owner_units   c   ON(a.owner_id=c.owner_no)"
		    +" LEFT JOIN  bas_param   d   ON(a.device_type=d.arg_value)"
			+" LEFT JOIN  gw_addvcd_b   e   ON(a.region_id=e.addvcd)";
	
	

}