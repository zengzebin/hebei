package com.ssxt.web.bean.warning;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * GwPptnR entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "gw_pptn_r", catalog = "hebei")
public class GwPptnR implements java.io.Serializable {

	// Fields

	private GwPptnRId id;
	private Double drp;
	private Double intv;
	private Double pdr;
	private Double dyp;
	private String wth;
	private String vt;

	// Constructors

	/** default constructor */
	public GwPptnR() {
	}

	/** minimal constructor */
	public GwPptnR(GwPptnRId id) {
		this.id = id;
	}

	/** full constructor */
	public GwPptnR(GwPptnRId id, Double drp, Double intv, Double pdr,
			Double dyp, String wth, String vt) {
		this.id = id;
		this.drp = drp;
		this.intv = intv;
		this.pdr = pdr;
		this.dyp = dyp;
		this.wth = wth;
		this.vt = vt;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "stcd", column = @Column(name = "STCD", nullable = false, length = 8)),
			@AttributeOverride(name = "tm", column = @Column(name = "TM", nullable = false, length = 19)) })
	public GwPptnRId getId() {
		return this.id;
	}

	public void setId(GwPptnRId id) {
		this.id = id;
	}

	@Column(name = "DRP", precision = 5, scale = 1)
	public Double getDrp() {
		return this.drp;
	}

	public void setDrp(Double drp) {
		this.drp = drp;
	}

	@Column(name = "INTV", precision = 5)
	public Double getIntv() {
		return this.intv;
	}

	public void setIntv(Double intv) {
		this.intv = intv;
	}

	@Column(name = "PDR", precision = 5)
	public Double getPdr() {
		return this.pdr;
	}

	public void setPdr(Double pdr) {
		this.pdr = pdr;
	}

	@Column(name = "DYP", precision = 5, scale = 1)
	public Double getDyp() {
		return this.dyp;
	}

	public void setDyp(Double dyp) {
		this.dyp = dyp;
	}

	@Column(name = "WTH", length = 1)
	public String getWth() {
		return this.wth;
	}

	public void setWth(String wth) {
		this.wth = wth;
	}

	@Column(name = "VT", length = 20)
	public String getVt() {
		return this.vt;
	}

	public void setVt(String vt) {
		this.vt = vt;
	}

}