package com.ssxt.web.bean;

// default package

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * BasWarehouseSurplusId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class BasWarehouseSurplusId implements java.io.Serializable {

	// Fields

	private Integer wareHouseId;
	private String model;
	private Integer state;
	private Integer deviceTypeId;

	// Constructors

	/** default constructor */
	public BasWarehouseSurplusId() {
	}

	/** full constructor */
	public BasWarehouseSurplusId(Integer wareHouseId, String model,
			Integer state, Integer deviceTypeId) {
		this.wareHouseId = wareHouseId;
		this.model = model;
		this.state = state;
		this.deviceTypeId = deviceTypeId;
	}

	// Property accessors

	@Column(name = "wareHouseId", nullable = false)
	public Integer getWareHouseId() {
		return this.wareHouseId;
	}

	public void setWareHouseId(Integer wareHouseId) {
		this.wareHouseId = wareHouseId;
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

	@Column(name = "deviceTypeId", nullable = false)
	public Integer getDeviceTypeId() {
		return this.deviceTypeId;
	}

	public void setDeviceTypeId(Integer deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof BasWarehouseSurplusId))
			return false;
		BasWarehouseSurplusId castOther = (BasWarehouseSurplusId) other;

		return ((this.getWareHouseId() == castOther.getWareHouseId()) || (this
				.getWareHouseId() != null && castOther.getWareHouseId() != null && this
				.getWareHouseId().equals(castOther.getWareHouseId())))
				&& ((this.getModel() == castOther.getModel()) || (this
						.getModel() != null && castOther.getModel() != null && this
						.getModel().equals(castOther.getModel())))
				&& ((this.getState() == castOther.getState()) || (this
						.getState() != null && castOther.getState() != null && this
						.getState().equals(castOther.getState())))
				&& ((this.getDeviceTypeId() == castOther.getDeviceTypeId()) || (this
						.getDeviceTypeId() != null
						&& castOther.getDeviceTypeId() != null && this
						.getDeviceTypeId().equals(castOther.getDeviceTypeId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getWareHouseId() == null ? 0 : this.getWareHouseId()
						.hashCode());
		result = 37 * result
				+ (getModel() == null ? 0 : this.getModel().hashCode());
		result = 37 * result
				+ (getState() == null ? 0 : this.getState().hashCode());
		result = 37
				* result
				+ (getDeviceTypeId() == null ? 0 : this.getDeviceTypeId()
						.hashCode());
		return result;
	}

}