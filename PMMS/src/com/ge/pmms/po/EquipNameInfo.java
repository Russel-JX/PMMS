/**
 * ============================================================
 * File : EquipmentNameInfo.java
 * Description : 
 * 
 * Package : com.ge.pmms.po
 * Author : iGATE
 * Last Edited By :
 * Version : 1.0
 * Created on : Jan 16, 2015
 * History
 * Modified By : Initial Release
 * Classification : GE Confidential
 * Copyright (C) 2015 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.pmms.po;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/*******************************************************************************
 *
 * @Author 		: iGATE
 * @Version 	: 1.0
 * @Date Created: Jan 16, 2015
 * @Date Modified : 
 * @Modified By : 
 * @Contact 	:
 * @Description : 
 * @History		:
 *
 ******************************************************************************/
@Entity(name="equipNmInfo")
@Table(name="EQUIP_NAME_INFO")
public class EquipNameInfo {

	private int id;
	private String equipmentNameId;
	private String equipmentName;
	private String creator;
	private Date dateTime=null;
	private String updater;
	private Date lastUpdatedDate=null;
	private String remark;
	private String equipType;
	
	
	/**
	 * @return the equipType
	 */
	@Column(name="EQUIP_TYPE")
	public String getEquipType() {
		return equipType;
	}

	
	/**
	 * @param equipType the equipType to set
	 */
	public void setEquipType(String equipType) {
		this.equipType = equipType;
	}

	@Id 
	@GeneratedValue(strategy=IDENTITY)
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	@Column(name="EQUIP_NAME_ID")
	public String getEquipmentNameId() {
		return equipmentNameId;
	}
	
	public void setEquipmentNameId(String equipmentNameId) {
		this.equipmentNameId = equipmentNameId;
	}
	@Column(name="EQUIP_NAME")
	public String getEquipmentName() {
		return equipmentName;
	}
	
	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}

	@Column(name="CREATOR")
	public String getCreator() {
		return creator;
	}
	
	public void setCreator(String creator) {
		this.creator = creator;
	}
	@Column(name="CREATED_DATE")
	public Date getDateTime() {
		//return dateTime;
		if(null != dateTime){
			return new Date(dateTime.getTime());
		}
		return null;
	}
	
	public void setDateTime(Date dateTime) {
		//this.dateTime = dateTime;
		if(null != dateTime){
			this.dateTime = new Date(dateTime.getTime());
		}
	}
	@Column(name="UPDATER")
	public String getUpdater() {
		return updater;
	}
	
	public void setUpdater(String updater) {
		this.updater = updater;
	}
	@Column(name="LAST_UPDATED_DATE")
	public Date getLastUpdatedDate() {
		//return lastUpdatedDate;
		if(null != lastUpdatedDate){
			return new Date(lastUpdatedDate.getTime());
		}
		return null;
	}
	
	public void setLastUpdatedDate(Date lastUpdatedDate) {
		//this.lastUpdatedDate = lastUpdatedDate;
		if(null != lastUpdatedDate){
			this.lastUpdatedDate = new Date(lastUpdatedDate.getTime());
		}
	}
	@Column(name="REMARK")
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
