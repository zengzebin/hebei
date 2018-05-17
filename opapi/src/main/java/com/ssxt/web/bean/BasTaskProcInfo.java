package com.ssxt.web.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * BasTaskProcInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "bas_task_proc_info", catalog = "hebei")
public class BasTaskProcInfo implements java.io.Serializable {

	// Fields

	private Integer id;
	private String taskNo;
	private String content;
	private Double longitude;
	private Double latitude;
	private Integer operaterId;
	private String operaterUserName;
	private Date operateTime;
	private String currentNode;
	private Set<BasTaskDealNode> basTaskDealNodes = new HashSet<BasTaskDealNode>(0);

	// Constructors

	/** default constructor */
	public BasTaskProcInfo() {
	}

	/** full constructor */
	public BasTaskProcInfo(String taskNo, String content, Double longitude, Double latitude, Integer operaterId,
			String operaterUserName, Date operateTime, String currentNode) {
		this.taskNo = taskNo;
		this.content = content;
		this.longitude = longitude;
		this.latitude = latitude;
		this.operaterId = operaterId;
		this.operaterUserName = operaterUserName;
		this.operateTime = operateTime;
		this.currentNode = currentNode;

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

	@Column(name = "taskNo", length = 10)
	public String getTaskNo() {
		return this.taskNo;
	}

	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}

	@Column(name = "content")
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "longitude", precision = 10, scale = 7)
	public Double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	@Column(name = "latitude", precision = 9, scale = 7)
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

	@Column(name = "operaterUserName")
	public String getOperaterUserName() {
		return this.operaterUserName;
	}

	public void setOperaterUserName(String operaterUserName) {
		this.operaterUserName = operaterUserName;
	}

	@Column(name = "operateTime", length = 19)
	public Date getOperateTime() {
		return this.operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	@Column(name = "currentNode", length = 30)
	public String getCurrentNode() {
		return this.currentNode;
	}

	public void setCurrentNode(String currentNode) {
		this.currentNode = currentNode;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "procId")
	@OrderBy("id asc")
	public Set<BasTaskDealNode> getBasTaskDealNodes() {
		return this.basTaskDealNodes;
	}

	public void setBasTaskDealNodes(Set<BasTaskDealNode> basTaskDealNodes) {
		this.basTaskDealNodes = basTaskDealNodes;
	}

}