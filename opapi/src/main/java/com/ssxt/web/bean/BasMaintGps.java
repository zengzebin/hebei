package com.ssxt.web.bean;

// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BasMaintGps entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "bas_maint_gps", catalog = "hebei")
public class BasMaintGps implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer maintUserId;
	private Double longitude;
	private Double latitude;
	private Date createTime;

	// Constructors

	/** default constructor */
	public BasMaintGps() {
	}

	/** full constructor */
	public BasMaintGps(Integer maintUserId, Double longitude, Double latitude, Date createTime) {
		this.maintUserId = maintUserId;
		this.longitude = longitude;
		this.latitude = latitude;
		this.createTime = createTime;
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

	@Column(name = "maintUserId")
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