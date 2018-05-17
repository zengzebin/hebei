package com.ssxt.common.service;

import static com.ssxt.common.util.DataUtil.notNullString;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.ssxt.activeMQ.DmzSend;
import com.ssxt.common.dao.GenericDao;
import com.ssxt.common.enums.ConstParam;
import com.ssxt.common.page.PageControl;
import com.ssxt.common.page.SqlBuffer;
import com.ssxt.common.page.SqlCondGroup;
import com.ssxt.common.util.DataUtil;
import com.ssxt.common.util.MDCUtil;

/**
 * @ClassName：GenericServiceImpl @Description：
 * @author zhengxican @date：2015-08-25
 * @version V1.0
 */
@Service
@SuppressWarnings("all")
public abstract class GenericServiceImpl<T extends Serializable, PK extends Serializable>
		implements GenericService<T, PK> {

	/*
	 * @Autowired private DmzSend sendQueue;
	 * 
	 * public DmzSend getSendQueue() { return sendQueue; }
	 */

	public abstract GenericDao<T, PK> getDao();

	public T get(PK id) {
		return (T) getDao().get(id);
	}

	public T load(PK id) {
		return (T) getDao().load(id);
	}

	public List<T> loadAll() {
		return getDao().loadAll();
	}

	public List<T> queryByExample(T entity) {
		return getDao().queryByExample(entity);
	}

	public T getFirstByExample(T entity) {
		return getDao().getFirstByExample(entity);
	}

	public PK save(T entity) {
		return getDao().save(entity);
	}

	public void saveOrUpdate(T entity) {
		getDao().saveOrUpdate(entity);
	}

	public void update(T entity) {
		getDao().update(entity);
	}

	public void delete(T entity) {
		getDao().delete(entity);
	}

	public void deleteByKey(PK id) {
		this.delete(this.load(id));
	}

	public void deleteAll(Collection<T> entities) {
		getDao().deleteAll(entities);
	}

	//
	//
	//
	//
	//// /* (non-Javadoc)
	//// *
	// @com.ssxt.supervise.common.service.GenericService#findBySQL(com.diyeasy.common.sql.PageControl,
	// java.lang.String, java.util.List)
	//// */
	//// @SuppressWarnings("unchecked")
	//// public List findBySQL(PageControl pageControl, String sql,
	//// List paramValues) {
	//// return findBySQL(pageControl, sql, paramValues.toArray());
	//// }
	//// /* (non-Javadoc)
	//// *
	// @com.ssxt.supervise.common.service.GenericService#findBySQL(com.diyeasy.common.sql.PageControl,
	// java.lang.String, java.lang.Object[])
	//// */
	//// @SuppressWarnings("unchecked")
	//// public List findBySQL(PageControl pageControl, String sql,
	//// Object[] paramValues) {
	//// return (List) ((GenericDao<T, PK>) getDao()).findBySQL(pageControl,
	// sql, paramValues);
	//// }
	// /**
	// *
	// 返回按属性条件查询的结果列表,采用in方式,即propertyName中第一个propertyName对应valueList中的第一个List，以此类推
	// * @param pageControl 用来控制排序和分页的参数类
	// * @param propertyName 属性名
	// * @param valueList 属性值列表
	// * @param sign 符号
	// * @param type 条件连接类型(and还是or)
	// * @return 查询结果(List列表)
	// */
	// @SuppressWarnings("unchecked")
	// public List<T> findByInProperties(PageControl pageControl,
	// String[] propertyName, List[] valueList, String[] sign, String[] type){
	// return getDao().findByInProperties(pageControl, propertyName, valueList,
	// sign, type);
	// }
	// /**
	// *
	// 返回带统计函数、按属性条件查询的结果列表（List<Map>）,采用in方式,即propertyName中第一个propertyName对应valueList中的第一个List，以此类推
	// * 必须在列前带表的别名如a.colmn1,c.colmn2
	// * @param pageControl 用来控制排序和分页的参数类
	// * @param fromTable from表名，多表如A m left outer join B n
	// * @param useProperty 需要获得的参数名
	// * @param alias 别名，用map.get(别名)获取参数值:
	// * @param propertyName 属性名
	// * @param valueList 属性值列表
	// * @param sign 符号
	// * @param type 条件连接类型(and还是or)
	// * @return 查询结果(List列表)
	// */
	// @SuppressWarnings("unchecked")
	// public List findByStatistics(PageControl pageControl,String
	// fromTable,String[] useProperty,String[] alias, String[] propertyName,
	// List[] valueList, String[] sign, String[] type){
	// return getDao().findByStatistics(pageControl, fromTable, useProperty,
	// alias, propertyName, valueList, sign, type);
	// }
	//
	//
	//
	//
	// /**
	// * 使用SQL语句查询数据
	// * @param sql 查询语句
	// * @return 查询结果(List列表)
	// */
	// @SuppressWarnings("unchecked")
	// public List findBySQL(String sql){
	// return getDao().findBySQL(sql);
	// }
	//
	//
	// /**
	// * 使用SQL语句查询数据，附加查询参数
	// * @param sql 查询语句
	// * @param value 属性值
	// * @return 查询结果(List列表)
	// */
	// @SuppressWarnings("unchecked")
	// public List findBySQL(String sql,Object[] value){
	// return getDao().findBySQL(sql,value);
	// }
	// /* (non-Javadoc)
	// * @see
	// com.ssxt.supervise.common.service.GenericService#findByNativeSQL(com.diyeasy.common.sql.PageControl,
	// java.lang.String, java.lang.Class)
	// */
	// @SuppressWarnings("unchecked")
	// public List findByNativeSQL(PageControl pageControl, String sql,
	// Class returnType) {
	// return getDao().findByNativeSQL(pageControl, sql, returnType);
	// }
	//
	// /* (non-Javadoc)
	// * @see
	// com.ssxt.supervise.common.service.GenericService#findByNativeSQL(com.diyeasy.common.sql.PageControl,
	// java.lang.String, java.lang.Object[], java.lang.Class)
	// */
	// @SuppressWarnings("unchecked")
	// public List findByNativeSQL(PageControl pageControl, String sql,
	// Object[] object, Class returnType) {
	// return getDao().findByNativeSQL(pageControl, sql, object,returnType);
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ssxt.supervise.common.service.GenericService#findByCondition(com.
	 * diyeasy.common.sql.PageControl, java.util.List)
	 */
	@SuppressWarnings("unchecked")
	public List findByCondition(PageControl pageControl, List<SqlCondGroup> conList) {
		return getDao().findByCondition(pageControl, conList);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ssxt.supervise.common.service.GenericService#findByNativeCondition(
	 * com.diyeasy.common.sql.PageControl, java.util.List, java.lang.String,
	 * java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	public List findByNativeCondition(PageControl pageControl, List<SqlCondGroup> conList, String preWhereSql,
			Class returnType) {
		if (conList == null) {
			SqlBuffer sbf1 = SqlBuffer.begin();
			conList = sbf1.end();
		}

		return getDao().findByNativeCondition(pageControl, conList, preWhereSql, returnType);
	}

	// /* (non-Javadoc)
	// *
	// @com.ssxt.supervise.common.service.GenericService#findByNativeInProperties(com.diyeasy.common.sql.PageControl,
	// java.lang.String, java.lang.String[], java.util.List[],
	// java.lang.String[], java.lang.String[], java.lang.Class)
	// */
	// @SuppressWarnings("unchecked")
	// public List findByNativeInProperties(PageControl pageControl,
	// String preWhereSql, String[] propertyName, List[] valueList,
	// String[] sign, String[] type, Class returnType) {
	// return getDao().findByNativeInProperties(pageControl, preWhereSql,
	// propertyName, valueList, sign, type,returnType);
	// }
	// /* (non-Javadoc)
	// * @see
	// com.ssxt.supervise.common.service.GenericService#putSchoolNameBySchoolId(java.util.List)
	// */
	// public List<Map> putSchoolNameBySchoolId(List<Map> list) {
	// SchoolDao dao = (SchoolDao)
	// SpringBeanFactory.getInstance().getBean("schoolDao");
	// List<SqlCondGroup> conList = new ArrayList<SqlCondGroup>();
	// PageControl pageControl = PageControl.getQueryOnlyInstance();
	// List<SchoolInfo> tlist=dao.findByCondition(pageControl, conList);
	// for (Iterator<Map> iterator = list.iterator(); iterator.hasNext();) {
	// Map map = (Map) iterator.next();
	// Integer id=(Integer) map.get("schoolId");
	// if(id==null)
	// id=(Integer) map.get("School_ID");
	// if(id==null)
	// id=(Integer) map.get("school_ID");
	// for (Iterator<SchoolInfo> iterator2 = tlist.iterator();
	// iterator2.hasNext();) {
	// SchoolInfo bean = (SchoolInfo) iterator2.next();
	// if(bean.getSchoolId().equals(id)){
	// map.put("School_Name", bean.getSchoolName());
	// break;
	// }
	// }
	// }
	// return list;
	// }
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ssxt.supervise.common.service.GenericService#putDptNameByDptId(java.
	 * util.List)
	 */
	// @SuppressWarnings("unchecked")
	// public List<Map> putDptNameByDptId(List<Map> list) {
	// SysDepartmentDao dao = (SysDepartmentDao)
	// SpringBeanFactory.getInstance().getBean("sysDepartmentDao");
	// List<SqlCondGroup> conList = new ArrayList<SqlCondGroup>();
	// PageControl pageControl = PageControl .getQueryOnlyInstance();
	// List<SysDepartment> tlist=dao.findByCondition(pageControl, conList);
	// for (Iterator<Map> iterator = list.iterator(); iterator.hasNext();) {
	// Map map = (Map) iterator.next();
	// Integer id=(Integer) map.get("dptId");
	// if(id==null)
	// id=(Integer) map.get("Dpt_Id");
	// if(id==null)
	// id=(Integer) map.get("dpt_Id");
	// for (Iterator<SysDepartment> iterator2 = tlist.iterator();
	// iterator2.hasNext();) {
	// SysDepartment bean = (SysDepartment) iterator2.next();
	// if(bean.getDptId().equals(id)){
	// map.put("School_Name", bean.getDptName());
	// break;
	// }
	// }
	// }
	// return list;
	// }

	//
	// /* (non-Javadoc)
	// * @see
	// com.ssxt.supervise.common.service.GenericService#getMapListGroupByInProperties(com.diyeasy.common.sql.PageControl,
	// java.lang.String[], java.lang.String[], java.util.List[],
	// java.lang.String[], java.lang.String[])
	// */
	// public List<Map> getMapListGroupByInProperties(PageControl
	// pageControl,String[] alias,String[] propertyName,List[]
	// valueList,String[] sign,String[] type ){
	//
	// String fromTable=getEntityClass().getName()+" as model";
	//// String fromTable="";
	// String[] useProperty=new String[alias.length];
	// for (int i = 0; i < alias.length; i++) {
	// useProperty[i]="model."+alias[i];
	//
	// if(-1!=alias[i].indexOf("."))
	// alias[i]=alias[i].substring(alias[i].indexOf(".")+1);
	// }
	// return getMapListGroupByInProperties(pageControl, fromTable,
	// useProperty, alias, propertyName, valueList, sign, type);
	// }
	//
	// /* (non-Javadoc)
	// * @see
	// com.ssxt.supervise.common.service.GenericService#getMapListGroupByInProperties(com.diyeasy.common.sql.PageControl,
	// java.lang.String[], java.lang.String[], java.lang.String[],
	// java.util.List[], java.lang.String[], java.lang.String[])
	// */
	// public List<Map> getMapListGroupByInProperties(PageControl pageControl,
	// String[] alias, String[] useProperty, String[] propertyName,
	// List[] valueList, String[] sign, String[] type) {
	//
	// String fromTable=getEntityClass().getName()+" as model";
	//// String fromTable="";
	// return getMapListGroupByInProperties(pageControl, fromTable, useProperty,
	// alias, propertyName, valueList, sign, type);
	// }
	// /*
	// * (non-Javadoc)
	// *
	// * @see
	// com.ssxt.supervise.common.service.GenericService#getMapListGroupByInProperties(com.diyeasy.common.sql.PageControl,
	// * java.lang.String, java.lang.String[], java.lang.String[],
	// * java.lang.String[], java.util.List[], java.lang.String[],
	// * java.lang.String[])
	// */
	// public List<Map> getMapListGroupByInProperties(PageControl pageControl,
	// String fromTable, String[] useProperty, String[] alias,
	// String[] propertyName, List[] valueList, String[] sign,
	// String[] type) {
	//// String[] useProperty=new
	// String[]{"model.eplId","model.eplName","model.sysDepartment.dptName","model.registerDate","SUM(t.tradeMoney)"};
	//// String[] alias=new
	// String[]{"eplId","eplName","dptName","registerDate","tradeMoney"};
	// String order=pageControl.getOrder();
	// if(alias!=null&&alias.length>1){
	// StringBuffer guoupBy=new StringBuffer(" group by ");
	// for (int i = 0; i < alias.length; i++) {
	// guoupBy.append(useProperty[i]+",");
	// }
	// pageControl.setGroupBy(guoupBy.substring(0, guoupBy.length()-1));
	// }
	// return this.findByStatistics(pageControl, fromTable, useProperty, alias,
	// propertyName, valueList, sign, type);
	// }
	// /* (non-Javadoc)
	// * @see
	// com.ssxt.supervise.common.service.GenericService#getSpecifiedInfoByThisViewProperties(com.diyeasy.common.sql.PageControl,
	// java.lang.String[], java.util.List, java.util.List, java.util.List,
	// java.util.List)
	// */
	// @SuppressWarnings("unchecked")
	// public List<T> getSpecifiedInfoByThisViewProperties(PageControl
	// pageControl,
	// String[] notNullproperty, List<String> propertyName,
	// List<List> valueList, List<String> sign, List<String> type) {
	// //-1是默认设置值为null的标准
	// List notNullValueList=Arrays.asList("-1");
	// if(type.size()<propertyName.size()-1||type.size()>propertyName.size()){
	// System.out.println("type下标超出范围！");
	// return null;
	// }
	// else if(type.size()==propertyName.size()-1){
	// type.add(" and(");
	// }
	// else{
	// String preType=type.get(type.size()-1);
	// if(preType==null||!preType.contains("and")||!preType.contains("(")){
	// type.remove(type.size()-1);
	// type.add(" and(");
	// }
	// }
	// for(int i=0;i<notNullproperty.length-1;i++){
	// propertyName.add(notNullproperty[i]);
	// sign.add("!=");
	// valueList.add(notNullValueList);
	// type.add("and");
	// }
	// propertyName.add(notNullproperty[notNullproperty.length-1]);
	// sign.add("!=");
	// valueList.add(notNullValueList);
	// type.add(")");
	//
	//
	// return getDao().findByInProperties(pageControl, propertyName.toArray(new
	// String[]{}), valueList.toArray(new List[]{}), sign.toArray(new
	// String[]{}),type.toArray(new String[]{}));
	// }

	// protected RiverDao riverDao;
	//
	// @Autowired
	// public void setRiverDao(RiverDao dao) {
	// this.riverDao = dao;
	// }
	// protected ReachDao reachDao;
	//
	// @Autowired
	// public void setReachDao(ReachDao dao) {
	// this.reachDao = dao;
	// }
	// protected CountyDao rountyDao;
	//
	// @Autowired
	// public void setCountyDao(CountyDao dao) {
	// this.rountyDao = dao;
	// }
	// protected DeptDao deptDao;
	//
	// @Autowired
	// public void setDeptDao(DeptDao dao) {
	// this.deptDao = dao;
	// }

	/**
	 * 
	 * @param table
	 * @param keyAsName
	 * @param valueAsName
	 * @param keyName
	 * @param valueName
	 * @return
	 */
	public List<Map> getTableKeyName(String table, String keyAsName, String valueAsName, String keyName,
			String valueName, String orderStr) {
		GenericDao dao = getDao();
		List<SqlCondGroup> conList = new ArrayList<SqlCondGroup>();
		PageControl pageControl = PageControl.getQueryOnlyInstance();

		String preWhereSql = String.format("select %s as %s ,%s as %s  from %s", keyName, keyAsName, valueName,
				valueAsName, table);
		List<Map> tlist = dao.findByNativeCondition(pageControl, conList, preWhereSql, Map.class);
		return tlist;
	}

	/***
	 * 
	 * @param list
	 * @param table
	 * @param keyAsName
	 * @param valueAsName
	 * @param keyName
	 * @param valueName
	 * @return
	 */
	public List<Map> getInitKeyName(List<Map> list, String table, String keyAsName, String valueAsName, String keyName,
			String valueName) {
		GenericDao dao = getDao();
		List<Map> tlist = getTableKeyName(table, keyAsName, valueAsName, keyName, valueName, "");
		for (Iterator<Map> iterator = list.iterator(); iterator.hasNext();) {
			Map map = (Map) iterator.next();
			String id = DataUtil.notNullString(map.get(keyAsName));
			if (!DataUtil.isNull(id))
				for (Iterator<Map> iterator2 = tlist.iterator(); iterator2.hasNext();) {
					Map bean = (Map) iterator2.next();
					if (bean.get(keyAsName).toString().equals(id)) {
						map.put(valueAsName, bean.get(valueAsName));
						break;
					}
				}
		}
		return list;
	}

	/**
	 * 
	 * 根据用户的提交数据(domain)，新增bean<br/>
	 * 
	 * @param schoolId
	 * @param sysUser
	 * @return Serializable
	 * @exception @since
	 *                1.0.0
	 */
	public Serializable saveDomain(String schoolId, Integer userId, String userName, T domain) {
		return getDao().saveDomain(schoolId, userId, userName, "用户创建数据", domain);
	}

	/**
	 * 
	 * 根据用户的提交数据(domain)，新增bean<br/>
	 * 
	 * @param schoolId
	 * @param sysUser
	 * @return Serializable
	 * @exception @since
	 *                1.0.0
	 */
	public Serializable saveDomain(String reason, T domain) {
		String schoolId = MDCUtil.getTenantId();
		Integer userId = MDCUtil.getUserId().intValue();
		String userName = MDCUtil.getUserName();
		return getDao().saveDomain(schoolId, userId, userName, reason, domain);
	}

	/**
	 * 
	 * 根据用户的提交数据(newBan)，更新原有数据(oldBean)的内容<br/>
	 * 
	 * @param schoolId
	 * @param sysUser
	 *            void
	 * @exception @since
	 *                1.0.0
	 */
	public void updateDomain(String schoolId, Integer userId, String userName, T oldBean, T newBean) {
		getDao().updateDomain(schoolId, userId, userName, "用户修改数据", oldBean, newBean);
	}

	/**
	 * 
	 * 根据用户的提交数据(newBan)，更新原有数据(oldBean)的内容<br/>
	 * 
	 * @param schoolId
	 * @param sysUser
	 *            void
	 * @exception @since
	 *                1.0.0
	 */
	public void updateDomain(String reason, T oldBean, T newBean) {
		String schoolId = MDCUtil.getTenantId();
		Integer userId = MDCUtil.getUserId().intValue();
		String userName = MDCUtil.getUserName();
		getDao().updateDomain(schoolId, userId, userName, "用户修改数据", oldBean, newBean);
	}

	/**
	 * 更新或者保存数据集
	 */
	public void saveOrUpdateAll(Collection<T> entities) {
		getDao().saveOrUpdateAll(entities);
	}

	public int updateByNativeCondition(String updateSql, List<SqlCondGroup> conList) {
		return getDao().updateByNativeCondition(updateSql, conList);
	}

	/**
	 * 删除修改
	 */
	public int updateByCondition(String updatehql, List<SqlCondGroup> conList) {
		return getDao().updateByCondition(updatehql, conList);
	}

	public int updateByCondition(List<SqlCondGroup> setList, List<SqlCondGroup> conList) {

		return getDao().updateByCondition(setList, conList);
	}

	/**
	 * 判斷是否存在Hql
	 * 
	 * @param sbf
	 * @return
	 */
	public boolean isExistHql(SqlBuffer sbf) {
		if (sbf == null)
			sbf = SqlBuffer.begin();
		int count = this.findByCondition(PageControl.getQueryOnlyInstance(), sbf.end()).size();
		if (count > 0)
			return true;
		else
			return false;
	}

	/**
	 * 判斷是否存在sql
	 * 
	 * @param sbf
	 * @return
	 */
	public boolean isExistSql(String preWhereSql, SqlBuffer sbf) {
		if (sbf == null)
			sbf = SqlBuffer.begin();
		int count = this.findByNativeCondition(PageControl.getQueryOnlyInstance(), sbf.end(), preWhereSql, Map.class)
				.size();
		if (count > 0)
			return true;
		else
			return false;
	}

	/**
	 * 删除
	 * 
	 * @param deleteSql
	 * @param conList
	 * @return
	 */
	public int deleteSql(String sql, List<SqlCondGroup> conList) {
		return getDao().deleteSql(sql, conList);
	}

	/**
	 * 删除
	 * 
	 * @param deleteSql
	 * @param conList
	 * @return
	 */
	public int deleteHql(List<SqlCondGroup> conList) {
		return getDao().deleteHql(conList);
	}

	/**
	 * 根據sql查詢
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
	 * @throws IOException
	 */
	public PageControl selectSql(@RequestParam(value = "searchtext", required = false) String searchtext,
			@RequestParam(value = "order", required = false) String sort,
			@RequestParam(value = "orderdir", required = false) String dir,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "length", required = false) Integer pageSize, HttpServletRequest request,
			HttpServletResponse response, String sql, Class returnType, SqlBuffer sbf) throws IOException {
		String info = "查找数据{}:{}-[{}]";

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

		pageControl.setList(getDao().findByNativeCondition(pageControl, sbf.end(), sql, returnType));
		return pageControl;
	}

	public List<T> exist(SqlBuffer sbf) {

		List<T> list = getDao().findByCondition(PageControl.getQueryOnlyInstance(), sbf.end());

		return list;

	}

	public List<T> findSQL(String hql) {
		return getDao().findSQL(hql);
	}

	public void prepareCall(final String name) {
		getDao().prepareCall(name);
	}

	/**
	 * 插入sql
	 * 
	 * @param sql
	 * @return
	 */
	public int insert(final String sql) {
		return getDao().insert(sql);
	}

}
