package com.ssxt.common.util;

import java.math.BigInteger;


/**
 * <b>类名称：</b>BcdBase<br/>
 * <b>类描述：</b><br/>
 * <b>创建人：</b>杨培新<br/>
 * <b>修改人：</b>杨培新<br/>
 * 
 * <b>修改时间：</b>2016年8月3日 上午9:33:19<br/>
 * <b>修改备注：</b><br/>
 * @version 1.0.0<br/>
 */
public abstract class BcdBase {

	/**
	 * 编码BCD byte
	 * @param value
	 * @param length
	 * @return 
	 */
    public abstract byte[] encode(String value, int length) ;
    /**
     * 解码bcd byte数组
     * @param buf
     * @return 
     */
    public abstract String decode(byte[] buf) ;
    public String removePrefixZero(String str){
    	return str.replaceAll("^(0+)", "");    	
    }
}
