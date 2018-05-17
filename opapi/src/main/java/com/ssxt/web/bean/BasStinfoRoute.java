package com.ssxt.web.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * BasStinfoRoute entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "bas_stinfo_route", catalog = "hebei")
public class BasStinfoRoute implements java.io.Serializable {

	// Fields

	private Integer id;
	private String stcd;
	private Double longitude;
	private Double latitude;
	private Integer userId;
	private String userName;
	private Date createTime;

	private Set<BasStinfoRouteImage> basStinfoRouteImages = new HashSet<BasStinfoRouteImage>(0);

	// Constructors

	/** default constructor */
	public BasStinfoRoute() {
	}

	/** full constructor */
	public BasStinfoRoute(String stcd, Double longitude, Double latitude, Integer userId, String userName,
			Date createTime) {
		this.stcd = stcd;
		this.longitude = longitude;
		this.latitude = latitude;
		this.userId = userId;
		this.userName = userName;
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

	@Column(name = "stcd", length = 10)
	public String getStcd() {
		return this.stcd;
	}

	public void setStcd(String stcd) {
		this.stcd = stcd;
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

	@Column(name = "userId")
	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "userName", length = 10)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "createTime", length = 10)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@OneToMany(cascade = { CascadeType.REMOVE, CascadeType.REFRESH, CascadeType.PERSIST })
	@JoinColumn(name = "procId")
	public Set<BasStinfoRouteImage> getBasStinfoRouteImages() {
		return basStinfoRouteImages;
	}

	public void setBasStinfoRouteImages(Set<BasStinfoRouteImage> basStinfoRouteImages) {
		this.basStinfoRouteImages = basStinfoRouteImages;
	}

}