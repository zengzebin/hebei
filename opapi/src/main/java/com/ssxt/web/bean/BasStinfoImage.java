package com.ssxt.web.bean;

 
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BasStinfoImage entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "bas_stinfo_image", catalog = "hebei")
public class BasStinfoImage implements java.io.Serializable {

	// Fields

	private Integer id;
	private String stcd;
	private String type;
	private String fileName;
	private String file;
	private String url;
	private Double size;
	private Integer createUserId;
	private Date createTime;

	// Constructors

	/** default constructor */
	public BasStinfoImage() {
	}

	/** minimal constructor */
	public BasStinfoImage(String stcd) {
		this.stcd = stcd;
	}

	/** full constructor */
	public BasStinfoImage(String stcd, String type, String fileName, String file, String url, Double size,
			Integer createUserId, Date createTime) {
		this.stcd = stcd;
		this.type = type;
		this.fileName = fileName;
		this.file = file;
		this.url = url;
		this.size = size;
		this.createUserId = createUserId;
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

	@Column(name = "stcd", nullable = false, length = 10)
	public String getStcd() {
		return this.stcd;
	}

	public void setStcd(String stcd) {
		this.stcd = stcd;
	}

	@Column(name = "type", length = 10)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "fileName", length = 50)
	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(name = "file", length = 200)
	public String getFile() {
		return this.file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	@Column(name = "url", length = 100)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "size", precision = 10)
	public Double getSize() {
		return this.size;
	}

	public void setSize(Double size) {
		this.size = size;
	}

	@Column(name = "createUserId")
	public Integer getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	@Column(name = "createTime", length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}