package com.ssxt.web.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * SymRoleUser entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sym_role_user", catalog = "hebei")
public class SymRoleUser implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer roleId;
	private Integer userId;

	// Constructors

	/** default constructor */
	public SymRoleUser() {
	}

	/** minimal constructor */
	public SymRoleUser(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public SymRoleUser(Integer id, Integer roleId, Integer userId) {
		this.id = id;
		this.roleId = roleId;
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

	@Column(name = "roleId")
	public Integer getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	@Column(name = "userId")
	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}