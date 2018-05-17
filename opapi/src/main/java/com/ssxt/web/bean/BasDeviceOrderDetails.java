package com.ssxt.web.bean;

// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BasDeviceOrderDetails entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "bas_device_order_details", catalog = "hebei")
public class BasDeviceOrderDetails implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer orderId;
	private Integer deviceTypeId;
	private Integer num;
	private String model;

	// Constructors

	/** default constructor */
	public BasDeviceOrderDetails() {
	}

	/** full constructor */
	public BasDeviceOrderDetails(Integer orderId, Integer deviceTypeId, Integer num, String model) {
		this.orderId = orderId;
		this.deviceTypeId = deviceTypeId;
		this.num = num;
		this.model = model;
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

	@Column(name = "deviceTypeId")
	public Integer getDeviceTypeId() {
		return this.deviceTypeId;
	}

	public void setDeviceTypeId(Integer deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}

	@Column(name = "num")
	public Integer getNum() {
		return this.num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	@Column(name = "model", length = 20)
	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

}