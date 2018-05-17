package com.ssxt.web.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BasTaskHelp entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "bas_task_help", catalog = "hebei")
public class BasTaskHelp implements java.io.Serializable {

	// Fields

	private String taskNo;
	private Integer userId;

	// Constructors

	/** default constructor */
	public BasTaskHelp() {
	}

	/** minimal constructor */
	public BasTaskHelp(String taskNo) {
		this.taskNo = taskNo;
	}

	/** full constructor */
	public BasTaskHelp(String taskNo, Integer userId) {
		this.taskNo = taskNo;
		this.userId = userId;
	}

	// Property accessors
	
	@Id
	public String getTaskNo() {
		return this.taskNo;
	}

	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}

	@Column(name = "userId")
	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}