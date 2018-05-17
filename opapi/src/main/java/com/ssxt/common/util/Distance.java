package com.ssxt.common.util;

public class Distance {
	private static double EARTH_RADIUS = 6371.137;

	/**
	 * 转化为弧度(rad)
	 */
	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	/**
	 * 基于余弦定理求两经纬度距离
	 * 
	 * @param lon1
	 *            第一点的纬度
	 * @param lat1
	 *            第一点的精度
	 * @param lon2
	 *            第二点的纬度
	 * @param lat3
	 *            第二点的精度
	 * @return 返回的距离，单位km
	 */
	public static double getDistance(double lat1, double lng1, double lat2, double lng2) {
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(
				Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000d) / 10000d;
//		s = s * 1000;
		return s;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		double m = Distance.getDistance(23.3829315368, 116.7297838781, 23.3836971865,116.7310463093);
		System.out.println(m);
		 double begin = System.currentTimeMillis();
	    double end = System.currentTimeMillis();
	        System.out.println("1万次循环所有的时间（s）"+((end-begin)/1000));
	}

}
