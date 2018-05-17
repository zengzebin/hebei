package com.ssxt.web.bean;

// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BasWorkDate entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "bas_work_date", catalog = "hebei")
public class BasWorkDate implements java.io.Serializable {

	// Fields

	private String date;
	private String name;
	private Integer workmk;
	private String remark;

	// Constructors

	/** default constructor */
	public BasWorkDate() {
	}

	/** minimal constructor */
	public BasWorkDate(String date) {
		this.date = date;
	}

	/** full constructor */
	public BasWorkDate(String date, String name, Integer workmk, String remark) {
		this.date = date;
		this.name = name;
		this.workmk = workmk;
		this.remark = remark;
	}

	// Property accessors
	@Id
	@Column(name = "date", unique = true, nullable = false, length = 10)
	public String getDate() {
		return this.date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Column(name = "name", length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "workmk")
	public Integer getWorkmk() {
		return this.workmk;
	}

	public void setWorkmk(Integer workmk) {
		this.workmk = workmk;
	}

	@Column(name = "remark", length = 50)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}