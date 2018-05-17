package com.ssxt.web.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BasTaskPlanDevice entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "bas_task_plan_device", catalog = "hebei")
public class BasTaskPlanDevice implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer parentId;
	private Integer deviceId;
	private Integer state;
	private Date modifyTime;
	private String content;
	
	
	private String name;
	 
	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	// Constructors

	/** default constructor */
	public BasTaskPlanDevice() {
	}

	/** minimal constructor */
	public BasTaskPlanDevice(Integer parentId, Integer deviceId) {
		this.parentId = parentId;
		this.deviceId = deviceId;
	}

	/** full constructor */
	public BasTaskPlanDevice(Integer parentId, Integer deviceId, Integer state, Date modifyTime, String content) {
		this.parentId = parentId;
		this.deviceId = deviceId;
		this.state = state;
		this.modifyTime = modifyTime;
		this.content = content;
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

	@Column(name = "parentId", nullable = false)
	public Integer getParentId() {
		return this.parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	@Column(name = "deviceId", nullable = false)
	public Integer getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}

	@Column(name = "state")
	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Column(name = "modifyTime", length = 19)
	public Date getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	@Column(name = "content", length = 100)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}