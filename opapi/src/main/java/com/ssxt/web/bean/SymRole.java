package com.ssxt.web.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * SymRole entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sym_role", catalog = "hebei")
public class SymRole implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private Integer createUserId;
	private Date createTime;
	private String indexUrl;
	private int isOperation;
	private String distinguish;

	/** default constructor */
	public SymRole() {
	}

	/** minimal constructor */
	public SymRole(Integer id, String name, Date createTime) {
		this.id = id;
		this.name = name;
		this.createTime = createTime;
	}

	/** full constructor */
	public SymRole(Integer id, String name, Integer createUserId, Date createTime, String indexUrl, int isOperation,
			String distinguish) {
		this.id = id;
		this.name = name;
		this.createUserId = createUserId;
		this.createTime = createTime;
		this.indexUrl = indexUrl;
		this.isOperation = isOperation;
		this.distinguish = distinguish;
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

	@Column(name = "name", nullable = false, length = 45)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "createUserId")
	public Integer getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	@Column(name = "createTime", nullable = false, length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "indexUrl", length = 50)
	public String getIndexUrl() {
		return this.indexUrl;
	}

	public void setIndexUrl(String indexUrl) {
		this.indexUrl = indexUrl;
	}

	@Column(name = "isOperation", length = 2)
	public int getIsOperation() {
		return isOperation;
	}

	public void setIsOperation(int isOperation) {
		this.isOperation = isOperation;
	}

	@Column(name = "distinguish", length = 10)
	public String getDistinguish() {
		return distinguish;
	}

	public void setDistinguish(String distinguish) {
		this.distinguish = distinguish;
	}

}