package com.ssxt.web.bean;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * BasProblemCommonId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class BasProblemCommonId implements java.io.Serializable {

	// Fields

	private Integer userId;
	private Integer problemId;

	// Constructors

	/** default constructor */
	public BasProblemCommonId() {
	}

	/** full constructor */
	public BasProblemCommonId(Integer userId, Integer problemId) {
		this.userId = userId;
		this.problemId = problemId;
	}

	// Property accessors

	@Column(name = "userId", nullable = false)
	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "problemId", nullable = false)
	public Integer getProblemId() {
		return this.problemId;
	}

	public void setProblemId(Integer problemId) {
		this.problemId = problemId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof BasProblemCommonId))
			return false;
		BasProblemCommonId castOther = (BasProblemCommonId) other;

		return ((this.getUserId() == castOther.getUserId()) || (this
				.getUserId() != null && castOther.getUserId() != null && this
				.getUserId().equals(castOther.getUserId())))
				&& ((this.getProblemId() == castOther.getProblemId()) || (this
						.getProblemId() != null
						&& castOther.getProblemId() != null && this
						.getProblemId().equals(castOther.getProblemId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getUserId() == null ? 0 : this.getUserId().hashCode());
		result = 37 * result
				+ (getProblemId() == null ? 0 : this.getProblemId().hashCode());
		return result;
	}

}