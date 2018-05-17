package com.ssxt.web.bean.warning;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BasWarning entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "bas_warning", catalog = "hebei")
public class BasWarning implements java.io.Serializable {

	// Fields

	private Integer id;
	private String code;
	private Date processTime;
	private Double processValue;
	private Integer processerId;
	private String remark;
	private Integer status;
	private Date time;
	private String type;
	private Double value;

	// Constructors

	/** default constructor */
	public BasWarning() {
	}

	/** minimal constructor */
	public BasWarning(Integer id, String code, String remark, Integer status, Date time, String type, Double value) {
		this.id = id;
		this.code = code;
		this.remark = remark;
		this.status = status;
		this.time = time;
		this.type = type;
		this.value = value;
	}

	/** full constructor */
	public BasWarning(Integer id, String code, Date processTime, Double processValue, Integer processerId,
			String remark, Integer status, Date time, String type, Double value) {
		this.id = id;
		this.code = code;
		this.processTime = processTime;
		this.processValue = processValue;
		this.processerId = processerId;
		this.remark = remark;
		this.status = status;
		this.time = time;
		this.type = type;
		this.value = value;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "Code", nullable = false, length = 8)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "ProcessTime", length = 19)
	public Date getProcessTime() {
		return this.processTime;
	}

	public void setProcessTime(Date processTime) {
		this.processTime = processTime;
	}

	@Column(name = "ProcessValue", precision = 22, scale = 0)
	public Double getProcessValue() {
		return this.processValue;
	}

	public void setProcessValue(Double processValue) {
		this.processValue = processValue;
	}

	@Column(name = "ProcesserId")
	public Integer getProcesserId() {
		return this.processerId;
	}

	public void setProcesserId(Integer processerId) {
		this.processerId = processerId;
	}

	@Column(name = "Remark", nullable = false, length = 50)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "Status", nullable = false)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "Time", nullable = false, length = 19)
	public Date getTime() {
		return this.time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	@Column(name = "Type", nullable = false, length = 50)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "Value", precision = 22, scale = 0)
	public Double getValue() {
		return this.value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

}