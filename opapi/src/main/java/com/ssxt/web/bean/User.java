package com.ssxt.web.bean;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * User entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "User", schema = "dbo", catalog = "swsq")
public class User implements java.io.Serializable {

	// Fields

	private Integer id;

	private String name;
	private String account;
	private String password;
	private String remark;
	private Timestamp createTime;
	private String systemRole;
	private String phone;

	// Constructors

	/** default constructor */
	public User() {
	}

	/** minimal constructor */
	public User(Integer id, String name, String account, String password, String systemRole) {
		this.id = id;
		this.name = name;
		this.account = account;
		this.password = password;
		this.systemRole = systemRole;
	}

	/** full constructor */
	public User(Integer id, String name, String account, String password, String remark, Timestamp createTime,
			String systemRole, String phone) {
		this.id = id;

		this.name = name;
		this.account = account;
		this.password = password;
		this.remark = remark;
		this.createTime = createTime;
		this.systemRole = systemRole;
		this.phone = phone;

	}

	// Property accessors
	@Id
	@Column(name = "Id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "Name", nullable = false)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "Account", nullable = false)
	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	@Column(name = "Password", nullable = false)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "Remark")
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "CreateTime", length = 23)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "SystemRole", nullable = false)
	public String getSystemRole() {
		return this.systemRole;
	}

	public void setSystemRole(String systemRole) {
		this.systemRole = systemRole;
	}

	@Column(name = "Phone")
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}