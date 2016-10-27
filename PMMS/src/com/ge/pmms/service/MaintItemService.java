/**
 * ============================================================
 * File : MaintItemService.java
 * Description : 
 * 
 * Package : com.ge.pmms.service
 * Author : iGATE
 * Last Edited By :
 * Version : 1.0
 * Created on : Jan 13, 2015
 * History
 * Modified By : Initial Release
 * Classification : GE Confidential
 * Copyright (C) 2015 General Electric Company. All rights reserved
 *
 * ============================================================
 */

package com.ge.pmms.service;

import javax.servlet.http.HttpServletResponse;

import com.ge.pmms.base.PmmsException;
import com.ge.pmms.base.ServiceReturns;
import com.ge.pmms.po.MaintItem;


/*******************************************************************************
 *
 * @Author 		: iGATE
 * @Version 	: 1.0
 * @Date Created: Jan 13, 2015
 * @Date Modified : 
 * @Modified By : 
 * @Contact 	:
 * @Description : 
 * @History		:
 *
 ******************************************************************************/
public interface MaintItemService{
	
	public ServiceReturns findEquipNamesByEquipType(String equipType)throws PmmsException;
	
	public ServiceReturns findMaintItemsByName(String equipNameID)throws PmmsException;
	
	public ServiceReturns addMaintItem(MaintItem maintItem)throws PmmsException;
	
	public ServiceReturns modifyMaintItem(MaintItem maintItem)throws PmmsException;
	
	public ServiceReturns removeMaintItems(String[] ids)throws PmmsException;
	
	public void exportToExcel(String equipNameId,HttpServletResponse response)throws PmmsException;
}
