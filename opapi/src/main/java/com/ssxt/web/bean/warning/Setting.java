package com.ssxt.web.bean.warning;

// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Setting entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "bas_setting",  catalog = "hebei")
public class Setting implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private String value;
	private String type;
	private String remark;

	// Constructors

	/** default constructor */
	public Setting() {
	}

	/** minimal constructor */
	public Setting(Integer id, String name, String value, String type) {
		this.id = id;
		this.name = name;
		this.value = value;
		this.type = type;
	}

	/** full constructor */
	public Setting(Integer id, String name, String value, String type, String remark) {
		this.id = id;
		this.name = name;
		this.value = value;
		this.type = type;
		this.remark = remark;
	}

	// Property accessors
	@Id
	@Column(name = "Id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "Name", nullable = false, length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "Value", nullable = false, length = 50)
	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Column(name = "Type", nullable = false, length = 50)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "Remark", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}