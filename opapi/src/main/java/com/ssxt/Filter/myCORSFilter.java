package com.ssxt.Filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import com.ssxt.web.controller.StatisticsController;

public class myCORSFilter implements Filter {
	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(myCORSFilter.class);

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		log.info("url:" + request.getRequestURL() + "---------Methods:" + request.getMethod());
		String origin = (String) servletRequest.getRemoteHost() + ":" + servletRequest.getRemotePort();
		if (request.getMethod().equals("OPTIONS")) {
			response.setStatus(200);
		}
		request.getHeader("Authorization");
		System.out.println(request.getMethod());

		response.setHeader("Access-Control-Allow-Origin",
				request.getHeader("origin") == null ? "*" : request.getHeader("origin"));
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE,OPTIONS");

		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "x-requested-with,Authorization");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		filterChain.doFilter(servletRequest, servletResponse);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	public void destroy() {
		// TODO Auto-generated method stub

	}
}
