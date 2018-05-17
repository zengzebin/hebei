package com.ssxt.common.dao;

import java.util.ArrayList;
import java.util.List;

public class CreateSql {
	private List<Condition> list = null;

	public List<Condition> getList() {
		return list;
	}

	public void setList(List<Condition> list) {
		this.list = list;
	}

	public CreateSql() {
		super();
		list = new ArrayList<Condition>();
	}

	/**
	 * like
	 * 
	 * @param name
	 * @param value
	 * @return
	 */
	public CreateSql addLike(String name, Object value) {
		list.add(new Condition(name, "like", "%" + value + "%"));
		return this;
	}

	public CreateSql add(String name, Object value) {
		list.add(new Condition(name, "=", value));
		return this;
	}

	public CreateSql in(String name, Object value) {
		list.add(new Condition(name, "in", "(" + value + ")"));
		return this;
	}

	public CreateSql add(String type, String name, String symbol, Object value) {
		list.add(new Condition(type, name, symbol, value));
		return this;
	}

	public CreateSql addText(String type, String text) {
		list.add(new Condition(type, text, null, null));
		return this;
	}

	public CreateSql order(String name, String sort) {
		list.add(new Condition("order by " + name, sort, null, null));
		return this;
	}

	public static void analysis(CreateSql create) {
		StringBuffer sql = new StringBuffer("where 1=1 ");
		List<Object> vlaues = new ArrayList<Object>();
		List<String> names = new ArrayList<String>();
		List<String> symbols = new ArrayList<String>();
		List<String> types = new ArrayList<String>();
		for (int i = 0; i < create.getList().size(); i++) {
			Condition c = create.getList().get(i);
			vlaues.add(c.getValue());
			names.add(c.getName());
			symbols.add(c.getSymbol());
			types.add(c.getType());
		}

		for (int i = 0; i < vlaues.size(); i++) {
			if (vlaues.get(i) instanceof List) {

			} else {
				sql.append(" " + types.get(i) == null ? "" : types.get(i))
						// .append("ddddddddddd"+names.get(i) == null ? "" :
						// names.get(i))
						.append(" --" + names.get(i)).append(" b b" + symbols.get(i) == null ? "" : symbols.get(i))
						.append(" ? ");

			}
		}

		System.out.println(sql.toString());
	}

	public static void main(String[] args) {
		CreateSql create = new CreateSql();
		create.add("id", 1);
		create.add("name", "组 织部");
		create.addLike("addvcd", "1008 6");
		create.add("and", "bin", "like", "2 2");
		// create.addText("and", "id='5'");
		// create.order("aa", "desc");
		analysis(create);

	}

}
