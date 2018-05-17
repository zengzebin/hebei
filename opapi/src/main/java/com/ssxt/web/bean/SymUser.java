package com.ssxt.web.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * SymUser entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sym_user", catalog = "hebei")
public class SymUser implements java.io.Serializable {

	// Fields

	private Integer id;
	private String loginId;
	@JSONField(serialize = false)
	private String passWord;
	private Integer roleId;
	private String name;
	private Integer superiorId;
	private Integer createUserId;
	private Date createTime;

	private String phone;
	private String addvcd;
	private Integer isApp;
	private String address;
	private String position;
	private Integer age;
	private Integer working;
	private String directlyUnder;
	private Integer isEnable;
	private String imagePath;
	private String imageUrl;

	// @JSONField(serialize = false)
	// private Set<SymAddvcdUser> symAddvcdUsers = new
	// HashSet<SymAddvcdUser>(0);

	/** default constructor */
	public SymUser() {
	}

	/** minimal constructor */
	public SymUser(Integer id, String loginId, Integer roleId, String name, Date createTime) {
		this.id = id;
		this.loginId = loginId;
		this.roleId = roleId;
		this.name = name;
		this.createTime = createTime;
	}

	/** full constructor */
	public SymUser(Integer id, String loginId, String passWord, Integer roleId, String name, Integer superiorId,
			Integer createUserId, Date createTime, Integer isEnable, String phone, String addvcd, Integer isApp,
			String imagePath, String imageUrl) {
		this.id = id;
		this.loginId = loginId;
		this.passWord = passWord;
		this.roleId = roleId;
		this.name = name;
		this.superiorId = superiorId;
		this.createUserId = createUserId;
		this.createTime = createTime;
		this.isEnable = isEnable;
		this.phone = phone;
		this.addvcd = addvcd;
		this.isApp = isApp;
		this.imagePath = imagePath;
		this.imageUrl = imageUrl;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "loginId", nullable = false, length = 45)
	public String getLoginId() {
		return this.loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	@Column(name = "passWord", length = 200)
	public String getPassWord() {
		return this.passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	@Column(name = "roleId", nullable = false)
	public Integer getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	@Column(name = "name", nullable = false, length = 45)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "superiorId")
	public Integer getSuperiorId() {
		return this.superiorId;
	}

	public void setSuperiorId(Integer superiorId) {
		this.superiorId = superiorId;
	}

	@Column(name = "createUserId")
	public Integer getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	@Column(name = "createTime", nullable = false, length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "phone", length = 20)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "addvcd", length = 500)
	public String getAddvcd() {
		return this.addvcd;
	}

	public void setAddvcd(String addvcd) {
		this.addvcd = addvcd;
	}

	@Column(name = "isApp")
	public Integer getIsApp() {
		return this.isApp;
	}

	public void setIsApp(Integer isApp) {
		this.isApp = isApp;
	}

	// @OneToMany(cascade = { CascadeType.REMOVE, CascadeType.REFRESH,
	// CascadeType.PERSIST })
	// @JoinColumn(name = "userId")
	// public Set<SymAddvcdUser> getSymAddvcdUsers() {
	// return symAddvcdUsers;
	// }
	//
	// public void setSymAddvcdUsers(Set<SymAddvcdUser> symAddvcdUsers) {
	// this.symAddvcdUsers = symAddvcdUsers;
	// }

	@Column(name = "address", length = 50)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "position", length = 10)
	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	@Column(name = "age")
	public Integer getAge() {
		return this.age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Column(name = "working")
	public Integer getWorking() {
		return this.working;
	}

	public void setWorking(Integer working) {
		this.working = working;
	}

	@Column(name = "directlyUnder", length = 20)
	public String getDirectlyUnder() {
		return this.directlyUnder;
	}

	public void setDirectlyUnder(String directlyUnder) {
		this.directlyUnder = directlyUnder;
	}

	@Column(name = "isEnable")
	public Integer getIsEnable() {
		return this.isEnable;
	}

	public void setIsEnable(Integer isEnable) {
		this.isEnable = isEnable;
	}

	@Column(name = "imagePath")
	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	@Column(name = "imageUrl")
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

}