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

import org.apache.commons.lang3.StringEscapeUtils;

import com.ssxt.common.util.DataUtil;

public class HttpProvider implements Closeable {

	public HttpProvider() {
		super();
	}
	private InputStream in_remote =null;
	private OutputStream out_remote=null;
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
	public String up2Url(String contentType ,String targetUrl, String para, String method) throws IOException {
		String content=null;
		try {
			targetUrl = DataUtil.isNull(para) ? targetUrl
					: (targetUrl.contains("?") ? (targetUrl + "&" + para) 
							: (targetUrl + "?" + para)).replace(" ", "%20");
			url = new URL(targetUrl);
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod(method);
			con.setFollowRedirects(false);

//			if(userAgent!=null)
//			con.setRequestProperty("User-Agent", userAgent);
			if(contentType!=null)
			con.setRequestProperty("Content-Type", contentType);
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
				content = "redirect:"+newUrl;
			}
			else{
				
				in_remote=con.getInputStream();
				content=IOTools.readStr(in_remote, "UTF-8",  8192);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return content;
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
	public  String up2Body(String contentType, String targetUrl, String para, String method) throws IOException {
		String content=null;
		try {

			url = new URL(targetUrl);
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod(method);
			con.setFollowRedirects(false);


//            con.setRequestProperty("accept", "*/*");
//            con.setRequestProperty("connection", "Keep-Alive");
            con.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			//add reuqest header
//			if(userAgent!=null)
//			con.setRequestProperty("User-Agent", userAgent);
			if(contentType!=null)
			con.setRequestProperty("Content-Type", contentType);

			boolean isFile=contentType!=null&&contentType.contains("multipart");
			
			System.out.println(String.format("Content-Type:%s", contentType));
			
			// Send post request
			con.setDoOutput(true);
			con.setDoInput(true);
			
			
			out_remote = new DataOutputStream(con.getOutputStream());

			long len=0;
			len=0;
			if(isFile){
			}
			else{
			}
            out_remote.write(para.getBytes("utf-8"));
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
				content = "redirect:"+newUrl;
			}
			else{
				in_remote = con.getInputStream();
				len=0;
				content=IOTools.readStr(in_remote, "UTF-8", 8192);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return content;
		
	}

	public void close() throws IOException {
		try {
			if(out_remote!=null){
				out_remote.close();
			}
			if(in_remote!=null){
				in_remote.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}


}