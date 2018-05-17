package com.ssxt.web.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * SymAddvcdUser entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sym_addvcd_user", catalog = "hebei")
public class SymAddvcdUser implements java.io.Serializable {

	// Fields

	private Integer id;
	private String addvcd;
	private Integer userId;

	// Constructors

	/** default constructor */
	public SymAddvcdUser() {
	}

	/** minimal constructor */
	public SymAddvcdUser(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public SymAddvcdUser(Integer id, String addvcd, Integer userId) {
		this.id = id;
		this.addvcd = addvcd;
		this.userId = userId;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "addvcd", length = 6)
	public String getAddvcd() {
		return this.addvcd;
	}

	public void setAddvcd(String addvcd) {
		this.addvcd = addvcd;
	}

	@Column(name = "userId")
	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}