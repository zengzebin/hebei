package com.ssxt.web.bean;
// default package

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * BasStinfoB entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "bas_stinfo_b", catalog = "hebei")
public class BasStinfoB implements java.io.Serializable {

	// Fields

	private Integer stionId;
	private String stcd;
	private String stnm;
	private String stlc;
	private String addvcd;
	private Double longitude;
	private Double latitude;
	private String sttp;
	private Integer repairId;
	private Integer deviceId;
	private String serviceType;
	private Integer valid;
	private Date createTime;
	// @JSONField(serialize = false)
	// private Set<BasStinfoSttp> basTaskProcInfos = new
	// HashSet<BasStinfoSttp>(0);

	// Constructors

	/** default constructor */
	public BasStinfoB() {
	}

	/** full constructor */
	public BasStinfoB(String stcd, String stnm, String stlc, String addvcd, Double longitude, Double latitude,
			String sttp, Integer repairId, Integer deviceId, String serviceType, Integer valid, Date createTime) {
		this.stcd = stcd;
		this.stnm = stnm;
		this.stlc = stlc;
		this.addvcd = addvcd;
		this.longitude = longitude;
		this.latitude = latitude;
		this.sttp = sttp;
		this.repairId = repairId;
		this.deviceId = deviceId;
		this.serviceType = serviceType;
		this.valid = valid;
		this.createTime = createTime;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "stionId", unique = true, nullable = false)
	public Integer getStionId() {
		return this.stionId;
	}

	public void setStionId(Integer stionId) {
		this.stionId = stionId;
	}

	@Column(name = "STCD", length = 20)
	public String getStcd() {
		return this.stcd;
	}

	public void setStcd(String stcd) {
		this.stcd = stcd;
	}

	@Column(name = "STNM", length = 30)
	public String getStnm() {
		return this.stnm;
	}

	public void setStnm(String stnm) {
		this.stnm = stnm;
	}

	@Column(name = "STLC", length = 60)
	public String getStlc() {
		return this.stlc;
	}

	public void setStlc(String stlc) {
		this.stlc = stlc;
	}

	@Column(name = "addvcd", length = 6)
	public String getAddvcd() {
		return this.addvcd;
	}

	public void setAddvcd(String addvcd) {
		this.addvcd = addvcd;
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

	@Column(name = "STTP", length = 20)
	public String getSttp() {
		return this.sttp;
	}

	public void setSttp(String sttp) {
		this.sttp = sttp;
	}

	@Column(name = "repairId")
	public Integer getRepairId() {
		return this.repairId;
	}

	public void setRepairId(Integer repairId) {
		this.repairId = repairId;
	}

	@Column(name = "deviceId")
	public Integer getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}

	@Column(name = "serviceType", length = 11)
	public String getServiceType() {
		return this.serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	@Column(name = "valid ")
	public Integer getValid() {
		return this.valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

	@Column(name = "createTime ")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	// @OneToMany(cascade = { CascadeType.REMOVE, CascadeType.REFRESH,
	// CascadeType.PERSIST })
	// @JoinColumn(name = "stcd")
	// public Set<BasStinfoSttp> getBasTaskProcInfos() {
	// return basTaskProcInfos;
	//
	// }
	//
	// public void setBasTaskProcInfos(Set<BasStinfoSttp> basTaskProcInfos) {
	// this.basTaskProcInfos = basTaskProcInfos;
	// }

}