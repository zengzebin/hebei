package com.ssxt.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 * 
 * <b>类名称：</b>EnCryptTool<br/>
 * <b>类描述：</b><br/>

 * <b>创建人：</b>杨培新<br/>

 * <b>修改人：</b>杨培新<br/>

 * <b>修改时间：</b>Jun 3, 2010 9:00:00 PM<br/>
 * <b>修改备注：</b><br/>
 * @version 1.0.0<br/>
 * 
 */
public class EnCryptTool {
	public static int SIXTEEN_BITES=16;
	public static int TWENTY=20;
	public static int THIRTY_TWO_BITES=32;
	private static final int SALT_LENGTH = 12;
	
	
	public final static String getEnCryptString(String inputStr,String charEncoding,String encName) throws UnsupportedEncodingException, NoSuchAlgorithmException {

		String enc="";
		if(encName==null||"".equals(encName))
			enc="MD5";
		else enc=encName;
		
		//声明加密后的口令数组变量
		byte[] pwd = null;
		
		//随机数生成器
		SecureRandom random = new SecureRandom();
		//声明盐数组变量
		byte[] salt = new byte[SALT_LENGTH];
		//将随机数放入盐变量中
		random.nextBytes(salt);
		
		//消息摘要对象		
		MessageDigest mdTemp = MessageDigest.getInstance(enc);
		//将盐数据传入消息摘要对象
		mdTemp.update(salt);
		//将口令的数据传给消息摘要对象
		mdTemp.update(inputStr.getBytes(charEncoding));	

		//获得消息摘要的字节数组
		byte[] md = mdTemp.digest();

		//因为要在口令的字节数组中存放盐，所以加上盐的字节长度
		pwd = new byte[md.length + SALT_LENGTH];
		//将盐的字节拷贝到生成的加密口令字节数组的前12个字节，以便在验证口令时取出盐
		System.arraycopy(salt, 0, pwd, 0, SALT_LENGTH);
		//将消息摘要拷贝到加密口令字节数组从第13个字节开始的字节
		System.arraycopy(md, 0, pwd, SALT_LENGTH, md.length);
		//将字节数组格式加密后的口令转化为16进制字符串格式的口令

		return StringUtil.byteToHexString(pwd);

	}
	/**
	 * 验证口令是否合法
	 * @param inputStr
	 * @param cryptedStr
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static boolean validEnCryptString(String inputStr, String cryptedStr,String charEncoding,String encName)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		String enc="";
		if(encName==null||"".equals(encName))
			enc="MD5";
		else enc=encName;
		//将16进制字符串格式口令转换成字节数组
		byte[] pwdInDb = StringUtil.hexStringToByte(cryptedStr);
		//声明盐变量
		byte[] salt = new byte[SALT_LENGTH];
		//将盐从数据库中保存的口令字节数组中提取出来
		System.arraycopy(pwdInDb, 0, salt, 0, SALT_LENGTH);
		//创建消息摘要对象
		MessageDigest md = MessageDigest.getInstance(enc);
		//将盐数据传入消息摘要对象
		md.update(salt);
		//将口令的数据传给消息摘要对象
		md.update(inputStr.getBytes(charEncoding));
		//生成输入口令的消息摘要
		byte[] digest = md.digest();
		//声明一个保存数据库中口令消息摘要的变量
		byte[] digestInDb = new byte[pwdInDb.length - SALT_LENGTH];
		//取得数据库中口令的消息摘要
		System.arraycopy(pwdInDb, SALT_LENGTH, digestInDb, 0, digestInDb.length);
		//比较根据输入口令生成的消息摘要和数据库中消息摘要是否相同
		if (Arrays.equals(digest, digestInDb)) {
			//口令正确返回口令匹配消息
			return true;
		} else {
			//口令不正确返回口令不匹配消息
			return false;
		}
	}
	
	public static String getMd5String(String inputStr,String charEncoding)throws UnsupportedEncodingException, NoSuchAlgorithmException{
		String md5Str=getEnCryptString(inputStr,charEncoding,"MD5");
		return md5Str;
		
	}
	public static boolean validMd5String(String inputStr, String cryptedStr,String charEncoding)throws UnsupportedEncodingException, NoSuchAlgorithmException{
		return validEnCryptString(inputStr, cryptedStr, charEncoding, "MD5");
	}
	public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		String user1="123456";
		String user2="123456";
		String charEncoding="utf-8";
		String encStr=getMd5String(user1, charEncoding);
		System.out.println(encStr);
		System.out.println(validMd5String(user2, "DEAE4067EF2B58AD380890571E3D9C3FAD2F284416F1B21E22A59B15",charEncoding));
	}
}
