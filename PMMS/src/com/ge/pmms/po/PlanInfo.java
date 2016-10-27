
package com.ge.pmms.po;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ge.pmms.utils.Tools;


@Entity(name="planInfo")
@Table(name="PLAN_INFO")
public class PlanInfo {
	private Integer id;            			// ID
	private String plan_id;         		// 计划编号
	private String equip_id;       			// 设备编号
	private String plan_type;         		// 计划类型
	private String maint_year;         		// 计划年份
	private String maint_jan;        		// 一月份的保养（如：“Y,Q,M”）
	private String maint_feb;        		// 二月份的保养
	private String maint_mar;        		// 三月份的保养
	private String maint_apr;        		// 四月份的保养
	private String maint_may;        		// 五月份的保养
	private String maint_jun;        		// 六月份的保养
	private String maint_jul;        		// 七月份的保养
	private String maint_agu;        		// 八月份的保养
	private String maint_sep;        		// 九月份的保养
	private String maint_oct;        		// 十月份的保养
	private String maint_nov;        		// 十一月份的保养
	private String maint_dec;        		// 十二月份的保养
	
	private String creator;         		// 创建人编号
	private Date created_date;         		// 创建日期
	private String updater;         		// 修改人编号
	private Date last_updated_date; 	    // 最后修改时间
	private String remark;         			// 备注
	
	private String equipModel;				//设备型号
	private String equipmentName;			//设备名称
	private String equipmentNameId;			//设备名称编号
	private String equipType;				//设备种类（类型）
	private String workOrderId;				//计划工单编号
	private String deptId;				//部门编号
	private String deptNm;				//部门名称
	
	
	@Id 
	@GeneratedValue(strategy=IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	/**
	 * @return the plan_id
	 */
	public String getPlan_id() {
		return plan_id;
	}

	
	/**
	 * @param plan_id the plan_id to set
	 */
	public void setPlan_id(String plan_id) {
		this.plan_id = plan_id;
	}

	
	/**
	 * @return the equip_id
	 */
	public String getEquip_id() {
		return equip_id;
	}

	
	/**
	 * @param equip_id the equip_id to set
	 */
	public void setEquip_id(String equip_id) {
		this.equip_id = equip_id;
	}

	
	/**
	 * @return the plan_type
	 */
	public String getPlan_type() {
		return plan_type;
	}

	
	/**
	 * @param plan_type the plan_type to set
	 */
	public void setPlan_type(String plan_type) {
		this.plan_type = plan_type;
	}

	
	/**
	 * @return the maint_year
	 */
	public String getMaint_year() {
		return maint_year;
	}

	
	/**
	 * @param maint_year the maint_year to set
	 */
	public void setMaint_year(String maint_year) {
		this.maint_year = maint_year;
	}

	
	/**
	 * @return the maint_jan
	 */
	public String getMaint_jan() {
		return maint_jan;
	}

	
	/**
	 * @param maint_jan the maint_jan to set
	 */
	public void setMaint_jan(String maint_jan) {
		this.maint_jan = maint_jan;
	}

	
	/**
	 * @return the maint_feb
	 */
	public String getMaint_feb() {
		return maint_feb;
	}

	
	/**
	 * @param maint_feb the maint_feb to set
	 */
	public void setMaint_feb(String maint_feb) {
		this.maint_feb = maint_feb;
	}

	
	/**
	 * @return the maint_mar
	 */
	public String getMaint_mar() {
		return maint_mar;
	}

	
	/**
	 * @param maint_mar the maint_mar to set
	 */
	public void setMaint_mar(String maint_mar) {
		this.maint_mar = maint_mar;
	}

	
	/**
	 * @return the maint_apr
	 */
	public String getMaint_apr() {
		return maint_apr;
	}

	
	/**
	 * @param maint_apr the maint_apr to set
	 */
	public void setMaint_apr(String maint_apr) {
		this.maint_apr = maint_apr;
	}

	
	/**
	 * @return the maint_may
	 */
	public String getMaint_may() {
		return maint_may;
	}

	
	/**
	 * @param maint_may the maint_may to set
	 */
	public void setMaint_may(String maint_may) {
		this.maint_may = maint_may;
	}

	
	/**
	 * @return the maint_jun
	 */
	public String getMaint_jun() {
		return maint_jun;
	}

	
	/**
	 * @param maint_jun the maint_jun to set
	 */
	public void setMaint_jun(String maint_jun) {
		this.maint_jun = maint_jun;
	}

	
	/**
	 * @return the maint_jul
	 */
	public String getMaint_jul() {
		return maint_jul;
	}

	
	/**
	 * @param maint_jul the maint_jul to set
	 */
	public void setMaint_jul(String maint_jul) {
		this.maint_jul = maint_jul;
	}

	
	/**
	 * @return the maint_agu
	 */
	public String getMaint_agu() {
		return maint_agu;
	}

	
	/**
	 * @param maint_agu the maint_agu to set
	 */
	public void setMaint_agu(String maint_agu) {
		this.maint_agu = maint_agu;
	}

	
	/**
	 * @return the maint_sep
	 */
	public String getMaint_sep() {
		return maint_sep;
	}

	
	/**
	 * @param maint_sep the maint_sep to set
	 */
	public void setMaint_sep(String maint_sep) {
		this.maint_sep = maint_sep;
	}

	
	/**
	 * @return the maint_oct
	 */
	public String getMaint_oct() {
		return maint_oct;
	}

	
	/**
	 * @param maint_oct the maint_oct to set
	 */
	public void setMaint_oct(String maint_oct) {
		this.maint_oct = maint_oct;
	}

	
	/**
	 * @return the maint_nov
	 */
	public String getMaint_nov() {
		return maint_nov;
	}

	
	/**
	 * @param maint_nov the maint_nov to set
	 */
	public void setMaint_nov(String maint_nov) {
		this.maint_nov = maint_nov;
	}

	
	/**
	 * @return the maint_dec
	 */
	public String getMaint_dec() {
		return maint_dec;
	}

	
	/**
	 * @param maint_dec the maint_dec to set
	 */
	public void setMaint_dec(String maint_dec) {
		this.maint_dec = maint_dec;
	}

	
	/**
	 * @return the creator
	 */
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
	 * @return the created_date
	 */
	public Date getCreated_date() {
		if(created_date==null){
			return null;
		}
		return (Date)created_date.clone();
	}

	
	/**
	 * @param created_date the created_date to set
	 */
	public void setCreated_date(Date created_date) {
		if(!Tools.isNull(created_date)){
			this.created_date = (Date)created_date.clone();
		}
	}

	
	/**
	 * @return the updater
	 */
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
	 * @return the last_updated_date
	 */
	public Date getLast_updated_date() {
		if(last_updated_date==null){
			return null;
		}
		return (Date)last_updated_date.clone();
	}

	
	/**
	 * @param last_updated_date the last_updated_date to set
	 */
	public void setLast_updated_date(Date last_updated_date) {
		if(!Tools.isNull(last_updated_date)){
			this.last_updated_date = (Date)last_updated_date.clone();
		}
		
	}

	
	/**
	 * @return the remark
	 */
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
	 * @return the equipModel
	 */
	@Transient
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
	 * @return the equipmentName
	 */
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

	
	/**
	 * @return the equipmentNameId
	 */
	@Transient
	public String getEquipmentNameId() {
		return equipmentNameId;
	}

	
	/**
	 * @param equipmentNameId the equipmentNameId to set
	 */
	public void setEquipmentNameId(String equipmentNameId) {
		this.equipmentNameId = equipmentNameId;
	}

	
	/**
	 * @return the equipType
	 */
	@Transient
	public String getEquipType() {
		return equipType;
	}

	
	/**
	 * @param equipType the equipType to set
	 */
	public void setEquipType(String equipType) {
		this.equipType = equipType;
	}

	
	/**
	 * @return the workOrderId
	 */
	@Transient
	public String getWorkOrderId() {
		return workOrderId;
	}

	
	/**
	 * @param workOrderId the workOrderId to set
	 */
	public void setWorkOrderId(String workOrderId) {
		this.workOrderId = workOrderId;
	}

	
	/**
	 * @return the deptId
	 */
	@Transient
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
