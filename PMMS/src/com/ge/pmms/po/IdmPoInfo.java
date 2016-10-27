package com.ge.pmms.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="IdmPoInfo")
@Table(name="IDM_PO_INFO")
public class IdmPoInfo {
	private Integer id;
	private String idmId;
	private String po;
	private String remainAmount;
	private String creator;
	private String updater;
	private Date createDate=null;
	private Date lastUpdateDate=null;
	private String inAmount;
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
	 * @return the remainAmount
	 */
	@Column(name="REMAIN_AMOUNT")
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
	 * @return the inAmount
	 */
	
	@Column(name="AMOUNT")
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
