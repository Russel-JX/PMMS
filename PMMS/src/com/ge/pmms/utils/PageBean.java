/**
 * ============================================================
 * File : PageBean.java
 * Description : 
 * 
 * Package : com.ge.pmms.utils
 * Author : iGATE
 * Last Edited By :
 * Version : 1.0
 * Created on : Jan 17, 2015
 * History
 * Modified By : Initial Release
 * Classification : GE Confidential
 * Copyright (C) 2015 General Electric Company. All rights reserved
 *
 * ============================================================
 */

package com.ge.pmms.utils;


/*******************************************************************************
 *
 * @Author 		: iGATE
 * @Version 	: 1.0
 * @Date Created: Jan 17, 2015
 * @Date Modified : 
 * @Modified By : 
 * @Contact 	:
 * @Description : 
 * @History		:
 *
 ******************************************************************************/
public class PageBean {
	
	private int sEcho = 0;
	private int iTotalRecords = 0;
	private int iTotalDisplayRecords = 0;
	
	public PageBean() {
		super();
	}
	
	/**
	 * @param sEcho
	 * @param iTotalRecords
	 * @param iTotalDisplayRecords
	 */
	public PageBean(int sEcho, int iTotalRecords, int iTotalDisplayRecords) {
		super();
		this.sEcho = sEcho;
		this.iTotalRecords = iTotalRecords;
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}

	/**
	 * @return the sEcho
	 */
	public int getsEcho() {
		return sEcho;
	}
	
	/**
	 * @param sEcho the sEcho to set
	 */
	public void setsEcho(int sEcho) {
		this.sEcho = sEcho;
	}
	
	/**
	 * @return the iTotalRecords
	 */
	public int getiTotalRecords() {
		return iTotalRecords;
	}
	
	/**
	 * @param iTotalRecords the iTotalRecords to set
	 */
	public void setiTotalRecords(int iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}
	
	/**
	 * @return the iTotalDisplayRecords
	 */
	public int getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}
	
	/**
	 * @param iTotalDisplayRecords the iTotalDisplayRecords to set
	 */
	public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}
	
	
}
