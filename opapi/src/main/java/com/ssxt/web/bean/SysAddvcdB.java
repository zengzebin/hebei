package com.ssxt.web.bean;

// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * SysAddvcdB entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sys_addvcd_b", catalog = "hebei")
public class SysAddvcdB implements java.io.Serializable {

	// Fields

	private String addvcd;
	private String addvnm;
	private String addvfull;
	private Double longitude;
	private Double latitude;
	private Integer zoom;
	private Integer level;
	private String parentId;

	// Constructors

	/** default constructor */
	public SysAddvcdB() {
	}

	/** minimal constructor */
	public SysAddvcdB(String addvcd, String addvnm) {
		this.addvcd = addvcd;
		this.addvnm = addvnm;
	}

	/** full constructor */
	public SysAddvcdB(String addvcd, String addvnm, String addvfull, Double longitude, Double latitude, Integer zoom,
			Integer level, String parentId) {
		this.addvcd = addvcd;
		this.addvnm = addvnm;
		this.addvfull = addvfull;
		this.longitude = longitude;
		this.latitude = latitude;
		this.zoom = zoom;
		this.level = level;
		this.parentId = parentId;
	}

	// Property accessors
	@Id
	@Column(name = "addvcd", unique = true, nullable = false, length = 6)
	public String getAddvcd() {
		return this.addvcd;
	}

	public void setAddvcd(String addvcd) {
		this.addvcd = addvcd;
	}

	@Column(name = "addvnm", nullable = false, length = 60)
	public String getAddvnm() {
		return this.addvnm;
	}

	public void setAddvnm(String addvnm) {
		this.addvnm = addvnm;
	}

	@Column(name = "addvfull", length = 1000)
	public String getAddvfull() {
		return this.addvfull;
	}

	public void setAddvfull(String addvfull) {
		this.addvfull = addvfull;
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

	@Column(name = "zoom")
	public Integer getZoom() {
		return this.zoom;
	}

	public void setZoom(Integer zoom) {
		this.zoom = zoom;
	}

	@Column(name = "level")
	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@Column(name = "parentId", length = 6)
	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

}