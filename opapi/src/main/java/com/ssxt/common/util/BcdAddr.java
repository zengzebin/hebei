package com.ssxt.common.util;

/**
 * <b>类名称：</b>BcdCommon<br/>
 * <b>类描述：</b><br/>
 * <b>创建人：</b>杨培新<br/>
 * <b>修改人：</b>杨培新<br/>
 * 
 * <b>修改时间：</b>2016年8月2日 下午9:04:17<br/>
 * <b>修改备注：</b><br/>
 * @version 1.0.0<br/>
 */
public class BcdAddr extends BcdBase{

	/**
	 * 编码BCD byte
	 * @param value
	 * @param length
	 * @return 
	 */
    public byte[] encode(String value, int length) {
        byte[] buf = new byte[length];
        for (int i = 0; i < buf.length; i++) {
        	buf[i]=0x00;
		}
        int charpos = 0; //char where we start
        int bufpos = 0;
        charpos=value.length()-1;
        bufpos=buf.length-1;
        while (charpos >0) {
            buf[bufpos] = (byte)(((value.charAt(charpos-1) - 48) << 4)
                | (value.charAt(charpos ) - 48));
            charpos -= 2;
            bufpos--;
        }
        
        if (charpos==0) {
            buf[bufpos] = (byte)(value.charAt(0) - 48);
            //for odd lengths first in the first byte
            buf[0] ='A';
            charpos = 1;
            bufpos = 1;
        }
		return buf;
    }
    /**
     * 解码bcd byte数组
     * @param buf
     * @return 
     */
    public  String decode(byte[] b) {
    	byte[] buf=new byte[b.length];
        System.arraycopy( b, 0, buf, 0, b.length);
    	if(buf[0]=='A'){
    		buf[0]=0x00;
    	}    	
    	int length=buf.length*2;
        char[] digits = new char[length];
        int start = 0, pos = 0;
        int i = pos;
        for (;i < pos + (length / 2) + (length % 2); i++) {
            digits[start++] = (char)(((buf[i] & 0xf0) >> 4) + 48);
            digits[start++] = (char)((buf[i] & 0x0f) + 48);
        }
//        digits[start++] = (char)(((buf[i] & 0xf0) >> 4) + 48);
//        digits[start++] = (char)((buf[i] & 0x0f) + 48);
        return removePrefixZero(new String(digits));
    }

}
