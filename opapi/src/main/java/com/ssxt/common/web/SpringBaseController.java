package com.ssxt.common.web;

import static com.ssxt.common.util.DataUtil.notNullString;
import static com.ssxt.common.util.DataUtil.showMsgException;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.cfg.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.util.Assert;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.ssxt.activeMQ.DmzSend;
import com.ssxt.common.enums.ActionMsgParam;
import com.ssxt.common.enums.ConstParam;
import com.ssxt.common.info.FieldComment;
import com.ssxt.common.info.FieldGroup;
import com.ssxt.common.info.HibernateCfgHelper;
import com.ssxt.common.json.JsonHelper;
import com.ssxt.common.page.PageControl;
import com.ssxt.common.page.SqlBuffer;
import com.ssxt.common.page.SqlCondGroup;
import com.ssxt.common.page.WebSearchBuffer;
import com.ssxt.common.service.GenericServiceImpl;
import com.ssxt.common.util.AccData;
import com.ssxt.common.util.CtxHelper;
import com.ssxt.common.util.DataUtil;

/**
 * <b>类名称：</b>SpringBaseController<br/>
 * <b>类描述：</b>Control的基类，处理日期、公共方法<br/>
 * 1 继承此类，则都拥有/api/xxx/开头的以下方法: /name,/funcs,/fields, CRUD方法(不映射) 2
 * 公共方法可以给子类直接copy，进行完整覆盖重写; 自定义的方法也可抄写下来，依样画葫芦 3
 * CRUD方法：/add,/load,/update,/delete,/select,/query
 * 仅提供样板，可以用overrideCRUDd代码提示产生调用映射 4 注意:映射方法必须完整重写(包括注解)才能覆盖，
 * 如果不覆盖，导致不同方法映射同一路径，会产生服务器启动异常 5 该方法完整通用，建议所有Controller实现类都继承此类 <b>创建人：</b>杨培新
 * <br/>
 * <b>修改人：</b>杨培新<br/>
 * 
 * <b>修改时间：</b>2016年9月10日 上午11:28:31<br/>
 * <b>修改备注：</b><br/>
 * 
 * @version 1.0.0<br/>
 * 
 */
@SuppressWarnings("all")
public abstract class SpringBaseController<T extends Serializable, PK extends Serializable> {

	/*
	 * @Autowired private DmzSend sendQueue;
	 * 
	 * public DmzSend getSendQueue() { return sendQueue; }
	 */

	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(SpringBaseController.class);

	public SpringBaseController() {
		this.entityClass = (Class<T>) DataUtil.getGenricType(getClass(), 0);
		this.entityClassName = entityClass.getName();
	}

	protected Class<T> entityClass; // 实体类Class类型型
	protected String entityClassName; // 实体类Class名称
	protected String entityNameTable = null; // 实体类名称（中文优先）

	/**
	 * 判断当前实例是否为hbm获取注释，默认为否（通过annotation注解获取注释）
	 * 
	 * @return boolean
	 */
	public abstract boolean getCommentByHbm();

	/**
	 * 获得当前实例名称
	 * 
	 * @return String
	 */
	public abstract String getEntityName();

	/**
	 * 获得当前bean的Service
	 * 
	 * @return GenericServiceImpl<T,PK>
	 */
	public abstract GenericServiceImpl<T, PK> getService();

	/**
	 * 获取实体类业务名 过滤为空的情况
	 * 
	 * @return String
	 * @exception @since
	 *                1.0.0
	 */
	public String getEntityNameNotNull() {
		if (DataUtil.isNull(entityNameTable)) {
			entityNameTable = getEntityName();
		}
		if (DataUtil.isNull(entityNameTable)) {
			// 采用一次性获取
			synchronized (entityClassName) {
				if (DataUtil.isNull(entityNameTable)) {
					entityNameTable = HibernateCfgHelper.getTableComment(entityClass);
				}
				if (DataUtil.isNull(entityNameTable)) {
					entityNameTable = entityClass.getSimpleName();
				}
			}
		}

		return entityNameTable;
	};

	@Resource
	private RequestMappingHandlerMapping requestMappingHandlerMapping;

	public void setRequestMappingHandlerMapping(RequestMappingHandlerMapping requestMappingHandlerMapping) {
		this.requestMappingHandlerMapping = requestMappingHandlerMapping;
	}

	@Comment("获得定义名称")
	@RequestMapping(value = "name", method = { RequestMethod.GET, RequestMethod.POST })
	public void name(HttpServletRequest request, HttpServletResponse response) {
		// request.getCookies();
		CtxHelper.responseResult(response, getEntityNameNotNull());
	}

	public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
		return requestMappingHandlerMapping;
	}

	@Comment("获得字段列表及说明")
	@RequestMapping(value = "fields", method = RequestMethod.GET)
	public void fields(HttpServletRequest request, HttpServletResponse response) {

		AccData ad = CtxHelper.createFailAccData();
		try {

			FieldComment pk = HibernateCfgHelper.getPkColumn(entityClass);
			FieldComment[] fields = null;
			if (getCommentByHbm()) {
				fields = HibernateCfgHelper.getCommentsArr(entityClass);
			} else {
				fields = HibernateCfgHelper.getCommentsArrByAnnotation(entityClass);
			}
			FieldGroup fg = new FieldGroup(getEntityNameNotNull(), pk, fields);
			// CtxHelper.responseResult(response, JsonHelper.toJSONString(fg));

			ad.setMsg("字段列表");
			ad.setData(fg);
			ad.setSuccess(true);
		} catch (Exception e) {

			log.error(getEntityNameNotNull() + " 加载失败！id:", e);
			ad.setMsg(DataUtil.getRootCauseMsg(e));
			ad.setData(e.toString());
			ad.setSuccess(false);
		}
		CtxHelper.setNoCache(response);
		CtxHelper.writeToJson(response, ad);
	}

	@Comment("获得简单字段列表及说明")
	@RequestMapping(value = "fieldsName", method = RequestMethod.GET)
	public void fieldsName(HttpServletRequest request, HttpServletResponse response) {
		CtxHelper.responseResult(response,
				String.format("%s:%s", getEntityNameNotNull(),
						getCommentByHbm() ? HibernateCfgHelper.getComments(entityClass).toString()
								: HibernateCfgHelper.getCommentsByAnnotation(entityClass).toString()));
	}

	/**
	 * 添加
	 * 
	 * @param bean
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@Comment("新增数据")
	public void add(@ModelAttribute("bean") T bean, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("新增数据");
		if (log.isDebugEnabled())
			log.debug("内容:", JsonHelper.toJSONString(bean));
		String info = "用户新增数据";
		AccData ad = CtxHelper.createFailAccData();
		try {
			Assert.notNull(bean, "参数不能为空");
			log.debug(info);
			/**
			 * 新增数据统一调用此方法 用户信息不需要填，放在线程变量里面，dao直接就可以获取
			 * createTime,createUser,modifyTime,modifyUser，不需要手工填，在dao做即可
			 */
			getService().saveDomain(info, bean);

			ad.setMsg(ActionMsgParam.ACM_SAV_SUC);
			ad.setSuccess(true);

		} catch (RuntimeException e) {
			log.info(getEntityNameNotNull() + "新增有误！", e);
			ad.setMsg(DataUtil.getRootCauseMsg(e));
			ad.setData(e.toString());
			ad.setSuccess(false);
		} catch (Exception e) {
			log.error(getEntityNameNotNull() + "新增失败！", e);
			ad.setMsg(DataUtil.getRootCauseMsg(e));
			ad.setData(e.toString());
			ad.setSuccess(false);
		}
		CtxHelper.setNoCache(response);
		CtxHelper.writeToJson(response, ad);
	}

	/**
	 * 加载单个
	 * 
	 * @param id
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@Comment("加载数据")
	public void load(@PathVariable PK id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		AccData ad = CtxHelper.createFailAccData();
		try {
			T bean = getService().get(id);
			String msg = "";
			if (bean == null) {
				msg = "没有该记录！id:" + id;
				ad.setMsg(msg);
				log.info(msg);
				showMsgException(msg);
			}
			ad.setData(bean);
			ad.setMsg(ActionMsgParam.ACM_LOA_SUC);
			ad.setSuccess(true);

		} catch (RuntimeException e) {
			ad.setMsg(DataUtil.getRootCauseMsg(e));
			ad.setData(e.toString());
			ad.setSuccess(false);
		} catch (Exception e) {
			log.error(getEntityNameNotNull() + " 加载失败！id:", id, e);
			ad.setMsg(DataUtil.getRootCauseMsg(e));
			ad.setData(e.toString());
			ad.setSuccess(false);
		}
		CtxHelper.setNoCache(response);
		CtxHelper.writeToJson(response, ad);
	}

	@Comment("判断存在")
	public T exist(PK id) {
		T bean = getService().get(id);
		return bean;

	}

	public List<T> exist(SqlBuffer sbf) {

		List<T> list = getService().findByCondition(PageControl.getQueryOnlyInstance(), sbf.end());

		return list;

	}

	/**
	 * 查询分页Hql
	 * 
	 * @param searchtext
	 * @param sort
	 * @param dir
	 * @param start
	 * @param pageSize
	 * @param request
	 * @param response
	 * @param sbf
	 * @return
	 * @throws Exception
	 */
	@Comment("搜索")
	public PageControl selectHql(@RequestParam(value = "searchtext", required = false) String searchtext,
			@RequestParam(value = "order", required = false) String sort,
			@RequestParam(value = "orderdir", required = false) String dir,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "length", required = false) Integer pageSize, HttpServletRequest request,
			HttpServletResponse response, SqlBuffer sbf) throws Exception {
		String info = "查找数据{}:{}-[{}]";
		log.debug(info, notNullString(searchtext), start, pageSize);

		pageSize = DataUtil.notNullInt(pageSize, ConstParam.DEFAULT_PAGE_SIZE);
		start = DataUtil.notNullInt(start, 0);
		pageSize = pageSize > 4 ? pageSize : ConstParam.DEFAULT_PAGE_SIZE;
		PageControl pageControl = PageControl.getPageEnableInstance(start, pageSize);

		String order = "";
		if (!DataUtil.isNull(sort) && !DataUtil.eq("1", sort)) {
			order = "order by ";
			/*
			 * if (!isNull(dir) && "desc".equalsIgnoreCase(dir)) order = order +
			 * " " + dir;
			 */
			String zidu = "";
			String[] sortDate = sort.split(",");
			String[] dirDate = dir.split(",");
			for (int i = 0; i < sortDate.length; i++) {
				if (i == 0) {
					zidu = sortDate[i] + " " + dirDate[i];
				} else {
					zidu += "," + sortDate[i] + " " + dirDate[i];
				}
			}

			pageControl.setOrder(order + zidu);
		} else {
			pageControl.setOrder("ORDER BY id desc");
		}

		if (sbf == null)
			sbf = SqlBuffer.begin();

		pageControl.setList(getService().findByCondition(pageControl, sbf.end()));
		return pageControl;
	}

	/**
	 * 查询分页sql
	 * 
	 * @param searchtext
	 * @param sort
	 * @param dir
	 * @param start
	 * @param pageSize
	 * @param request
	 * @param response
	 * @param returnType
	 * @param sbf
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	@Comment("搜索")
	public PageControl selectSql(@RequestParam(value = "searchtext", required = false) String searchtext,
			@RequestParam(value = "order", required = false) String sort,
			@RequestParam(value = "orderdir", required = false) String dir,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "length", required = false) Integer pageSize, HttpServletRequest request,
			HttpServletResponse response, Class returnType, SqlBuffer sbf, String sql) throws Exception {
		String info = "查找数据{}:{}-[{}]";
		log.debug(info, notNullString(searchtext), start, pageSize);
		boolean page = true;
		if (pageSize == null)
			page = false;
		pageSize = DataUtil.notNullInt(pageSize, ConstParam.DEFAULT_PAGE_SIZE);
		start = DataUtil.notNullInt(start, 0);
		pageSize = pageSize > 4 ? pageSize : ConstParam.DEFAULT_PAGE_SIZE;

		PageControl pageControl = PageControl.getPageEnableInstance(start, pageSize);
		if (page == false)
			pageControl = PageControl.getQueryOnlyInstance();
		String order = "";
		if (!DataUtil.isNull(sort) && !DataUtil.eq("1", sort)) {
			order = "order by ";
			/*
			 * if (!isNull(dir) && "desc".equalsIgnoreCase(dir)) order = order +
			 * " " + dir;
			 */
			String zidu = "";
			String[] sortDate = sort.split(",");
			String[] dirDate = dir.split(",");
			for (int i = 0; i < sortDate.length; i++) {
				if (i == 0) {
					zidu = sortDate[i] + " " + dirDate[i];
				} else {
					zidu += "," + sortDate[i] + " " + dirDate[i];
				}
			}

			pageControl.setOrder(order + zidu);
		} else {
			pageControl.setOrder("ORDER BY id desc");
		}

		if (sbf == null)
			sbf = SqlBuffer.begin();
		if (returnType == null)
			returnType = Map.class;
		pageControl.setList(getService().findByNativeCondition(pageControl, sbf.end(), sql, returnType));
		return pageControl;
	}

	/**
	 * 修改数据
	 * 
	 * @param id
	 * @param bean
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@Comment("修改数据")
	public void update(@PathVariable PK id, @ModelAttribute("bean") T bean, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("修改数据:{}", id);
		if (log.isDebugEnabled())
			log.debug("内容:", JsonHelper.toJSONString(bean));
		AccData ad = CtxHelper.createFailAccData();

		try {
			String info = "修改数据存在:{}";
			log.debug(info, bean);

			Assert.notNull(id, "id不能为空");
			Assert.notNull(bean, "参数不能为空");
			// if(!id.equals(bean.getId())){
			// showMsgException("id必须一致！");
			// }
			T old = getService().get(id);
			if (old == null) {
				showMsgException("找不到" + getEntityNameNotNull() + "的数据,id:" + id);
			}
			/**
			 * 修改数据统一调用此方法 用户信息不需要填，放在线程变量里面，dao直接就可以获取
			 * createTime,createUser,modifyTime,modifyUser，不需要手工填，在dao做即可
			 * 具备属性空值过滤、对比修改记录（中文）功能
			 */
			getService().updateDomain("用户修改数据", old, bean);

			ad.setMsg(ActionMsgParam.ACM_MOD_SUC);
			ad.setSuccess(true);

		} catch (RuntimeException e) {
			log.info(getEntityNameNotNull() + "更新有误！", e);
			ad.setMsg(DataUtil.getRootCauseMsg(e));
			ad.setData(e.toString());
			ad.setSuccess(false);
		} catch (Exception e) {
			log.error(getEntityNameNotNull() + "更新失败！", e);
			ad.setMsg(DataUtil.getRootCauseMsg(e));
			ad.setData(e.toString());
			ad.setSuccess(false);
		}
		CtxHelper.setNoCache(response);
		CtxHelper.writeToJson(response, ad);

	}

	/**
	 * 删除数据
	 * 
	 * @param id
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@Comment("删除数据")
	public void delete(@PathVariable PK id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("删除数据:{}", id);
		AccData ad = CtxHelper.createFailAccData();
		try {
			Assert.notNull(id, "id不能为空");

			getService().deleteByKey(id);

			ad.setMsg(ActionMsgParam.ACM_DEL_SUC);
			ad.setSuccess(true);

			List<SqlCondGroup> conList = new ArrayList<SqlCondGroup>();
			PageControl pageControl = PageControl.getPageEnableInstance();

		} catch (RuntimeException e) {
			log.info(getEntityNameNotNull() + "删除有误！", e);
			ad.setMsg(DataUtil.getRootCauseMsg(e));
			ad.setData(e.toString());
			ad.setSuccess(false);

		} catch (Exception e) {
			log.error(getEntityNameNotNull() + "删除失败！", e);
			ad.setMsg(DataUtil.getRootCauseMsg(e));
			ad.setData(e.toString());
			ad.setSuccess(false);
		}
		CtxHelper.setNoCache(response);
		CtxHelper.writeToJson(response, ad);
	}

	/**
	 * 返回全部数据
	 * 
	 * @param request
	 * @param response
	 * @param sbf
	 * @throws Exception
	 */
	@Comment("返回全部数据")
	public void loadAll(HttpServletRequest request, HttpServletResponse response, SqlBuffer sbf, String sql,
			PageControl p) throws Exception {

		AccData ad = CtxHelper.createFailAccData();
		if (p == null)
			p = PageControl.getQueryOnlyInstance();
		try {

			if (sql != null) {
				if (sbf == null)
					sbf = sbf.begin();
				ad.setSuccess(true);
				ad.setMsg(ActionMsgParam.ACM_LIS_SUC);
				ad.setData(getService().findByNativeCondition(p, sbf.end(), sql, Map.class));
			} else {
				if (sbf == null)
					ad.setData(getService().loadAll());
				else
					ad.setData(getService().findByCondition(p, sbf.end()));
				ad.setMsg(ActionMsgParam.ACM_LIS_SUC);
				ad.setSuccess(true);

			}

			// List<SqlCondGroup> conList = new ArrayList<SqlCondGroup>();
			// PageControl pageControl = PageControl.getPageEnableInstance();

		} catch (RuntimeException e) {
			log.info(getEntityNameNotNull() + "查询有误！", e);
			ad.setMsg(DataUtil.getRootCauseMsg(e));
			ad.setData(e.toString());
			ad.setSuccess(false);

		} catch (Exception e) {
			log.error(getEntityNameNotNull() + "查询失败！", e);
			ad.setMsg(DataUtil.getRootCauseMsg(e));
			ad.setData(e.toString());
			ad.setSuccess(false);
		}
		CtxHelper.setNoCache(response);
		CtxHelper.writeToJson(response, ad);
	}

	/**
	 * 返回json
	 * 
	 * @param param
	 * @param count
	 * @param success
	 * @param message
	 * @param request
	 * @param response
	 */
	@Comment("返回json")
	public void toJson(Object param, Object count, boolean success, String message, HttpServletRequest request,
			HttpServletResponse response) {
		AccData ad = CtxHelper.createFailAccData();
		ad.setData(param);
		ad.setTotalCount(DataUtil.parseLong(count.toString(), new Long(0)));
		ad.setSuccess(success);
		ad.setMsg(message);
		CtxHelper.setNoCache(response);
		CtxHelper.writeToJson(response, ad);
	}

	/**
	 * 初始化分页信息
	 * 
	 * @param searchtext
	 * @param sort
	 * @param dir
	 * @param start
	 * @param pageSize
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public PageControl initPage(@RequestParam(value = "searchtext", required = false) String searchtext,
			@RequestParam(value = "order", required = false) String sort,
			@RequestParam(value = "orderdir", required = false) String dir,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "length", required = false) Integer pageSize, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String info = "查找数据{}:{}-[{}]";
		log.debug(info, notNullString(searchtext), start, pageSize);

		pageSize = DataUtil.notNullInt(pageSize, ConstParam.DEFAULT_PAGE_SIZE);
		start = DataUtil.notNullInt(start, 0);
		pageSize = pageSize > 4 ? pageSize : ConstParam.DEFAULT_PAGE_SIZE;
		PageControl pageControl = PageControl.getPageEnableInstance(start, pageSize);

		String order = "";
		if (!DataUtil.isNull(sort) && !DataUtil.eq("1", sort)) {
			order = "order by ";
			/*
			 * if (!isNull(dir) && "desc".equalsIgnoreCase(dir)) order = order +
			 * " " + dir;
			 */
			String zidu = "";
			String[] sortDate = sort.split(",");
			String[] dirDate = dir.split(",");
			for (int i = 0; i < sortDate.length; i++) {
				if (i == 0) {
					zidu = sortDate[i] + " " + dirDate[i];
				} else {
					zidu += "," + sortDate[i] + " " + dirDate[i];
				}
			}

			pageControl.setOrder(order + zidu);
		} else {
			pageControl.setOrder("ORDER BY id desc");
		}
		// 不分页
		String page = request.getParameter("page");
		if (page != null)
			if (page.equals("false"))
				pageControl.setUsePage(false);
		return pageControl;
	}

	/**
	 * 查询
	 * 
	 * @param request
	 * @param response
	 * @param p
	 *            分页
	 * @param sbf
	 *            条件
	 * @param sql
	 *            如果为空hql
	 * @return
	 * @throws Exception
	 */
	@Comment("返回全部数据")
	public PageControl commonFind(HttpServletRequest request, HttpServletResponse response, PageControl p,
			SqlBuffer sbf, String sql, Class c) throws Exception {

		AccData ad = CtxHelper.createFailAccData();
		if (p == null)
			p = PageControl.getQueryOnlyInstance();

		if (sql != null) {
			if (sbf == null)
				sbf = sbf.begin();
			List list = getService().findByNativeCondition(p, sbf.end(), sql, c);
			p.setList(list);

		} else {
			List list = getService().findByCondition(p, sbf.end());
			p.setList(list);

		}

		return p;

	}

}
