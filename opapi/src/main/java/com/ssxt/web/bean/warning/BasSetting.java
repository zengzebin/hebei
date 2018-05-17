package com.ssxt.web.bean.warning;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BasSetting entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "bas_setting", catalog = "hebei")
public class BasSetting implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private String remark;
	private String type;
	private String value;

	// Constructors

	/** default constructor */
	public BasSetting() {
	}

	/** minimal constructor */
	public BasSetting(String name, String type, String value) {
		this.name = name;
		this.type = type;
		this.value = value;
	}

	/** full constructor */
	public BasSetting(String name, String remark, String type, String value) {
		this.name = name;
		this.remark = remark;
		this.type = type;
		this.value = value;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
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

	@Column(name = "Remark", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "Type", nullable = false, length = 50)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "Value", nullable = false, length = 50)
	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}