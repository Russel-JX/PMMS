/**
 * ============================================================
 * File : UserInfo.java
 * Description : 
 * 
 * Package : com.ge.pmms.po
 * Author : iGATE
 * Last Edited By :
 * Version : 1.0
 * Created on : Feb 11, 2015
 * History
 * Modified By : Initial Release
 * Classification : GE Confidential
 * Copyright (C) 2015 General Electric Company. All rights reserved
 *
 * ============================================================
 */

package com.ge.pmms.po;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


/*******************************************************************************
 *
 * @Author 		: iGATE
 * @Version 	: 1.0
 * @Date Created: Feb 11, 2015
 * @Date Modified : 
 * @Modified By : 
 * @Contact 	:
 * @Description : 
 * @History		:
 *
 ******************************************************************************/
@Entity(name="UserInfo")
@Table(name="Users")
public class UserInfo {
	private int id;
	private String sso;
	private String pwd;
	private String name;
//	private String lastName;
	private String role;
	private String contractNo;
	private String emailId;
	private String isActive;
	private String roleNm;
	/**
	 * @return the id
	 */
	@Id 
	@GeneratedValue(strategy=IDENTITY)
	public int getId() {
		return id;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * @return the sso
	 */
	@Column(name="SSO")
	public String getSso() {
		return sso;
	}
	
	/**
	 * @param sso the sso to set
	 */
	public void setSso(String sso) {
		this.sso = sso;
	}
	
	/**
	 * @return the pwd
	 */
	@Column(name="Password")
	public String getPwd() {
		return pwd;
	}
	
	/**
	 * @param pwd the pwd to set
	 */
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	
	
	/**
	 * @return the name
	 */
	@Column(name="Name")
	public String getName() {
		return name;
	}

	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the role
	 */
	@Column(name="Role")
	public String getRole() {
		return role;
	}
	
	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}
	
	/**
	 * @return the contractNo
	 */
	@Column(name="ContactNo")
	public String getContractNo() {
		return contractNo;
	}
	
	/**
	 * @param contractNo the contractNo to set
	 */
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	
	/**
	 * @return the emailId
	 */
	@Column(name="EmailId")
	public String getEmailId() {
		return emailId;
	}
	
	/**
	 * @param emailId the emailId to set
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	
	/**
	 * @return the isActive
	 */
	@Column(name="IS_ACTIVE")
	public String getIsActive() {
		return isActive;
	}

	
	/**
	 * @param isActive the isActive to set
	 */
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	
	/**
	 * @return the roleNm
	 */
	@Transient
	public String getRoleNm() {
		return roleNm;
	}

	
	/**
	 * @param roleNm the roleNm to set
	 */
	public void setRoleNm(String roleNm) {
		this.roleNm = roleNm;
	}
	
	
}
