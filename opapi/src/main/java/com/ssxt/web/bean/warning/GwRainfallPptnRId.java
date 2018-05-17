package com.ssxt.web.bean.warning;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * GwRainfallPptnRId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class GwRainfallPptnRId implements java.io.Serializable {

	// Fields

	private String stcd;
	private Timestamp tm;

	// Constructors

	/** default constructor */
	public GwRainfallPptnRId() {
	}

	/** full constructor */
	public GwRainfallPptnRId(String stcd, Timestamp tm) {
		this.stcd = stcd;
		this.tm = tm;
	}

	// Property accessors

	@Column(name = "STCD", nullable = false, length = 8)
	public String getStcd() {
		return this.stcd;
	}

	public void setStcd(String stcd) {
		this.stcd = stcd;
	}

	@Column(name = "TM", nullable = false, length = 19)
	public Timestamp getTm() {
		return this.tm;
	}

	public void setTm(Timestamp tm) {
		this.tm = tm;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof GwRainfallPptnRId))
			return false;
		GwRainfallPptnRId castOther = (GwRainfallPptnRId) other;

		return ((this.getStcd() == castOther.getStcd()) || (this.getStcd() != null
				&& castOther.getStcd() != null && this.getStcd().equals(
				castOther.getStcd())))
				&& ((this.getTm() == castOther.getTm()) || (this.getTm() != null
						&& castOther.getTm() != null && this.getTm().equals(
						castOther.getTm())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getStcd() == null ? 0 : this.getStcd().hashCode());
		result = 37 * result + (getTm() == null ? 0 : this.getTm().hashCode());
		return result;
	}

}