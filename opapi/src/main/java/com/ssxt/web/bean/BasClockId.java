package com.ssxt.web.bean;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * BasClockId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class BasClockId implements java.io.Serializable {

	// Fields

	private Integer userId;
	private String date;

	// Constructors

	/** default constructor */
	public BasClockId() {
	}

	/** full constructor */
	public BasClockId(Integer userId, String date) {
		this.userId = userId;
		this.date = date;
	}

	// Property accessors

	@Column(name = "userId", nullable = false)
	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "date", nullable = false, length = 10)
	public String getDate() {
		return this.date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof BasClockId))
			return false;
		BasClockId castOther = (BasClockId) other;

		return ((this.getUserId() == castOther.getUserId()) || (this.getUserId() != null
				&& castOther.getUserId() != null && this.getUserId().equals(castOther.getUserId())))
				&& ((this.getDate() == castOther.getDate()) || (this.getDate() != null && castOther.getDate() != null
						&& this.getDate().equals(castOther.getDate())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getUserId() == null ? 0 : this.getUserId().hashCode());
		result = 37 * result + (getDate() == null ? 0 : this.getDate().hashCode());
		return result;
	}

}