package com.ssxt.web.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BasTaskDealNode entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "bas_task_deal_node", catalog = "hebei")
public class BasTaskDealNode implements java.io.Serializable {

	// Fields

	private Integer id;
	private String taskNo;
	private String content;
	private Integer procId;
	private Integer dealType;
	private String path;
	private String url;
	private Double longitude;
	private Double latitude;
	private Integer operaterId;
	private String operateName;

	private Date operateTime;
	private Double size;
	private String fileName;

	// Constructors

	/** default constructor */
	public BasTaskDealNode() {
	}

	/** full constructor */
	public BasTaskDealNode(String taskNo, String content, Integer procId, Integer dealType, String path, String url,
			Double longitude, String operateName, Double latitude, Integer operaterId, Date operateTime, Double size,
			String fileName) {
		this.taskNo = taskNo;
		this.content = content;
		this.procId = procId;
		this.dealType = dealType;
		this.path = path;
		this.url = url;
		this.longitude = longitude;
		this.latitude = latitude;
		this.operaterId = operaterId;
		this.operateName = operateName;
		this.operateTime = operateTime;
		this.size = size;
		this.fileName = fileName;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "taskNo", length = 10)
	public String getTaskNo() {
		return this.taskNo;
	}

	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}

	@Column(name = "content", length = 100)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "procId")
	public Integer getProcId() {
		return this.procId;
	}

	public void setProcId(Integer procId) {
		this.procId = procId;
	}

	@Column(name = "dealType")
	public Integer getDealType() {
		return this.dealType;
	}

	public void setDealType(Integer dealType) {
		this.dealType = dealType;
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

	@Column(name = "longitude", precision = 9, scale = 7)
	public Double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	@Column(name = "latitude", precision = 10, scale = 7)
	public Double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	@Column(name = "operaterId")
	public Integer getOperaterId() {
		return this.operaterId;
	}

	public void setOperaterId(Integer operaterId) {
		this.operaterId = operaterId;
	}

	@Column(name = "operateName")
	public String getOperateName() {
		return operateName;
	}

	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}

	@Column(name = "operateTime", length = 19)
	public Date getOperateTime() {
		return this.operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	@Column(name = "size")
	public Double getSize() {
		return this.size;
	}

	public void setSize(Double size) {
		this.size = size;
	}

	@Column(name = "fileName", length = 20)
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}