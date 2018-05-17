package com.ssxt.web.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BasWarehouse entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "bas_warehouse", catalog = "hebei")
public class BasWarehouse implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private String address;
	private String addvcd;
	private Date createTime;
	private Integer createUserId;
	private Integer responsibilityId;

	// Constructors

	/** default constructor */
	public BasWarehouse() {
	}

	/** full constructor */
	public BasWarehouse(String name, String address, String addvcd, Date createTime, Integer createUserId,
			Integer responsibilityId) {
		this.name = name;
		this.addvcd = addvcd;
		this.createTime = createTime;
		this.createUserId = createUserId;
		this.responsibilityId = responsibilityId;
		this.address = address;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "name", length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "address")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "addvcd", length = 10)
	public String getAddvcd() {
		return this.addvcd;
	}

	public void setAddvcd(String addvcd) {
		this.addvcd = addvcd;
	}

	@Column(name = "createTime", length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "createUserId")
	public Integer getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	@Column(name = "responsibilityId")
	public Integer getResponsibilityId() {
		return this.responsibilityId;
	}

	public void setResponsibilityId(Integer responsibilityId) {
		this.responsibilityId = responsibilityId;
	}

}