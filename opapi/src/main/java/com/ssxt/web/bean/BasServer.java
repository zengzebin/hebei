package com.ssxt.web.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BasServer entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "bas_server", catalog = "hebei")
public class BasServer implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private String addvcd;
	private String ip;
	private Integer port;

	// Constructors

	/** default constructor */
	public BasServer() {
	}

	/** full constructor */
	public BasServer(String name, String addvcd, String ip, Integer port) {
		this.name = name;
		this.addvcd = addvcd;
		this.ip = ip;
		this.port = port;

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

	@Column(name = "name", length = 10)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "addvcd", length = 10)
	public String getAddvcd() {
		return this.addvcd;
	}

	public void setAddvcd(String addvcd) {
		this.addvcd = addvcd;
	}

	@Column(name = "ip", length = 20)
	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Column(name = "port")
	public Integer getPort() {
		return this.port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

}