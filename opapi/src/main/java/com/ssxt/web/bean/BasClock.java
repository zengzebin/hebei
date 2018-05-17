package com.ssxt.web.bean;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * BasClock entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "bas_clock", catalog = "hebei")
public class BasClock implements java.io.Serializable {

	// Fields

	private BasClockId id;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date toWork;
	private Date offWork;
	private Integer state;

	// Constructors

	/** default constructor */
	public BasClock() {
	}

	/** minimal constructor */
	public BasClock(BasClockId id) {
		this.id = id;
	}

	/** full constructor */
	public BasClock(BasClockId id, Date toWork, Date offWork) {
		this.id = id;
		this.toWork = toWork;
		this.offWork = offWork;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "userId", column = @Column(name = "userId", nullable = false) ),
			@AttributeOverride(name = "date", column = @Column(name = "date", nullable = false, length = 10) ) })
	public BasClockId getId() {
		return this.id;
	}

	public void setId(BasClockId id) {
		this.id = id;
	}

	@Column(name = "toWork", length = 19)
	public Date getToWork() {
		return this.toWork;
	}

	public void setToWork(Date toWork) {
		this.toWork = toWork;
	}

	@Column(name = "offWork", length = 19)
	public Date getOffWork() {
		return this.offWork;
	}

	public void setOffWork(Date offWork) {
		this.offWork = offWork;
	}

	@Column(name = "state")
	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

}