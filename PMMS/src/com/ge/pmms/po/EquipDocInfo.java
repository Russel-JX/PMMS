/**
 * ============================================================
 * File : EquipDocInfo.java
 * Description : 
 * 
 * Package : com.ge.pmms.po
 * Author : iGATE
 * Last Edited By :
 * Version : 1.0
 * Created on : Jan 22, 2015
 * History
 * Modified By : Initial Release
 * Classification : GE Confidential
 * Copyright (C) 2015 General Electric Company. All rights reserved
 *
 * ============================================================
 */

package com.ge.pmms.po;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;

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
 * @Date Created: Jan 22, 2015
 * @Date Modified : 
 * @Modified By : 
 * @Contact 	:
 * @Description : 
 * @History		:
 *
 ******************************************************************************/

@Entity(name="EquipDocInfo")
@Table(name="EQUIP_DOC_INFO ")
public class EquipDocInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2046349246337062977L;
	
	private Integer id;
	private String equipDocId;
	private String docType;
	private String fileSize;
	private String fileCount;
	private String creator;
	private Date createTime;
	private String updater;
	private Date updateTime;
	private String description;
	private String CategoryName;
	
	private String equipCategory;
	private String equipModelId;
	private String equipNo;
	
	
	@Id 
	@GeneratedValue(strategy=IDENTITY)
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="EQUIP_DOC_ID")
	public String getEquipDocId() {
		return equipDocId;
	}
	
	public void setEquipDocId(String equipDocId) {
		this.equipDocId = equipDocId;
	}
	
	
	@Column(name="DOC_TYPE")
	public String getDocType() {
		return docType;
	}
	
	public void setDocType(String docType) {
		this.docType = docType;
	}

	@Column(name="CATEGORY_NAME")
	public String getCategoryName() {
		return CategoryName;
	}

	
	public void setCategoryName(String categoryName) {
		CategoryName = categoryName;
	}

	@Column(name="FILE_SIZE")
	public String getFileSize() {
		return fileSize;
	}
	
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	
	@Column(name="FILE_COUNT")
	public String getFileCount() {
		return fileCount;
	}
	
	public void setFileCount(String fileCount) {
		this.fileCount = fileCount;
	}
	
	public String getCreator() {
		return creator;
	}
	
	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	public Date getCreateTime() {
	  if(null != createTime){
      return new Date(createTime.getTime());      
    }else{
      return null;
    }
	}
	
	public void setCreateTime(Date createTime) {
	  if(null != createTime){
	    this.createTime = new Date(createTime.getTime());
	  }
	}
	
	
	public String getUpdater() {
		return updater;
	}

	
	public void setUpdater(String updater) {
		this.updater = updater;
	}

	public Date getUpdateTime() {
	  if(null != updateTime){
      return new Date(updateTime.getTime());      
    }else{
      return null;
    }
	}
	
	public void setUpdateTime(Date updateTime) {
	  if(null != updateTime){
	    this.updateTime = new Date(updateTime.getTime());
	  }
	}
	
	@Column(name="DESCRIPTION")
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	@Transient
	public String getEquipCategory() {
		return equipCategory;
	}

	public void setEquipCategory(String equipCategory) {
		this.equipCategory = equipCategory;
	}
	

	@Transient
	public String getEquipModelId() {
		return equipModelId;
	}

	
	public void setEquipModelId(String equipModelId) {
		this.equipModelId = equipModelId;
	}

	@Transient
	public String getEquipNo() {
		return equipNo;
	}

	public void setEquipNo(String equipNo) {
		this.equipNo = equipNo;
	}
	
	
}
