/**
 * ============================================================
 * File : SparePartTransInfoDao.java
 * Description : 
 * 
 * Package : com.ge.pmms.dao
 * Author : iGATE
 * Last Edited By :
 * Version : 1.0
 * Created on : Feb 5, 2015
 * History
 * Modified By : Initial Release
 * Classification : GE Confidential
 * Copyright (C) 2015 General Electric Company. All rights reserved
 *
 * ============================================================
 */

package com.ge.pmms.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ge.pmms.base.BaseDao;
import com.ge.pmms.base.PmmsException;
import com.ge.pmms.po.SafetyStockInfo;
import com.ge.pmms.po.SparePartTransInfo;


/*******************************************************************************
 *
 * @Author 		: iGATE
 * @Version 	: 1.0
 * @Date Created: Feb 5, 2015
 * @Date Modified : 
 * @Modified By : 
 * @Contact 	:
 * @Description : 
 * @History		:
 *
 ******************************************************************************/
public interface SparePartTransInfoDao extends BaseDao<SparePartTransInfo> {
	/**
	 * 备件出入库记录
	 * @Author: iGATE 
	 * @param sparePartTransInfo
	 * @return
	 * @throws PmmsException
	 * @Description:
	 */
	public List<SparePartTransInfo> getSPTrans(SparePartTransInfo sparePartTransInfo) throws PmmsException;
	/**
	 * 根据工单 获取备件出入库记录
	 * @Author: iGATE 
	 * @param workOrderId
	 * @return
	 * @throws PmmsException
	 * @Description:
	 */
	public List<SparePartTransInfo> getSPTransByWOID(String  workOrderId) throws PmmsException;
	/**
	 * 
	 * @Author: iGATE 
	 * @throws PmmsException
	 * @Description:
	 */
	public List<SafetyStockInfo> getSTDPreparedData(Date startDate,Date endDate) throws PmmsException;
	/**
	 * 获取备货周期（备件交付时间差）
	 * @Author: iGATE 
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws PmmsException
	 * @Description:
	 */
	public Map<String, Double> getLeadTime(Date startDate,Date endDate) throws PmmsException;
	
	/**
	 * 
	 * @Author: iGATE 
	 * @param ssList
	 * @throws PmmsException
	 * @Description:
	 */
	public void updateSS(List<SafetyStockInfo> ssList) throws PmmsException;
	
	/**
	 * 获取所有备件的编号，存到list中
	 * @Author: iGATE 
	 * @return
	 * @throws PmmsException
	 * @Description:
	 */
	public List<String> getSPIds() throws PmmsException;
}
