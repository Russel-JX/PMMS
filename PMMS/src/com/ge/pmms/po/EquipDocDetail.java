/**
 * ============================================================
 * File : EquipDocDetail.java
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

@Entity(name="EquipDocDetail")
@Table(name="EQUIP_DOC_DETAIL")
public class EquipDocDetail implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -298475021592529927L;
	
	private Integer id;
	private String docDetailId;
	private String equipDocId;
	//private String equipId;
	private String fileName;
	private String filePath;
	private String fileSize;
	private String creator;
	private Date createDate;
	
	
	private String equipId;
	private String equipNmId;
	private String equipName;
	private String equipModel;
	private String docType;
	private String description;
	private String docId;
	
	
	@Id 
	@GeneratedValue(strategy=IDENTITY)
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	
	@Column(name="DOC_DETAIL_ID")
	public String getDocDetailId() {
		return docDetailId;
	}
	
	public void setDocDetailId(String docDetailId) {
		this.docDetailId = docDetailId;
	}
	
	
	

	/*@Column(name="EQUIP_ID")
	public String getEquipId() {
		return equipId;
	}
	
	public void setEquipId(String equipId) {
		this.equipId = equipId;
	}*/
	
	@Column(name="EQUIP_DOC_ID")
	public String getEquipDocId() {
		return equipDocId;
	}

	public void setEquipDocId(String equipDocId) {
		this.equipDocId = equipDocId;
	}

	@Column(name="FILE_NAME")
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	@Column(name="FILE_PATH")
	public String getFilePath() {
		return filePath;
	}
	
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	@Column(name="FILE_SIZE")
	public String getFileSize() {
		return fileSize;
	}
	
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	
	public String getCreator() {
		return creator;
	}
	
	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	@Column(name="CREATETIME")
	public Date getCreateDate() {
		return new Date(createDate.getTime());
	}
	
	public void setCreateDate(Date createDate) {
	  this.createDate = new Date(createDate.getTime());
	}

	@Transient
	public String getEquipId() {
		return equipId;
	}

	public void setEquipId(String equipId) {
		this.equipId = equipId;
	}

	@Transient
	public String getEquipNmId() {
		return equipNmId;
	}

	public void setEquipNmId(String equipNmId) {
		this.equipNmId = equipNmId;
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
	public String getDocType() {
		return docType;
	}

	
	public void setDocType(String docType) {
		this.docType = docType;
	}

	@Transient
	public String getDescription() {
		return description;
	}

	
	public void setDescription(String description) {
		this.description = description;
	}

	@Transient
	public String getDocId() {
		return docId;
	}

	
	public void setDocId(String docId) {
		this.docId = docId;
	}
	
	
}
