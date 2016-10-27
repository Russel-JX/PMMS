/**
 * ============================================================
 * File : EquipDocMgmtService.java
 * Description : upload equipment documents service
 * 
 * Package : com.ge.pmms.service.impl
 * Author : Flash
 * Last Edited By :
 * Version : 1.0
 * Created on : Jan 20, 2015
 * History
 * Modified By : Initial Release
 * Classification : GE Confidential
 * Copyright (C) 2015 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.pmms.service;

import java.util.List;

import com.ge.pmms.base.PmmsException;
import com.ge.pmms.base.ServiceReturns;
import com.ge.pmms.po.EquipDocDetail;
import com.ge.pmms.po.EquipDocInfo;

/*******************************************************************************
*
* @Author 		: Flash
* @Version 	: 1.0
* @Date Created: Jan 20, 2015
* @Date Modified : 
* @Modified By : 
* @Contact 	:
* @Description : 
* @History		:
*
******************************************************************************/
public interface EquipDocMgmtService {

	/**
	 * save equipment doucments
	 * @Author: Flash 
	 * @param docInfo vo
	 * @param lstDocDetail list
	 * @return ServiceReturns
	 * @throws PmmsException
	 * @Description:
	 */
	public ServiceReturns saveDocsInfo(EquipDocInfo docInfo,
			List<EquipDocDetail> lstDocDetail) throws PmmsException;
	
	
	/**
	 * edit equipment document info
	 * @Author: iGATE 
	 * @param docInfo
	 * @param lstDocDetail
	 * @return ServiceReturns
	 * @throws PmmsException
	 * @Description:
	 */
	public ServiceReturns editEquipDocs(EquipDocInfo docInfo,
			List<EquipDocDetail> lstDocDetail) throws PmmsException;
	/**
	 * get all equipment documents 
	 * @Author: Flash
	 * @return ServiceReturns
	 * @Description:
	 */
	public ServiceReturns getEquipDocslst();

	/**
	 * find equip docs info by equipment id
	 * @Author: iGATE 
	 * @param id
	 * @return ServiceReturns
	 * @Description:
	 */
	public ServiceReturns getEquipDocsInfo(String id);
	
	
	/**
	 * find equipment document info 
	 * @Author: iGATE 
	 * @param id
	 * @return EquipDocInfo
	 * @Description:
	 */
	public EquipDocInfo getEquipDocs(String id);

	/**
	 * delete upload documents by equipment detail id
	 * @Author: iGATE 
	 * @param docDetailId primary key id
	 * @return ServiceReturns
	 * @Description:
	 */
	public ServiceReturns delDocDetail(String id);

	/**
	 * delete upload equipment document info by equipment info id
	 * @Author: iGATE 
	 * @param equipDocId primary key id
	 * @return ServiceReturns
	 * @Description:
	 */
	public ServiceReturns delEqDocInfo(String id);
}
