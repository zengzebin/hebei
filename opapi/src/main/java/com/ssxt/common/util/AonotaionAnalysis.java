package com.ssxt.common.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.persistence.Table;

import com.ssxt.web.bean.BasTaskInfo;
import com.ssxt.web.bean.SymMenu;

import javax.persistence.Column;

public class AonotaionAnalysis {
	private String table;
	private String id;
	private String field;

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public static AonotaionAnalysis createSql(Class c, String abandon, String alias) throws Exception {
		String[] abandons = null;
		if (abandon != null) {
			abandons = abandon.split(",");
		}

		AonotaionAnalysis bean = new AonotaionAnalysis();
		Field[] fields = c.getDeclaredFields();
		StringBuffer str = new StringBuffer();
		if (c.isAnnotationPresent(Table.class)) {
			Table table = (javax.persistence.Table) c.getAnnotation(Table.class);
			// System.out.println("表名称：" + table.name());
			bean.setTable(table.name() + " " + alias);
		}
		boolean boo = true;

		for (Field field : fields) {

			boo = true;
			PropertyDescriptor pd = new PropertyDescriptor(field.getName(), c);

			Method getMehod = pd.getReadMethod();
			if (getMehod.isAnnotationPresent(Column.class)) {
				Column column = (Column) getMehod.getAnnotation(Column.class);
				if (abandons != null)
					for (int i = 0; i < abandons.length; i++) {
						if (abandons[i].equals(column.name())) {
							boo = false;
							break;
						}

					}
				if (boo)
					str.append("," + alias + "." + column.name());
			}

		}
		bean.setField(str.toString());

		return bean;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			AonotaionAnalysis bean = AonotaionAnalysis.createSql(BasTaskInfo.class, "taskNo", "alias");
			System.out.println(bean.field);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
