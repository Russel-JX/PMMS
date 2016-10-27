/**
 * ============================================================
 * File : IdmTransInfo.java
 * Description : 
 * 
 * Package : com.ge.pmms.po
 * Author : iGATE
 * Last Edited By :
 * Version : 1.0
 * Created on : Feb 2, 2015
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
import javax.persistence.Transient;


/*******************************************************************************
 *
 * @Author 		: iGATE
 * @Version 	: 1.0
 * @Date Created: Feb 2, 2015
 * @Date Modified : 
 * @Modified By : 
 * @Contact 	:
 * @Description : 
 * @History		:
 *
 ******************************************************************************/
@Entity(name="idmTransInfo")
@Table(name="IDM_TRANSACTION")
public class IdmTransInfo {
	private Integer id;
	private String transId;
	private String idmId;
	private String idmType;
	private String idmNm;
	private String amount;
	private String measurement;
	private String price;
	private String receiver;
	private String creator;
	private String updater;
	private String remark;
	private Date createDate;
	private Date lastUpdateDate;
	private String inTime;
	private String outTime;
	private String startTime;
	private String endTime;
	private String transType;
	private String strCreateDate;
	//〖(x_i-x ̅)〗^2 方差
	private String variance;
	private String leadTime;
	private String po;
	//仓库管理员名字
	private String keeperNm;
	private String receiverNm;
	private String remainAmount;
	//为po明细表建的
	private String inAmount;
	/**
	 * @return the po
	 */
	public String getPo() {
		return po;
	}




	
	/**
	 * @param po the po to set
	 */
	public void setPo(String po) {
		this.po = po;
	}




	/**
	 * @return the strCreateDate
	 */
	@Transient
	public String getStrCreateDate() {
		return strCreateDate;
	}



	
	/**
	 * @param strCreateDate the strCreateDate to set
	 */
	public void setStrCreateDate(String strCreateDate) {
		this.strCreateDate = strCreateDate;
	}



	/**
	 * @return the transType
	 */
	@Column(name="TRANS_TYPE")
	public String getTransType() {
		return transType;
	}


	
	/**
	 * @param transType the transType to set
	 */
	public void setTransType(String transType) {
		this.transType = transType;
	}


	/**
	 * @return the startTime
	 */
	@Transient
	public String getStartTime() {
		return startTime;
	}

	
	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	
	/**
	 * @return the endTime
	 */
	@Transient
	public String getEndTime() {
		return endTime;
	}

	
	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the id
	 */
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	
	/**
	 * @return the transId
	 */
	@Column(name="TRANS_ID")
	public String getTransId() {
		return transId;
	}
	
	/**
	 * @param transId the transId to set
	 */
	public void setTransId(String transId) {
		this.transId = transId;
	}
	
	/**
	 * @return the idmId
	 */
	@Column(name="IDM_ID")
	public String getIdmId() {
		return idmId;
	}
	
	/**
	 * @param idmId the idmId to set
	 */
	public void setIdmId(String idmId) {
		this.idmId = idmId;
	}
	
	/**
	 * @return the idmType
	 */
	@Column(name="IDM_TYPE")
	public String getIdmType() {
		return idmType;
	}
	
	/**
	 * @param idmType the idmType to set
	 */
	public void setIdmType(String idmType) {
		this.idmType = idmType;
	}
	
	/**
	 * @return the idmNm
	 */
	@Column(name="IDM_NAME")
	public String getIdmNm() {
		return idmNm;
	}
	
	/**
	 * @param idmNm the idmNm to set
	 */
	public void setIdmNm(String idmNm) {
		this.idmNm = idmNm;
	}
	
	/**
	 * @return the amount
	 */
	@Column(name="AMOUNT")
	public String getAmount() {
		return amount;
	}
	
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	/**
	 * @return the measurement
	 */
	@Column(name="MEASUREMENT")
	public String getMeasurement() {
		return measurement;
	}
	
	/**
	 * @param measurement the measurement to set
	 */
	public void setMeasurement(String measurement) {
		this.measurement = measurement;
	}
	
	/**
	 * @return the price
	 */
	@Column(name="PRICE")
	public String getPrice() {
		return price;
	}
	
	/**
	 * @param price the price to set
	 */
	public void setPrice(String price) {
		this.price = price;
	}
	
	/**
	 * @return the receiver
	 */
	@Column(name="RECEIVER")
	public String getReceiver() {
		return receiver;
	}
	
	/**
	 * @param receiver the receiver to set
	 */
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	
	/**
	 * @return the creator
	 */
	@Column(name="CREATOR")
	public String getCreator() {
		return creator;
	}
	
	/**
	 * @param creator the creator to set
	 */
	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	/**
	 * @return the updater
	 */
	@Column(name="UPDATER")
	public String getUpdater() {
		return updater;
	}
	
	/**
	 * @param updater the updater to set
	 */
	public void setUpdater(String updater) {
		this.updater = updater;
	}
	
	/**
	 * @return the remark
	 */
	@Column(name="REMARK")
	public String getRemark() {
		return remark;
	}
	
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	/**
	 * @return the createDate
	 */
	@Column(name="CREATED_DATE")
	public Date getCreateDate() {
		//return createDate;
		if(null != createDate){
			return new Date(createDate.getTime());
		}
		return null;
	}
	
	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		//this.createDate = createDate;
		if(null != createDate){
			this.createDate = new Date(createDate.getTime());
		}
	}
	
	/**
	 * @return the lastUpdateDate
	 */
	@Column(name="LAST_UPDATED_DATE")
	public Date getLastUpdateDate() {
		//return lastUpdateDate;
		if(null != lastUpdateDate){
			return new Date(lastUpdateDate.getTime());
		}
		return null;
	}
	
	/**
	 * @param lastUpdateDate the lastUpdateDate to set
	 */
	public void setLastUpdateDate(Date lastUpdateDate) {
		//this.lastUpdateDate = lastUpdateDate;
		if(null != lastUpdateDate){
		     this.lastUpdateDate = new Date(lastUpdateDate.getTime());
	  }
	}

	
	/**
	 * @return the inTime
	 */
	@Column(name="IDM_IN_TIME")
	public String getInTime() {
		return inTime;
	}

	
	/**
	 * @param inTime the inTime to set
	 */
	public void setInTime(String inTime) {
		this.inTime = inTime;
	}

	
	/**
	 * @return the outTime
	 */
	@Column(name="IDM_OUT_TIME")
	public String getOutTime() {
		return outTime;
	}

	
	/**
	 * @param outTime the outTime to set
	 */
	public void setOutTime(String outTime) {
		this.outTime = outTime;
	}




	
	/**
	 * @return the variance
	 */
	@Transient
	public String getVariance() {
		return variance;
	}




	
	/**
	 * @param variance the variance to set
	 */
	public void setVariance(String variance) {
		this.variance = variance;
	}




	
	/**
	 * @return the leadTime
	 */
	@Column(name="LEAD_TIME")
	public String getLeadTime() {
		return leadTime;
	}




	
	/**
	 * @param leadTime the leadTime to set
	 */
	public void setLeadTime(String leadTime) {
		this.leadTime = leadTime;
	}





	
	/**
	 * @return the keeperNm
	 */
	@Transient
	public String getKeeperNm() {
		return keeperNm;
	}





	
	/**
	 * @param keeperNm the keeperNm to set
	 */
	public void setKeeperNm(String keeperNm) {
		this.keeperNm = keeperNm;
	}





	
	/**
	 * @return the receiverNm
	 */
	@Transient
	public String getReceiverNm() {
		return receiverNm;
	}





	
	/**
	 * @param receiverNm the receiverNm to set
	 */
	public void setReceiverNm(String receiverNm) {
		this.receiverNm = receiverNm;
	}
	
	/**
	 * @return the remainAmount
	 */
	@Transient
	public String getRemainAmount() {
		return remainAmount;
	}
	
	/**
	 * @param remainAmount the remainAmount to set
	 */
	public void setRemainAmount(String remainAmount) {
		this.remainAmount = remainAmount;
	}


	
	/**
	 * @return the inAmount
	 */
	@Transient
	public String getInAmount() {
		return inAmount;
	}

	
	/**
	 * @param inAmount the inAmount to set
	 */
	public void setInAmount(String inAmount) {
		this.inAmount = inAmount;
	}
	
	
	
} 
