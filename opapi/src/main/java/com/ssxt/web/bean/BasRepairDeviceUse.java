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
 * BasRepaorDeviceUse entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "bas_repair_device_use", catalog = "hebei")
public class BasRepairDeviceUse implements java.io.Serializable {
	// Fields

	private Integer id;
	private Integer stionId;
	private String taskNo;
	private Integer userId;
	private Integer deviceTypeId;
	private String model;
	private Integer state;
	private Integer num;
	private Date modifyTime;

	// Constructors

	/** default constructor */
	public BasRepairDeviceUse() {
	}

	/** minimal constructor */
	public BasRepairDeviceUse(Integer userId, Integer deviceTypeId, String model, Integer state, Integer num) {
		this.userId = userId;
		this.deviceTypeId = deviceTypeId;
		this.model = model;
		this.state = state;
		this.num = num;
	}

	/** full constructor */
	public BasRepairDeviceUse(Integer stionId, String taskNo, Integer userId, Integer deviceTypeId, String model,
			Integer state, Integer num, Date modifyTime) {
		this.stionId = stionId;
		this.taskNo = taskNo;
		this.userId = userId;
		this.deviceTypeId = deviceTypeId;
		this.model = model;
		this.state = state;
		this.num = num;
		this.modifyTime = modifyTime;
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

	@Column(name = "stionId", length = 10)
	public Integer getStionId() {
		return this.stionId;
	}

	public void setStionId(Integer stionId) {
		this.stionId = stionId;
	}

	@Column(name = "taskNo", length = 10)
	public String getTaskNo() {
		return this.taskNo;
	}

	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}

	@Column(name = "userId", nullable = false)
	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "deviceTypeId", nullable = false)
	public Integer getDeviceTypeId() {
		return this.deviceTypeId;
	}

	public void setDeviceTypeId(Integer deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}

	@Column(name = "model", nullable = false, length = 20)
	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	@Column(name = "state", nullable = false)
	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Column(name = "num", nullable = false)
	public Integer getNum() {
		return this.num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	@Column(name = "modifyTime", length = 19)
	public Date getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

}
