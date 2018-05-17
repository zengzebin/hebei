package com.ssxt.web.bean;

// default package

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * BasRepairSurplusId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class BasRepairSurplusId implements java.io.Serializable {
	// Fields

	private Integer userId;
	private Integer deviceTypeId;
	private Integer state;
	private String model;

	// Constructors

	/** default constructor */
	public BasRepairSurplusId() {
	}

	/** full constructor */
	public BasRepairSurplusId(Integer userId, Integer deviceTypeId, Integer state, String model) {
		this.userId = userId;
		this.deviceTypeId = deviceTypeId;
		this.state = state;
		this.model = model;
	}

	// Property accessors

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

	@Column(name = "state", nullable = false)
	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Column(name = "model", nullable = false, length = 20)
	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof BasRepairSurplusId))
			return false;
		BasRepairSurplusId castOther = (BasRepairSurplusId) other;

		return ((this.getUserId() == castOther.getUserId()) || (this.getUserId() != null
				&& castOther.getUserId() != null && this.getUserId().equals(castOther.getUserId())))
				&& ((this.getDeviceTypeId() == castOther.getDeviceTypeId())
						|| (this.getDeviceTypeId() != null && castOther.getDeviceTypeId() != null
								&& this.getDeviceTypeId().equals(castOther.getDeviceTypeId())))
				&& ((this.getState() == castOther.getState()) || (this.getState() != null
						&& castOther.getState() != null && this.getState().equals(castOther.getState())))
				&& ((this.getModel() == castOther.getModel()) || (this.getModel() != null
						&& castOther.getModel() != null && this.getModel().equals(castOther.getModel())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getUserId() == null ? 0 : this.getUserId().hashCode());
		result = 37 * result + (getDeviceTypeId() == null ? 0 : this.getDeviceTypeId().hashCode());
		result = 37 * result + (getState() == null ? 0 : this.getState().hashCode());
		result = 37 * result + (getModel() == null ? 0 : this.getModel().hashCode());
		return result;
	}

}