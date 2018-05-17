package com.ssxt.web.bean;

// default package

 
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BasClassRoom entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "bas_class_room", catalog = "hebei")
public class BasClassRoom implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private String author;
	private String type;
	private Integer clickNum;
	private String fileName;
	private String file;
	private String url;
	private double size;
	private Integer createUserId;
	private Date createTime;
	private Integer modifyUserId;
	private Date modifyTime;

	// Constructors

	/** default constructor */
	public BasClassRoom() {
	}

	/** full constructor */
	public BasClassRoom(String name, String author, String type, Integer clickNum, String fileName, String file,
			String url, double size, Integer createUserId, Date createTime, Integer modifyUserId,
			Date modifyTime) {
		this.name = name;
		this.author = author;
		this.type = type;
		this.clickNum = clickNum;
		this.fileName = fileName;
		this.file = file;
		this.url = url;
		this.size = size;
		this.createUserId = createUserId;
		this.createTime = createTime;
		this.modifyUserId = modifyUserId;
		this.modifyTime = modifyTime;
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

	@Column(name = "name", length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "author", length = 20)
	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Column(name = "type", length = 10)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "clickNum")
	public Integer getClickNum() {
		return this.clickNum;
	}

	public void setClickNum(Integer clickNum) {
		this.clickNum = clickNum;
	}

	@Column(name = "fileName", length = 20)
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

	@Column(name = "size")
	public double getSize() {
		return this.size;
	}

	public void setSize(double size) {
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

	@Column(name = "modifyUserId")
	public Integer getModifyUserId() {
		return this.modifyUserId;
	}

	public void setModifyUserId(Integer modifyUserId) {
		this.modifyUserId = modifyUserId;
	}

	@Column(name = "modifyTime", length = 19)
	public Date getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

}