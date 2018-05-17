package com.ssxt.web.bean.warning;

import java.sql.Timestamp;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * GwWtqR entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "gw_wtq_r", catalog = "hebei")
public class GwWtqR implements java.io.Serializable {

	// Fields

	private GwWtqRId id;
	private Timestamp ltstm;
	private Double ph;
	private Double redox;
	private Integer cond;
	private Double ss;
	private Double dsco2;
	private Double agco2;
	private Double dox;
	private Double nh3n;
	private Double no2;
	private Double no3;
	private Double codmn;
	private Double codcr;
	private Double cn;
	private Double ars;
	private Double f;
	private Double vlph;
	private Double cr6;
	private Double hg;
	private Double fe;
	private Double mn;
	private Double tp;
	private Double pb;
	private String tcg;
	private Double tds;
	private Short turb;
	private String neobj;
	private Double cu;
	private Double zn;
	private Double mo;
	private Double co;
	private Double las;
	private Double i;
	private Double se;
	private Double be;
	private Double ba;
	private Double ni;
	private Double bhc;
	private Double ddt;
	private String bctc;
	private Double talatv;
	private Double tbtatv;
	private String mainguid;

	// Constructors

	/** default constructor */
	public GwWtqR() {
	}

	/** minimal constructor */
	public GwWtqR(GwWtqRId id) {
		this.id = id;
	}

	/** full constructor */
	public GwWtqR(GwWtqRId id, Timestamp ltstm, Double ph, Double redox,
			Integer cond, Double ss, Double dsco2, Double agco2, Double dox,
			Double nh3n, Double no2, Double no3, Double codmn, Double codcr,
			Double cn, Double ars, Double f, Double vlph, Double cr6,
			Double hg, Double fe, Double mn, Double tp, Double pb, String tcg,
			Double tds, Short turb, String neobj, Double cu, Double zn,
			Double mo, Double co, Double las, Double i, Double se, Double be,
			Double ba, Double ni, Double bhc, Double ddt, String bctc,
			Double talatv, Double tbtatv, String mainguid) {
		this.id = id;
		this.ltstm = ltstm;
		this.ph = ph;
		this.redox = redox;
		this.cond = cond;
		this.ss = ss;
		this.dsco2 = dsco2;
		this.agco2 = agco2;
		this.dox = dox;
		this.nh3n = nh3n;
		this.no2 = no2;
		this.no3 = no3;
		this.codmn = codmn;
		this.codcr = codcr;
		this.cn = cn;
		this.ars = ars;
		this.f = f;
		this.vlph = vlph;
		this.cr6 = cr6;
		this.hg = hg;
		this.fe = fe;
		this.mn = mn;
		this.tp = tp;
		this.pb = pb;
		this.tcg = tcg;
		this.tds = tds;
		this.turb = turb;
		this.neobj = neobj;
		this.cu = cu;
		this.zn = zn;
		this.mo = mo;
		this.co = co;
		this.las = las;
		this.i = i;
		this.se = se;
		this.be = be;
		this.ba = ba;
		this.ni = ni;
		this.bhc = bhc;
		this.ddt = ddt;
		this.bctc = bctc;
		this.talatv = talatv;
		this.tbtatv = tbtatv;
		this.mainguid = mainguid;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "stcd", column = @Column(name = "STCD", nullable = false, length = 8)),
			@AttributeOverride(name = "spt", column = @Column(name = "SPT", nullable = false, length = 19)) })
	public GwWtqRId getId() {
		return this.id;
	}

	public void setId(GwWtqRId id) {
		this.id = id;
	}

	@Column(name = "LTSTM", length = 19)
	public Timestamp getLtstm() {
		return this.ltstm;
	}

	public void setLtstm(Timestamp ltstm) {
		this.ltstm = ltstm;
	}

	@Column(name = "PH", precision = 4)
	public Double getPh() {
		return this.ph;
	}

	public void setPh(Double ph) {
		this.ph = ph;
	}

	@Column(name = "REDOX", precision = 5, scale = 1)
	public Double getRedox() {
		return this.redox;
	}

	public void setRedox(Double redox) {
		this.redox = redox;
	}

	@Column(name = "COND", precision = 5, scale = 0)
	public Integer getCond() {
		return this.cond;
	}

	public void setCond(Integer cond) {
		this.cond = cond;
	}

	@Column(name = "SS", precision = 7, scale = 1)
	public Double getSs() {
		return this.ss;
	}

	public void setSs(Double ss) {
		this.ss = ss;
	}

	@Column(name = "DSCO2", precision = 4)
	public Double getDsco2() {
		return this.dsco2;
	}

	public void setDsco2(Double dsco2) {
		this.dsco2 = dsco2;
	}

	@Column(name = "AGCO2", precision = 4)
	public Double getAgco2() {
		return this.agco2;
	}

	public void setAgco2(Double agco2) {
		this.agco2 = agco2;
	}

	@Column(name = "DOX", precision = 4)
	public Double getDox() {
		return this.dox;
	}

	public void setDox(Double dox) {
		this.dox = dox;
	}

	@Column(name = "NH3N", precision = 6)
	public Double getNh3n() {
		return this.nh3n;
	}

	public void setNh3n(Double nh3n) {
		this.nh3n = nh3n;
	}

	@Column(name = "NO2", precision = 5, scale = 3)
	public Double getNo2() {
		return this.no2;
	}

	public void setNo2(Double no2) {
		this.no2 = no2;
	}

	@Column(name = "NO3", precision = 5)
	public Double getNo3() {
		return this.no3;
	}

	public void setNo3(Double no3) {
		this.no3 = no3;
	}

	@Column(name = "CODMN", precision = 4, scale = 1)
	public Double getCodmn() {
		return this.codmn;
	}

	public void setCodmn(Double codmn) {
		this.codmn = codmn;
	}

	@Column(name = "CODCR", precision = 7, scale = 1)
	public Double getCodcr() {
		return this.codcr;
	}

	public void setCodcr(Double codcr) {
		this.codcr = codcr;
	}

	@Column(name = "CN", precision = 5, scale = 3)
	public Double getCn() {
		return this.cn;
	}

	public void setCn(Double cn) {
		this.cn = cn;
	}

	@Column(name = "ARS", precision = 7, scale = 5)
	public Double getArs() {
		return this.ars;
	}

	public void setArs(Double ars) {
		this.ars = ars;
	}

	@Column(name = "F", precision = 5)
	public Double getF() {
		return this.f;
	}

	public void setF(Double f) {
		this.f = f;
	}

	@Column(name = "VLPH", precision = 5, scale = 3)
	public Double getVlph() {
		return this.vlph;
	}

	public void setVlph(Double vlph) {
		this.vlph = vlph;
	}

	@Column(name = "CR6", precision = 5, scale = 3)
	public Double getCr6() {
		return this.cr6;
	}

	public void setCr6(Double cr6) {
		this.cr6 = cr6;
	}

	@Column(name = "HG", precision = 7, scale = 5)
	public Double getHg() {
		return this.hg;
	}

	public void setHg(Double hg) {
		this.hg = hg;
	}

	@Column(name = "FE", precision = 4)
	public Double getFe() {
		return this.fe;
	}

	public void setFe(Double fe) {
		this.fe = fe;
	}

	@Column(name = "MN", precision = 4)
	public Double getMn() {
		return this.mn;
	}

	public void setMn(Double mn) {
		this.mn = mn;
	}

	@Column(name = "TP", precision = 5, scale = 3)
	public Double getTp() {
		return this.tp;
	}

	public void setTp(Double tp) {
		this.tp = tp;
	}

	@Column(name = "PB", precision = 7, scale = 5)
	public Double getPb() {
		return this.pb;
	}

	public void setPb(Double pb) {
		this.pb = pb;
	}

	@Column(name = "TCG", length = 10)
	public String getTcg() {
		return this.tcg;
	}

	public void setTcg(String tcg) {
		this.tcg = tcg;
	}

	@Column(name = "TDS", precision = 7)
	public Double getTds() {
		return this.tds;
	}

	public void setTds(Double tds) {
		this.tds = tds;
	}

	@Column(name = "TURB", precision = 3, scale = 0)
	public Short getTurb() {
		return this.turb;
	}

	public void setTurb(Short turb) {
		this.turb = turb;
	}

	@Column(name = "NEOBJ", length = 40)
	public String getNeobj() {
		return this.neobj;
	}

	public void setNeobj(String neobj) {
		this.neobj = neobj;
	}

	@Column(name = "CU", precision = 7, scale = 4)
	public Double getCu() {
		return this.cu;
	}

	public void setCu(Double cu) {
		this.cu = cu;
	}

	@Column(name = "ZN", precision = 6, scale = 4)
	public Double getZn() {
		return this.zn;
	}

	public void setZn(Double zn) {
		this.zn = zn;
	}

	@Column(name = "MO", precision = 7, scale = 5)
	public Double getMo() {
		return this.mo;
	}

	public void setMo(Double mo) {
		this.mo = mo;
	}

	@Column(name = "CO", precision = 7, scale = 5)
	public Double getCo() {
		return this.co;
	}

	public void setCo(Double co) {
		this.co = co;
	}

	@Column(name = "LAS", precision = 4)
	public Double getLas() {
		return this.las;
	}

	public void setLas(Double las) {
		this.las = las;
	}

	@Column(name = "I", precision = 5, scale = 3)
	public Double getI() {
		return this.i;
	}

	public void setI(Double i) {
		this.i = i;
	}

	@Column(name = "SE", precision = 7, scale = 5)
	public Double getSe() {
		return this.se;
	}

	public void setSe(Double se) {
		this.se = se;
	}

	@Column(name = "BE", precision = 7, scale = 5)
	public Double getBe() {
		return this.be;
	}

	public void setBe(Double be) {
		this.be = be;
	}

	@Column(name = "BA", precision = 7, scale = 5)
	public Double getBa() {
		return this.ba;
	}

	public void setBa(Double ba) {
		this.ba = ba;
	}

	@Column(name = "NI", precision = 7, scale = 5)
	public Double getNi() {
		return this.ni;
	}

	public void setNi(Double ni) {
		this.ni = ni;
	}

	@Column(name = "BHC", precision = 7, scale = 6)
	public Double getBhc() {
		return this.bhc;
	}

	public void setBhc(Double bhc) {
		this.bhc = bhc;
	}

	@Column(name = "DDT", precision = 7, scale = 6)
	public Double getDdt() {
		return this.ddt;
	}

	public void setDdt(Double ddt) {
		this.ddt = ddt;
	}

	@Column(name = "BCTC", length = 10)
	public String getBctc() {
		return this.bctc;
	}

	public void setBctc(String bctc) {
		this.bctc = bctc;
	}

	@Column(name = "TALATV", precision = 6, scale = 4)
	public Double getTalatv() {
		return this.talatv;
	}

	public void setTalatv(Double talatv) {
		this.talatv = talatv;
	}

	@Column(name = "TBTATV", precision = 6, scale = 4)
	public Double getTbtatv() {
		return this.tbtatv;
	}

	public void setTbtatv(Double tbtatv) {
		this.tbtatv = tbtatv;
	}

	@Column(name = "MAINGUID", length = 200)
	public String getMainguid() {
		return this.mainguid;
	}

	public void setMainguid(String mainguid) {
		this.mainguid = mainguid;
	}

}