package com.ssxt.common.util;

import bsh.This;
/**
 * GPS信息工具类
 * @author admin
 *
 */
public class GpsUtil {
	//经度小数点前位数
	public static final int LON_DIGIT = 3;
	//经度小数点后位数
	public static final int LON_ROUNDED = 10;
	//纬度小数点前位数
	public static final int LAT_DIGIT = 2;
	//纬度小数点后位数
	public static final int LAT_ROUNDED = 10;
	
	/**
	 * 调整经度位数长度
	 * @param lon：经度
	 * @return
	 */
	public static String fixLon(String lon) {
		return subLonLat(lon, LON_DIGIT  , LON_ROUNDED);
	}
	
	/**
	 * 调整纬度位数长度
	 * @param lon：经度
	 * @return
	 */
	public static String fixLat(String lat) {
		return subLonLat(lat, LAT_DIGIT  , LAT_ROUNDED);
	}
	

	/**
	 * 调整经纬度位数长度
	 * @param str
	 * @param digit
	 * @param rounded
	 * @return
	 */
	public static String subLonLat(String str, int digit, int rounded) {
		int index = str.indexOf(".");
		int subIndex = 0;
		if (index > -1) {
			if (index > digit)
				subIndex = index - digit;
			str = str.substring(subIndex, index + rounded + 1);
		}
		if (str.length() > digit + rounded + 1) {
			str = str.substring(0, digit + rounded +1);
		}
		return str;
	}


}
