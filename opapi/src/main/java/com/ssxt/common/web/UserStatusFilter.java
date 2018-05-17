package com.ssxt.common.web;

import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.MDC;

import com.ssxt.common.enums.LoginParam;
import com.ssxt.common.page.PageControl;
import com.ssxt.common.page.SqlBuffer;
import com.ssxt.common.util.AccData;
import com.ssxt.common.util.CtxHelper;
import com.ssxt.common.util.DataUtil;
import com.ssxt.common.util.MDCUtil;
import com.ssxt.common.util.SpringUtil;
import com.ssxt.task.AutomaticService;
import com.ssxt.web.bean.SymRole;
import com.ssxt.web.bean.SymUser;
import com.ssxt.web.service.RoleService;
import com.ssxt.web.service.UserService;

/**
 * <b>类名称：</b>UserStatusFilter<br/>
 * <b>类描述：</b><br/>
 * <b>创建人：</b>杨培新<br/>
 * <b>修改人：</b>杨培新<br/>
 * 
 * <b>修改时间：</b>2017年1月22日 下午3:33:32<br/>
 * <b>修改备注：</b><br/>
 * 
 * @version 1.0.0<br/>
 */
public class UserStatusFilter implements Filter {

	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(UserStatusFilter.class);

	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("----用户状态过滤器初始化----");
		if (notLoginUrl != null) {
			return;
		}
		String str = filterConfig.getInitParameter("notLoginUrl");
		str = DataUtil.notNullString(str);
		notLoginUrl = Arrays.asList(str.split(","));
	}

	private static List<String> notLoginUrl = null;

	/**
	 * 判断用户是否登录 并在登录的情况下,保存用户的登录信息在MDC本地线程变量中， 不需要再调用request参数就可以随时调用
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest rq = (HttpServletRequest) request;
		HttpServletResponse rp = (HttpServletResponse) response;

		boolean loginControlFlag = LoginParam.USE_FILTER_LOGIN_CONTROL;
		SymUser user = CtxHelper.getCurUser(rq);
 
		String url = null;
		if (user != null) {
			putMDC(user, rq, null);

		} else if (loginControlFlag) {
			url = rq.getServletPath();
			while (url.startsWith("//")) {
				url = url.substring(1);
			}
			int idx = DataUtil.indexOfStart(notLoginUrl, url);
			if (-1 == idx) {
				// rp.setStatus(401);
				AccData ad = CtxHelper.createFailAccData();
				ad.setSuccess(false);
				ad.setErrcode(530);
				ad.setMsg("未登录，或会话超时");
				CtxHelper.writeToJson(rp, ad);
				return;
			}

		}
		try {
			chain.doFilter(request, response); // 让目标资源执行，放行
		} catch (NullPointerException e) {
			// CtxHelper.doError(rq, rp, Err.server_interal_error, "空指针异常!", e);
		} catch (RuntimeException e) {
			// CtxHelper.doError(rq, rp, Err.param_not_match, "操作错误!", e);
		} catch (Exception e) {
			// CtxHelper.doError(rq, rp, Err.server_interal_error, "操作失败!", e);
		} finally {
			clearMDC();
		}
	}

	public void putMDC(SymUser user, HttpServletRequest request, HttpServletResponse response) {
		MDCUtil.putMDCbyRequest(user, request, response);
	}

	public void clearMDC() {
		MDCUtil.clearMDC();
	}

	public void destroy() {
		System.out.println("----用户状态过滤器销毁----");
	}
}