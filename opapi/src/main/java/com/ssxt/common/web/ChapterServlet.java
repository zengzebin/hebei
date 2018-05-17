package com.ssxt.common.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChapterServlet extends HttpServlet {
        private static final long serialVersionUID = -1534235656L;

        private static String apiserver=null;
        public static String getApiserver() {
			return apiserver;
		}
		public void init() throws ServletException {
			if(apiserver==null){
	        	apiserver=this.getInitParameter("apiserver");				
			}
          }   
        public String  getRequestUrl(HttpServletRequest request){
        	String url=request.getRequestURI()+(request.getQueryString()==null?"":"?"+request.getQueryString());
        	System.out.println("___" + url);
        	String prefix="";
        	if(apiserver.indexOf("://")==-1){
        		prefix = request.getScheme() + "://127.0.0.1:" + request.getServerPort();
        	}
        	String contextPath= this.getServletContext().getContextPath() ;
        	String goUrl=prefix + apiserver +url.substring(url.indexOf(contextPath)+contextPath.length());
        	System.out.println(" ***************Goto Server:"+goUrl);
        	return goUrl;
        	
        }
        @Override
        protected void doHead(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            String method="HEAD";
            System.out.print(method);
            String targetUrl=getRequestUrl(request);
            IOTools.up2Url(request, response, targetUrl,  method);
        }
        
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            String method="GET";
            System.out.print(method);
            String targetUrl=getRequestUrl(request);
            IOTools.up2Url(request, response, targetUrl,  method);
        }

        protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            String method="DELETE";
            System.out.print(method);
            String targetUrl=getRequestUrl(request);
            IOTools.up2Url(request, response, targetUrl,  method);
        }


        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            String method="POST";
            System.out.print(method);
            String targetUrl=getRequestUrl(request);
            IOTools.up2Body(request, response, targetUrl,  method);
        }

        protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            String method="PUT";
            System.out.print(method);
            String targetUrl=getRequestUrl(request);
            IOTools.up2Body(request, response, targetUrl,  method);
        }

}