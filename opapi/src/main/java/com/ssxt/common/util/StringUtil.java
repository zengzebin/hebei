/**
 * <b>项目名：</b>中山大学环境软件中心-大气监测管理系统<br/>
 * <b>包名：</b>com.diyeasy.common.util<br/>
 * <b>文件名：</b>StringUtil.java<br/>

 * <b>版本信息：</b><br/>
 * <b>日期：</b>2010-4-29-上午10:10:53<br/>
 * <b>Copyright (c)</b> 2010中山大学环境软件中心-版权所有<br/>
 * 
 */
package com.ssxt.common.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * <b>类名称：</b>StringUtil<br/>
 * <b>类描述：</b><br/>

 * <b>创建人：</b>杨培新<br/>

 * <b>修改人：</b>杨培新<br/>

 * <b>修改时间：</b>2010-4-29 上午10:10:53<br/>
 * <b>修改备注：</b><br/>
 * @version 1.0.0<br/>
 * 
 */
public class StringUtil {

	/**
	 * 如果指定字符串不为为null, 返回该字符串, 否则返回一个非空字符串
	 * @param str
	 *        指定字符串
	 * @return 处理后的字符串
	 */
	public static String notNull(String str) {
		if (str == null || str.length() < 1)
			return "";
		return str;
	}
	/**
	 * 当且仅当该字符串为null或空白字符串时, 才返回真, 否则返回假
	 * @param str
	 * @return  返回真,当且仅当该字符串为null或空白字符串时
	 */
	public static boolean isNoUse(String str) {
		if (str == null || str.length() < 1||"".equals(str.trim()))
			return true;
		return false;
	}
	/**
	 * 
	 * 返回一个字符串的中间字符串
	 * @param string
	 *           要处理的字符串
	 * @param start
	 *           该中间字符串前面的字符串
	 * @param end
	 *           该中间字符串后面的字符串
	 * @return 处理过的字符串
	 */

	public static String getMiddleString(String string, String start, String end) {
		int i = string.indexOf(start) + start.length();
		return (string.substring(i, string.indexOf(end, i)));
	}
	/**
	 * 
	 * 返回一个字符串的尾部字符串
	 * @param string
	 *           要处理的字符串
	 * @param start
	 *           该尾部字符串前面的字符串
	 * @return 处理过的字符串
	 */

	public static String getAfterString(String string, String start) {
		int i = string.indexOf(start) + start.length();
		return (string.substring(i));
	}
	/**
	 * 返回指定正则匹配的一个字符串
	 * @param sTotalString
	 * 			
	 * @param regEx
	 *          用来匹配此字符串的正则表达式 
	 * @param i
	 * 			匹配位
	 * @return 返回匹配的一个字符串
	 */
	public static String getMatcherString(String sTotalString, String regEx,
			int i) {
		Pattern p = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(sTotalString);
		if (m.find()) {
			return (m.group(i));
		}
		return null;
	}
	/**
	 * 返回指定正则匹配的一组字符串
	 * @param sTotalString
	 * 			
	 * @param regEx
	 *          用来匹配此字符串的正则表达式 
	 * @param i
	 * 			匹配位
	 * @return 返回匹配的一组字符串
	 */

	public static List getMatcherStringList(String sTotalString,
			String regEx, int i) {
		List<String> list = new ArrayList<String>();
		Pattern p = Pattern.compile(regEx, Pattern.DOTALL);
		Matcher m = p.matcher(sTotalString);
		while (m.find()) {
			list.add(m.group(i));
		}
		return list;
	}
	/**
	 * 返回指定对象的类的全名
	 * @param obj
	 *        指定对象
	 * @return 返回指定对象的类的全名
	 */

	public static String getClassFullName(Object obj) {
		return obj.getClass().getName();
	}
	/**
	 * 返回指定对象的类的名称
	 * @param obj
	 *         指定对象
	 * @return 返回指定对象的类的名称
	 */

	public static String getClassShortName(Object obj) {
		String tmp = getClassFullName(obj);
		if (tmp.indexOf(".") > 0)
			tmp = tmp.substring(tmp.lastIndexOf(".") + 1);
		return tmp;
	}
	
	public static String getFileType(String fileName)
	{
		String tmp="";
		if (fileName.indexOf(".") > 0)
			tmp = fileName.substring(fileName.lastIndexOf("."));
		return tmp;
	}
	/**
	 * 返回按时间为种子的String, 用于自动生成不重复的文件名. 该方法是线程安全的
	 * 
	 * @return 返回按时间为种子的String, 用于自动生成不重复的文件名. 该方法是线程安全的
	 */
	
	public synchronized static String getTimeString()
	{
		java.util.Date date = new java.util.Date();	
		java.text.SimpleDateFormat format = 	
			new java.text.SimpleDateFormat("yyyyMMddHHssSSS");
		return format.format(date).toString();
	}
	/**
	 * 返回按时间为种子的String, 用于自动生成不重复的文件名. 该方法是线程安全的
	 * @param simpleDateFormat
	 * 				时间格式
	 * @return
	 * 				返回按时间为种子的String,用于自动生成不重复的文件名
	 * 				该方法是线程安全的
	 */
	
	public synchronized static String getTimeString(String simpleDateFormat)
	{
		java.util.Date date = new java.util.Date();	
		java.text.SimpleDateFormat format = 	
			new java.text.SimpleDateFormat(simpleDateFormat);
		return format.format(date).toString();
	}
	/**
	 * 返回指定字符串的回文, 即"倒背"
	 * @param str
	 *         指定的字符串
	 * @return 返回指定字符串的回文
	 */
	
	public static String reverseString(String str)
	{
		StringBuffer sb=new StringBuffer();
		char[] c=str.toCharArray();
		for(int i=str.length()-1;i>=0;i--)
		{
			sb.append(c[i]);
		}
		return sb.toString();
	}
	/**
	 * 
	 * toGBK(这里用一句话描述这个方法的作用)<br/>
	 * (这里描述这个方法适用条件 – 可选)<br/>
	 * (这里描述这个方法的执行流程 – 可选)<br/>
	 * (这里描述这个方法的使用方法 – 可选)<br/>
	 * (这里描述这个方法的注意事项 – 可选)<br/>
	 * @param strvalue
	 * @return 
	 *String
	 * @exception 
	 * @since  1.0.0
	 */
	public static String toGBK(String strvalue){
		try {
			strvalue=strvalue==null?"":new String(strvalue.getBytes("ISO8859_1"), "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return strvalue;
	  }
	public static String base64Decoder(String s) throws Exception {
        sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
        byte[] b = decoder.decodeBuffer(s);
        return (new String(b));
    }
	/**
	 * 
	 * base64Encoder(这里用一句话描述这个方法的作用)<br/>
	 * (这里描述这个方法适用条件 – 可选)<br/>
	 * (这里描述这个方法的执行流程 – 可选)<br/>
	 * (这里描述这个方法的使用方法 – 可选)<br/>
	 * (这里描述这个方法的注意事项 – 可选)<br/>
	 * @param s
	 * @return
	 * @throws Exception 
	 *String
	 * @exception 
	 * @since  1.0.0
	 */
	public static String base64Encoder(String s) throws Exception {
        sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
        String b = encoder.encode(s.getBytes());
        return b;
    }
	/**
	 * 
	 * urlEncode(这里用一句话描述这个方法的作用)<br/>
	 * (这里描述这个方法适用条件 – 可选)<br/>
	 * (这里描述这个方法的执行流程 – 可选)<br/>
	 * (这里描述这个方法的使用方法 – 可选)<br/>
	 * (这里描述这个方法的注意事项 – 可选)<br/>
	 * @param s
	 * @param charset
	 * @return
	 *String
	 * @exception 
	 * @since  1.0.0
	 */
	public static String urlEncode(String s,String charset){
        try {
			return java.net.URLEncoder.encode(s, charset);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "UnsupportedEncode";
    }
	/**
	 * 
	 * urlDecode(这里用一句话描述这个方法的作用)<br/>
	 * (这里描述这个方法适用条件 – 可选)<br/>
	 * (这里描述这个方法的执行流程 – 可选)<br/>
	 * (这里描述这个方法的使用方法 – 可选)<br/>
	 * (这里描述这个方法的注意事项 – 可选)<br/>
	 * @param s
	 * @param charset
	 * @return
	 *String
	 * @exception 
	 * @since  1.0.0
	 */
	public static String urlDecode(String s,String charset){
        try {
			return java.net.URLDecoder.decode(s, charset);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "UnsupportedEncode";
    }
	/**
	 * 
	 * escape(使用html编码方式对字符串编码)<br/>
	 * (这里描述这个方法适用条件 – 可选)<br/>
	 * (这里描述这个方法的执行流程 – 可选)<br/>
	 * (这里描述这个方法的使用方法 – 可选)<br/>
	 * (这里描述这个方法的注意事项 – 可选)<br/>
	 * @param src
	 * @return 
	 *String
	 * @exception 
	 * @since  1.0.0
	 */
	public static String escape(String src) {
		int i;
		char j;
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length() * 6);
		for (i = 0; i < src.length(); i++) {
			j = src.charAt(i);
			if (Character.isDigit(j) || Character.isLowerCase(j)
					|| Character.isUpperCase(j))
				tmp.append(j);
			else if (j < 256) {
				tmp.append("%");
				if (j < 16)
					tmp.append("0");
				tmp.append(Integer.toString(j, 16));
			} else {
				tmp.append("%u");
				tmp.append(Integer.toString(j, 16));
			}
		}
		return tmp.toString();
	}
	/**
	 * 
	 * unescape(使用html编码方式对字符串解码)<br/>
	 * (这里描述这个方法适用条件 – 可选)<br/>
	 * (这里描述这个方法的执行流程 – 可选)<br/>
	 * (这里描述这个方法的使用方法 – 可选)<br/>
	 * (这里描述这个方法的注意事项 – 可选)<br/>
	 * @param src
	 * @return 
	 *String
	 * @exception 
	 * @since  1.0.0
	 */
	public static String unescape(String src) {
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length());
		int lastPos = 0, pos = 0;
		char ch;
		while (lastPos < src.length()) {
			pos = src.indexOf("%", lastPos);
			if (pos == lastPos) {
				if (src.charAt(pos + 1) == 'u') {
					ch = (char) Integer.parseInt(src
							.substring(pos + 2, pos + 6), 16);
					tmp.append(ch);
					lastPos = pos + 6;
				} else {
					ch = (char) Integer.parseInt(src
							.substring(pos + 1, pos + 3), 16);
					tmp.append(ch);
					lastPos = pos + 3;
				}
			} else {
				if (pos == -1) {
					tmp.append(src.substring(lastPos));
					lastPos = src.length();
				} else {
					tmp.append(src.substring(lastPos, pos));
					lastPos = pos;
				}
			}
		}
		return tmp.toString();
	}
	
	public static void main(String[] args) {
		String tmp = "~!@#$%^&*()_+|\\=-,./?><;'][{}\"中华人脉噶";
		System.out.println("testing escape : " + tmp);
		tmp = escape(tmp);
		System.out.println(tmp);
		System.out.println("testing unescape :" + tmp);
		System.out.println(unescape(tmp));
	}
	private static final String HEX_NUMS_STR="0123456789ABCDEF";
	
	/** 
	 * 将16进制字符串转换成字节数组 
	 * @param hex 
	 * @return 
	 */
	public static byte[] hexStringToByte(String hex) {
		int len = (hex.length() / 2);
		byte[] result = new byte[len];
		char[] hexChars = hex.toCharArray();
		int k=0;
		for (int i = 0; i < len; i++) {
			result[i] = (byte) (HEX_NUMS_STR.indexOf(hexChars[k++]) << 4 
							| HEX_NUMS_STR.indexOf(hexChars[k++]));
		}
		return result;
	}

	
	/**
	 * 将指定byte数组转换成16进制字符串
	 * @param b
	 * @return
	 */
	public static String byteToHexString(byte[] b) {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			hexString.append(hex.toUpperCase());
		}
		return hexString.toString();
	}
}
