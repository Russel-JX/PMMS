/**
 * ============================================================
 * File : SparePartTransInfo.java
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
 * @Date Created: Feb 5, 2015
 * @Date Modified : 
 * @Modified By : 
 * @Contact 	:
 * @Description : 
 * @History		:
 *
 ******************************************************************************/
@Entity(name="sparePartTransInfo")
@Table(name="SPARE_PART_TRANS_INFO")
public class SparePartTransInfo implements Serializable {
	
	private static final long serialVersionUID = -1928900887813371970L;
	private Integer id;
	private String transId;
	private String sparePartId;
	private String sparePartName;    //备件名称
	private String sparePartType;    //规格型号
	private String transType;
	private Integer amount;
	private String measurement;
	private Float price;
	private String receiver;
	private String workOrderId;
	private String creator;
	private Date createdDate;
	private String updater;
	private Date lastUpdatedDate;
	private String remark;
	private Date createMonth;
	private float leadTime;      //备件交付时间差
	//*****************************************
	private String searchFromDate;   //查询开始时间
	private String searchToDate;     //查询结束时间
	private String strCreatedDate;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name="TRANS_ID")
	public String getTransId() {
		return transId;
	}
	
	public void setTransId(String transId) {
		this.transId = transId;
	}
	@Column(name="SPARE_PART_ID")
	public String getSparePartId() {
		return sparePartId;
	}
	
	public void setSparePartId(String sparePartId) {
		this.sparePartId = sparePartId;
	}
	@Column(name="SPARE_PART_NAME")
	public String getSparePartName() {
		return sparePartName;
	}

	public void setSparePartName(String sparePartName) {
		this.sparePartName = sparePartName;
	}
	@Column(name="SPARE_PART_TYPE")
	public String getSparePartType() {
		return sparePartType;
	}
	
	public void setSparePartType(String sparePartType) {
		this.sparePartType = sparePartType;
	}
	@Column(name="TRANS_TYPE")
	public String getTransType() {
		return transType;
	}
	
	public void setTransType(String transType) {
		this.transType = transType;
	}
	@Column(name="AMOUNT")
	public Integer getAmount() {
		return amount;
	}
	
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	@Column(name="MEASUREMENT")
	public String getMeasurement() {
		return measurement;
	}
	
	public void setMeasurement(String measurement) {
		this.measurement = measurement;
	}
	@Column(name="PRICE")
	public Float getPrice() {
		return price;
	}
	
	public void setPrice(Float price) {
		this.price = price;
	}
	@Column(name="RECEIVER")
	public String getReceiver() {
		return receiver;
	}
	
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	@Column(name="WO_ID")
	public String getWorkOrderId() {
		return workOrderId;
	}
	
	public void setWorkOrderId(String workOrderId) {
		this.workOrderId = workOrderId;
	}
	@Column(name="CREATOR")
	public String getCreator() {
		return creator;
	}
	
	public void setCreator(String creator) {
		this.creator = creator;
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
	@Column(name="CREATE_MONTH")
	public Date getCreateMonth() {
		if(null != createMonth){
			return new Date(createMonth.getTime());
		}
		return null;
	}
	
	public void setCreateMonth(Date createMonth) {
		if(null != createMonth){
			this.createMonth = new Date(createMonth.getTime());
		}
	}
	
	@Column(name="LEAD_TIME")
	public float getLeadTime() {
		return leadTime;
	}

	
	public void setLeadTime(float leadTime) {
		this.leadTime = leadTime;
	}

	@Transient
	public String getSearchFromDate() {
		return searchFromDate;
	}
	
	public void setSearchFromDate(String searchFromDate) {
		this.searchFromDate = searchFromDate;
	}

	@Transient
	public String getSearchToDate() {
		return searchToDate;
	}

	public void setSearchToDate(String searchToDate) {
		this.searchToDate = searchToDate;
	}

	@Transient
	public String getStrCreatedDate() {
		return strCreatedDate;
	}

	
	public void setStrCreatedDate(String strCreatedDate) {
		this.strCreatedDate = strCreatedDate;
	}	
	
}
