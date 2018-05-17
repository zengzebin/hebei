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
 * BasWarehouseDevice entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "bas_warehouse_device", catalog = "hebei")
public class BasWarehouseDevice implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer wareHouseId;
	private Integer deviceTypeId;
	private String model;
	private Integer num;
	private Integer state;
	private Integer addUserId;
	private Date addTime;
	private String remarks;

	// Constructors

	/** default constructor */
	public BasWarehouseDevice() {
	}

	/** full constructor */
	public BasWarehouseDevice(Integer wareHouseId, Integer deviceTypeId, String model, Integer num, Integer state,
			Integer addUserId, Date addTime, String remarks) {
		this.wareHouseId = wareHouseId;
		this.deviceTypeId = deviceTypeId;
		this.model = model;
		this.num = num;
		this.state = state;
		this.addUserId = addUserId;
		this.addTime = addTime;
		this.remarks = remarks;
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

	@Column(name = "wareHouseId")
	public Integer getWareHouseId() {
		return this.wareHouseId;
	}

	public void setWareHouseId(Integer wareHouseId) {
		this.wareHouseId = wareHouseId;
	}

	@Column(name = "deviceTypeId")
	public Integer getDeviceTypeId() {
		return this.deviceTypeId;
	}

	public void setDeviceTypeId(Integer deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}

	@Column(name = "model", length = 20)
	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	@Column(name = "num")
	public Integer getNum() {
		return this.num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	@Column(name = "state")
	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Column(name = "addUserId")
	public Integer getAddUserId() {
		return this.addUserId;
	}

	public void setAddUserId(Integer addUserId) {
		this.addUserId = addUserId;
	}

	@Column(name = "addTime", length = 19)
	public Date getAddTime() {
		return this.addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	@Column(name = "remarks", length = 50)
	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}