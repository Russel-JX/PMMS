/**
 * ============================================================
 * File : SparePartInfo.java
 * Description : 
 * 
 * Package : com.ge.pmms.po
 * Author : iGATE
 * Last Edited By :
 * Version : 1.0
 * Created on : Feb 1, 2015
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
 * @Date Created: Feb 1, 2015
 * @Date Modified : 
 * @Modified By : 
 * @Contact 	:
 * @Description : 
 * @History		:
 *
 ******************************************************************************/
@Entity(name="sparePartInfo")
@Table(name="SPARE_PART_INFO")
public class SparePartInfo implements Serializable{

	private static final long serialVersionUID = -2955020909499356434L;
	private Integer id;               //主键 ID
	private String sparePartId;       //备件编码，唯一
	private String sparePartName;     //备件名称
	private String sparePartType;     //规格型号
	private String source;            //生产厂商
	private String measurement;       //单位
	private Float price;              //单价
	private Integer stock;			  //库存量
	private Double safetyStock;       //安全库存
	private Double sysSafetyStock;    //建议安全库存(系统计算)
	private String creator;			  //创建人
	private Date createdDate;         //创建时间
	private String updater;           //修改人
	private Date lastUpdatedDate;     //修改时间
	private String location;          //存放地点
	private String remark;            //备注
	//************************************************
	private Float inPrice;             //入库价格
	private Integer account;           //入库数量
	private String inRemark;           //入库备注
	private boolean safetyFlag;        //查询库存小于安全库存的标志，true查询，false不查询
	private String outRemark;          //出库备注
	private String workOrderId;        //出库关联工单号
	private Float leadTime;            //备件交付时间差
	
	/**
	 * @return the id
	 */
	@Column(name="ID")
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
	 * @return the sparePartId
	 */
	@Column(name="SPARE_PART_ID")
	public String getSparePartId() {
		return sparePartId;
	}
	

	/**
	 * @param sparePartId the sparePartId to set
	 */
	public void setSparePartId(String sparePartId) {
		this.sparePartId = sparePartId;
	}
	
	/**
	 * @return the sparePartName
	 */
	@Column(name="SPARE_PART_NAME")
	public String getSparePartName() {
		return sparePartName;
	}

	
	/**
	 * @param sparePartName the sparePartName to set
	 */
	public void setSparePartName(String sparePartName) {
		this.sparePartName = sparePartName;
	}

	/**
	 * @return the sparePartType
	 */
	@Column(name="SPARE_PART_TYPE")
	public String getSparePartType() {
		return sparePartType;
	}
	
	/**
	 * @param sparePartType the sparePartType to set
	 */
	public void setSparePartType(String sparePartType) {
		this.sparePartType = sparePartType;
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
	public Float getPrice() {
		return price;
	}
	
	/**
	 * @param price the price to set
	 */
	public void setPrice(Float price) {
		this.price = price;
	}
	
	/**
	 * @return the stock
	 */
	@Column(name="STOCK")
	public Integer getStock() {
		return stock;
	}
	
	/**
	 * @param stock the stock to set
	 */
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	
	@Column(name="SAFETY_STOCK")
	public Double getSafetyStock() {
		return safetyStock;
	}

	
	public void setSafetyStock(Double safetyStock) {
		this.safetyStock = safetyStock;
	}

	@Column(name="SYS_SAFETY_STOCK")
	public Double getSysSafetyStock() {
		return sysSafetyStock;
	}

	
	public void setSysSafetyStock(Double sysSafetyStock) {
		this.sysSafetyStock = sysSafetyStock;
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
	 * @return the createdDate
	 */
	@Column(name="CREATED_DATE")
	public Date getCreatedDate() {
		if(null != createdDate){
			return new Date(createdDate.getTime());
		}
		return null;
	}
	
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		if(null != createdDate){
			this.createdDate = new Date(createdDate.getTime());
		}
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
	 * @return the lastUpdatedDate
	 */
	@Column(name="LAST_UPDATED_DATE")
	public Date getLastUpdatedDate() {
		if(null != lastUpdatedDate){
			return new Date(lastUpdatedDate.getTime());
		}
		return null;	
	}
	
	/**
	 * @param lastUpdatedDate the lastUpdatedDate to set
	 */
	public void setLastUpdatedDate(Date lastUpdatedDate) {
		if(null != lastUpdatedDate){
			this.lastUpdatedDate = new Date(lastUpdatedDate.getTime());
		}
	}
	@Column(name="LOCATION")
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
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

	@Transient
	public Float getInPrice() {
		return inPrice;
	}

	
	public void setInPrice(Float inPrice) {
		this.inPrice = inPrice;
	}

	@Transient
	public Integer getAccount() {
		return account;
	}

	
	public void setAccount(Integer account) {
		this.account = account;
	}

	@Transient
	public String getInRemark() {
		return inRemark;
	}

	
	public void setInRemark(String inRemark) {
		this.inRemark = inRemark;
	}

	@Transient
	public boolean isSafetyFlag() {
		return safetyFlag;
	}

	
	public void setSafetyFlag(boolean safetyFlag) {
		this.safetyFlag = safetyFlag;
	}

	@Transient
	public String getOutRemark() {
		return outRemark;
	}

	
	public void setOutRemark(String outRemark) {
		this.outRemark = outRemark;
	}

	@Transient
	public String getWorkOrderId() {
		return workOrderId;
	}

	
	public void setWorkOrderId(String workOrderId) {
		this.workOrderId = workOrderId;
	}

	@Transient
	public Float getLeadTime() {
		return leadTime;
	}

	
	public void setLeadTime(Float leadTime) {
		this.leadTime = leadTime;
	}


	
}
