package com.ssxt.web.bean;

import java.util.Date;

// default package

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * BasDeviceOrder entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "bas_device_order", catalog = "hebei")
public class BasDeviceOrder implements java.io.Serializable {

	// Fields

	private Integer orderId;
	private Integer applyUserId;
	private Date applyTime;
	private String content;
	private Integer examine;
	private Integer reviewUserId;
	private String reflectContent;
	private Date modifyTime;

	private Set<BasDeviceOrderDetails> BasDeviceOrderDetails = new HashSet<BasDeviceOrderDetails>(0);

	// Constructors
	@OneToMany(cascade = { CascadeType.REMOVE, CascadeType.REFRESH, CascadeType.PERSIST })
	@JoinColumn(name = "orderId")
	public Set<BasDeviceOrderDetails> getBasDeviceOrderDetails() {
		return BasDeviceOrderDetails;
	}

	public void setBasDeviceOrderDetails(Set<BasDeviceOrderDetails> basDeviceOrderDetails) {
		BasDeviceOrderDetails = basDeviceOrderDetails;
	}

	/** default constructor */
	public BasDeviceOrder() {
	}

	/** full constructor */
	public BasDeviceOrder(Integer applyUserId, Date applyTime, String content, Integer examine, Integer reviewUserId,
			String reflectContent, Date modifyTime) {
		this.applyUserId = applyUserId;
		this.applyTime = applyTime;
		this.content = content;
		this.examine = examine;
		this.reviewUserId = reviewUserId;
		this.reflectContent = reflectContent;
		this.modifyTime = modifyTime;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "orderId", unique = true, nullable = false)
	public Integer getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	@Column(name = "applyUserId")
	public Integer getApplyUserId() {
		return this.applyUserId;
	}

	public void setApplyUserId(Integer applyUserId) {
		this.applyUserId = applyUserId;
	}

	@Column(name = "applyTime", length = 19)
	public Date getApplyTime() {
		return this.applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	@Column(name = "content", length = 200)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "examine")
	public Integer getExamine() {
		return this.examine;
	}

	public void setExamine(Integer examine) {
		this.examine = examine;
	}

	@Column(name = "reviewUserId")
	public Integer getReviewUserId() {
		return this.reviewUserId;
	}

	public void setReviewUserId(Integer reviewUserId) {
		this.reviewUserId = reviewUserId;
	}

	@Column(name = "reflectContent", length = 200)
	public String getReflectContent() {
		return this.reflectContent;
	}

	public void setReflectContent(String reflectContent) {
		this.reflectContent = reflectContent;
	}

	@Column(name = "modifyTime", length = 19)
	public Date getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

}