/**
 * ============================================================
 * File : idmInfo.java
 * Description : 
 * 
 * Package : com.ge.pmms.po
 * Author : iGATE
 * Last Edited By :
 * Version : 1.0
 * Created on : Jan 26, 2015
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
 * @Date Created: Jan 26, 2015
 * @Date Modified : 
 * @Modified By : 
 * @Contact 	:
 * @Description : 
 * @History		:
 *
 ******************************************************************************/
@Entity(name="idmInfo")
@Table(name="IDM_INFO")
public class IdmInfo {
	private Integer id;
	private String idmId;
	private String type;
	private String typeId;
	private String subType;
	private String idmNm;
	private String source;
	private String size;
	private String measurement;
	private String price;
	private String stockNum;
	private String safetyNm;
	private String position;
	private String creator;
	private String updater;
	private String remark;
	private Date createDate=null;
	private Date lastUpdateDate=null;
	private String subTypeId;
	private String typeDetailId;
	private String SUB_TYPE_NM;
	private String TYPE_DETAIL_NM;
	private String sugSaveStock;
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
	 * @return the type
	 */
	@Column(name="TYPE")
	public String getType() {
		return type;
	}
	
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	
	/**
	 * @return the typeId
	 */
	@Column(name="TYPE_ID")
	public String getTypeId() {
		return typeId;
	}

	
	/**
	 * @param typeId the typeId to set
	 */
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	/**
	 * @return the subType
	 */
	@Column(name="SUB_TYPE")
	public String getSubType() {
		return subType;
	}
	
	/**
	 * @param subType the subType to set
	 */
	public void setSubType(String subType) {
		this.subType = subType;
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
	 * @return the source
	 */
	@Column(name="SOURCE")
	public String getSource() {
		return source;
	}
	
	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}
	
	/**
	 * @return the size
	 */
	@Column(name="SIZE")
	public String getSize() {
		return size;
	}
	
	/**
	 * @param size the size to set
	 */
	public void setSize(String size) {
		this.size = size;
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
	 * @return the stockNum
	 */
	@Column(name="STOCK_NUM")
	public String getStockNum() {
		return stockNum;
	}
	
	/**
	 * @param stockNum the stockNum to set
	 */
	public void setStockNum(String stockNum) {
		this.stockNum = stockNum;
	}
	
	/**
	 * @return the safetyNm
	 */
	@Column(name="SAFETY_STOCK")
	public String getSafetyNm() {
		return safetyNm;
	}
	
	/**
	 * @param safetyNm the safetyNm to set
	 */
	public void setSafetyNm(String safetyNm) {
		this.safetyNm = safetyNm;
	}
	
	/**
	 * @return the position
	 */
	@Column(name="POSITION")
	public String getPosition() {
		return position;
	}
	
	/**
	 * @param position the position to set
	 */
	public void setPosition(String position) {
		this.position = position;
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
	 * @return the subTypeId
	 */
	@Column(name="SUB_TYPE_ID")
	public String getSubTypeId() {
		return subTypeId;
	}

	
	/**
	 * @param subTypeId the subTypeId to set
	 */
	public void setSubTypeId(String subTypeId) {
		this.subTypeId = subTypeId;
	}

	
	/**
	 * @return the typeDetailId
	 */
	@Column(name="TYPE_DETAIL_ID")
	public String getTypeDetailId() {
		return typeDetailId;
	}

	
	/**
	 * @param typeDetailId the typeDetailId to set
	 */
	public void setTypeDetailId(String typeDetailId) {
		this.typeDetailId = typeDetailId;
	}

	
	/**
	 * @return the sUB_TYPE_NM
	 */
	@Transient
	public String getSUB_TYPE_NM() {
		return SUB_TYPE_NM;
	}

	
	/**
	 * @param sUB_TYPE_NM the sUB_TYPE_NM to set
	 */
	public void setSUB_TYPE_NM(String sUB_TYPE_NM) {
		SUB_TYPE_NM = sUB_TYPE_NM;
	}

	
	/**
	 * @return the tYPE_DETAIL_NM
	 */
	@Transient
	public String getTYPE_DETAIL_NM() {
		return TYPE_DETAIL_NM;
	}

	
	/**
	 * @param tYPE_DETAIL_NM the tYPE_DETAIL_NM to set
	 */
	public void setTYPE_DETAIL_NM(String tYPE_DETAIL_NM) {
		TYPE_DETAIL_NM = tYPE_DETAIL_NM;
	}

	
	/**
	 * @return the sugSaveStock
	 */
	@Column(name="SUG_SAFETY_NUM")
	public String getSugSaveStock() {
		return sugSaveStock;
	}

	
	/**
	 * @param sugSaveStock the sugSaveStock to set
	 */
	public void setSugSaveStock(String sugSaveStock) {
		this.sugSaveStock = sugSaveStock;
	}
	
	
}
