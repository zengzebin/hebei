package com.ssxt.web.bean;

 
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BasServerSituation entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "bas_server_situation", catalog = "hebei")
public class BasServerSituation implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer serverId;
	private Integer state;
	private Date createTime;

	// Constructors

	/** default constructor */
	public BasServerSituation() {
	}

	/** minimal constructor */
	public BasServerSituation(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public BasServerSituation(Integer id, Integer serverId, Integer state,
			Date createTime) {
		this.id = id;
		this.serverId = serverId;
		this.state = state;
		this.createTime = createTime;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "serverId")
	public Integer getServerId() {
		return this.serverId;
	}

	public void setServerId(Integer serverId) {
		this.serverId = serverId;
	}

	@Column(name = "state")
	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Column(name = "createTime", length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}