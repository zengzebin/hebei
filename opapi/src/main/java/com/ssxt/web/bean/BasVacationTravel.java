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

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

/**
 * BasVacationBusinesstrip entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "bas_vacation_travel", catalog = "hebei")
public class BasVacationTravel implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer type;
	private Integer userId;
	private String reason;

	private String content;
	@DateTimeFormat(iso=ISO.DATE)
	private Date startTime;
	@DateTimeFormat(iso=ISO.DATE)
	private Date endTime;
	private Integer state;
	private Date createTime;
	private Date modify;
	private Integer modifyUserId;

	// Constructors

	/** default constructor */
	public BasVacationTravel() {
	}

	/** full constructor */
	public BasVacationTravel(Integer type, Integer userId, String reason, String content, Date startTime, Date endTime,
			Integer state, Date createTime, Date modify, Integer modifyUserId) {
		this.type = type;
		this.userId = userId;
		this.content = content;
		this.reason = reason;
		this.startTime = startTime;
		this.endTime = endTime;
		this.state = state;
		this.createTime = createTime;
		this.modify = modify;
		this.modifyUserId = modifyUserId;
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

	@Column(name = "type")
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "userId")
	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "reason", length = 200)

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Column(name = "content", length = 200)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "startTime", length = 19)
	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Column(name = "endTime", length = 19)
	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Column(name = "state")
	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Column(name = "createTime", length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "modify", length = 19)
	public Date getModify() {
		return this.modify;
	}

	public void setModify(Date modify) {
		this.modify = modify;
	}

	@Column(name = "modifyUserId")
	public Integer getModifyUserId() {
		return this.modifyUserId;
	}

	public void setModifyUserId(Integer modifyUserId) {
		this.modifyUserId = modifyUserId;
	}

}