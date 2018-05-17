package com.ssxt.common.web;

import static com.ssxt.common.util.DataUtil.showMsgException;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ssxt.common.enums.ActionMsgParam;
import com.ssxt.common.util.AccData;
import com.ssxt.common.util.CtxHelper;
import com.ssxt.common.util.DataUtil;
import com.ssxt.web.bean.SymUser;

/**
 * <b>类名称：</b>UserSessionFilter<br/>
 * <b>类描述：</b><br/>
 * <b>创建人：</b>杨培新<br/>
 * <b>修改人：</b>杨培新<br/>
 * 
 * <b>修改时间：</b>2017年1月22日 下午3:33:22<br/>
 * <b>修改备注：</b><br/>
 * 
 * @version 1.0.0<br/>
 */
public class UserSessionFilter implements Filter {

	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(UserSessionFilter.class);

	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("----用户会话过滤器启动----");
		if (LOG_OUT_AFTER_LOGIN_TIME != 0) {
			return;
		}
		String str = filterConfig.getInitParameter("timeoutsec");
		LOG_OUT_AFTER_LOGIN_TIME = DataUtil.notNullLong(str, -1l) * 1000;
	}

	private static long LOG_OUT_AFTER_LOGIN_TIME = 0; // 登录后超时的时间

	/**
	 * 判断用户是否保持会话
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest rq = (HttpServletRequest) request;
		HttpServletResponse rp = (HttpServletResponse) response;

		AccData ad = CtxHelper.createFailAccData();
		ad.setMsg(ActionMsgParam.ACM_OP_ERR);
		try {
			HttpSession session = SessionHelper.getSession(rq);
			long t1 = session.getCreationTime();
			// long t2=rq.getSession().getLastAccessedTime();
			long t3 = System.currentTimeMillis();
			long t_period = t3 - t1;
			if (t_period > LOG_OUT_AFTER_LOGIN_TIME) {
				// rq.getSession().invalidate();
				ad.setData("");

				showMsgException("会话超时或未登录!");
			} else {
				SymUser user = CtxHelper.getCurUser(rq);
				// ad.setData(new String[]{
				// user==null?"不明用户":user.getName(),
				// (user==null||user.getSymRole()==null)?"不明角色":user.getSymRole().getName(),
				// user==null?"不明用户":user.getLoginId()
				// });
				ad.setMsg(ActionMsgParam.ACM_OP_SUC);
				ad.setSuccess(true);
			}
		} catch (NullPointerException e) {

		} catch (RuntimeException e) {
			ad.setMsg(DataUtil.getRootCauseMsg(e));
			ad.setData(e.toString());
			ad.setSuccess(false);
		} catch (Exception e) {

		}
		CtxHelper.writeToJson(rp, ad);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {
		System.out.println("----用户会话过滤器销毁----");

	}

}