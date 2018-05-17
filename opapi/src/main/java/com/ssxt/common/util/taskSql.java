package com.ssxt.common.util;
/*package com.ssxt.rdbox.common.util;

public class taskSql {

	// 微信任务列表
	public static String weChatSQL = "SELECT task.dataType dataType,task.taskNo taskNo,task.addvcd addvcd,"
			+ " task.maintUserId maintUserId,task.taskStatusName taskStatusName,  task.taskStatus taskStatus,"
			+ " task.ADDVNM addvnm,  task.softNameCn softNameCn,task.operateCode operateCode,"
			+ " task.operateName operateName,task.priorityName priorityName,"
			+ " task.priorityStatus priorityStatus,task.updateCustomer updateCustomer,task.updateMaint updateMaint,"
			+ " task.createTime createTime,task.modifyTime  modifyTime,task.reciveTime reciveTime,"
			+ " task.content content,maint.name maintName,task.soureType soureType,task.plannedId plannedId, "
			+ " device.device_info deviceInfo, device.device_name deviceName ,"
			+ " device.device_sn deviceSn, device.log longitude, device.lat  latitude,task.isSign isSign,0 taskProblemList"
			+ " FROM task  task" + " LEFT JOIN sym_user  maint    ON (task.maintUserId=maint.ID)"// 维修人员
			+ " LEFT  JOIN  sym_addvcd_user  addvcdUser ON(task.ADDVCD=addvcdUser.ADDVCD)" // 所属区域任务单
			+ " LEFT JOIN bas_device_info device ON (device.device_sn =task.deviceSn)";
 
	
	public static String webTaskSQLnew="SELECT  task.dataType dataType, task.taskNo taskNo, task.ADDVNM addvnm, task.taskStatus taskStatus,"
			 +" task.taskStatusName taskStatusName,maint.name maintName,task.operateCode operateCode, task.operateName operateName,"
			 +" task.priorityName priorityName, task.priorityStatus priorityStatus, task.createTime createTime,task.modifyTime modifyTime,"
			 +" task.updateMaint updateMaint,task.updateCustomer updateCustomer,"
			 +" task.content content, task.updateService updateService, task.soureType soureType,task.plannedId plannedId,"
			 +" device.device_sn deviceSn,device.DEVICE_ADDR addr, device.device_name deviceName,"
			 +" device.log longitude, device.lat  latitude,task.isSign isSign,0 taskProblemList,"
			 +" soft.soft_name_cn softName,soft.id softId,"
			 +" hard.hardName hardName,hard.id hardId"
			 +" FROM"
			 +" core_device_task_info task "
			 +" LEFT JOIN sym_user maint  ON (task.maintUserId = maint.ID) "
			 +" LEFT JOIN sym_user createUser ON (task.createId = createUser.ID)"
			 +" LEFT JOIN bas_device_info device ON(device.device_sn=task.deviceSn)"
			 +" LEFT JOIN bas_soft_install_info soft ON(task.softId=soft.id)"
			 +" LEFT JOIN bas_hard_info hard ON(task.hardId=hard.id)"
			 +" INNER   JOIN  sym_addvcd_user  addvcdUser ON(task.ADDVCD=addvcdUser.ADDVCD)";
	
	

	// 微信任务单详情
	public static String weChatInfoSQL = "SELECT task.dataType dataType,task.taskNo taskNo,task.addvcd addvcd,"
			+ " task.maintUserId maintUserId,task.taskStatusName taskStatusName,  task.taskStatus taskStatus,"
			+ " task.ADDVNM addvnm,  task.softNameCn softNameCn,task.operateCode operateCode,"
			+ " task.operateName operateName,task.priorityName priorityName,"
			+ " task.priorityStatus priorityStatus,task.updateCustomer updateCustomer,task.updateMaint updateMaint,"
			+ " task.createTime createTime,task.modifyTime  modifyTime,task.reciveTime reciveTime,"
			+ " task.content content,maint.name maintName,task.soureType soureType,task.plannedId plannedId, "
			+ " device.device_info deviceInfo, device.device_name deviceName,"
			+ " device.device_sn deviceSn, device.log longitude, device.lat  latitude,task.isSign isSign,0 taskProblemList"
			+ " FROM core_device_task_info  task" + " LEFT JOIN sym_user  maint    ON (task.maintUserId=maint.ID)"// 维修人员
			+ " LEFT JOIN bas_device_info device ON (device.device_sn =task.deviceSn)";
	 
	
	
	
	public static String weChatInfoSQLnew="SELECT  task.dataType dataType, task.taskNo taskNo, task.ADDVNM addvnm, task.taskStatus taskStatus,"
			 +" task.taskStatusName taskStatusName,maint.name maintName,task.operateCode operateCode, task.operateName operateName,"
			 +" task.priorityName priorityName, task.priorityStatus priorityStatus, task.createTime createTime,task.modifyTime modifyTime,"
			 +" task.updateMaint updateMaint,task.updateCustomer updateCustomer,"
			 +" task.content content, task.updateService updateService, task.soureType soureType,task.plannedId plannedId,"
			 +" device.device_sn deviceSn,device.DEVICE_ADDR addr, device.device_name deviceName,"
			 +" device.log longitude, device.lat  latitude,task.isSign isSign,0 taskProblemList,"
			 +" soft.soft_name_cn softName,soft.id softId,"
			 +" hard.hardName hardName,hard.id hardId"
			 +" FROM"
			 +" core_device_task_info task "
			 +" LEFT JOIN sym_user maint  ON (task.maintUserId = maint.ID) "
			 +" LEFT JOIN sym_user createUser ON (task.createId = createUser.ID)"
			 +" LEFT JOIN bas_device_info device ON(device.device_sn=task.deviceSn)"
			 +" LEFT JOIN bas_soft_install_info soft ON(task.softId=soft.id)"
			 +" LEFT JOIN bas_hard_info hard ON(task.hardId=hard.id)";
 	
	
	

	// web任务单
	public static String webSQL = "SELECT  task.dataType dataType,task.taskNo taskNo,task.addvcd addvcd,task.ADDVNM addvnm,"
			+ "task.taskStatus  taskStatus,task.taskStatusName taskStatusName,task.deviceSn deviceSn,task.maintUserId maintUserId, "
			+ " task.deviceName deviceName,task.softInstallId softInstallId, task.softNameCn softNameCn,task.maintType maintType,  "
			+ "task.operateCode operateCode,task.operateName operateName,task.DEVICE_ADDR deviceAddr,"
			+ "task.priorityName priorityName,task.priorityStatus priorityStatus,"
			+ " task.createTime createTime,task.modifyTime  modifyTime,task.reciveTime reciveTime,"
			+ " task.content content,  task.nextNode,task.nextNodeName,task.updateService updateService,"
			+ " maint.name maintName, createUser.name createUserName,task.soureType soureType,task.plannedId plannedId "
			+ " FROM task  task "
			+ " LEFT JOIN sym_user  maint    ON (task.maintUserId=maint.ID)"// 维修人员
			+ " INNER   JOIN  sym_addvcd_user  addvcdUser ON(task.ADDVCD=addvcdUser.ADDVCD)" // 任务单属于的区域
			+ " LEFT JOIN sym_user  createUser   ON (task.createId = createUser.ID) ";// 创建人

	
	
  
	
	
	
	// web任务单历史
	public static String webHistorySQL = "SELECT  task.dataType dataType,task.taskNo taskNo,task.addvcd addvcd,task.ADDVNM addvnm,"
			+ "task.taskStatus  taskStatus,task.taskStatusName taskStatusName,task.deviceSn deviceSn,task.maintUserId maintUserId, "
			+ " task.deviceName deviceName,task.softInstallId softInstallId, task.softNameCn softNameCn,task.maintType maintType,  "
			+ "task.operateCode operateCode,task.operateName operateName,task.DEVICE_ADDR deviceAddr,"
			+ "task.priorityName priorityName,task.priorityStatus priorityStatus,"
			+ " task.createTime createTime,task.modifyTime  modifyTime,task.reciveTime reciveTime,"
			+ " task.content content,  task.nextNode,task.nextNodeName,task.updateService updateService,"
			+ " maint.name maintName, createUser.name createUserName,task.soureType soureType,task.plannedId plannedId "
			+ " FROM task  task " + " LEFT JOIN sym_user  maint    ON (task.maintUserId=maint.ID)"// 维修人员
			+ " LEFT JOIN sym_user  createUser   ON (task.createId = createUser.ID) ";// 创建人

	 
	//web任务单
	public static String webTaskSQL="SELECT  task.dataType dataType, task.taskNo taskNo, task.addvcd addvcd,task.ADDVNM addvnm, task.taskStatus taskStatus,"
			 +" task.taskStatusName taskStatusName,task.maintUserId maintUserId,maint.name maintName,task.operateCode operateCode, task.operateName operateName,"
			 +" task.priorityName priorityName, task.priorityStatus priorityStatus, task.createTime createTime,task.modifyTime modifyTime, task.reciveTime reciveTime,"
			 +" task.content content, task.updateService updateService, task.soureType soureType,task.plannedId plannedId, createUser.name createUserName,"
			 +" device.device_sn deviceSn,device.DEVICE_ADDR addr, device.device_name deviceName,"
			 +" soft.soft_name_cn softName,soft.id softId,"
			 +" hard.hardName hardName,hard.id hardId"
			 +" FROM"
			 +" core_device_task_info task "
			 +" LEFT JOIN sym_user maint  ON (task.maintUserId = maint.ID) "
			 +" LEFT JOIN sym_user createUser ON (task.createId = createUser.ID)"
			 +" LEFT JOIN bas_device_info device ON(device.device_sn=task.deviceSn)"
			 +" LEFT JOIN bas_soft_install_info soft ON(task.softId=soft.id)"
			 +" LEFT JOIN bas_hard_info hard ON(task.hardId=hard.id)";
 	
	
	public static String device="";
			
 	public static String soft="";	
 	
 	public static String hash="";		
	
	 

	
	
	
	
	
	// web业主任务单
	public static final String webSQL_YEZHU = "SELECT  task.dataType dataType,task.taskNo taskNo,task.addvcd addvcd,task.ADDVNM addvnm,"
			+ "task.taskStatus  taskStatus,task.taskStatusName taskStatusName,task.deviceSn deviceSn,task.maintUserId maintUserId, "
			+ " task.deviceName deviceName,task.softInstallId softInstallId, task.softNameCn softNameCn,task.maintType maintType,  "
			+ "task.operateCode operateCode,task.operateName operateName,"
			+ "task.priorityName priorityName,task.priorityStatus priorityStatus,"
			+ " task.createTime createTime,task.modifyTime  modifyTime,task.reciveTime reciveTime,"
			+ " task.content content,  task.nextNode,task.nextNodeName,task.updateService updateService,"
			+ " maint.name maintName, createUser.name createUserName,task.soureType soureType,task.plannedId plannedId "
			+ " FROM task  task " + " LEFT JOIN sym_user  maint    ON (task.maintUserId=maint.ID)"// 维修人员
			+ " INNER   JOIN  sym_user  ownerUser  ON(ownerUser.owner_no=task.ownerNo)" // 任务单属于归属单位
			+ " LEFT JOIN sym_user  createUser   ON (task.createId = createUser.ID) ";// 创建人

}*/