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

/**
 * BasQuit entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "bas_quit", catalog = "hebei")
public class BasQuit implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer userId;
	private String reason;
	private String content;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date quitTime;
	private Date createTime;
	private Date modifyTime;
	private Integer modifyUserId;
	private Integer state;

	// Constructors

	/** default constructor */
	public BasQuit() {
	}

	/** full constructor */
	public BasQuit(Integer userId, String reason, String content, Date quitTime, Date createTime, Date modifyTime,
			Integer modifyUserId, Integer state) {
		this.userId = userId;
		this.reason = reason;
		this.content = content;
		this.quitTime = quitTime;
		this.createTime = createTime;
		this.modifyTime = modifyTime;
		this.modifyUserId = modifyUserId;
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

	@Column(name = "userId")
	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "reason", length = 50)
	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Column(name = "content", length = 50)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "quitTime", length = 19)
	public Date getQuitTime() {
		return this.quitTime;
	}

	public void setQuitTime(Date quitTime) {
		this.quitTime = quitTime;
	}

	@Column(name = "createTime", length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "modifyTime", length = 19)
	public Date getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	@Column(name = "modifyUserId")
	public Integer getModifyUserId() {
		return this.modifyUserId;
	}

	public void setModifyUserId(Integer modifyUserId) {
		this.modifyUserId = modifyUserId;
	}

	@Column(name = "state")
	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

}