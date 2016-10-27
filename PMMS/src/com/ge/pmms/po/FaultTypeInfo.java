/**
 * ============================================================
 * File : FaultTypeInfo.java
 * Description : 
 * 
 * Package : com.ge.pmms.po
 * Author : iGATE
 * Last Edited By :
 * Version : 1.0
 * Created on : Apr 16, 2015
 * History
 * Modified By : Initial Release
 * Classification : GE Confidential
 * Copyright (C) 2015 General Electric Company. All rights reserved
 *
 * ============================================================
 */

package com.ge.pmms.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/*******************************************************************************
 *
 * @Author 		: iGATE
 * @Version 	: 1.0
 * @Date Created: Apr 16, 2015
 * @Date Modified : 
 * @Modified By : 
 * @Contact 	:
 * @Description : 
 * @History		:
 *
 ******************************************************************************/
@Entity(name="faultTypeInfo")
@Table(name="FAULT_TYPE_INFO")
public class FaultTypeInfo {
	private int id;               //主键ID
	private String faultId;   //故障种类ID
	private String faultNM;   //故障种类名称
	
	@Column(name="ID")
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	@Column(name="FAULT_ID")
	public String getFaultId() {
		return faultId;
	}

	
	public void setFaultId(String faultId) {
		this.faultId = faultId;
	}

	@Column(name="FAULT_NAME")
	public String getFaultNM() {
		return faultNM;
	}

	
	public void setFaultNM(String faultNM) {
		this.faultNM = faultNM;
	}

}
	
