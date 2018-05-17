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
 * BasProblemAnswer entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "bas_problem_answer", catalog = "hebei")
public class BasProblemAnswer implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer problemId;
	private String answer;
	private Integer createUserId;
	private Date createTime;

	// Constructors

	/** default constructor */
	public BasProblemAnswer() {
	}

	/** full constructor */
	public BasProblemAnswer(Integer problemId, String answer, Integer createUserId, Date createTime) {
		this.problemId = problemId;
		this.answer = answer;
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

	@Column(name = "problemId")
	public Integer getProblemId() {
		return this.problemId;
	}

	public void setProblemId(Integer problemId) {
		this.problemId = problemId;
	}

	@Column(name = "answer", length = 65535)
	public String getAnswer() {
		return this.answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
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