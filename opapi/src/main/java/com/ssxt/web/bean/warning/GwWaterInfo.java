package com.ssxt.web.bean.warning;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * GwWaterInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "gw_water_info", catalog = "hebei")
public class GwWaterInfo implements java.io.Serializable {

	// Fields

	private String stcd;
	private Timestamp observationTime;
	private Timestamp sendTime;
	private Timestamp insertTime;
	private String dataType;
	private String functionCode;
	private Double ff0e1;
	private Double ff0e2;
	private Double ff0e3;
	private Double ff0e4;
	private Double ff0e5;
	private Double ff0e6;
	private Double ff031;
	private Double ff032;
	private Double ff033;
	private Double ff034;
	private Double ff035;
	private Double ff036;
	private Double ff391;
	private Double ff392;
	private Double ff393;
	private Double ff394;
	private Double ff395;
	private Double ff396;
	private Double vt;
	private String mainguid;

	// Constructors

	/** default constructor */
	public GwWaterInfo() {
	}

	/** minimal constructor */
	public GwWaterInfo(String stcd) {
		this.stcd = stcd;
	}

	/** full constructor */
	public GwWaterInfo(String stcd, Timestamp observationTime,
			Timestamp sendTime, Timestamp insertTime, String dataType,
			String functionCode, Double ff0e1, Double ff0e2, Double ff0e3,
			Double ff0e4, Double ff0e5, Double ff0e6, Double ff031,
			Double ff032, Double ff033, Double ff034, Double ff035,
			Double ff036, Double ff391, Double ff392, Double ff393,
			Double ff394, Double ff395, Double ff396, Double vt, String mainguid) {
		this.stcd = stcd;
		this.observationTime = observationTime;
		this.sendTime = sendTime;
		this.insertTime = insertTime;
		this.dataType = dataType;
		this.functionCode = functionCode;
		this.ff0e1 = ff0e1;
		this.ff0e2 = ff0e2;
		this.ff0e3 = ff0e3;
		this.ff0e4 = ff0e4;
		this.ff0e5 = ff0e5;
		this.ff0e6 = ff0e6;
		this.ff031 = ff031;
		this.ff032 = ff032;
		this.ff033 = ff033;
		this.ff034 = ff034;
		this.ff035 = ff035;
		this.ff036 = ff036;
		this.ff391 = ff391;
		this.ff392 = ff392;
		this.ff393 = ff393;
		this.ff394 = ff394;
		this.ff395 = ff395;
		this.ff396 = ff396;
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

	@Column(name = "FF0E1", precision = 18, scale = 3)
	public Double getFf0e1() {
		return this.ff0e1;
	}

	public void setFf0e1(Double ff0e1) {
		this.ff0e1 = ff0e1;
	}

	@Column(name = "FF0E2", precision = 18, scale = 3)
	public Double getFf0e2() {
		return this.ff0e2;
	}

	public void setFf0e2(Double ff0e2) {
		this.ff0e2 = ff0e2;
	}

	@Column(name = "FF0E3", precision = 18, scale = 3)
	public Double getFf0e3() {
		return this.ff0e3;
	}

	public void setFf0e3(Double ff0e3) {
		this.ff0e3 = ff0e3;
	}

	@Column(name = "FF0E4", precision = 18, scale = 3)
	public Double getFf0e4() {
		return this.ff0e4;
	}

	public void setFf0e4(Double ff0e4) {
		this.ff0e4 = ff0e4;
	}

	@Column(name = "FF0E5", precision = 18, scale = 3)
	public Double getFf0e5() {
		return this.ff0e5;
	}

	public void setFf0e5(Double ff0e5) {
		this.ff0e5 = ff0e5;
	}

	@Column(name = "FF0E6", precision = 18, scale = 3)
	public Double getFf0e6() {
		return this.ff0e6;
	}

	public void setFf0e6(Double ff0e6) {
		this.ff0e6 = ff0e6;
	}

	@Column(name = "FF031", precision = 50, scale = 1)
	public Double getFf031() {
		return this.ff031;
	}

	public void setFf031(Double ff031) {
		this.ff031 = ff031;
	}

	@Column(name = "FF032", precision = 50, scale = 1)
	public Double getFf032() {
		return this.ff032;
	}

	public void setFf032(Double ff032) {
		this.ff032 = ff032;
	}

	@Column(name = "FF033", precision = 50, scale = 1)
	public Double getFf033() {
		return this.ff033;
	}

	public void setFf033(Double ff033) {
		this.ff033 = ff033;
	}

	@Column(name = "FF034", precision = 50, scale = 1)
	public Double getFf034() {
		return this.ff034;
	}

	public void setFf034(Double ff034) {
		this.ff034 = ff034;
	}

	@Column(name = "FF035", precision = 50, scale = 1)
	public Double getFf035() {
		return this.ff035;
	}

	public void setFf035(Double ff035) {
		this.ff035 = ff035;
	}

	@Column(name = "FF036", precision = 50, scale = 1)
	public Double getFf036() {
		return this.ff036;
	}

	public void setFf036(Double ff036) {
		this.ff036 = ff036;
	}

	@Column(name = "FF391", precision = 18, scale = 3)
	public Double getFf391() {
		return this.ff391;
	}

	public void setFf391(Double ff391) {
		this.ff391 = ff391;
	}

	@Column(name = "FF392", precision = 18, scale = 3)
	public Double getFf392() {
		return this.ff392;
	}

	public void setFf392(Double ff392) {
		this.ff392 = ff392;
	}

	@Column(name = "FF393", precision = 18, scale = 3)
	public Double getFf393() {
		return this.ff393;
	}

	public void setFf393(Double ff393) {
		this.ff393 = ff393;
	}

	@Column(name = "FF394", precision = 18, scale = 3)
	public Double getFf394() {
		return this.ff394;
	}

	public void setFf394(Double ff394) {
		this.ff394 = ff394;
	}

	@Column(name = "FF395", precision = 18, scale = 3)
	public Double getFf395() {
		return this.ff395;
	}

	public void setFf395(Double ff395) {
		this.ff395 = ff395;
	}

	@Column(name = "FF396", precision = 18, scale = 3)
	public Double getFf396() {
		return this.ff396;
	}

	public void setFf396(Double ff396) {
		this.ff396 = ff396;
	}

	@Column(name = "VT", precision = 10, scale = 3)
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