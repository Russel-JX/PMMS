
package com.ge.pmms.po;


public class PlanMaintInfo {
	private String woStatus;            	// 工单完成状态
	private String equipName;         		// 设备名称
	private String equipModel;       		// 设备型号
	private String equipId;         		// 设备编号
	private String planMonth;         		// 计划保养月份
	private String mechanicSSO;         	// 维修人SSO
	private String firstname;        		// 名
	private String lastname;        		// 姓
	private String plan_start_month;        // 计划保养开始月份
	private String maint_start_date;        // 开始维修时间
	private String maint_end_date;        	// 结束维修时间
	
	
	/**
	 * 
	 */
	public PlanMaintInfo() {
		super();
	}

	/**
	 * @param woStatus
	 * @param equipName
	 * @param equipModel
	 * @param equipId
	 * @param mechanicSSO
	 * @param firstname
	 * @param lastname
	 * @param plan_start_month
	 * @param maint_start_date
	 * @param maint_end_date
	 */
	public PlanMaintInfo(String woStatus, String equipName, String equipModel,
			String equipId, String planMonth,String mechanicSSO, String firstname,
			String lastname, String plan_start_month, String maint_start_date,
			String maint_end_date) {
		super();
		this.woStatus = woStatus;
		this.equipName = equipName;
		this.equipModel = equipModel;
		this.equipId = equipId;
		this.planMonth = planMonth;
		this.mechanicSSO = mechanicSSO;
		this.firstname = firstname;
		this.lastname = lastname;
		this.plan_start_month = plan_start_month;
		this.maint_start_date = maint_start_date;
		this.maint_end_date = maint_end_date;
	}

	/**
	 * @return the woStatus
	 */
	public String getWoStatus() {
		return woStatus;
	}
	
	/**
	 * @param woStatus the woStatus to set
	 */
	public void setWoStatus(String woStatus) {
		this.woStatus = woStatus;
	}
	
	/**
	 * @return the equipName
	 */
	public String getEquipName() {
		return equipName;
	}
	
	/**
	 * @param equipName the equipName to set
	 */
	public void setEquipName(String equipName) {
		this.equipName = equipName;
	}
	
	/**
	 * @return the equipModel
	 */
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
	 * @return the equipId
	 */
	public String getEquipId() {
		return equipId;
	}
	
	
	/**
	 * @return the planMonth
	 */
	public String getPlanMonth() {
		return planMonth;
	}

	
	/**
	 * @param planMonth the planMonth to set
	 */
	public void setPlanMonth(String planMonth) {
		this.planMonth = planMonth;
	}

	/**
	 * @param equipId the equipId to set
	 */
	public void setEquipId(String equipId) {
		this.equipId = equipId;
	}
	
	/**
	 * @return the mechanicSSO
	 */
	public String getMechanicSSO() {
		return mechanicSSO;
	}
	
	/**
	 * @param mechanicSSO the mechanicSSO to set
	 */
	public void setMechanicSSO(String mechanicSSO) {
		this.mechanicSSO = mechanicSSO;
	}
	
	/**
	 * @return the firstname
	 */
	public String getFirstname() {
		return firstname;
	}
	
	/**
	 * @param firstname the firstname to set
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	/**
	 * @return the lastname
	 */
	public String getLastname() {
		return lastname;
	}
	
	/**
	 * @param lastname the lastname to set
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	/**
	 * @return the plan_start_month
	 */
	public String getPlan_start_month() {
		return plan_start_month;
	}
	
	/**
	 * @param plan_start_month the plan_start_month to set
	 */
	public void setPlan_start_month(String plan_start_month) {
		this.plan_start_month = plan_start_month;
	}
	
	/**
	 * @return the maint_start_date
	 */
	public String getMaint_start_date() {
		return maint_start_date;
	}
	
	/**
	 * @param maint_start_date the maint_start_date to set
	 */
	public void setMaint_start_date(String maint_start_date) {
		this.maint_start_date = maint_start_date;
	}
	
	/**
	 * @return the maint_end_date
	 */
	public String getMaint_end_date() {
		return maint_end_date;
	}
	
	/**
	 * @param maint_end_date the maint_end_date to set
	 */
	public void setMaint_end_date(String maint_end_date) {
		this.maint_end_date = maint_end_date;
	}
	
	

	
}
