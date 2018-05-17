package com.ssxt.web.bean.warning;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * GwWtR entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "gw_wt_r", catalog = "hebei")
public class GwWtR implements java.io.Serializable {

	// Fields

	private GwWtRId id;
	private Double wt;
	private Double at;
	private String rmsy;
	private String mainguid;
	private String vt;

	// Constructors

	/** default constructor */
	public GwWtR() {
	}

	/** minimal constructor */
	public GwWtR(GwWtRId id) {
		this.id = id;
	}

	/** full constructor */
	public GwWtR(GwWtRId id, Double wt, Double at, String rmsy,
			String mainguid, String vt) {
		this.id = id;
		this.wt = wt;
		this.at = at;
		this.rmsy = rmsy;
		this.mainguid = mainguid;
		this.vt = vt;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "stcd", column = @Column(name = "STCD", nullable = false, length = 8)),
			@AttributeOverride(name = "tm", column = @Column(name = "TM", nullable = false, length = 19)) })
	public GwWtRId getId() {
		return this.id;
	}

	public void setId(GwWtRId id) {
		this.id = id;
	}

	@Column(name = "WT", precision = 3, scale = 1)
	public Double getWt() {
		return this.wt;
	}

	public void setWt(Double wt) {
		this.wt = wt;
	}

	@Column(name = "AT", precision = 3, scale = 1)
	public Double getAt() {
		return this.at;
	}

	public void setAt(Double at) {
		this.at = at;
	}

	@Column(name = "RMSY", length = 2)
	public String getRmsy() {
		return this.rmsy;
	}

	public void setRmsy(String rmsy) {
		this.rmsy = rmsy;
	}

	@Column(name = "MAINGUID", length = 200)
	public String getMainguid() {
		return this.mainguid;
	}

	public void setMainguid(String mainguid) {
		this.mainguid = mainguid;
	}

	@Column(name = "VT", length = 20)
	public String getVt() {
		return this.vt;
	}

	public void setVt(String vt) {
		this.vt = vt;
	}

}