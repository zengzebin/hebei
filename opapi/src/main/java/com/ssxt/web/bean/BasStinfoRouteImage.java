package com.ssxt.web.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BasStinfoRouteImage entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "bas_stinfo_route_image", catalog = "hebei")
public class BasStinfoRouteImage implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer procId;
	private String fileName;
	private String path;
	private String url;
	private String type;
	private Double size;

	// Constructors

	/** default constructor */
	public BasStinfoRouteImage() {
	}

	/** full constructor */
	public BasStinfoRouteImage(Integer procId, String fileName, String path,
			String url, String type, Double size) {
		this.procId = procId;
		this.fileName = fileName;
		this.path = path;
		this.url = url;
		this.type = type;
		this.size = size;
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

	@Column(name = "procId")
	public Integer getProcId() {
		return this.procId;
	}

	public void setProcId(Integer procId) {
		this.procId = procId;
	}

	@Column(name = "fileName", length = 50)
	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(name = "path")
	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Column(name = "url")
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "type", length = 10)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "size", precision = 10)
	public Double getSize() {
		return this.size;
	}

	public void setSize(Double size) {
		this.size = size;
	}

}