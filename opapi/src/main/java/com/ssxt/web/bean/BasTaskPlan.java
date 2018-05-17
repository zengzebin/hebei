package com.ssxt.web.bean;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * BasTaskPlan entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "bas_task_plan", catalog = "hebei")
public class BasTaskPlan implements java.io.Serializable {

	// Fields

	private Integer id;
	private String addvcd;
	private String content;
	private Integer maintUserId;
	private String reflect;
	private Integer status;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date startTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date endTime;
	private Integer createUserId;
	private Date createTime;
	private Date modifyTime;
	private String stcds;

	private Set<BasTaskPlanStcd> basTaskPlanStcds = new HashSet<BasTaskPlanStcd>(0);

	@OneToMany(cascade = { CascadeType.REMOVE, CascadeType.REFRESH, CascadeType.PERSIST })
	@JoinColumn(name = "planId")
	@OrderBy("id asc")  
	public Set<BasTaskPlanStcd> getBasTaskPlanStcds() {
		return basTaskPlanStcds;
	}

	public void setBasTaskPlanStcds(Set<BasTaskPlanStcd> basTaskPlanStcds) {
		this.basTaskPlanStcds = basTaskPlanStcds;
	}

	// Constructors

	/** default constructor */
	public BasTaskPlan() {
	}

	/** full constructor */
	public BasTaskPlan(String addvcd, String content, Integer maintUserId, String reflect, Integer status,
			Date startTime, Date endTime, Integer createUserId, Date createTime, Date modifyTime, String stcds) {
		this.addvcd = addvcd;
		this.content = content;
		this.maintUserId = maintUserId;
		this.reflect = reflect;
		this.status = status;
		this.startTime = startTime;
		this.endTime = endTime;
		this.createUserId = createUserId;
		this.createTime = createTime;
		this.modifyTime = modifyTime;
		this.stcds = stcds;
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

	@Column(name = "addvcd", length = 11)
	public String getAddvcd() {
		return this.addvcd;
	}

	public void setAddvcd(String addvcd) {
		this.addvcd = addvcd;
	}

	@Column(name = "content", length = 30)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "maintUserId")
	public Integer getMaintUserId() {
		return this.maintUserId;
	}

	public void setMaintUserId(Integer maintUserId) {
		this.maintUserId = maintUserId;
	}

	@Column(name = "reflect", length = 200)
	public String getReflect() {
		return this.reflect;
	}

	public void setReflect(String reflect) {
		this.reflect = reflect;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "startTime", length = 19)
	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Column(name = "endTime", length = 19)
	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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

	@Column(name = "modifyTime", length = 19)
	public Date getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	@Column(name = "stcds", length = 50)
	public String getStcds() {
		return stcds;
	}

	public void setStcds(String stcds) {
		this.stcds = stcds;
	}

}