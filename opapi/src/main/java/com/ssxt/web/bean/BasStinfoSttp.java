package com.ssxt.web.bean;
// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BasStinfoSttp entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "bas_stinfo_sttp", catalog = "hebei")
public class BasStinfoSttp implements java.io.Serializable {

	// Fields

	private Integer id;
	private String stcd;
	private String sttp;

	// Constructors

	/** default constructor */
	public BasStinfoSttp() {
	}

	/** full constructor */
	public BasStinfoSttp(String stcd, String sttp) {
		this.stcd = stcd;
		this.sttp = sttp;
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

	@Column(name = "STCD", length = 20)
	public String getStcd() {
		return this.stcd;
	}

	public void setStcd(String stcd) {
		this.stcd = stcd;
	}

	@Column(name = "STTP", length = 20)
	public String getSttp() {
		return this.sttp;
	}

	public void setSttp(String sttp) {
		this.sttp = sttp;
	}

}