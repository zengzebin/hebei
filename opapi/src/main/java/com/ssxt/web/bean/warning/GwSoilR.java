package com.ssxt.web.bean.warning;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * GwSoilR entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "gw_soil_r", catalog = "hebei")
public class GwSoilR implements java.io.Serializable {

	// Fields

	private GwSoilRId id;
	private String exkey;
	private Double vtavslm;
	private Double srlslm;
	private Double slm10;
	private Double slm20;
	private Double slm30;
	private Double slm40;
	private Double slm60;
	private Double slm80;
	private Double slm100;
	private String crpty;
	private String crpgrwprd;
	private String hitrsn;
	private String slmmmt;
	private String mainguid;
	private String vt;

	// Constructors

	/** default constructor */
	public GwSoilR() {
	}

	/** minimal constructor */
	public GwSoilR(GwSoilRId id) {
		this.id = id;
	}

	/** full constructor */
	public GwSoilR(GwSoilRId id, String exkey, Double vtavslm, Double srlslm,
			Double slm10, Double slm20, Double slm30, Double slm40,
			Double slm60, Double slm80, Double slm100, String crpty,
			String crpgrwprd, String hitrsn, String slmmmt, String mainguid,
			String vt) {
		this.id = id;
		this.exkey = exkey;
		this.vtavslm = vtavslm;
		this.srlslm = srlslm;
		this.slm10 = slm10;
		this.slm20 = slm20;
		this.slm30 = slm30;
		this.slm40 = slm40;
		this.slm60 = slm60;
		this.slm80 = slm80;
		this.slm100 = slm100;
		this.crpty = crpty;
		this.crpgrwprd = crpgrwprd;
		this.hitrsn = hitrsn;
		this.slmmmt = slmmmt;
		this.mainguid = mainguid;
		this.vt = vt;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "stcd", column = @Column(name = "STCD", nullable = false, length = 8)),
			@AttributeOverride(name = "tm", column = @Column(name = "TM", nullable = false, length = 19)) })
	public GwSoilRId getId() {
		return this.id;
	}

	public void setId(GwSoilRId id) {
		this.id = id;
	}

	@Column(name = "EXKEY", length = 1)
	public String getExkey() {
		return this.exkey;
	}

	public void setExkey(String exkey) {
		this.exkey = exkey;
	}

	@Column(name = "VTAVSLM", precision = 4, scale = 1)
	public Double getVtavslm() {
		return this.vtavslm;
	}

	public void setVtavslm(Double vtavslm) {
		this.vtavslm = vtavslm;
	}

	@Column(name = "SRLSLM", precision = 4, scale = 1)
	public Double getSrlslm() {
		return this.srlslm;
	}

	public void setSrlslm(Double srlslm) {
		this.srlslm = srlslm;
	}

	@Column(name = "SLM10", precision = 4, scale = 1)
	public Double getSlm10() {
		return this.slm10;
	}

	public void setSlm10(Double slm10) {
		this.slm10 = slm10;
	}

	@Column(name = "SLM20", precision = 4, scale = 1)
	public Double getSlm20() {
		return this.slm20;
	}

	public void setSlm20(Double slm20) {
		this.slm20 = slm20;
	}

	@Column(name = "SLM30", precision = 4, scale = 1)
	public Double getSlm30() {
		return this.slm30;
	}

	public void setSlm30(Double slm30) {
		this.slm30 = slm30;
	}

	@Column(name = "SLM40", precision = 4, scale = 1)
	public Double getSlm40() {
		return this.slm40;
	}

	public void setSlm40(Double slm40) {
		this.slm40 = slm40;
	}

	@Column(name = "SLM60", precision = 4, scale = 1)
	public Double getSlm60() {
		return this.slm60;
	}

	public void setSlm60(Double slm60) {
		this.slm60 = slm60;
	}

	@Column(name = "SLM80", precision = 4, scale = 1)
	public Double getSlm80() {
		return this.slm80;
	}

	public void setSlm80(Double slm80) {
		this.slm80 = slm80;
	}

	@Column(name = "SLM100", precision = 4, scale = 1)
	public Double getSlm100() {
		return this.slm100;
	}

	public void setSlm100(Double slm100) {
		this.slm100 = slm100;
	}

	@Column(name = "CRPTY", length = 1)
	public String getCrpty() {
		return this.crpty;
	}

	public void setCrpty(String crpty) {
		this.crpty = crpty;
	}

	@Column(name = "CRPGRWPRD", length = 1)
	public String getCrpgrwprd() {
		return this.crpgrwprd;
	}

	public void setCrpgrwprd(String crpgrwprd) {
		this.crpgrwprd = crpgrwprd;
	}

	@Column(name = "HITRSN", length = 1)
	public String getHitrsn() {
		return this.hitrsn;
	}

	public void setHitrsn(String hitrsn) {
		this.hitrsn = hitrsn;
	}

	@Column(name = "SLMMMT", length = 1)
	public String getSlmmmt() {
		return this.slmmmt;
	}

	public void setSlmmmt(String slmmmt) {
		this.slmmmt = slmmmt;
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