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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.formula.functions.T;

import com.ssxt.common.dao.GenericDao;
import com.ssxt.common.util.DataUtil;
import com.ssxt.common.util.SpringBeanUtil;
 
/**
 * <b>类名称：</b>SqlUtil<br/>
 * <b>类描述：</b><br/>
 * <b>创建人：</b>杨培新<br/>
 * <b>修改人：</b>杨培新<br/>
 * 
 * <b>修改时间：</b>2016年12月5日 上午11:55:41<br/>
 * <b>修改备注：</b><br/>
 * 
 * @version 1.0.0<br/>
 */
public class SqlUtil {

	/**
	 * 
	 * 增加区域查询条件
	 * 
	 * @param conList
	 * @param prefix
	 * @param area
	 *            void
	 * @exception @since
	 *                1.0.0
	 */
	public static void addAreaCon(List<SqlCondGroup> conList, String prefix, String area) {
		int addvcd = DataUtil.parseInt(area, -1);
		addAreaCon(false,conList, prefix, addvcd);
	}

	/**
	 * 
	 * 增加区域查询条件
	 * 
	 * @param conList
	 * @param prefix
	 * @param area
	 *            void
	 * @exception @since
	 *                1.0.0
	 */
	public static void addAreaCon(boolean includeSon,List<SqlCondGroup> conList, String prefix, String area) {
		int addvcd = DataUtil.parseInt(area, -1);
		addAreaCon(includeSon,conList, prefix, addvcd);
	}
	/**
	 * 增加区域查询条件 支持国标三级区域分级样式，即省2市2区2
	 * @param includeSon 是否包括下级
	 * @param conList
	 * @param prefix
	 * @param addvcd 
	 *void
	 * @exception 
	 * @since  1.0.0
	 */
	public static void addAreaCon(boolean includeSon,List<SqlCondGroup> conList, String prefix, int addvcd) {
		String o = "";
		if(!includeSon){
			conList.add(new SqlCondGroup(prefix + "addvcd", addvcd + o, "=", "and"));
		}
		else{
			if (addvcd == 0) {
				 conList.add(new SqlCondGroup(prefix+"addvcd", addvcd+ o, "=","and"));
			} else if (addvcd > 0) {
				if (addvcd % 10000 == 0) {					
					conList.add(new SqlCondGroup(prefix + "addvcd", addvcd + o, ">=", "and"));
					conList.add(new SqlCondGroup(prefix + "addvcd", addvcd + 10000 + o, "<", "and"));
				} else if (addvcd % 100 == 0) {
					conList.add(new SqlCondGroup(prefix + "addvcd", addvcd + o, ">=", "and"));
					conList.add(new SqlCondGroup(prefix + "addvcd", addvcd + 100 + o, "<", "and"));
				} else {
					conList.add(new SqlCondGroup(prefix + "addvcd", addvcd + o, "=", "and"));
				}
			} else {
				conList.add(new SqlCondGroup(prefix + "addvcd", addvcd + o, "=", "and"));
			}
		}
	}

	/**
	 * 增加区域查询条件 支持国标三级区域分级样式，即省2市2区2
	 * @param includeSon 是否包括下级
	 * @param conList
	 * @param prefix
	 * @param addvcd 
	 *void
	 * @exception 
	 * @since  1.0.0
	 */
	public static void addAreaConOr(boolean includeSon,List<SqlCondGroup> conList, String prefix, int addvcd) {
		String o = "";
		if(!includeSon){
			conList.add(new SqlCondGroup(prefix + "addvcd", addvcd + o, "=", "or"));
		}
		else{
			if (addvcd == 0) {
				 conList.add(new SqlCondGroup(prefix+"addvcd", addvcd+ o, "=","or"));
			} else if (addvcd > 0) {
				if (addvcd % 10000 == 0) {
					
					conList.add(new SqlCondGroup("("+prefix + "addvcd", addvcd + o, ">=", "and"));
					conList.add(new SqlCondGroup(prefix + "addvcd", addvcd + 10000 + o, "<", ") or"));
				} else if (addvcd % 100 == 0) {
					conList.add(new SqlCondGroup("("+prefix + "addvcd", addvcd + o, ">=", "and"));
					conList.add(new SqlCondGroup(prefix + "addvcd", addvcd + 100 + o, "<", ") or"));

				} else {
					conList.add(new SqlCondGroup(prefix + "addvcd", addvcd + o, "=", "or"));

				}
			} else {
				conList.add(new SqlCondGroup(prefix + "addvcd", addvcd + o, "=", "or"));
			}
		}
	}
	/**
	 * 增加区域查询条件 支持国标三级区域分级样式，即省2市2区2
	 * 
	 * @param conList
	 * @param prefix
	 * @param addvcd
	 *            void
	 * @exception @since
	 *                1.0.0
	 */
	public static void addAreaCon(boolean includeSon,List<SqlCondGroup> conList, String prefix, List<Integer> areaList) {
		String o = "";
		if(areaList!=null){	
			if(areaList.size()>1){

				conList.add(new SqlCondGroup("", null, "(", ""));
				for (Integer addvcd : areaList) {
					addAreaConOr(includeSon, conList, prefix, addvcd);
				}
				conList.add(new SqlCondGroup("", null, ")", ""));
			}
			else 
				addAreaCon(includeSon, conList, prefix, areaList.get(0));
		}
	}
 
	 

	/**
	 * 判断条件是否不为空，不为空则sql拼接suffix
	 * 
	 * @param conList
	 * @param sql
	 * @param suffix
	 * @return String
	 * @exception @since
	 *                1.0.0
	 */
	public static String judgeNotNullAddSuffix(List<SqlCondGroup> conList, String sql, String suffix) {
		String str = sql;
		if (!DataUtil.isNull(conList)) {
			str = sql + " where ";
		}
		return str;
	}

	/**
	 * 判断条件是否不为空，不为空则sql拼接suffix
	 * 
	 * @param conList
	 * @param sql
	 * @param suffix
	 * @return StringBuffer
	 * @exception @since
	 *                1.0.0
	 */
	public static StringBuffer judgeNotNullAddSuffix(List<SqlCondGroup> conList, StringBuffer sql, String suffix) {
		if (!DataUtil.isNull(conList)) {
			sql.append(" where ");
		}
		return sql;
	}

	/**
	 * 判断是否存在外键,使用原生sql
	 * 
	 * @param dao
	 * @param tableComment
	 * @param tableName
	 * @param columnName
	 * @param value
	 * @return boolean
	 * @exception @since
	 *                1.0.0
	 */
	public static boolean judgeExistForeignKeyNative(GenericDao<?, ?> dao, String tableComment, String tableName,
			String columnName, Serializable value) {

		boolean flag = false;
		PageControl pageControl = PageControl.getCountOnlyInstance();
		String sql = "select 1 from " + tableName;
		SqlBuffer sbf = SqlBuffer.begin();
		sbf.add(columnName, value);
		dao.findByNativeCondition(pageControl, sbf.end(), sql, java.util.Map.class);
		if (pageControl.getRowCount() >= 1) {
			flag = true;
			if (!DataUtil.isNull(tableComment)) {
				DataUtil.showMsgException("存在关联记录:" + tableComment);
			}
		}
		return flag;
	}

	/**
	 * 判断是否存在外键
	 * 
	 * @param dao
	 * @param tableComment
	 * @param tableName
	 * @param columnName
	 * @param value
	 * @return boolean
	 * @exception @since
	 *                1.0.0
	 */
	public static boolean judgeExistForeignKey(GenericDao<?, ?> dao, String tableComment, String columnName,
			Serializable value) {

		boolean flag = false;
		PageControl pageControl = PageControl.getCountOnlyInstance();
		SqlBuffer sbf = SqlBuffer.begin();
		sbf.add(columnName, value);
		dao.findByCondition(pageControl, sbf.end());
		if (pageControl.getRowCount() >= 1) {
			flag = true;
			if (!DataUtil.isNull(tableComment)) {
				DataUtil.showMsgException("存在关联记录:" + tableComment);
			}
		}
		return flag;
	}
}
