package com.ssxt.web.bean.warning;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * GwZR entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "gw_z_r", catalog = "hebei")
public class GwZR implements java.io.Serializable {

	// Fields

	private GwZRId id;
	private Double rd;
	private Double bd;
	private String rmsy;
	private String mainguid;
	private String vt;

	// Constructors

	/** default constructor */
	public GwZR() {
	}

	/** minimal constructor */
	public GwZR(GwZRId id) {
		this.id = id;
	}

	/** full constructor */
	public GwZR(GwZRId id, Double rd, Double bd, String rmsy, String mainguid,
			String vt) {
		this.id = id;
		this.rd = rd;
		this.bd = bd;
		this.rmsy = rmsy;
		this.mainguid = mainguid;
		this.vt = vt;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "stcd", column = @Column(name = "STCD", nullable = false, length = 8)),
			@AttributeOverride(name = "tm", column = @Column(name = "TM", nullable = false, length = 19)) })
	public GwZRId getId() {
		return this.id;
	}

	public void setId(GwZRId id) {
		this.id = id;
	}

	@Column(name = "RD", precision = 5)
	public Double getRd() {
		return this.rd;
	}

	public void setRd(Double rd) {
		this.rd = rd;
	}

	@Column(name = "BD", precision = 5)
	public Double getBd() {
		return this.bd;
	}

	public void setBd(Double bd) {
		this.bd = bd;
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