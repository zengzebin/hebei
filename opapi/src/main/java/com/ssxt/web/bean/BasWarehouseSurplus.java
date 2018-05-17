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
 * BasWarehouseSurplus entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "bas_warehouse_surplus", catalog = "hebei")
public class BasWarehouseSurplus implements java.io.Serializable {

	// Fields

	private BasWarehouseSurplusId id;
	private Integer num;
	private Integer modifyUserId;
	private Date modifyTime;

	// Constructors

	/** default constructor */
	public BasWarehouseSurplus() {
	}

	/** minimal constructor */
	public BasWarehouseSurplus(BasWarehouseSurplusId id) {
		this.id = id;
	}

	/** full constructor */
	public BasWarehouseSurplus(BasWarehouseSurplusId id, Integer num, Integer modifyUserId, Date modifyTime) {
		this.id = id;

		this.num = num;
		this.modifyUserId = modifyUserId;
		this.modifyTime = modifyTime;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "wareHouseId", column = @Column(name = "wareHouseId", nullable = false) ),
			@AttributeOverride(name = "model", column = @Column(name = "model", nullable = false, length = 20) ) })
	public BasWarehouseSurplusId getId() {
		return this.id;
	}

	public void setId(BasWarehouseSurplusId id) {
		this.id = id;
	}
 
	@Column(name = "num")
	public Integer getNum() {
		return this.num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	@Column(name = "modifyUserId")
	public Integer getModifyUserId() {
		return this.modifyUserId;
	}

	public void setModifyUserId(Integer modifyUserId) {
		this.modifyUserId = modifyUserId;
	}

	@Column(name = "modifyTime", length = 19)
	public Date getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

}