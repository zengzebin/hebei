package com.ssxt.web.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BasProblemGrade entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "bas_problem_grade", catalog = "hebei")
public class BasProblemGrade implements java.io.Serializable {

	// Fields

	private Integer userId;
	private Integer score;
	private int grade;
	private Date updateTime;

	// Constructors

	/** default constructor */
	public BasProblemGrade() {
	}

	/** minimal constructor */
	public BasProblemGrade(Integer userId) {
		this.userId = userId;
	}

	/** full constructor */
	public BasProblemGrade(Integer userId, Integer score, int grade, Date updateTime) {
		this.userId = userId;
		this.score = score;
		this.grade = grade;
		this.updateTime = updateTime;
	}

	// Property accessors
	@Id
	@Column(name = "userId", unique = true, nullable = false)
	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "score")
	public Integer getScore() {
		return this.score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	@Column(name = "grade", length = 50)
	public int getGrade() {
		return this.grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	@Column(name = "updateTime", length = 19)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}