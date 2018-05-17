package com.ssxt.web.bean;

import java.util.Date;

// default package

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * BasRepairSurplus entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "bas_repair_surplus", catalog = "hebei")
public class BasRepairSurplus implements java.io.Serializable {

	// Fields

	private BasRepairSurplusId id;
	private Integer num;
	private Date modifyTime;

	// Constructors

	/** default constructor */
	public BasRepairSurplus() {
	}

	/** minimal constructor */
	public BasRepairSurplus(BasRepairSurplusId id) {
		this.id = id;
	}

	/** full constructor */
	public BasRepairSurplus(BasRepairSurplusId id, Integer num,
			Date modifyTime) {
		this.id = id;
		this.num = num;
		this.modifyTime = modifyTime;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "userId", column = @Column(name = "userId", nullable = false)),
			@AttributeOverride(name = "deviceTypeId", column = @Column(name = "deviceTypeId", nullable = false)),
			@AttributeOverride(name = "state", column = @Column(name = "state", nullable = false)),
			@AttributeOverride(name = "model", column = @Column(name = "model", nullable = false, length = 20)) })
	public BasRepairSurplusId getId() {
		return this.id;
	}

	public void setId(BasRepairSurplusId id) {
		this.id = id;
	}

	@Column(name = "num")
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