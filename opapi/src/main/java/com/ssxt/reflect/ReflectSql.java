package com.ssxt.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;

import javax.persistence.Id;
import javax.persistence.Table;

import com.ssxt.web.bean.BasParam;
import com.ssxt.web.bean.SymUser;
import com.ssxt.web.service.UserService;

public class ReflectSql {
	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ReflectSql.class);

	public static String getString(Object fieldValue) {
		String value = "";
		if (fieldValue == null) {
			value = null;
		} else {
			value = "'" + fieldValue + "'";
		}
		return value;
	}

	public static String getNumerical(Object fieldValue) {
		String value = "";
		if (fieldValue == null) {
			value = null;
		} else {
			value = "'" + fieldValue + "'";
		}
		return value;
	}

	public static String getDate(Object fieldValue) {
		String value = "";
		if (fieldValue == null) {
			value = null;
		} else {
			SimpleDateFormat myFmt2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			value = "'" + myFmt2.format(fieldValue) + "'";

		}
		return value;
	}

	public static String getTblaeName(Object bean) {

		Class c = bean.getClass();
		// 判断是否包含Table类型的注解
		if (!c.isAnnotationPresent(Table.class)) {
			return null;
		}
		// 2.获取到table的名字
		Table t = (Table) c.getAnnotation(Table.class);
		String tableName = t.name();
		return tableName;
	}

	public static Map<String, Object> getfieldInfo(Field field, Object u) throws Exception {
		Class c = u.getClass();
		Map<String, Object> map = new HashMap<String, Object>();
		// 4.2.拿到字段的名
		String filedName = field.getName();
		map.put("filedName", filedName);
		// 获取相应字段的getXXX()方法
		String getMethodName = "get" + filedName.substring(0, 1).toUpperCase() + filedName.substring(1);
		// 字段的值
		Object fieldValue = null;// 属性有int、String等，所以定义为Object类

		Method getMethod = c.getMethod(getMethodName);
		fieldValue = getMethod.invoke(u);
		map.put("fieldValue", fieldValue);
		map.put("filed", false);
		map.put("pkId", false);
		if (getMethod.isAnnotationPresent(Column.class)) {
			Column column = getMethod.getAnnotation(Column.class);
			String name = column.name();
			map.put("name", name);
			map.put("filed", true);
		}
		if (getMethod.isAnnotationPresent(Id.class)) {
			map.put("name", filedName);
			map.put("pkId", true);
		}

		return map;

	}

	/**
	 * 条件语句
	 * 
	 * @param old
	 * @return
	 * @throws Exception
	 */
	public static String whereBean(Object old) throws Exception {
		Class c = old.getClass();
		StringBuffer where = new StringBuffer(" where 1=1 ");
		Field[] fArray = c.getDeclaredFields(); // 获取类属性的所有字段，
		// 3.遍历所有的字段
		for (Field field : fArray) {
			Map<String, Object> map = getfieldInfo(field, old);

			if ((Boolean) map.get("filed") || (Boolean) map.get("pkId")) {
				// String filedName = map.get("filedName").toString();
				String name = map.get("name").toString();
				Object fieldValue = map.get("fieldValue");
				if (fieldValue == null)
					continue;
				if (field.getType() == Double.class || field.getType() == Integer.class) {
					where.append(" and ").append(name).append("=").append(fieldValue);
				} else if (field.getType() == Date.class) {
					where.append(" and ").append(name).append("=").append(getDate(fieldValue));
				} else {
					where.append(" and ").append(name).append("=").append(getString(fieldValue));
				}
			}

		}

		return where.toString();

	}

	/**
	 * 插入语句
	 * 
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public static String inertSql(Object bean) throws Exception {
		Class c = bean.getClass();
		StringBuffer sqlStrBuilder = new StringBuffer("insert ");
		StringBuffer values = new StringBuffer(" values(");

		sqlStrBuilder.append(getTblaeName(bean)).append("  (");

		Field[] fArray = c.getDeclaredFields(); // 获取类属性的所有字段，

		// 3.遍历所有的字段
		for (Field field : fArray) {
			Map<String, Object> map = getfieldInfo(field, bean);

			if ((Boolean) map.get("filed") || (Boolean) map.get("pkId")) {
				// String filedName = map.get("filedName").toString();
				String name = map.get("name").toString();
				Object fieldValue = map.get("fieldValue");
				if (field.getType() == Double.class || field.getType() == Integer.class) {
					values.append(fieldValue).append(",");
					sqlStrBuilder.append(name).append(",");
				} else if (field.getType() == Date.class) {
					values.append(getDate(fieldValue)).append(",");
					sqlStrBuilder.append(name).append(",");
				} else {
					values.append(getString(fieldValue)).append(",");
					sqlStrBuilder.append(name).append(",");
				}
			}

		}
		sqlStrBuilder.deleteCharAt(sqlStrBuilder.length() - 1).append(")");
		values.deleteCharAt(values.length() - 1).append(")");
		sqlStrBuilder.append(values);
		log.info("生成插入语句:" + sqlStrBuilder.toString());
		return sqlStrBuilder.toString();

	}

	/**
	 * 更新语句
	 * 
	 * @param bean
	 *            更新的字段
	 * @param whereBean
	 *            更新的条件
	 * @param isNull
	 *            更新的时候null要不要更新,true要false不要
	 * @return
	 * @throws Exception
	 */
	public static String updateSql(Object bean, Object whereBean, boolean isNull) throws Exception {
		Class c = bean.getClass();

		StringBuffer sqlupdate = new StringBuffer("update ");
		StringBuffer whereStr = new StringBuffer(" where 1=1 ");

		sqlupdate.append(getTblaeName(bean)).append("  set ");

		Field[] fArray = c.getDeclaredFields(); // 获取类属性的所有字段，

		// 3.遍历所有的字段
		for (Field field : fArray) {
			Map<String, Object> map = getfieldInfo(field, bean);

			if ((Boolean) map.get("filed") || (Boolean) map.get("pkId")) {
				// String filedName = map.get("filedName").toString();
				String name = map.get("name").toString();
				Object fieldValue = map.get("fieldValue");
				// 如果为空是否继续update
				if (!isNull) {
					if (fieldValue == null)
						continue;
				}

				if (field.getType() == Double.class || field.getType() == Integer.class) {
					sqlupdate.append(name).append("=").append(fieldValue).append(",");
					if (whereBean == null && (Boolean) map.get("pkId"))
						whereStr.append(" and ").append(name).append("=").append(fieldValue);

				} else if (field.getType() == Date.class) {

					sqlupdate.append(name).append("=").append(getDate(fieldValue)).append(",");
					if (whereBean == null && (Boolean) map.get("pkId"))
						whereStr.append(" and ").append(name).append("=").append(getDate(fieldValue));

				} else {

					sqlupdate.append(name).append("=").append(getString(fieldValue)).append(",");
					if (whereBean == null && (Boolean) map.get("pkId"))
						whereStr.append(" and ").append(name).append("=").append(getString(fieldValue));

				}
			}

		}

		sqlupdate.deleteCharAt(sqlupdate.length() - 1);
		if (whereBean == null)
			sqlupdate.append(whereStr);
		else
			sqlupdate.append(whereBean(whereBean));
		log.info("生成更新语句:" + sqlupdate.toString());

		return sqlupdate.toString();

	}

	/**
	 * 查询语句
	 * 
	 * @param bean
	 * @param alias
	 * @return
	 * @throws Exception
	 */
	public static String selectSql(Object bean) throws Exception {
		Class c = bean.getClass();
		StringBuffer selectSql = new StringBuffer("select ");

		Field[] fArray = c.getDeclaredFields(); // 获取类属性的所有字段，

		// 3.遍历所有的字段
		for (Field field : fArray) {
			Map<String, Object> map = getfieldInfo(field, bean);

			if ((Boolean) map.get("filed") || (Boolean) map.get("pkId")) {
				String name = map.get("name").toString();
				Object fieldValue = map.get("fieldValue");
				selectSql.append(name).append(",");
			}

		}
		selectSql.deleteCharAt(selectSql.length() - 1);
		selectSql.append(" from " + getTblaeName(bean)).append(whereBean(bean));
		log.info("生成查询语句:" + selectSql);
		return selectSql.toString();

	}

	/**
	 * 查询语句
	 * 
	 * @param bean
	 * @param alias
	 * @return
	 * @throws Exception
	 */
	public static String selectSql(Object bean, String alias, String ignore) throws Exception {
		Class c = bean.getClass();
		StringBuffer selectSql = new StringBuffer("select ");
		String[] ignores = null;
		if (ignore != null) {
			ignores = ignore.split(",");
		}
		Field[] fArray = c.getDeclaredFields(); // 获取类属性的所有字段，
       
		// 3.遍历所有的字段
		for (Field field : fArray) {
			Map<String, Object> map = getfieldInfo(field, bean);
			 boolean bool=true;
			if ((Boolean) map.get("filed") || (Boolean) map.get("pkId")) {
				String name = map.get("name").toString();
				Object fieldValue = map.get("fieldValue");
				if(ignores!=null)
				for (int i = 0; i < ignores.length; i++) {
					if (ignores[i].toLowerCase().equals(name.toLowerCase()))
						bool=false;
				}
				if(bool)
				selectSql.append(alias + ".").append(name).append(",");
			}

		}
		selectSql.deleteCharAt(selectSql.length() - 1);
		log.info("生成查询语句:" + selectSql);
		return selectSql.toString();

	}

	/**
	 * 删除语句
	 * 
	 * @param bean
	 *            条件
	 * @return
	 * @throws Exception
	 */
	public static String deleteSql(Object bean) throws Exception {
		Class c = bean.getClass();
		StringBuffer deleteSql = new StringBuffer("delete from  ");
		StringBuffer whereStr = new StringBuffer(" where 1=1 ");

		deleteSql.append(getTblaeName(bean));

		Field[] fArray = c.getDeclaredFields(); // 获取类属性的所有字段，

		deleteSql.append(whereBean(bean));
		log.info("生成删除语句:" + deleteSql.toString());

		return deleteSql.toString();
	}

	public static void main(String[] args) {
		BasParam bean = new BasParam();
		bean.setArgName("zzb");
		bean.setId(11);
		BasParam bean1 = new BasParam();
		try {
			ReflectSql.selectSql(new SymUser(), "test","id");
			// reflectSql.updateSql(bean, null, true);
			// reflectSql.deleteSql(bean);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
