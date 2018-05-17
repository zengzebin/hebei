package com.ssxt.web.bean.warning;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * GwRsvrR entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "gw_rsvr_r", catalog = "hebei")
public class GwRsvrR implements java.io.Serializable {

	// Fields

	private GwRsvrRId id;
	private Double rz;
	private Double inq;
	private Double w;
	private Double blrz;
	private Double otq;
	private String rwchrcd;
	private String rwptn;
	private Double inqdr;
	private String msqmt;
	private String vt;

	// Constructors

	/** default constructor */
	public GwRsvrR() {
	}

	/** minimal constructor */
	public GwRsvrR(GwRsvrRId id) {
		this.id = id;
	}

	/** full constructor */
	public GwRsvrR(GwRsvrRId id, Double rz, Double inq, Double w, Double blrz,
			Double otq, String rwchrcd, String rwptn, Double inqdr,
			String msqmt, String vt) {
		this.id = id;
		this.rz = rz;
		this.inq = inq;
		this.w = w;
		this.blrz = blrz;
		this.otq = otq;
		this.rwchrcd = rwchrcd;
		this.rwptn = rwptn;
		this.inqdr = inqdr;
		this.msqmt = msqmt;
		this.vt = vt;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "stcd", column = @Column(name = "STCD", nullable = false, length = 8)),
			@AttributeOverride(name = "tm", column = @Column(name = "TM", nullable = false, length = 19)) })
	public GwRsvrRId getId() {
		return this.id;
	}

	public void setId(GwRsvrRId id) {
		this.id = id;
	}

	@Column(name = "RZ", precision = 7, scale = 3)
	public Double getRz() {
		return this.rz;
	}

	public void setRz(Double rz) {
		this.rz = rz;
	}

	@Column(name = "INQ", precision = 9, scale = 3)
	public Double getInq() {
		return this.inq;
	}

	public void setInq(Double inq) {
		this.inq = inq;
	}

	@Column(name = "W", precision = 9, scale = 3)
	public Double getW() {
		return this.w;
	}

	public void setW(Double w) {
		this.w = w;
	}

	@Column(name = "BLRZ", precision = 7, scale = 3)
	public Double getBlrz() {
		return this.blrz;
	}

	public void setBlrz(Double blrz) {
		this.blrz = blrz;
	}

	@Column(name = "OTQ", precision = 9, scale = 3)
	public Double getOtq() {
		return this.otq;
	}

	public void setOtq(Double otq) {
		this.otq = otq;
	}

	@Column(name = "RWCHRCD", length = 1)
	public String getRwchrcd() {
		return this.rwchrcd;
	}

	public void setRwchrcd(String rwchrcd) {
		this.rwchrcd = rwchrcd;
	}

	@Column(name = "RWPTN", length = 1)
	public String getRwptn() {
		return this.rwptn;
	}

	public void setRwptn(String rwptn) {
		this.rwptn = rwptn;
	}

	@Column(name = "INQDR", precision = 5)
	public Double getInqdr() {
		return this.inqdr;
	}

	public void setInqdr(Double inqdr) {
		this.inqdr = inqdr;
	}

	@Column(name = "MSQMT", length = 1)
	public String getMsqmt() {
		return this.msqmt;
	}

	public void setMsqmt(String msqmt) {
		this.msqmt = msqmt;
	}

	@Column(name = "VT", length = 20)
	public String getVt() {
		return this.vt;
	}

	public void setVt(String vt) {
		this.vt = vt;
	}

}