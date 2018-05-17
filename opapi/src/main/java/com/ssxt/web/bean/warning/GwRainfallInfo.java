package com.ssxt.web.bean.warning;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * GwRainfallInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "gw_rainfall_info", catalog = "hebei")
public class GwRainfallInfo implements java.io.Serializable {

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
	private String pt;
	private String pd;
	private Double vt;
	private String mainguid;

	// Constructors

	/** default constructor */
	public GwRainfallInfo() {
	}

	/** minimal constructor */
	public GwRainfallInfo(String stcd) {
		this.stcd = stcd;
	}

	/** full constructor */
	public GwRainfallInfo(String stcd, Timestamp observationTime,
			Timestamp sendTime, Timestamp insertTime, String dataType,
			String functionCode, Double drp, Double intv, Double pdr,
			Double dyp, String wth, String pt, String pd, Double vt,
			String mainguid) {
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
		this.pt = pt;
		this.pd = pd;
		this.vt = vt;
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

	@Column(name = "VT", precision = 9, scale = 3)
	public Double getVt() {
		return this.vt;
	}

	public void setVt(Double vt) {
		this.vt = vt;
	}

	@Column(name = "MAINGUID", length = 200)
	public String getMainguid() {
		return this.mainguid;
	}

	public void setMainguid(String mainguid) {
		this.mainguid = mainguid;
	}

}