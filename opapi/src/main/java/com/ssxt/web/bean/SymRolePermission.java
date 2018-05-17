package com.ssxt.web.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * SymRolePermission entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sym_role_permission", catalog = "hebei")
public class SymRolePermission implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer roleId;
	private Integer menuId;

	// Constructors

	/** default constructor */
	public SymRolePermission() {
	}

	/** minimal constructor */
	public SymRolePermission(Integer id, Integer roleId) {
		this.id = id;
		this.roleId = roleId;
	}

	/** full constructor */
	public SymRolePermission(Integer id, Integer roleId, Integer menuId) {
		this.id = id;
		this.roleId = roleId;
		this.menuId = menuId;
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

	@Column(name = "roleId", nullable = false)
	public Integer getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	@Column(name = "menuId")
	public Integer getMenuId() {
		return this.menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

}