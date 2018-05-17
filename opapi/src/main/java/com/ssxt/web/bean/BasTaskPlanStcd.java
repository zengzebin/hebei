package com.ssxt.web.bean;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * BasTaskPlanStcd entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "bas_task_plan_stcd", catalog = "hebei")
public class BasTaskPlanStcd implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer planId;
	private String stcd;
	private String name;
 
	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private Set<BasTaskPlanDevice> basTaskPlanDevices = new HashSet<BasTaskPlanDevice>(0);

	@OneToMany(cascade = { CascadeType.REMOVE, CascadeType.REFRESH, CascadeType.PERSIST })
	@JoinColumn(name = "parentId")
	@OrderBy("id asc")
	public Set<BasTaskPlanDevice> getBasTaskPlanDevices() {
		return basTaskPlanDevices;
	}

	public void setBasTaskPlanDevices(Set<BasTaskPlanDevice> basTaskPlanDevices) {
		this.basTaskPlanDevices = basTaskPlanDevices;
	}

	/** default constructor */
	public BasTaskPlanStcd() {
	}

	/** full constructor */
	public BasTaskPlanStcd(Integer planId, String stcd) {
		this.planId = planId;
		this.stcd = stcd;
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

	@Column(name = "planId")
	public Integer getPlanId() {
		return this.planId;
	}

	public void setPlanId(Integer planId) {
		this.planId = planId;
	}

	@Column(name = "stcd", length = 20)
	public String getStcd() {
		return this.stcd;
	}

	public void setStcd(String stcd) {
		this.stcd = stcd;
	}

}