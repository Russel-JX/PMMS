/**
 * ============================================================
 * File : SparePartTransInfoService.java
 * Description : 
 * 
 * Package : com.ge.pmms.service
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

package com.ge.pmms.service;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.ge.pmms.base.PmmsException;
import com.ge.pmms.base.ServiceReturns;
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
public interface SparePartTransInfoService {
	/**
	 * *获取备件出入库记录数据
	 * @Author: iGATE 
	 * @return
	 * @throws PmmsException
	 * @Description:
	 */
	public ServiceReturns getSPTrans(SparePartTransInfo sparePartTransInfo) throws PmmsException;
	/**
	 * 根据工单id,查询出该工单使用的备件
	 * @Author: iGATE 
	 * @param workOrderId
	 * @return
	 * @throws PmmsException
	 * @Description:
	 */
	public ServiceReturns getSPTransByWOID(String workOrderId) throws PmmsException;
	/**
	 * 导出备件出入库记录
	 * @Author: iGATE 
	 * @return
	 * @throws PmmsException
	 * @Description:
	 */
	public HSSFWorkbook spTransExportToExcel() throws PmmsException; 
	/**
	 * 备件的系统建议安全库存
	 * @Author: iGATE 
	 * @return
	 * @throws PmmsException
	 * @Description:
	 */
	public ServiceReturns updateSysSafetyStock() throws PmmsException;
	/**
	 * 计算std
	 * @Author: iGATE 
	 * @param list
	 * @return
	 * @Description:
	 */
	public  Map<String, Double> calculateSTD(List<SafetyStockInfo> list);

	public List<SafetyStockInfo> calculateSafetyStock(List<String> spIdList, Map<String, Double> stdMap,Map<String, Double> lMap);
}
