/**
 * <b>项目名：</b>中山大学环境软件中心-大气监测管理系统<br/>
 * <b>包名：</b>com.diyeasy.common.sql<br/>
 * <b>文件名：</b>SqlConditionGroup.java<br/>

 * <b>版本信息：</b><br/>
 * <b>日期：</b>May 18, 2010-9:57:41 AM<br/>
 * <b>Copyright (c)</b> 2010中山大学环境软件中心-版权所有<br/>
 * 
 */
package com.ssxt.common.page;

import static com.ssxt.common.util.DataUtil.notNullInt;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ssxt.common.enums.ConstParam;
import com.ssxt.common.util.DataUtil;

/**
 * <b>类名称：</b>WebSearchBuffer<br/>
 * <b>类描述：</b><br/>
 * <b>创建人：</b>杨培新<br/>
 * <b>修改人：</b>杨培新<br/>
 * 
 * <b>修改时间：</b>2016年9月9日 下午2:43:39<br/>
 * <b>修改备注：</b><br/>
 * @version 1.0.0<br/>
 */
public class WebSearchBuffer implements Serializable {
	public SqlBuffer getSqlBuffer() {
		return sqlBuffer;
	}
	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(WebSearchBuffer.class);
	/**
	 * @since 1.0.0
	 */
	private static final long serialVersionUID = -8084952294833119220L;
	private SqlBuffer sqlBuffer = null;
	private PageControl pageControl =null;
	private boolean usePage=true;

	private WebSearchBuffer() {
		super();
	}

	public static WebSearchBuffer begin(HttpServletRequest request, HttpServletResponse response) {
		WebSearchBuffer wsb =new WebSearchBuffer();
		wsb.request=request;
		wsb.response=response;
		wsb.sqlBuffer = SqlBuffer.begin();
		return wsb;		
	}
	private HttpServletRequest request;
	private HttpServletResponse response;

	/**
	 * 初始化排序
	 * @param order_default
	 * @return 
	 *WebSearchBuffer
	 * @exception 
	 * @since  1.0.0
	 */
	public  WebSearchBuffer initOrder(String order_default) {
		WebSearchBuffer wsb=this;			
		String order = "";
		if (!DataUtil.isNull(request.getParameter("order"))) {
			order = "order by " + request.getParameter("order") + " ";
			if (!DataUtil.isNull(request.getParameter("orderdir"))
					&& "desc".equalsIgnoreCase(request.getParameter("orderdir")))
				order = order + request.getParameter("orderdir");
			wsb.pageControl.setOrder(order);
		}
		else if(!DataUtil.isNull(order_default)){
			pageControl.setOrder(order_default);
		}

		return this;
	}
//	/**
//	 * 初始化分页
//	 * @param request
//	 * @param response 
//	 *void
//	 * @exception 
//	 * @since  1.0.0
//	 */
//	public  WebSearchBuffer initPage() {
////		return initPage("order by id desc");
//		return initPage(null);
//	}
	/**
	 * 初始化分页
	 * @param order_default
	 * @return 
	 *WebSearchBuffer
	 * @exception 
	 * @since  1.0.0
	 */
	public  WebSearchBuffer initPage(String order_default) {
		WebSearchBuffer wsb=this;
		Integer pageSize = null;
		Integer start = null;
		start = DataUtil.parseInt(request.getParameter("start"), 0);
		pageSize = DataUtil.parseInt(request.getParameter("length"), ConstParam.DEFAULT_PAGE_SIZE);		

		usePage=!"0".equals(request.getParameter("usePage"));
		if(!usePage){
			pageControl=pageControl.getQueryOnlyInstance();
		}
		else{
			pageSize = notNullInt(pageSize, ConstParam.DEFAULT_PAGE_SIZE);
			start = notNullInt(start, 0);
			pageSize = pageSize > 4 ? pageSize : ConstParam.DEFAULT_PAGE_SIZE;
			pageControl = PageControl.getPageEnableInstance(start, pageSize);
			
		}
		wsb.initOrder(order_default);

		return this;
	}
	/**
	 * 初始化分页
	 * @param request
	 * @param response 
	 *void
	 * @exception 
	 * @since  1.0.0
	 */
	public  WebSearchBuffer initPageQueryOnly(String order_default) {
		WebSearchBuffer wsb=this;
		wsb.pageControl = PageControl.getQueryOnlyInstance();	
		wsb.initOrder(order_default);
		return this;	
	}
	/**
	 * 初始化分页
	 * @param request
	 * @param response 
	 *void
	 * @exception 
	 * @since  1.0.0
	 */
	public  WebSearchBuffer initPageCountOnly(String order_default) {
		WebSearchBuffer wsb=this;
		wsb.pageControl = PageControl.getCountOnlyInstance();	
		return this;	
	}

	public  WebSearchBuffer addLike(String[] params) {
		if(params!=null)
		for (String name : params) {
			try {
				sqlBuffer.addLike(name, request.getParameter(name));	
			} catch (Exception e) {
				log.error("addLike error",e);
			}
		}
		return this;
	}


	public  WebSearchBuffer addString(String[] params) {
		if(params!=null)
		for (String name : params) {	
			try {
				sqlBuffer.add(name, request.getParameter(name));	
			} catch (Exception e) {
				log.error("addString error",e);
			}		
		}
		return this;
	}

	public  WebSearchBuffer addInteger(String[] params) {
		if(params!=null)
		for (String name : params) {
			Integer valueInt=null;
			String value=request.getParameter(name);
			try {
				if(value!=null&&!"-1".equals(value)&&!"null".equals(value)){
					int tmp=DataUtil.parseInt(value, -1);
					valueInt=tmp;
				}
				if(valueInt!=null)
				sqlBuffer.add(name, valueInt);	
			} catch (Exception e) {
				log.error("出错:{}-{}",value,valueInt,e);
			}		
		}
		return this;
	}
	

	/**
	 * 
	 * getConList(作用)<br/>
	 * (适用条件):<br/>
	 * (执行流程):<br/>
	 * (使用方法):<br/>
	 * (注意事项):<br/>
	 * @return 
	 *List<SqlCondGroup>
	 * @exception 
	 * @since  1.0.0
	 */
	public List<SqlCondGroup> getConList() {
		return sqlBuffer.end();
	}

	public PageControl getPageControl() {
		return pageControl;
	}

	public long getRowCount() {
		long count=0;
		if (pageControl.isUsePage()) {
			count = pageControl.getRowCount();
		} else {
			count = getPageControl().getList().size();
		}
		return count;
	}

	public boolean isUsePage() {
		return usePage;
	}

	public void setUsePage(boolean usePage) {
		this.usePage = usePage;
	}
}
