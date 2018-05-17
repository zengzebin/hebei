package com.ssxt.web.bean.warning;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * GwSoilmoistureInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "gw_soilmoisture_info", catalog = "hebei")
public class GwSoilmoistureInfo implements java.io.Serializable {

	// Fields

	private String stcd;
	private Timestamp observationTime;
	private Timestamp sendTime;
	private Timestamp insertTime;
	private String dataType;
	private String functionCode;
	private String message;
	private String mainguid;
	private String vt;

	// Constructors

	/** default constructor */
	public GwSoilmoistureInfo() {
	}

	/** minimal constructor */
	public GwSoilmoistureInfo(String stcd) {
		this.stcd = stcd;
	}

	/** full constructor */
	public GwSoilmoistureInfo(String stcd, Timestamp observationTime,
			Timestamp sendTime, Timestamp insertTime, String dataType,
			String functionCode, String message, String mainguid, String vt) {
		this.stcd = stcd;
		this.observationTime = observationTime;
		this.sendTime = sendTime;
		this.insertTime = insertTime;
		this.dataType = dataType;
		this.functionCode = functionCode;
		this.message = message;
		this.mainguid = mainguid;
		this.vt = vt;
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

	@Column(name = "VT", length = 20)
	public String getVt() {
		return this.vt;
	}

	public void setVt(String vt) {
		this.vt = vt;
	}

}