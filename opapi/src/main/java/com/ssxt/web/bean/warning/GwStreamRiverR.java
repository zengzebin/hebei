package com.ssxt.web.bean.warning;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * GwStreamRiverR entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "gw_stream_river_r", catalog = "hebei")
public class GwStreamRiverR implements java.io.Serializable {

	// Fields

	private GwStreamRiverRId id;
	private Double z;
	private Double q;
	private Double xsa;
	private Double xsavv;
	private Double xsmxv;
	private String flwchrcd;
	private String wptn;
	private String msqmt;
	private String msamt;
	private String msvmt;
	private String vt;

	// Constructors

	/** default constructor */
	public GwStreamRiverR() {
	}

	/** minimal constructor */
	public GwStreamRiverR(GwStreamRiverRId id) {
		this.id = id;
	}

	/** full constructor */
	public GwStreamRiverR(GwStreamRiverRId id, Double z, Double q, Double xsa,
			Double xsavv, Double xsmxv, String flwchrcd, String wptn,
			String msqmt, String msamt, String msvmt, String vt) {
		this.id = id;
		this.z = z;
		this.q = q;
		this.xsa = xsa;
		this.xsavv = xsavv;
		this.xsmxv = xsmxv;
		this.flwchrcd = flwchrcd;
		this.wptn = wptn;
		this.msqmt = msqmt;
		this.msamt = msamt;
		this.msvmt = msvmt;
		this.vt = vt;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "stcd", column = @Column(name = "STCD", nullable = false, length = 8)),
			@AttributeOverride(name = "tm", column = @Column(name = "TM", nullable = false, length = 19)) })
	public GwStreamRiverRId getId() {
		return this.id;
	}

	public void setId(GwStreamRiverRId id) {
		this.id = id;
	}

	@Column(name = "Z", precision = 7, scale = 3)
	public Double getZ() {
		return this.z;
	}

	public void setZ(Double z) {
		this.z = z;
	}

	@Column(name = "Q", precision = 9, scale = 3)
	public Double getQ() {
		return this.q;
	}

	public void setQ(Double q) {
		this.q = q;
	}

	@Column(name = "XSA", precision = 9, scale = 3)
	public Double getXsa() {
		return this.xsa;
	}

	public void setXsa(Double xsa) {
		this.xsa = xsa;
	}

	@Column(name = "XSAVV", precision = 5, scale = 3)
	public Double getXsavv() {
		return this.xsavv;
	}

	public void setXsavv(Double xsavv) {
		this.xsavv = xsavv;
	}

	@Column(name = "XSMXV", precision = 5, scale = 3)
	public Double getXsmxv() {
		return this.xsmxv;
	}

	public void setXsmxv(Double xsmxv) {
		this.xsmxv = xsmxv;
	}

	@Column(name = "FLWCHRCD", length = 1)
	public String getFlwchrcd() {
		return this.flwchrcd;
	}

	public void setFlwchrcd(String flwchrcd) {
		this.flwchrcd = flwchrcd;
	}

	@Column(name = "WPTN", length = 1)
	public String getWptn() {
		return this.wptn;
	}

	public void setWptn(String wptn) {
		this.wptn = wptn;
	}

	@Column(name = "MSQMT", length = 1)
	public String getMsqmt() {
		return this.msqmt;
	}

	public void setMsqmt(String msqmt) {
		this.msqmt = msqmt;
	}

	@Column(name = "MSAMT", length = 1)
	public String getMsamt() {
		return this.msamt;
	}

	public void setMsamt(String msamt) {
		this.msamt = msamt;
	}

	@Column(name = "MSVMT", length = 1)
	public String getMsvmt() {
		return this.msvmt;
	}

	public void setMsvmt(String msvmt) {
		this.msvmt = msvmt;
	}

	@Column(name = "VT", length = 20)
	public String getVt() {
		return this.vt;
	}

	public void setVt(String vt) {
		this.vt = vt;
	}

}