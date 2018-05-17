package com.ssxt.common.web;

import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SimpleProxy implements Closeable {

	public SimpleProxy() {
		super();
	}
	private InputStream in_remote =null;
	private OutputStream out_local = null;
	private OutputStream out_remote=null;
	private InputStream in_local=null;
	private URL url = null;
	private HttpURLConnection con =null;
	
	/**
	 * 使用GET,DELETE提交到目标服务器。
	 * 
	 * @param request
	 * @param response
	 * @param targetUrl
	 * @param method 
	 * @throws IOException
	 */
	@SuppressWarnings("static-access")
	public void up2Url(HttpServletRequest request, HttpServletResponse response, String targetUrl, String method) throws IOException {
		try {
			out_local = response.getOutputStream();
			url = new URL(targetUrl);
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod(method);
			con.setFollowRedirects(false);		//是否跟着跳转
			upCookie(request, response, con);

			int responseCode = 0;
			try {
				responseCode=con.getResponseCode();
			} catch (Exception e) {
			}
			if(responseCode>=400){
				throw new RuntimeException("HTTP  error code :"+responseCode);
			}
			boolean redirect = false;
			if (responseCode != HttpURLConnection.HTTP_OK) {
				if (responseCode == HttpURLConnection.HTTP_MOVED_TEMP
					|| responseCode == HttpURLConnection.HTTP_MOVED_PERM
						|| responseCode == HttpURLConnection.HTTP_SEE_OTHER)
				redirect = true;
			}

			System.out.println("Response Code ... " + responseCode);

			if (redirect) {
				// get redirect url from "location" header field
				String newUrl = con.getHeaderField("Location");
				goRedirect(request, response, con, newUrl);
			}
			else{
//				response.setCharacterEncoding("UTF-8");
				response.setContentType(con.getContentType());
				
				in_remote=con.getInputStream();
				downCookie(request, response, con);
				IOTools.copy(in_remote, "UTF-8", out_local, response.getCharacterEncoding(), 8192);
			}
			out_local.flush();
		} catch (Exception e) {
			e.printStackTrace();
			String ss=("{errorcode:98,msg:\"" + e.getMessage() +"\"}");
			if(out_local!=null){

				out_local.write(ss.getBytes());
				out_local.flush();
			}
		} finally {
			close();
		}
	}
	/**
	 * 使用POST PUT提交到目标服务器。
	 * 
	 * @param request
	 * @param response
	 * @param targetUrl
	 * @throws IOException
	 */
	@SuppressWarnings("static-access")
	public  void up2Body(HttpServletRequest request, HttpServletResponse response, String targetUrl, String method) throws IOException {
		try {

			url = new URL(targetUrl);
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod(method);
			con.setFollowRedirects(false);
			upCookie(request, response, con);


			//add reuqest header
			con.setRequestProperty("User-Agent", "Mozilla/5.0");

			// 可以拷贝客户端的head信息作为请求的head参数
//			con.setRequestProperty("Charset", "UTF-8");
//			con.setRequestProperty("Content-Type", "application/json");
			String contentType=request.getContentType();
			boolean isFile=contentType!=null&&contentType.contains("multipart");
			con.setRequestProperty("Content-Type", contentType);
			
//			String urlParameters = getPostString(request);
			System.out.println(String.format("Content-Type:%s", request.getContentType()));
			
			// Send post request
			con.setDoOutput(true);
			con.setDoInput(true);
			
			
			out_remote = new DataOutputStream(con.getOutputStream());
//			wr.write(urlParameters.getBytes("UTF-8"));
//			wr.flush();
//			wr.close();
			response.setCharacterEncoding("UTF-8");
			in_local = request.getInputStream();
			out_local=new DataOutputStream(response.getOutputStream());

			long len=0;
			len=0;
			if(isFile){
				len=IOTools.copyDirect(in_local, out_remote, 8192);		
				System.out.println("POST上传文件，长度:" + len);
			}
			else{
				len=IOTools.copy(in_local,  request.getCharacterEncoding() ,out_remote,"UTF-8", 8192);				
				System.out.println("POST长度:" + len);	
			}
			out_remote.flush();

//			System.out.println("Post parameters : " + urlParameters);
			int responseCode = 0;
			try {
				responseCode=con.getResponseCode();
			} catch (Exception e) {
			}
			if(responseCode>=400){
				throw new RuntimeException("HTTP  error code :"+responseCode);
			}

			boolean redirect = false;
			if (responseCode != HttpURLConnection.HTTP_OK) {
				if (responseCode == HttpURLConnection.HTTP_MOVED_TEMP
					|| responseCode == HttpURLConnection.HTTP_MOVED_PERM
						|| responseCode == HttpURLConnection.HTTP_SEE_OTHER)
				redirect = true;
			}

			System.out.println("Response Code ... " + responseCode);

			if (redirect) {
				// get redirect url from "location" header field
				String newUrl = con.getHeaderField("Location");
				goRedirect(request, response, con, newUrl);
			}
			else{
				response.setContentType(con.getContentType());
				downCookie(request, response, con);
				// 获取返回值
				in_remote = con.getInputStream();
				len=0;
				len=IOTools.copy(in_remote, "UTF-8", out_local, response.getCharacterEncoding(), 8192);
			}

		} catch (Exception e) {
			e.printStackTrace();
			String ss=("{errorcode:98,msg:\"" + e.getMessage() +"\"}");
			out_local.write(ss.getBytes());
			out_local.flush();
		} finally {
			close();
		}
		
		
	}

	public void close() throws IOException {
		try {
			if(out_remote!=null){
				out_remote.close();
			}
			if(in_local!=null){
				in_local.close();
			}
			if(in_remote!=null){
				in_remote.close();
			}
			if(out_local!=null){
				out_local.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * 
	 * 重定向,跳转指定的网址<br/>
	 * (适用条件):<br/>
	 * (执行流程):<br/>
	 * (使用方法):<br/>
	 * (注意事项):<br/>
	 * @param request
	 * @param response
	 * @param con		http链接
	 * @param newUrl	新的地址1
	 * @throws IOException 
	 *void
	 * @exception 
	 * @since  1.0.0
	 */
	public static void goRedirect(HttpServletRequest request, HttpServletResponse response,HttpURLConnection con,String newUrl ) throws IOException{

		// get the cookie if need, for login
//		String cookies = con.getHeaderField("Set-Cookie");
		System.out.println("Redirect:"+newUrl);
		System.out.println("apiserver:"+ChapterServlet.getApiserver());

		String apiserver = ChapterServlet.getApiserver();
		String contexPath = request.getContextPath();
		if(newUrl.startsWith("http")){
			
			if(apiserver.contains("http")){
				contexPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
			}
			else {
				newUrl=newUrl.substring(newUrl.indexOf("/", 10));
			}
		}
		System.out.println("getContextPath:"+contexPath);
		newUrl = newUrl.replace(ChapterServlet.getApiserver(), contexPath);
		System.out.println("Redirect to :"+newUrl);
		downCookie(request, response, con);
		response.sendRedirect(newUrl);
	}

	/**
	 * 
	 * upCookie(作用)提交请求的时候附带cookie<br/>
	 * (适用条件):<br/>
	 * (执行流程):<br/>
	 * (使用方法):<br/>
	 * (注意事项):<br/>
	 * @param request
	 * @param response
	 * @param con 
	 *void
	 * @exception 
	 * @since  1.0.0
	 */
	public static void upCookie(HttpServletRequest request, HttpServletResponse response,HttpURLConnection con){

		String cookie_up=request.getHeader("Cookie");
		if(cookie_up==null)
			return;
		con.setRequestProperty("Cookie", cookie_up);
//		System.out.println("Cookie:" + cookie_up);
	}
	/**
	 * 
	 * downCookie(作用)返回请求的时候附带传输setcookie<br/>
	 * (适用条件):<br/>
	 * (执行流程):<br/>
	 * (使用方法):<br/>
	 * (注意事项):<br/>
	 * @param request
	 * @param response
	 * @param con 
	 *void
	 * @exception 
	 * @since  1.0.0
	 */
	public static void downCookie(HttpServletRequest request, HttpServletResponse response,HttpURLConnection con ){

		String cookieFieldName="Set-Cookie";
		String cookie_set=con.getHeaderField("Set-Cookie");
		String name="JSESSIONID";
		String value="";
		Map<String,List<String>> map=con.getHeaderFields();
		
		//判断是否含有特殊cookie(即session的ID，通常是JSESSIONID)
		for (Entry<String,List<String>> entry: map.entrySet()) {
			if(cookieFieldName.equals(entry.getKey())){
				if(entry.getValue()!=null)
				for (String str : entry.getValue()) {
					
					if(str==null || "".equals(str.trim()))continue;
					int start=str.indexOf(name);
					if(start<0)continue;
					int end=str.indexOf(";",start+name.length());
					if(end<0)continue;
					
					value=str.substring(start+name.length()+1,end);	
					
				}
				break;
			}
		}
		if(cookie_set==null)
			return;
		response.setHeader("Set-Cookie", cookie_set);

		//特殊cookie值需要特殊处理，path转为"/"（服务器级而非context级）
		if (value != null && !"".equals(value.trim())) {
			Cookie cookie = new Cookie(name, value);
			cookie.setPath("/");
//			cookie.setHttpOnly(true);
			response.addCookie(cookie);
			System.out.println("Set-Cookie " + name + "=" + value);
		}
		
	}
	

}