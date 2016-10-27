/**
 * ============================================================
 * File : WorkOrderMaintenanceInfo.java
 * Description : 
 * 
 * Package : com.ge.pmms.po
 * Author : iGATE
 * Last Edited By :
 * Version : 1.0
 * Created on : Jan 14, 2015
 * History
 * Modified By : Initial Release
 * Classification : GE Confidential
 * Copyright (C) 2015 General Electric Company. All rights reserved
 *
 * ============================================================
 */

package com.ge.pmms.po;

import java.util.Date;

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
 * @Date Created: Jan 14, 2015
 * @Date Modified : 
 * @Modified By : 
 * @Contact 	:
 * @Description : 
 * @History		:
 *
 ******************************************************************************/
@Entity(name="workOrderMaintenanceInfo")
@Table(name="WO_MAINT_INFO")
public class WorkOrderMaintenanceInfo {
	private int id;
	private String workOrderMaintanceId;        //工单维修编号
	private String workOrderId;			        //工单编号 **外键**
	private String mechianic;                   //维修人
	private String faultType;				    //故障种类
	private boolean sparePartInvolved;		    //是否关联备件
	private boolean externalServiceInvolved;    //是否等外部服务
	private Date createdDate;
	private Date maintanceStartDate = null;		//维修开始时间
	private Date maintanceEndDate = null;	    //维修结束时间
	private String updater;
	private Date lastUpdatedDate;
	private String remark;                      //故障种类补充信息
	private String operator;                    //关闭工单人员
	
	@Column(name="ID")
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	@Column(name="WO_MAINT_ID")
	public String getWorkOrderMaintanceId() {
		return workOrderMaintanceId;
	}
	
	public void setWorkOrderMaintanceId(String workOrderMaintanceId) {
		this.workOrderMaintanceId = workOrderMaintanceId;
	}
	@Column(name="WO_ID")
	public String getWorkOrderId() {
		return workOrderId;
	}
	
	public void setWorkOrderId(String workOrderId) {
		this.workOrderId = workOrderId;
	}
	@Column(name="MECHANIC")
	public String getMechianic() {
		return mechianic;
	}
	
	public void setMechianic(String mechianic) {
		this.mechianic = mechianic;
	}
	@Column(name="FAULT_TYPE")	
	public String getFaultType() {
		return faultType;
	}
	
	public void setFaultType(String faultType) {
		this.faultType = faultType;
	}

	@Column(name="SPARE_PART_INVOLVED")
	public boolean isSparePartInvolved() {
		return sparePartInvolved;
	}
	
	public void setSparePartInvolved(boolean sparePartInvolved) {
		this.sparePartInvolved = sparePartInvolved;
	}
	@Column(name="EXTERNAL_SERVICE_INVOLVED")
	public boolean isExternalServiceInvolved() {
		return externalServiceInvolved;
	}
	
	public void setExternalServiceInvolved(boolean externalServiceInvolved) {
		this.externalServiceInvolved = externalServiceInvolved;
	}
	@Column(name="CREATED_DATE")
	public Date getCreatedDate() {
		if(null != createdDate){
			return new Date(createdDate.getTime());
		}
		return null;
	}
	
	public void setCreatedDate(Date createdDate) {
		if(null != createdDate){
			this.createdDate = new Date(createdDate.getTime());
		}
	}
	@Column(name="MAINT_START_DATE")
	public Date getMaintanceStartDate() {
		if(null != maintanceStartDate){
			return new Date(maintanceStartDate.getTime());
		}
		return null;
	}
	
	public void setMaintanceStartDate(Date maintanceStartDate) {
		if(null != maintanceStartDate){
			this.maintanceStartDate = new Date(maintanceStartDate.getTime());
		}
	}
	@Column(name="MAINT_END_DATE")
	public Date getMaintanceEndDate() {
		if(null != maintanceEndDate){
			return new Date(maintanceEndDate.getTime());
		}
		return null;
	}
	
	public void setMaintanceEndDate(Date maintanceEndDate) {
		if(null != maintanceEndDate){
			this.maintanceEndDate = new Date(maintanceEndDate.getTime());
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
		if(null != lastUpdatedDate){
			return new Date(lastUpdatedDate.getTime());
		}
		return null;
	}
	
	public void setLastUpdatedDate(Date lastUpdatedDate) {
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

	@Column(name="OPERATOR")
	public String getOperator() {
		return operator;
	}

	
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
}
