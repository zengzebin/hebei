package com.ssxt.common.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 
 * <b>类名称：</b>PBizlogParam<br/>
 * <b>类描述：</b><br/>
 * <b>创建人：</b>杨培新<br/>
 * <b>修改人：</b>杨培新<br/>
 * <b>修改时间：</b>2011-9-23 下午04:46:34<br/>
 * <b>修改备注：</b><br/>
 * @version 1.0.0<br/>
 *
 */
public class BizlogParam{
	public static final Map<String, String> logMap = new HashMap<String, String>(){{
		put("PDataItem","true");
		put("PDayData","true");
		put("PDayData","true");
		put("PEquipItem","true");
		put("PEquipType","true");
		put("PHourData","true");
		put("PMonthData","true");
		put("PYearData","true");
		put("PRealData","true");
		put("PRealStatus","true");
		put("PStation","true");
		put("PStationEquip","true");
		put("PWkStation","true");
		put("PBizlog","false");
	}};
	public static boolean checkLogStatus(String key){
		String str=logMap.get(key);
		return str!=null&&"true".equals(str);
	}
}