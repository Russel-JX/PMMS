/**
 * ============================================================
 * File : DeptInfo.java
 * Description : 
 * 
 * Package : com.ge.pmms.po
 * Author : Xun Jiang
 * Last Edited By :
 * Version : 1.0
 * Created on : Feb 27, 2015
 * History
 * Modified By : Initial Release
 * Classification : GE Confidential
 * Copyright (C) 2015 General Electric Company. All rights reserved
 *
 * ============================================================
 */

package com.ge.pmms.po;

import java.util.Date;

import com.ge.pmms.utils.Tools;

/*******************************************************************************
 *
 * @Author 		: iGATE
 * @Version 	: 1.0
 * @Date Created: Feb 27, 2015
 * @Date Modified : 
 * @Modified By : 
 * @Contact 	:
 * @Description : 
 * @History		:
 *
 ******************************************************************************/
public class ChartInfo {
	private String equipId;			//设备编号
	private String deptId;			//部门编号
	private String deptNm;			//部门名称
	private String maintMonth;		//统计周期（如：1到12 月；5年跨度）period
	private double count;			//聚合查询的数量（如计划工单数量、设备donw机时间、备件使用数量等等）
	private double rate;			//指标（有效值除以总值）
	private String sparePartId;
	private String sparePartName;
	
	private Date maintStartDate;	//维修开始时间
	private Date maintEndDate;	//维修结束时间
	
	private int counter;            //计数器，指示当前有多少记录数
	private double amount;          //当前月的总量(分母)
	
	
	/**
	 * @return the sparePartName
	 */
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
	 * @return the sparePartId
	 */
	public String getSparePartId() {
		return sparePartId;
	}

	
	/**
	 * @param sparePartId the sparePartId to set
	 */
	public void setSparePartId(String sparePartId) {
		this.sparePartId = sparePartId;
	}

	public ChartInfo() {
		super();
	}
	
	/**
	 * @param equipId
	 * @param deptId
	 * @param deptNm
	 * @param maintMonth
	 * @param count
	 * @param rate
	 */
	public ChartInfo(String equipId, String deptId, String deptNm,
			String maintMonth, double count, double rate) {
		super();
		this.equipId = equipId;
		this.deptId = deptId;
		this.deptNm = deptNm;
		this.maintMonth = maintMonth;
		this.count = count;
		this.rate = rate;
	}

	/**
	 * @return the equipId
	 */
	public String getEquipId() {
		return equipId;
	}
	
	/**
	 * @param equipId the equipId to set
	 */
	public void setEquipId(String equipId) {
		this.equipId = equipId;
	}
	
	/**
	 * @return the deptId
	 */
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
	public String getDeptNm() {
		return deptNm;
	}
	
	/**
	 * @param deptNm the deptNm to set
	 */
	public void setDeptNm(String deptNm) {
		this.deptNm = deptNm;
	}
	
	/**
	 * @return the maintMonth
	 */
	public String getMaintMonth() {
		return maintMonth;
	}
	
	/**
	 * @param maintMonth the maintMonth to set
	 */
	public void setMaintMonth(String maintMonth) {
		this.maintMonth = maintMonth;
	}
	
	/**
	 * @return the count
	 */
	public double getCount() {
		return count;
	}
	
	/**
	 * @param d the count to set
	 */
	public void setCount(double d) {
		this.count = d;
	}
	
	/**
	 * @return the rate
	 */
	public double getRate() {
		return rate;
	}
	
	/**
	 * @param rate the rate to set
	 */
	public void setRate(double rate) {
		this.rate = rate;
	}




	
	
	public int getCounter() {
		return counter;
	}



	
	public void setCounter(int counter) {
		this.counter = counter;
	}



	
	public double getAmount() {
		return amount;
	}



	
	public void setAmount(double amount) {
		this.amount = amount;
	}



	
	/**
	 * @return the maintStartDate
	 */
	public Date getMaintStartDate() {
		if(maintStartDate==null){
			return null;
		}
		return (Date)maintStartDate.clone();
	}



	
	/**
	 * @param maintStartDate the maintStartDate to set
	 */
	public void setMaintStartDate(Date maintStartDate) {
		if(!Tools.isNull(maintStartDate)){
			this.maintStartDate = (Date)maintStartDate.clone();
		}
		
	}



	
	/**
	 * @return the maintEndDate
	 */
	public Date getMaintEndDate() {
		if(maintEndDate==null){
			return null;
		}
		return (Date)maintEndDate.clone();
	}



	
	/**
	 * @param maintEndDate the maintEndDate to set
	 */
	public void setMaintEndDate(Date maintEndDate) {
		if(!Tools.isNull(maintEndDate)){
			this.maintEndDate = (Date)maintEndDate.clone();
		}
		
	}



	

	
	
	

}
