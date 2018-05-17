package com.ssxt.common.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.beust.jcommander.ParameterException;
import com.ssxt.common.util.AccData;
import com.ssxt.common.util.CtxHelper;
import com.ssxt.common.util.DataUtil;
import com.ssxt.common.util.FastJsonUtils;

public class MyExceptionHandler implements HandlerExceptionResolver {
	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(MyExceptionHandler.class);

	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		Map<String, Object> model = new HashMap<String, Object>();
		AccData ad = CtxHelper.createFailAccData();
		log.error("错误的信息", ex);
		ad.setMsg(ex.toString());
		ad.setSuccess(false);
 		CtxHelper.writeToJson(response, ad);
		// 根据不同错误转向不同页面

//		response.setCharacterEncoding("UTF-8");
//		response.setContentType("application/json; charset=utf-8");
//		PrintWriter out = null;
//		try {
//			out = response.getWriter();
//			out.append(FastJsonUtils.toJSONString(ad));
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			if (out != null) {
//				out.close();
//			}
//		}

		return null;

	}
}