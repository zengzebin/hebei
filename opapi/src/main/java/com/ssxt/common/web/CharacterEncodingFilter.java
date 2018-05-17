package com.ssxt.common.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;


/**
 * <b>类名称：</b>CharacterEncodingFilter<br/>
 * <b>类描述：</b><br/>
 * <b>创建人：</b>杨培新<br/>
 * <b>修改人：</b>杨培新<br/>
 * 
 * <b>修改时间：</b>2016年8月31日 上午9:35:30<br/>
 * <b>修改备注：</b><br/>
 * @version 1.0.0<br/>
 */
public class CharacterEncodingFilter implements Filter {

    private FilterConfig config;
    private  String encoding = "utf-8";
    private boolean forceEncoding=false;
    
    public void init(FilterConfig filterConfig) throws ServletException {
        this.config = filterConfig;
        encoding=this.config.getInitParameter("encoding");
        String fcode=this.config.getInitParameter("forceEncoding");
        forceEncoding=("true".equals(fcode)||"1".equals(fcode));        	
        System.out.println("----编码过滤器初始化----");
    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain filterChain) throws IOException, ServletException {
        if (this.encoding != null && (this.forceEncoding || request.getCharacterEncoding() == null)) {  
            request.setCharacterEncoding(this.encoding);  
            if (this.forceEncoding) {  
                response.setCharacterEncoding(this.encoding);  
            }  
        }  
        filterChain.doFilter(request, response);  
    }

    public void destroy() {
        System.out.println("----编码过滤器销毁----");
    }
}