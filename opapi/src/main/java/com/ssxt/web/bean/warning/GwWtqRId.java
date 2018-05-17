package com.ssxt.web.bean.warning;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * GwWtqRId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class GwWtqRId implements java.io.Serializable {

	// Fields

	private String stcd;
	private Timestamp spt;

	// Constructors

	/** default constructor */
	public GwWtqRId() {
	}

	/** full constructor */
	public GwWtqRId(String stcd, Timestamp spt) {
		this.stcd = stcd;
		this.spt = spt;
	}

	// Property accessors

	@Column(name = "STCD", nullable = false, length = 8)
	public String getStcd() {
		return this.stcd;
	}

	public void setStcd(String stcd) {
		this.stcd = stcd;
	}

	@Column(name = "SPT", nullable = false, length = 19)
	public Timestamp getSpt() {
		return this.spt;
	}

	public void setSpt(Timestamp spt) {
		this.spt = spt;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof GwWtqRId))
			return false;
		GwWtqRId castOther = (GwWtqRId) other;

		return ((this.getStcd() == castOther.getStcd()) || (this.getStcd() != null
				&& castOther.getStcd() != null && this.getStcd().equals(
				castOther.getStcd())))
				&& ((this.getSpt() == castOther.getSpt()) || (this.getSpt() != null
						&& castOther.getSpt() != null && this.getSpt().equals(
						castOther.getSpt())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getStcd() == null ? 0 : this.getStcd().hashCode());
		result = 37 * result
				+ (getSpt() == null ? 0 : this.getSpt().hashCode());
		return result;
	}

}