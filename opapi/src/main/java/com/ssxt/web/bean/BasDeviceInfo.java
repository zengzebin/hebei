package com.ssxt.web.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * BasDeviceInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "bas_device_info", catalog = "hebei", uniqueConstraints = @UniqueConstraint(columnNames = "address") )
public class BasDeviceInfo implements java.io.Serializable {

	// Fields

	private Integer id;
	private String address;
	private Integer delFlag;
	private String deviceName;

	private String deviceModel;
	private String ver;
	private Integer status;
	private String idenitfication;
	private String addvcd;
	private String ownerId;
	private String deviceInfo;
	private String factoryName;
	private String factoryTel;
	private String supplyerName;
	private String supplyerTel;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")   
    private Date installTime;
	private String mainNo;
	private String mainName;
	private String barcode;
	private String remark;
	private Double longitude;
	private Double latitude;
	private Integer since;
	private String stcd;

	// Constructors

	/** default constructor */
	public BasDeviceInfo() {
	}

	/** minimal constructor */
	public BasDeviceInfo(Integer id, String address) {
		this.id = id;
		this.address = address;

	}

	/** full constructor */
	public BasDeviceInfo(String address, Integer delFlag, String deviceName, String deviceModel, String ver,
			Integer status, String idenitfication, String addvcd, String ownerId, String deviceInfo, String factoryName,
			String factoryTel, String supplyerName, String supplyerTel, Date installTime, String mainNo,
			String mainName, String barcode, String remark, Double longitude, Double latitude, Integer since,
			String stcd) {
		this.address = address;
		this.delFlag = delFlag;
		this.deviceName = deviceName;
		this.deviceModel = deviceModel;
		this.ver = ver;
		this.status = status;
		this.idenitfication = idenitfication;
		this.addvcd = addvcd;
		this.ownerId = ownerId;
		this.deviceInfo = deviceInfo;
		this.factoryName = factoryName;
		this.factoryTel = factoryTel;
		this.supplyerName = supplyerName;
		this.supplyerTel = supplyerTel;
		this.installTime = installTime;
		this.mainNo = mainNo;
		this.mainName = mainName;
		this.barcode = barcode;
		this.remark = remark;
		this.longitude = longitude;
		this.latitude = latitude;
		this.since = since;
		this.stcd = stcd;

	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "address", unique = true, nullable = false, length = 10)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "delFlag")
	public Integer getDelFlag() {
		return this.delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

	@Column(name = "deviceName", length = 100)
	public String getDeviceName() {
		return this.deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	@Column(name = "deviceModel", length = 50)
	public String getDeviceModel() {
		return this.deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	@Column(name = "ver", length = 50)
	public String getVer() {
		return this.ver;
	}

	public void setVer(String ver) {
		this.ver = ver;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "idenitfication", length = 30)
	public String getIdenitfication() {
		return this.idenitfication;
	}

	public void setIdenitfication(String idenitfication) {
		this.idenitfication = idenitfication;
	}

	@Column(name = "addvcd", length = 30)
	public String getAddvcd() {
		return this.addvcd;
	}

	public void setAddvcd(String addvcd) {
		this.addvcd = addvcd;
	}

	@Column(name = "ownerId", length = 30)
	public String getOwnerId() {
		return this.ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	@Column(name = "deviceInfo")
	public String getDeviceInfo() {
		return this.deviceInfo;
	}

	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

	@Column(name = "factoryName", length = 60)
	public String getFactoryName() {
		return this.factoryName;
	}

	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}

	@Column(name = "factoryTel", length = 15)
	public String getFactoryTel() {
		return this.factoryTel;
	}

	public void setFactoryTel(String factoryTel) {
		this.factoryTel = factoryTel;
	}

	@Column(name = "supplyerName", length = 60)
	public String getSupplyerName() {
		return this.supplyerName;
	}

	public void setSupplyerName(String supplyerName) {
		this.supplyerName = supplyerName;
	}

	@Column(name = "supplyerTel", length = 15)
	public String getSupplyerTel() {
		return this.supplyerTel;
	}

	public void setSupplyerTel(String supplyerTel) {
		this.supplyerTel = supplyerTel;
	}

	@Column(name = "installTime", length = 19)
	public Date getInstallTime() {
		return this.installTime;
	}

	public void setInstallTime(Date installTime) {
		this.installTime = installTime;
	}

	@Column(name = "mainNo", length = 30)
	public String getMainNo() {
		return this.mainNo;
	}

	public void setMainNo(String mainNo) {
		this.mainNo = mainNo;
	}

	@Column(name = "mainName", length = 100)
	public String getMainName() {
		return this.mainName;
	}

	public void setMainName(String mainName) {
		this.mainName = mainName;
	}

	@Column(name = "barcode", length = 40)
	public String getBarcode() {
		return this.barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	@Column(name = "remark")
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	@Column(name = "since")
	public Integer getSince() {
		return this.since;
	}

	public void setSince(Integer since) {
		this.since = since;
	}

	@Column(name = "stcd", length = 11)
	public String getStcd() {
		return this.stcd;
	}

	public void setStcd(String stcd) {
		this.stcd = stcd;
	}

}