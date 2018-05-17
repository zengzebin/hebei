package com.ssxt.web.bean;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * BasProblemCommon entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "bas_problem_common", catalog = "hebei")
public class BasProblemCommon implements java.io.Serializable {

	// Fields

	private BasProblemCommonId id;
	private Date clickTime;

	// Constructors

	/** default constructor */
	public BasProblemCommon() {
	}

	/** minimal constructor */
	public BasProblemCommon(BasProblemCommonId id) {
		this.id = id;
	}

	/** full constructor */
	public BasProblemCommon(BasProblemCommonId id, Date clickTime) {
		this.id = id;
		this.clickTime = clickTime;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "userId", column = @Column(name = "userId", nullable = false) ),
			@AttributeOverride(name = "problemId", column = @Column(name = "problemId", nullable = false) ) })
	public BasProblemCommonId getId() {
		return this.id;
	}

	public void setId(BasProblemCommonId id) {
		this.id = id;
	}

	@Column(name = "clickTime", length = 19)
	public Date getClickTime() {
		return this.clickTime;
	}

	public void setClickTime(Date clickTime) {
		this.clickTime = clickTime;
	}

}