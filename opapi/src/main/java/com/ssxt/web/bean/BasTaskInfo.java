package com.ssxt.web.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * BasTaskInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "bas_task_info", catalog = "hebei")
public class BasTaskInfo implements java.io.Serializable {

	// Fields

	private String taskNo;
	private Integer stionId;
	private String addvcd;
	private String content;
	private Integer createUserId;
	private String currentNode;
	private Integer taskStatus;
	private String taskStatusName;
	private Integer maintUserId;
	private Date reciveTime;
	private Date startTime;
	private Date dealTime;
	private Date dealFinished;
	private Date finishTime;
	private Date modifyTime;
	private Integer priorityStatus;
	private String priorityName;
	private Integer updateCheck;
	private Integer updateMaint;
	private Integer errorCode;
	private Date createTime;
	private String helpPersonnel;
	@JSONField(serialize = false)
	private Set<BasTaskProcInfo> basTaskProcInfos = new HashSet<BasTaskProcInfo>(0);

	@JSONField(serialize = false)
	private Set<BasTaskHelp> basTaskHelps = new HashSet<BasTaskHelp>(0);

	// Constructors

	/** default constructor */
	public BasTaskInfo() {
	}

	/** full constructor */
	public BasTaskInfo(Integer stionId, String addvcd, String content, Integer createUserId, String currentNode,
			Integer taskStatus, String taskStatusName, Integer maintUserId, Date reciveTime, Date startTime,
			Date dealTime, Date dealFinished, Date finishTime, Date modifyTime, Integer priorityStatus,
			String priorityName, Integer updateCheck, Integer updateMaint, Integer errorCode, Date createTime,
			String helpPersonnel) {
		this.stionId = stionId;
		this.addvcd = addvcd;
		this.content = content;
		this.createUserId = createUserId;
		this.currentNode = currentNode;
		this.taskStatus = taskStatus;
		this.taskStatusName = taskStatusName;
		this.maintUserId = maintUserId;
		this.reciveTime = reciveTime;
		this.startTime = startTime;
		this.dealTime = dealTime;
		this.dealFinished = dealFinished;
		this.finishTime = finishTime;
		this.modifyTime = modifyTime;
		this.priorityStatus = priorityStatus;
		this.priorityName = priorityName;
		this.updateCheck = updateCheck;
		this.updateMaint = updateMaint;
		this.errorCode = errorCode;
		this.createTime = createTime;
		this.helpPersonnel = helpPersonnel;
	}

	// Property accessors
	@Id
	@Column(name = "taskNo", unique = true, nullable = false, length = 10)
	public String getTaskNo() {
		return this.taskNo;
	}

	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}

	@Column(name = "stionId", length = 10)
	public Integer getStionId() {
		return this.stionId;
	}

	public void setStionId(Integer stionId) {
		this.stionId = stionId;
	}

	@Column(name = "addvcd", length = 6)
	public String getAddvcd() {
		return this.addvcd;
	}

	public void setAddvcd(String addvcd) {
		this.addvcd = addvcd;
	}

	@Column(name = "content")
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "createUserId")
	public Integer getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	@Column(name = "currentNode", length = 30)
	public String getCurrentNode() {
		return this.currentNode;
	}

	public void setCurrentNode(String currentNode) {
		this.currentNode = currentNode;
	}

	@Column(name = "taskStatus")
	public Integer getTaskStatus() {
		return this.taskStatus;
	}

	public void setTaskStatus(Integer taskStatus) {
		this.taskStatus = taskStatus;
	}

	@Column(name = "taskStatusName", length = 100)
	public String getTaskStatusName() {
		return this.taskStatusName;
	}

	public void setTaskStatusName(String taskStatusName) {
		this.taskStatusName = taskStatusName;
	}

	@Column(name = "maintUserId")
	public Integer getMaintUserId() {
		return this.maintUserId;
	}

	public void setMaintUserId(Integer maintUserId) {
		this.maintUserId = maintUserId;
	}

	@Column(name = "reciveTime", length = 19)
	public Date getReciveTime() {
		return this.reciveTime;
	}

	public void setReciveTime(Date reciveTime) {
		this.reciveTime = reciveTime;
	}

	@Column(name = "startTime", length = 19)
	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Column(name = "dealTime", length = 19)
	public Date getDealTime() {
		return this.dealTime;
	}

	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}

	@Column(name = "dealFinished", length = 19)
	public Date getDealFinished() {
		return this.dealFinished;
	}

	public void setDealFinished(Date dealFinished) {
		this.dealFinished = dealFinished;
	}

	@Column(name = "finishTime", length = 19)
	public Date getFinishTime() {
		return this.finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	@Column(name = "modifyTime", length = 19)
	public Date getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	@Column(name = "priorityStatus")
	public Integer getPriorityStatus() {
		return this.priorityStatus;
	}

	public void setPriorityStatus(Integer priorityStatus) {
		this.priorityStatus = priorityStatus;
	}

	@Column(name = "priorityName")
	public String getPriorityName() {
		return this.priorityName;
	}

	public void setPriorityName(String priorityName) {
		this.priorityName = priorityName;
	}

	@Column(name = "updateCheck")
	public Integer getUpdateCheck() {
		return this.updateCheck;
	}

	public void setUpdateCheck(Integer updateCheck) {
		this.updateCheck = updateCheck;
	}

	@Column(name = "updateMaint")
	public Integer getUpdateMaint() {
		return this.updateMaint;
	}

	public void setUpdateMaint(Integer updateMaint) {
		this.updateMaint = updateMaint;
	}

	@Column(name = "errorCode")
	public Integer getErrorCode() {
		return this.errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	@Column(name = "createTime", length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "helpPersonnel", length = 50)
	public String getHelpPersonnel() {
		return this.helpPersonnel;
	}

	public void setHelpPersonnel(String helpPersonnel) {
		this.helpPersonnel = helpPersonnel;
	}

	@OneToMany(cascade = { CascadeType.REMOVE, CascadeType.REFRESH, CascadeType.PERSIST })
	@JoinColumn(name = "taskNo")
	public Set<BasTaskProcInfo> getBasTaskProcInfos() {
		return this.basTaskProcInfos;
	}

	public void setBasTaskProcInfos(Set<BasTaskProcInfo> basTaskProcInfos) {
		this.basTaskProcInfos = basTaskProcInfos;
	}

	@OneToMany(cascade = { CascadeType.REMOVE, CascadeType.REFRESH, CascadeType.PERSIST })
	@JoinColumn(name = "taskNo")
	public Set<BasTaskHelp> getBasTaskHelps() {
		return basTaskHelps;
	}

	public void setBasTaskHelps(Set<BasTaskHelp> basTaskHelps) {
		this.basTaskHelps = basTaskHelps;
	}
}