package com.ssxt.web.bean.warning;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * GwStreamInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "gw_stream_info", catalog = "hebei")
public class GwStreamInfo implements java.io.Serializable {

	// Fields

	private String stcd;
	private Timestamp observationTime;
	private Timestamp sendTime;
	private Timestamp insertTime;
	private String dataType;
	private String functionCode;
	private Double drp;
	private Double intv;
	private Double pdr;
	private Double dyp;
	private String wth;
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
	private Double rz;
	private Double inq;
	private Double w;
	private Double blrz;
	private Double otq;
	private String rwchrcd;
	private String rwptn;
	private Double inqdr;
	private String msqmt2;
	private String pj;
	private String pt;
	private String pd;
	private String vt;
	private String message;
	private String mainguid;

	// Constructors

	/** default constructor */
	public GwStreamInfo() {
	}

	/** minimal constructor */
	public GwStreamInfo(String stcd) {
		this.stcd = stcd;
	}

	/** full constructor */
	public GwStreamInfo(String stcd, Timestamp observationTime,
			Timestamp sendTime, Timestamp insertTime, String dataType,
			String functionCode, Double drp, Double intv, Double pdr,
			Double dyp, String wth, Double z, Double q, Double xsa,
			Double xsavv, Double xsmxv, String flwchrcd, String wptn,
			String msqmt, String msamt, String msvmt, Double rz, Double inq,
			Double w, Double blrz, Double otq, String rwchrcd, String rwptn,
			Double inqdr, String msqmt2, String pj, String pt, String pd,
			String vt, String message, String mainguid) {
		this.stcd = stcd;
		this.observationTime = observationTime;
		this.sendTime = sendTime;
		this.insertTime = insertTime;
		this.dataType = dataType;
		this.functionCode = functionCode;
		this.drp = drp;
		this.intv = intv;
		this.pdr = pdr;
		this.dyp = dyp;
		this.wth = wth;
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
		this.rz = rz;
		this.inq = inq;
		this.w = w;
		this.blrz = blrz;
		this.otq = otq;
		this.rwchrcd = rwchrcd;
		this.rwptn = rwptn;
		this.inqdr = inqdr;
		this.msqmt2 = msqmt2;
		this.pj = pj;
		this.pt = pt;
		this.pd = pd;
		this.vt = vt;
		this.message = message;
		this.mainguid = mainguid;
	}

	// Property accessors
	@Id
	@Column(name = "STCD", unique = true, nullable = false, length = 8)
	public String getStcd() {
		return this.stcd;
	}

	public void setStcd(String stcd) {
		this.stcd = stcd;
	}

	@Column(name = "observationTime", length = 19)
	public Timestamp getObservationTime() {
		return this.observationTime;
	}

	public void setObservationTime(Timestamp observationTime) {
		this.observationTime = observationTime;
	}

	@Column(name = "sendTime", length = 19)
	public Timestamp getSendTime() {
		return this.sendTime;
	}

	public void setSendTime(Timestamp sendTime) {
		this.sendTime = sendTime;
	}

	@Column(name = "insertTime", length = 19)
	public Timestamp getInsertTime() {
		return this.insertTime;
	}

	public void setInsertTime(Timestamp insertTime) {
		this.insertTime = insertTime;
	}

	@Column(name = "dataType", length = 50)
	public String getDataType() {
		return this.dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	@Column(name = "functionCode", length = 50)
	public String getFunctionCode() {
		return this.functionCode;
	}

	public void setFunctionCode(String functionCode) {
		this.functionCode = functionCode;
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

	@Column(name = "MSQMT2", length = 1)
	public String getMsqmt2() {
		return this.msqmt2;
	}

	public void setMsqmt2(String msqmt2) {
		this.msqmt2 = msqmt2;
	}

	@Column(name = "PJ", length = 5)
	public String getPj() {
		return this.pj;
	}

	public void setPj(String pj) {
		this.pj = pj;
	}

	@Column(name = "PT", length = 5)
	public String getPt() {
		return this.pt;
	}

	public void setPt(String pt) {
		this.pt = pt;
	}

	@Column(name = "PD", length = 5)
	public String getPd() {
		return this.pd;
	}

	public void setPd(String pd) {
		this.pd = pd;
	}

	@Column(name = "VT", length = 50)
	public String getVt() {
		return this.vt;
	}

	public void setVt(String vt) {
		this.vt = vt;
	}

	@Column(name = "message", length = 65535)
	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Column(name = "MAINGUID", length = 200)
	public String getMainguid() {
		return this.mainguid;
	}

	public void setMainguid(String mainguid) {
		this.mainguid = mainguid;
	}

}