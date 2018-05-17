package com.ssxt.common.dao;

public class Condition {
	private String type;
	private String name;
	private String symbol;
	private Object value;
	public static final String DEFAULT_TYPE = "and";

	public Condition(String name, String symbol, Object value) {
		super();
		this.type = Condition.DEFAULT_TYPE;
		this.name = name;
		this.symbol = symbol;
		this.value = value;

	}

	public Condition(String type, String name, String symbol, Object value) {
		this.type = type;
		this.name = name;
		this.symbol = symbol;
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

}
