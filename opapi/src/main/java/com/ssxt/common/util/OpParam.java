package com.ssxt.common.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <b>类名称：</b>OpParam<br/>
 * <b>类描述：</b><br/>
 * <b>创建人：</b>杨培新<br/>
 * <b>修改人：</b>杨培新<br/>
 * 
 * <b>修改时间：</b>2016年9月7日 上午9:22:35<br/>
 * <b>修改备注：</b><br/>
 * 
 * @version 1.0.0<br/>
 */
public class OpParam {
	// 设备类型
	public static final Map<String, String> deviceTypeMap = new LinkedHashMap<String, String>() {
		{
			int i = 1;
			put(String.valueOf(i++), "雨量计");
			put(String.valueOf(i++), "墒情仪");
		}
	};
	// 设备维护分类
	public static final Map<String, String> deviceMaintTypeMap = new LinkedHashMap<String, String>() {
		{
			int i = 1;
			put(String.valueOf(i++), "DBA");
			put(String.valueOf(i++), "技术支持");
		}
	};

	// 软件类型
	public static final Map<String, String> softTypeMap = new LinkedHashMap<String, String>() {
		{
			int i = 1;
			put(String.valueOf(i++), "数据库");
			put(String.valueOf(i++), "采集器");
			put(String.valueOf(i++), "windows");
		}
	};
	// 软件维护分类
	public static final Map<String, String> softMaintTypeMap = new LinkedHashMap<String, String>() {
		{
			int i = 1;
			put(String.valueOf(i++), "水文监测设备");
			put(String.valueOf(i++), "服务器");
			put(String.valueOf(i++), "网络设备");
		}
	};

	// 人员所在状态
	public static final Map<String, String> procUserStatusMap = new LinkedHashMap<String, String>() {
		{
			int i = 1;
			put(String.valueOf(i++), "签到");
			put(String.valueOf(i++), "签退");
			put(String.valueOf(i++), "参与");
		}
	};

	// 故障类型
	// public static final Map<Integer, String> errTypeMap =
	// SpringBeanFactory.getErrorCodeNameMap();
	// new LinkedHashMap<String, String>(){{
	//// put(String.valueOf(101001),"线路故障");
	//// put(String.valueOf(101002),"网络故障");
	//// put(String.valueOf(101003),"损坏故障");
	//// put(String.valueOf(0),"交流电停电");
	//// put(String.valueOf(1),"蓄电池电压低");
	//// put(String.valueOf(2),"水位超限报警");
	//// put(String.valueOf(3),"流量超限报警");
	//// put(String.valueOf(4),"水质超限报警");
	//// put(String.valueOf(5),"流量仪表故障");
	//// put(String.valueOf(6),"水位仪表故障");
	//// put(String.valueOf(7),"终端箱门关闭");
	//// put(String.valueOf(8),"存储器状态异常");
	////// put(String.valueOf(9),"IC卡功能关闭"); //关闭是0，有效是1
	//// put(String.valueOf(10),"水泵停机");
	//// put(String.valueOf(11),"水量超限");
	////
	////
	////
	//// put(String.valueOf(12),"雨量筒仪表状态");
	//// put(String.valueOf(13),"CPU状态");
	//// put(String.valueOf(14),"GSM模块");
	//// put(String.valueOf(15),"Zigbee模块");
	//// put(String.valueOf(16),"时钟状态");
	//// put(String.valueOf(17),"网络设备状态");
	// Map<Integer, CoreErrType> errCodeMap=SpringBeanFactory.getErrorCodeMap();
	// for (Map.Entry<Integer, CoreErrType> entry :
	// SpringBeanFactory.getErrorCodeMap().entrySet()) {
	// put(entry.getKey(), entry.getValue().getName());
	// }
	//
	// }};

	// 任务单级别
	public static final Map<String, String> priorityMap = new LinkedHashMap<String, String>() {
		{
			put(String.valueOf(1), "紧急");
			put(String.valueOf(2), "优先");
			put(String.valueOf(3), "普通");
		}
	};

}