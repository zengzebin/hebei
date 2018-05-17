package com.ssxt.common.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

public class IOTools {
	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(IOTools.class);

	/**
	 * 使用GET, DELETE提交到目标服务器
	 * 
	 * @param request
	 * @param response
	 * @param targetUrl
	 * @param method 
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	public static String getContentByLink(String contentType, String targetUrl, String para, String method) throws IOException {
		log.warn("getContentByLink {} :{},para:{}",method,targetUrl,para);
		return new HttpProvider().up2Url(contentType, targetUrl, para, method);
	}

	/**
	 * 使用POST, PUT提交到目标服务器
	 *  
	 * @param request
	 * @param response
	 * @param targetUrl
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	public static  String getContentByForm(String contentType, String targetUrl, String para,  String method) throws IOException {
		log.warn("getContentByForm {} :{},para:{}",method,targetUrl,para);
		return new HttpProvider().up2Body(contentType, targetUrl, para,method);	
	}
	/**
	 * 使用GET, DELETE提交到目标服务器
	 * 
	 * @param request
	 * @param response
	 * @param targetUrl
	 * @param method 
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	public static void up2Url(HttpServletRequest request, HttpServletResponse response, String targetUrl, String method) throws IOException {
		new SimpleProxy().up2Url(request, response, targetUrl, method);	
	}

	/**
	 * 使用POST, PUT提交到目标服务器
	 *  
	 * @param request
	 * @param response
	 * @param targetUrl
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	public static  void up2Body(HttpServletRequest request, HttpServletResponse response, String targetUrl, String method) throws IOException {
		new SimpleProxy().up2Body(request, response, targetUrl, method);	
	}
	/**
	 * 获得Post的String
	 * @param request
	 * @return 
	 *String
	 */
	public static String getRequestPara(HttpServletRequest request){
        String queryString = null;
        Map<String, String[]> params = request.getParameterMap();	
        StringBuilder sb = new StringBuilder();
        for (Entry<String, String[]> en : params.entrySet()) {	
        	String key=en.getKey();
        	String[] values=en.getValue();
            for (int i = 0; i < values.length; i++) {	
                String value = values[i];	
                sb.append("&" +key + "=" + value) ;	
            }
        }
        // 去掉最后一个空格
        queryString = sb.length()<=1?"":sb.substring(1);
        return queryString;
	}

	/**
	 * Copies the content of a InputStream into an OutputStream
	 * 使用字符流拷贝数据
	 * 
	 * @param input
	 *            the InputStream to copy
	 * @param output
	 *            the target Stream
	 * @param buffersize
	 *            the buffer size to use
	 * @throws IOException
	 *             if an error occurs
	 */
	public static long copy(InputStream input, String inChartset, OutputStream output,String outCharset , int buffersize) throws IOException {
		char[] buffer = new char[buffersize];
		int n = 0;
		long count = 0;
		InputStreamReader in = null; 
		OutputStreamWriter out = null; 
		try{
//			in = (new InputStreamReader(input, "UTF-8")); 
////			out = (new OutputStreamWriter(output,"UTF-8")); 
////			in = (new InputStreamReader(input)); 
//			out = (new OutputStreamWriter(output)); 
			System.out.println("from:" + inChartset +", to:"+outCharset);
			in = (new InputStreamReader(input, inChartset)); 
			out = (new OutputStreamWriter(output,outCharset)); 
			while (-1 != (n = in.read(buffer))) {
//				output.write(buffer, 0, n);
//				System.out.println("IOTools.read(size) " + n);
//				System.out.println("IOTools.copy() " + new String(buffer,0,n));
				out.write(buffer, 0, n);
				count += n;
			}
			out.flush();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(in!=null){
				in.close();
			}
			if(out!=null){
				out.close();
			}
		}
		return count;
	}

	/**
	 * Copies the bytes of a InputStream into an OutputStream
	 * 使用字节流拷贝数据
	 * 
	 * @param input
	 *            the InputStream to copy
	 * @param output
	 *            the target Stream
	 * @param buffersize
	 *            the buffer size to use
	 * @throws IOException
	 *             if an error occurs
	 */
	public static long copyDirect(InputStream input, OutputStream output, int buffersize) throws IOException {
		byte[] buffer = new byte[buffersize];
		int n = 0;
		long count = 0;
		try{
			while (-1 != (n = input.read(buffer))) {
//				output.write(buffer, 0, n);
//				System.out.println("IOTools.read(size) " + n);
//				System.out.println("IOTools.copy() " + new String(buffer,0,n));
				output.write(buffer, 0, n);
				count += n;
			}
			output.flush();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
		}
		return count;
	}
	

	/**
	 * 使用字符流读取
	 * @param input
	 * @param inChartset
	 * @param outCharset
	 * @param buffersize
	 * @return
	 * @throws IOException 
	 *String
	 * @exception 
	 * @since  1.0.0
	 */
	public static String readStr(InputStream input, String inChartset, int buffersize) throws IOException {
		char[] buffer = new char[buffersize];
		int n = 0;
		long count = 0;
		InputStreamReader in = null; 
		StringBuffer sb = new StringBuffer();
		
		try{
			in = (new InputStreamReader(input, inChartset)); 
			while (-1 != (n = in.read(buffer))) {
				sb.append(buffer, 0, n);
				count += n;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(in!=null){
				in.close();
			}
		}
		return sb.toString();
	}

	/**
	 * Copies the bytes of a InputStream into an OutputStream
	 * 使用字节流拷贝数据
	 * 
	 * @param input
	 *            the InputStream to copy
	 * @param output
	 *            the target Stream
	 * @param buffersize
	 *            the buffer size to use
	 * @throws IOException
	 *             if an error occurs
	 */
	public static byte[] readByte(InputStream input) throws IOException {
		return IOUtils.toByteArray(input);
	}
}
