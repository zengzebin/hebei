package com.ssxt.web.bean;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BasOwnerUnits entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "bas_owner_units", catalog = "hebei")
public class BasOwnerUnits implements java.io.Serializable {

	// Fields

	private String ownerNo;
	private Integer delFlag;
	private Integer lockFlag;
	private String ownerName;
	private String content;
	private String address;
	private String contact;
	private String tel;
	private Long createId;
	private String createName;
	private Date createTime;
	private String remark;

	// Constructors

	/** default constructor */
	public BasOwnerUnits() {
	}

	/** minimal constructor */
	public BasOwnerUnits(String ownerNo, Integer delFlag, Integer lockFlag, String ownerName, Long createId,
			Timestamp createTime) {
		this.ownerNo = ownerNo;
		this.delFlag = delFlag;
		this.lockFlag = lockFlag;
		this.ownerName = ownerName;
		this.createId = createId;
		this.createTime = createTime;
	}

	/** full constructor */
	public BasOwnerUnits(String ownerNo, Integer delFlag, Integer lockFlag, String ownerName, String content,
			String address, String contact, String tel, Long createId, String createName, Date createTime,
			String remark) {
		this.ownerNo = ownerNo;
		this.delFlag = delFlag;
		this.lockFlag = lockFlag;
		this.ownerName = ownerName;
		this.content = content;
		this.address = address;
		this.contact = contact;
		this.tel = tel;
		this.createId = createId;
		this.createName = createName;
		this.createTime = createTime;
		this.remark = remark;
	}

	// Property accessors
	@Id
	@Column(name = "ownerNo", unique = true, nullable = false, length = 30)
	public String getOwnerNo() {
		return this.ownerNo;
	}

	public void setOwnerNo(String ownerNo) {
		this.ownerNo = ownerNo;
	}

	@Column(name = "delFlag", nullable = false)
	public Integer getDelFlag() {
		return this.delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

	@Column(name = "lockFlag", nullable = false)
	public Integer getLockFlag() {
		return this.lockFlag;
	}

	public void setLockFlag(Integer lockFlag) {
		this.lockFlag = lockFlag;
	}

	@Column(name = "ownerName", nullable = false, length = 100)
	public String getOwnerName() {
		return this.ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	@Column(name = "content", length = 1000)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "address")
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "contact", length = 50)
	public String getContact() {
		return this.contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	@Column(name = "tel", length = 15)
	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Column(name = "createId", nullable = false)
	public Long getCreateId() {
		return this.createId;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	@Column(name = "createName", length = 100)
	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@Column(name = "createTime", nullable = false, length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "remark")
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}