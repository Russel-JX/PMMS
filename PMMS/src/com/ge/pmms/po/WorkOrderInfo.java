/**
 * ============================================================
 * File : WorkOrderInfo.java
 * Description : 
 * 
 * Package : com.ge.pmms.po
 * Author : iGATE
 * Last Edited By :
 * Version : 1.0
 * Created on : Jan 13, 2015
 * History
 * Modified By : Initial Release
 * Classification : GE Confidential
 * Copyright (C) 2015 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.pmms.po;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


/*******************************************************************************
 *
 * @Author 		: iGATE
 * @Version 	: 1.0
 * @Date Created: Jan 13, 2015
 * @Date Modified : 
 * @Modified By : 
 * @Contact 	:
 * @Description : 
 * @History		:
 *
 ******************************************************************************/
@Entity(name="workOrderInfo")
@Table(name="WO_INFO")
public class WorkOrderInfo implements Serializable{
	
	private static final long serialVersionUID = 6993501374969371628L;
	private int id;
	private String workOrderId="";        //工单编号
	private String equipId="";		      //设备编号
	private String workOrderType="";	  //工单类型
	private String creator="";			  //申报人编号
	private String faultDescription="";   //故障描述
	private Date planStartMonth;          //计划工单产生月
	private boolean shutdownFlag;         //是否需停机
	private boolean safetyInvolved;	      //涉及安全隐患 
	private String workOrderStatus="";    //工单状态 
	private String planId="";			  //关联计划编号
	private Date createdate = null;       //工单申报时间
	private String updater="";			 
	private Date lastUpdatedDate = null;	
	//private WorkOrderMaintenanceInfo workOrderMaintenanceInfo;
	/*******************************************/
	private String mechianic="";              //维修人
	private Date maintanceStartDate = null;   //维修开始时间
	private Date maintanceEndDate = null;	  //维修结束时间
	private String remark="";                 //故障种类补充信息
	private String equipName="";              //设备名称
	private String equipModel="";             //设备型号
	private String assetId="";				  //资产编号
	private boolean sparePartInvolved;        //是否需要备件
	private boolean externalServiceInvolved;  //是否需要外部服务
	private String woMaintId="";              //维修工单ID
	private String strCreateDate = "";        //工单创建时间 string
	private String strMaintanceStartDate="";  //维修开始时间 string
	private String strMaintanceEndDate="";    //维修结束时间 string
	private String faultType="";              //故障种类
	/************************************************/
	private String orderFlag;                 //当前工单 历史工单标志信息
	private String startDate;                 //查询开始时间
	private String endDate;				      //查询结束时间
	private boolean selectItem=false;         //前台选择框显示作用
	private String maintContent="";           //保养内容，页面datatable显示作用
	private String deptId="";                 //部门（区域）编号
	private String deptNM="";                 //部门（区域）名称
	private String equipType="";              //设备类型（1,2.3）
	private String strPlanStartMonth="";      //计划工单产生月
	private String equipNameId;               //设备名称编号
	
	private double timeGap;                   //维修结束时间-维修开始时间
	
	
	//关闭工单人sso
	private String operator;
	//申报人姓名
	private String creatorFirtName;
	private String creatorLastName;
	//维修人姓名
	private String mechianicFirtName;
	private String mechianicLastName;
	//关闭工单人姓名
	private String operationFirstName;
	private String operationLastName;
	
	//首页工单信息显示需要
	private String WOType = "";
	private String WOStatus = "";
	private String SSO = "";
	private String firtName = "";
	private String lastName = "";
	private String name = "";
	
	@Column(name="ID")
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	@Column(name="WO_ID")
	public String getWorkOrderId() {
		return workOrderId;
	}
	
	public void setWorkOrderId(String workOrderId) {
		this.workOrderId = workOrderId;
	}
	@Column(name="EQUIP_ID")
	public String getEquipId() {
		return equipId;
	}
	
	public void setEquipId(String equipId) {
		this.equipId = equipId;
	}
	@Column(name="WO_TYPE")
	public String getWorkOrderType() {
		return workOrderType;
	}
	
	public void setWorkOrderType(String workOrderType) {
		this.workOrderType = workOrderType;
	}
	@Column(name="CREATOR")
	public String getCreator() {
		return creator;
	}
	
	@Column(name="FAULT_DESCRIPTION")
	public String getFaultDescription() {
		return faultDescription;
	}
	
	public void setFaultDescription(String faultDescription) {
		this.faultDescription = faultDescription;
	}

	@Column(name="PLAN_START_MONTH")
	public Date getPlanStartMonth() {
		if(null != planStartMonth){
			return new Date(planStartMonth.getTime());			
		}else{
			return null;
		}
	}
	
	public void setPlanStartMonth(Date planStartMonth) {
	  if(null != planStartMonth){
		    this.planStartMonth = new Date(planStartMonth.getTime());
	  }
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}
	@Column(name="SHUTDOWN_FLAG")
	public boolean isShutdownFlag() {
		return shutdownFlag;
	}
	
	public void setShutdownFlag(boolean shutdownFlag) {
		this.shutdownFlag = shutdownFlag;
	}
	@Column(name="SAFETY_INVOLVED")
	public boolean isSafetyInvolved() {
		return safetyInvolved;
	}
	
	public void setSafetyInvolved(boolean safetyInvolved) {
		this.safetyInvolved = safetyInvolved;
	}
	@Column(name="WO_STATUS")
	public String getWorkOrderStatus() {
		return workOrderStatus;
	}
	
	public void setWorkOrderStatus(String workOrderStatus) {
		this.workOrderStatus = workOrderStatus;
	}
	@Column(name="PLAN_ID")
	public String getPlanId() {
		return planId;
	}
	
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	@Column(name="CREATED_DATE")
	public Date getCreatedate() {
		if(null != createdate){
			return new Date(createdate.getTime());
		}
		return null;
	}
	
	public void setCreatedate(Date createdate) {
		if(null != createdate){
			this.createdate = new Date(createdate.getTime());
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

	/***************************************************************/

	@Transient
	public String getMechianic() {
		return mechianic;
	}

	
	public void setMechianic(String mechianic) {
		this.mechianic = mechianic;
	}

	@Transient
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

	@Transient
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

	@Transient
	public String getRemark() {
		return remark;
	}

	
	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Transient
	public String getEquipName() {
		return equipName;
	}

	
	public void setEquipName(String equipName) {
		this.equipName = equipName;
	}

	@Transient
	public String getEquipModel() {
		return equipModel;
	}

	
	public void setEquipModel(String equipModel) {
		this.equipModel = equipModel;
	}

	@Transient
	public String getAssetId() {
		return assetId;
	}

	
	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	@Transient
	public boolean isSparePartInvolved() {
		return sparePartInvolved;
	}

	
	public void setSparePartInvolved(boolean sparePartInvolved) {
		this.sparePartInvolved = sparePartInvolved;
	}

	@Transient
	public boolean isExternalServiceInvolved() {
		return externalServiceInvolved;
	}

	
	public void setExternalServiceInvolved(boolean externalServiceInvolved) {
		this.externalServiceInvolved = externalServiceInvolved;
	}

	@Transient
	public String getWoMaintId() {
		return woMaintId;
	}

	
	public void setWoMaintId(String woMaintId) {
		this.woMaintId = woMaintId;
	}

	@Transient
	public String getStrCreateDate() {
		return strCreateDate;
	}

	
	public void setStrCreateDate(String strCreateDate) {
		this.strCreateDate = strCreateDate;
	}

	@Transient
	public String getStrMaintanceStartDate() {
		return strMaintanceStartDate;
	}

	
	public void setStrMaintanceStartDate(String strMaintanceStartDate) {
		this.strMaintanceStartDate = strMaintanceStartDate;
	}

	@Transient
	public String getStrMaintanceEndDate() {
		return strMaintanceEndDate;
	}

	
	public void setStrMaintanceEndDate(String strMaintanceEndDate) {
		this.strMaintanceEndDate = strMaintanceEndDate;
	}

	@Transient
	public String getFaultType() {
		return faultType;
	}

	
	public void setFaultType(String faultType) {
		this.faultType = faultType;
	}

	@Transient
	public String getOrderFlag() {
		return orderFlag;
	}

	
	public void setOrderFlag(String orderFlag) {
		this.orderFlag = orderFlag;
	}

	@Transient
	public String getStartDate() {
		return startDate;
	}

	
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	@Transient
	public String getEndDate() {
		return endDate;
	}

	
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	@Transient
	public boolean isSelectItem() {
		return selectItem;
	}

	
	public void setSelectItem(boolean selectItem) {
		this.selectItem = selectItem;
	}

	@Transient
	public String getMaintContent() {
		return maintContent;
	}

	public void setMaintContent(String maintContent) {
		this.maintContent = maintContent;
	}

	@Transient
	public String getDeptId() {
		return deptId;
	}
	
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	

	@Transient
	public String getDeptNM() {
		return deptNM;
	}

	
	public void setDeptNM(String deptNM) {
		this.deptNM = deptNM;
	}

	@Transient
	public String getEquipType() {
		return equipType;
	}

	
	public void setEquipType(String equipType) {
		this.equipType = equipType;
	}

	@Transient
	public String getStrPlanStartMonth() {
		return strPlanStartMonth;
	}

	public void setStrPlanStartMonth(String strPlanStartMonth) {
		this.strPlanStartMonth = strPlanStartMonth;
	}

	@Transient
	public String getEquipNameId() {
		return equipNameId;
	}

	
	public void setEquipNameId(String equipNameId) {
		this.equipNameId = equipNameId;
	}

	
	@Transient
	public String getOperator() {
		return operator;
	}

	
	public void setOperator(String operator) {
		this.operator = operator;
	}

	@Transient
	public String getCreatorFirtName() {
		return creatorFirtName;
	}

	
	public void setCreatorFirtName(String creatorFirtName) {
		this.creatorFirtName = creatorFirtName;
	}

	@Transient
	public String getCreatorLastName() {
		return creatorLastName;
	}

	
	public void setCreatorLastName(String creatorLastName) {
		this.creatorLastName = creatorLastName;
	}

	@Transient
	public String getMechianicFirtName() {
		return mechianicFirtName;
	}

	
	public void setMechianicFirtName(String mechianicFirtName) {
		this.mechianicFirtName = mechianicFirtName;
	}

	@Transient
	public String getMechianicLastName() {
		return mechianicLastName;
	}

	
	public void setMechianicLastName(String mechianicLastName) {
		this.mechianicLastName = mechianicLastName;
	}

	@Transient
	public String getOperationFirstName() {
		return operationFirstName;
	}

	
	public void setOperationFirstName(String operationFirstName) {
		this.operationFirstName = operationFirstName;
	}

	@Transient
	public String getOperationLastName() {
		return operationLastName;
	}

	
	public void setOperationLastName(String operationLastName) {
		this.operationLastName = operationLastName;
	}

	@Transient
	public String getWOType() {
		return WOType;
	}

	
	public void setWOType(String wOType) {
		WOType = wOType;
	}

	@Transient
	public String getWOStatus() {
		return WOStatus;
	}

	
	public void setWOStatus(String wOStatus) {
		WOStatus = wOStatus;
	}

	@Transient
	public String getSSO() {
		return SSO;
	}

	
	public void setSSO(String sSO) {
		SSO = sSO;
	}

	@Transient
	public String getFirtName() {
		return firtName;
	}

	
	public void setFirtName(String firtName) {
		this.firtName = firtName;
	}

	@Transient
	public String getLastName() {
		return lastName;
	}

	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Transient
	public String getName() {
		return name;
	}

	
	public void setName(String name) {
		this.name = name;
	}

	@Transient
	public double getTimeGap() {
		return timeGap;
	}

	
	public void setTimeGap(double timeGap) {
		this.timeGap = timeGap;
	}

	
	
}
