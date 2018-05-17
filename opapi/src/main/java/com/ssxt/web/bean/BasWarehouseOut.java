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
 * BasWarehouseOut entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "bas_warehouse_out", catalog = "hebei")
public class BasWarehouseOut implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer orderId;
	private Integer wareHouseId;
	private Integer deviceTypeId;
	private String model;
	private Integer num;
	private Integer createUserId;
	private Date createTime;
	private Integer state;

	// Constructors

	/** default constructor */
	public BasWarehouseOut() {
	}

	/** full constructor */
	public BasWarehouseOut(Integer orderId, Integer wareHouseId, Integer deviceTypeId, String model, Integer num,
			Integer createUserId, Date createTime, Integer state) {
		this.orderId = orderId;
		this.wareHouseId = wareHouseId;
		this.deviceTypeId = deviceTypeId;
		this.model = model;
		this.num = num;
		this.createUserId = createUserId;
		this.createTime = createTime;
		this.state = state;
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

	@Column(name = "orderId")
	public Integer getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
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

	@Column(name = "createUserId")
	public Integer getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	@Column(name = "createTime", length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "state")
	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

}