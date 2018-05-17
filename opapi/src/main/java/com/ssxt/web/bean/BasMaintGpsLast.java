package com.ssxt.web.bean;

import java.util.Date;

// default package

 
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BasMaintGpsLast entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "bas_maint_gps_last", catalog = "hebei")
public class BasMaintGpsLast implements java.io.Serializable {

	// Fields

	private Integer maintUserId;
	private Double longitude;
	private Double latitude;
	private Date createTime;

	// Constructors

	/** default constructor */
	public BasMaintGpsLast() {
	}

	/** minimal constructor */
	public BasMaintGpsLast(Integer maintUserId) {
		this.maintUserId = maintUserId;
	}

	/** full constructor */
	public BasMaintGpsLast(Integer maintUserId, Double longitude,
			Double latitude, Date createTime) {
		this.maintUserId = maintUserId;
		this.longitude = longitude;
		this.latitude = latitude;
		this.createTime = createTime;
	}

	// Property accessors
	@Id
	@Column(name = "maintUserId", unique = true, nullable = false)
	public Integer getMaintUserId() {
		return this.maintUserId;
	}

	public void setMaintUserId(Integer maintUserId) {
		this.maintUserId = maintUserId;
	}

	@Column(name = "longitude", precision = 10, scale = 7)
	public Double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	@Column(name = "latitude", precision = 9, scale = 7)
	public Double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	@Column(name = "createTime", length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}