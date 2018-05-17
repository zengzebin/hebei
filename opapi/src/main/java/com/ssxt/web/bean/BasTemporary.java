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
 * BasTemporary entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "bas_temporary", catalog = "hebei")
public class BasTemporary implements java.io.Serializable {

	// Fields

	private Integer id;
	private String reason;
	private String startTime;
	private String endTime;
	private Integer userId;
	private Integer temporaryUserId;
	private Integer createUserId;

	// Constructors

	/** default constructor */
	public BasTemporary() {
	}

	/** full constructor */
	public BasTemporary(String reason, String startTime, String endTime, Integer userId, Integer temporaryUserId,
			Integer createUserId) {
		this.reason = reason;
		this.startTime = startTime;
		this.endTime = endTime;
		this.userId = userId;
		this.temporaryUserId = temporaryUserId;
		this.createUserId = createUserId;
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

	@Column(name = "reason", length = 50)
	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Column(name = "startTime", length = 20)
	public String getStartTime() {
		return this.startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	@Column(name = "endTime", length = 20)
	public String getEndTime() {
		return this.endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	@Column(name = "userId")
	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "temporaryUserId")
	public Integer getTemporaryUserId() {
		return this.temporaryUserId;
	}

	public void setTemporaryUserId(Integer temporaryUserId) {
		this.temporaryUserId = temporaryUserId;
	}

	@Column(name = "createUserId")
	public Integer getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

}