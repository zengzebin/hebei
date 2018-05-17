package com.ssxt.web.bean;

 

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BasParam entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "bas_param", catalog = "hebei")
public class BasParam implements java.io.Serializable {

	// Fields

	private Integer id;
	private String paramType;
	private String argName;
	private String argValue;
	private Integer sort;
	private String remark;

	// Constructors

	/** default constructor */
	public BasParam() {
	}

	/** minimal constructor */
	public BasParam(Integer id, String paramType, String argName, Integer sort) {
		this.id = id;
		this.paramType = paramType;
		this.argName = argName;
		this.sort = sort;
	}

	/** full constructor */
	public BasParam(Integer id, String paramType, String argName,
			String argValue, Integer sort, String remark) {
		this.id = id;
		this.paramType = paramType;
		this.argName = argName;
		this.argValue = argValue;
		this.sort = sort;
		this.remark = remark;
	}

	// Property accessors
	@Id
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "paramType", nullable = false, length = 100)
	public String getParamType() {
		return this.paramType;
	}

	public void setParamType(String paramType) {
		this.paramType = paramType;
	}

	@Column(name = "argName", nullable = false, length = 100)
	public String getArgName() {
		return this.argName;
	}

	public void setArgName(String argName) {
		this.argName = argName;
	}

	@Column(name = "argValue")
	public String getArgValue() {
		return this.argValue;
	}

	public void setArgValue(String argValue) {
		this.argValue = argValue;
	}

	@Column(name = "sort", nullable = false)
	public Integer getSort() {
		return this.sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@Column(name = "remark")
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}