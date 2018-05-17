package com.ssxt.web.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * SymMenu entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sym_menu", catalog = "hebei")
public class SymMenu implements java.io.Serializable {

	// Fields

	private Integer menuId;
	private String menuText;
	private String menuIcon;
	private String menuHref;
	private Integer parentId;
	private Integer haveChild;
	private Integer sort;

	// Constructors

	/** default constructor */
	public SymMenu() {
	}

	/** minimal constructor */
	public SymMenu(Integer menuId) {
		this.menuId = menuId;
	}

	/** full constructor */
	public SymMenu(Integer menuId, String menuText, String menuIcon, String menuHref, Integer parentId,
			Integer haveChild, Integer sort) {
		this.menuId = menuId;
		this.menuText = menuText;
		this.menuIcon = menuIcon;
		this.menuHref = menuHref;
		this.parentId = parentId;
		this.haveChild = haveChild;
		this.sort = sort;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	public Integer getMenuId() {
		return this.menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	@Column(name = "menuText", length = 100)
	public String getMenuText() {
		return this.menuText;
	}

	public void setMenuText(String menuText) {
		this.menuText = menuText;
	}

	@Column(name = "menuIcon", length = 100)
	public String getMenuIcon() {
		return this.menuIcon;
	}

	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}

	@Column(name = "menuHref", length = 100)
	public String getMenuHref() {
		return this.menuHref;
	}

	public void setMenuHref(String menuHref) {
		this.menuHref = menuHref;
	}

	@Column(name = "parentId", length = 50)
	public Integer getParentId() {
		return this.parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	@Column(name = "haveChild")
	public Integer getHaveChild() {
		return this.haveChild;
	}

	public void setHaveChild(Integer haveChild) {
		this.haveChild = haveChild;
	}

	@Column(name = "sort")
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

}