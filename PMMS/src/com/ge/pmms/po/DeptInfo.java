/**
 * ============================================================
 * File : DeptInfo.java
 * Description : 
 * 
 * Package : com.ge.pmms.po
 * Author : iGATE
 * Last Edited By :
 * Version : 1.0
 * Created on : Feb 5, 2015
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


/*******************************************************************************
 *
 * @Author 		: iGATE
 * @Version 	: 1.0
 * @Date Created: Feb 5, 2015
 * @Date Modified : 
 * @Modified By : 
 * @Contact 	:
 * @Description : 
 * @History		:
 *
 ******************************************************************************/
@Entity(name="deptInfo")
@Table(name="dept_info")
public class DeptInfo {
	private int id;
	private String deptId;
	private String deptNm;
	
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
	 * @return the deptId
	 */
	@Column(name="DEPT_ID")
	public String getDeptId() {
		return deptId;
	}
	
	/**
	 * @param deptId the deptId to set
	 */
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	
	/**
	 * @return the deptNm
	 */
	@Column(name="DEPT_NAME")
	public String getDeptNm() {
		return deptNm;
	}
	
	/**
	 * @param deptNm the deptNm to set
	 */
	public void setDeptNm(String deptNm) {
		this.deptNm = deptNm;
	}

}
