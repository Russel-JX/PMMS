
package com.ge.pmms.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity(name="equipInfo")
@Table(name="EQUIP_INFO")
public class EquipInfo {
	
	private Integer id;
	//private String id; 22
	private String equipId;
	private String assetId;
	private String equipNmId;
	private String equipModel;
	private String source;
	private String factoryNo;
	private String releaseDate;
	private String powerConsum;
	private String installDate;
	private String originPrice;
	private String currPrice;
	private String size;
	private String weight;
	private String inUse;
	private String deptId;
	private String TypeId;
	private String creator;
	private Date createDate=null;
	private String updater;
	private Date lastUpdateDate=null;
	private String remark;
	
	private String equipmentName;
	private String equipType;
	private String equipDocId;
	private String deptNm;
	/**
	 * @return the id
	 */
	
	
	
	
	
	/**
	 * @return the equipDocId
	 */
	@Column(name="EQUIP_DOC_ID")
	public String getEquipDocId() {
		return equipDocId;
	}

	public EquipInfo() {
		super();
	}

	
	/**
	 * @param id
	 * @param equipId
	 * @param assetId
	 * @param equipNmId
	 * @param equipModel
	 * @param source
	 * @param factoryNo
	 * @param releaseDate
	 * @param powerConsum
	 * @param installDate
	 * @param originPrice
	 * @param currPrice
	 * @param size
	 * @param weight
	 * @param inUse
	 * @param deptId
	 * @param typeId
	 * @param creator
	 * @param createDate
	 * @param updater
	 * @param lastUpdateDate
	 * @param remark
	 * @param equipmentName
	 * @param equipType
	 * @param equipDocId
	 * @param deptNm
	 */
	public EquipInfo(String equipId, 
			String equipNmId,
			String equipmentName, String deptNm) {
		super();
		this.equipId = equipId;
		this.equipNmId = equipNmId;
		this.equipmentName = equipmentName;
		this.deptNm = deptNm;
	}




	/**
	 * @param equipDocId the equipDocId to set
	 */
	public void setEquipDocId(String equipDocId) {
		this.equipDocId = equipDocId;
	}



	/**
	 * @return the equipId
	 */
	@Column(name="equip_id")
	public String getEquipId() {
		return equipId;
	}
	
	
	
	/*
	@Id 
	@GeneratedValue(strategy=IDENTITY)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
*/
	
	
	
	public void setEquipId(String equipId) {
		this.equipId = equipId;
	}
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}



	/**
	 * @return the assetId
	 */
	@Column(name="ASSETS_ID")
	public String getAssetId() {
		return assetId;
	}
	
	/**
	 * @param assetId the assetId to set
	 */
	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}
	
	/**
	 * @return the equipNmId
	 */
	@Column(name="equip_name_id")
	public String getEquipNmId() {
		return equipNmId;
	}
	
	/**
	 * @param equipNmId the equipNmId to set
	 */
	public void setEquipNmId(String equipNmId) {
		this.equipNmId = equipNmId;
	}
	
	/**
	 * @return the equipModel
	 */
	@Column(name="equip_model")
	public String getEquipModel() {
		return equipModel;
	}
	
	/**
	 * @param equipModel the equipModel to set
	 */
	public void setEquipModel(String equipModel) {
		this.equipModel = equipModel;
	}
	
	/**
	 * @return the source
	 */
	@Column(name="source")
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
	 * @return the factoryNo
	 */
	@Column(name="factory_no")
	public String getFactoryNo() {
		return factoryNo;
	}
	
	/**
	 * @param factoryNo the factoryNo to set
	 */
	public void setFactoryNo(String factoryNo) {
		this.factoryNo = factoryNo;
	}
	
	/**
	 * @return the releaseDate
	 */
	@Column(name="release_date")
	public String getReleaseDate() {
		return releaseDate;
	}
	
	/**
	 * @param releaseDate the releaseDate to set
	 */
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
	
	/**
	 * @return the powerConsum
	 */
	@Column(name="POWER_CONSUMPTION")
	public String getPowerConsum() {
		return powerConsum;
	}
	
	/**
	 * @param powerConsum the powerConsum to set
	 */
	public void setPowerConsum(String powerConsum) {
		this.powerConsum = powerConsum;
	}
	
	/**
	 * @return the installDate
	 */
	@Column(name="INSTALLATION_DATE")
	public String getInstallDate() {
		return installDate;
	}
	
	/**
	 * @param installDate the installDate to set
	 */
	public void setInstallDate(String installDate) {
		this.installDate = installDate;
	}
	
	/**
	 * @return the originPrice
	 */
	@Column(name="ORIGINAL_PRICE")
	public String getOriginPrice() {
		return originPrice;
	}
	
	/**
	 * @param originPrice the originPrice to set
	 */
	public void setOriginPrice(String originPrice) {
		this.originPrice = originPrice;
	}
	
	/**
	 * @return the currPrice
	 */
	@Column(name="CURRENT_PRICE")
	public String getCurrPrice() {
		return currPrice;
	}
	
	/**
	 * @param currPrice the currPrice to set
	 */
	public void setCurrPrice(String currPrice) {
		this.currPrice = currPrice;
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
	 * @return the weight
	 */
	@Column(name="WEIGHT")
	public String getWeight() {
		return weight;
	}
	
	/**
	 * @param weight the weight to set
	 */
	public void setWeight(String weight) {
		this.weight = weight;
	}
	
	/**
	 * @return the inUse
	 */
	@Column(name="INUSE")
	public String getInUse() {
		return inUse;
	}
	
	/**
	 * @param inUse the inUse to set
	 */
	public void setInUse(String inUse) {
		this.inUse = inUse;
	}
	
	/**
	 * @return the deptId
	 */
	@Column(name="DEPT_ID")
	public String getDeptId() {
		return deptId;
	}
	
	/**
	 * @param deptId the deptId to set
	 */
	public void setDeptId(String deptId) {
		this.deptId = deptId;
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
	public String getEquipmentName() {
		return equipmentName;
	}

	
	/**
	 * @param equipmentName the equipmentName to set
	 */
	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}
	
	@Transient
	public String getEquipType() {
		return equipType;
	}
	
	
	public void setEquipType(String equipType) {
		this.equipType = equipType;
	}




	
	/**
	 * @return the typeId
	 */
	@Column(name="EQUIP_TYPE")
	public String getTypeId() {
		return TypeId;
	}




	
	/**
	 * @param typeId the typeId to set
	 */
	public void setTypeId(String typeId) {
		TypeId = typeId;
	}




	
	/**
	 * @return the deptNm
	 */
	@Transient
	public String getDeptNm() {
		return deptNm;
	}




	
	/**
	 * @param deptNm the deptNm to set
	 */
	public void setDeptNm(String deptNm) {
		this.deptNm = deptNm;
	}
	
	
}
