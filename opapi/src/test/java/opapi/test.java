package opapi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.ssxt.common.util.DataUtil;
import com.ssxt.reflect.ReflectSql;
import com.ssxt.web.bean.SymUser;

import io.netty.handler.codec.base64.Base64;
import sun.misc.BASE64Decoder;

public class test {

	 public static byte[] decode(String str){    
		   byte[] bt = null;    
		   try {    
		       sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();    
		       bt = decoder.decodeBuffer( str );    
		   } catch (IOException e) {    
		       e.printStackTrace();    
		   }    
		   
		       return bt;    
		   }    

	public static void main(String[] args) {

		String code = "1514889192608ca00d38-aee6-4364-9384-f8e1ca59ae9d1514251480889245743d-f51c-4115-ab23-1489db2ae32115148891926077556124-BB8E-489D-AF86-26240ACD90DD151488919260";
//		System.out.println(code.substring(48, 48 + 10));
		System.out.println((1- DataUtil.deciMal(24, (2 * 24))));

//		String userNameAndPasswd = new String(bytes, 0, bytes.length);

	}

}
