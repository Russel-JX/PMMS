package com.ge.pmms.po;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="IdmTypeDetail")
@Table(name="IDM_TYPE_DETAIL")
public class IdmTypeDetail {
	private int id;
	private String SUB_TYPE_ID;
	private String SUB_TYPE_NM;
	private String TYPE_DETAIL_ID;
	private String TYPE_DETAIL_NM;
	private String TYPE_ID;
	private String REMARK;
	
	/**
	 * @return the id
	 */
	@Id 
	@GeneratedValue(strategy=IDENTITY)
	public int getId() {
		return id;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * @return the sUB_TYPE_ID
	 */
	public String getSUB_TYPE_ID() {
		return SUB_TYPE_ID;
	}
	
	/**
	 * @param sUB_TYPE_ID the sUB_TYPE_ID to set
	 */
	public void setSUB_TYPE_ID(String sUB_TYPE_ID) {
		SUB_TYPE_ID = sUB_TYPE_ID;
	}
	
	/**
	 * @return the sUB_TYPE_NM
	 */
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
	 * @return the tYPE_DETAIL_ID
	 */
	public String getTYPE_DETAIL_ID() {
		return TYPE_DETAIL_ID;
	}
	
	/**
	 * @param tYPE_DETAIL_ID the tYPE_DETAIL_ID to set
	 */
	public void setTYPE_DETAIL_ID(String tYPE_DETAIL_ID) {
		TYPE_DETAIL_ID = tYPE_DETAIL_ID;
	}
	
	/**
	 * @return the tYPE_DETAIL_NM
	 */
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
	 * @return the tYPE_ID
	 */
	public String getTYPE_ID() {
		return TYPE_ID;
	}
	
	/**
	 * @param tYPE_ID the tYPE_ID to set
	 */
	public void setTYPE_ID(String tYPE_ID) {
		TYPE_ID = tYPE_ID;
	}
	
	/**
	 * @return the rEMARK
	 */
	public String getREMARK() {
		return REMARK;
	}
	
	/**
	 * @param rEMARK the rEMARK to set
	 */
	public void setREMARK(String rEMARK) {
		REMARK = rEMARK;
	}
	
}
