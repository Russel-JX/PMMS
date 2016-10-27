/**
 * ============================================================
 * File : IdmTransService.java
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

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.ge.pmms.base.PmmsException;
import com.ge.pmms.base.ServiceReturns;
import com.ge.pmms.po.IdmTransInfo;


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
public interface IdmTransService {
	
	/**
	 * 往间接物料出入库记录表插入一条入库数据
	 * @Author:  iGATE
	 * @param idmTransInfoReq 间接物料交易对象
	 * @return ServiceReturns
	 * @throws Exception
	 * @Description:
	 */
	public ServiceReturns insertRecordTb(IdmTransInfo idmTransInfoReq);
	/**
	 * 往间接物料出入库记录表插入一条出库数据
	 * @Author:  iGATE
	 * @param idmTransInfoReq 间接物料交易对象
	 * @return ServiceReturns
	 * @throws PmmsException 
	 * @throws Exception
	 * @Description:
	 */
	public ServiceReturns scanOutStock(IdmTransInfo idmTransInfoReq) throws PmmsException;
	/**
	 * 根据时间段及出入库类型获取间接物料出入库记录
	 * @Author:  iGATE
	 * @param idmTransInfoReq 间接物料交易对象
	 * @return ServiceReturns
	 * @throws 
	 * @Description:
	 */
	public ServiceReturns getIdmRecordByPeriod(IdmTransInfo idmTransInfoReq);
	/**
	 * 间接物料出入库管理页面导出excel
	 * @Author:  iGATE
	 * @param 
	 * @return HSSFWorkbook
	 * @throws 
	 * @Description:
	 */
	public HSSFWorkbook  idmRecordExportToExcel();
	/**
	 * 获取批次明细
	 * @Author:  iGATE
	 * @param idmTransInfoReq 间接物料交易对象
	 * @return ServiceReturns
	 * @throws 
	 * @Description:
	 */
	public ServiceReturns getPoDetail(IdmTransInfo idmTransInfoReq);
}
