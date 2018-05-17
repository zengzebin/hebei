package com.ssxt.Filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

public class CrossFilter extends OncePerRequestFilter {

	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if (request.getHeader("Access-Control-Request-Method") != null && "OPTIONS".equals(request.getMethod())) {
			// CORS "pre-flight" request

			System.out.println("origin==" + request.getHeader("origin") == null ? "*" : request.getHeader("origin"));
//			response.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");
			System.out.println(request.getMethod());
		 
			response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE,OPTIONS");
			response.setHeader("Access-Control-Allow-Headers", "Content-Type");
			response.setHeader("Access-Control-Allow-Credentials", "true");
			response.setHeader("X-Powered-By", " 3.2.1");
			response.setHeader("Access-Control-Max-Age", "1800");// 30 min
//			response.setHeader("Content-Type", "application/json;charset=utf-8");
			System.out.println("跨域");
		}

		filterChain.doFilter(request, response);
	}
}