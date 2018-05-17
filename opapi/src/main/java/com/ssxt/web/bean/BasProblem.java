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
import javax.persistence.Table;

/**
 * BasProblem entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "bas_problem", catalog = "hebei")
public class BasProblem implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer clickNum;
	private String title;
	private String problem;
	private Integer createUserId;
	private Date createTime;

	private Set<BasProblemAnswer> basProblemAnswers = new HashSet<BasProblemAnswer>(0);

	// Constructors

	@OneToMany(cascade = { CascadeType.REMOVE, CascadeType.REFRESH, CascadeType.PERSIST })
	@JoinColumn(name = "problemId")
	public Set<BasProblemAnswer> getBasProblemAnswers() {
		return basProblemAnswers;
	}

	public void setBasProblemAnswers(Set<BasProblemAnswer> basProblemAnswers) {
		this.basProblemAnswers = basProblemAnswers;
	}

	/** default constructor */
	public BasProblem() {
	}

	/** full constructor */
	public BasProblem(Integer clickNum, String title, String problem, Integer createUserId, Date createTime) {
		this.clickNum = clickNum;
		this.title = title;
		this.problem = problem;
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

	@Column(name = "clickNum")
	public Integer getClickNum() {
		return this.clickNum;
	}

	public void setClickNum(Integer clickNum) {
		this.clickNum = clickNum;
	}

	@Column(name = "title", length = 50)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "problem", length = 65535)
	public String getProblem() {
		return this.problem;
	}

	public void setProblem(String problem) {
		this.problem = problem;
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