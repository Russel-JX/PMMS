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

import java.io.Serializable;
import java.util.List;

/*******************************************************************************
 *
 * @Author 		: iGATE
 * @Version 	: 1.0
 * @Date Created: Mar 2, 2015
 * @Date Modified : 
 * @Modified By : 
 * @Contact 	:
 * @Description : 
 * @History		:
 *
 ******************************************************************************/
public class ChartDetailInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9084104576469978271L;
	private String name;			
	private List<Double> data;
	
	public ChartDetailInfo() {
		super();
	}
	
	/**
	 * @param name
	 */
	public ChartDetailInfo(String name) {
		super();
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return the data
	 */
	public List<Double> getData() {
		return data;
	}
	
	/**
	 * @param data the data to set
	 */
	public void setData(List<Double> data) {
		this.data = data;
	}			
	

}
