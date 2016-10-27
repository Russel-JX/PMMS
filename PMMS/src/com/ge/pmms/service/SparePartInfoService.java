/**
 * ============================================================
 * File : SparePartInfoService.java
 * Description : 
 * 
 * Package : com.ge.pmms.service
 * Author : iGATE
 * Last Edited By :
 * Version : 1.0
 * Created on : Feb 1, 2015
 * History
 * Modified By : Initial Release
 * Classification : GE Confidential
 * Copyright (C) 2015 General Electric Company. All rights reserved
 *
 * ============================================================
 */

package com.ge.pmms.service;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.ge.pmms.base.PmmsException;
import com.ge.pmms.base.ServiceReturns;
import com.ge.pmms.po.SparePartInfo;


/*******************************************************************************
 *
 * @Author 		: iGATE
 * @Version 	: 1.0
 * @Date Created: Feb 1, 2015
 * @Date Modified : 
 * @Modified By : 
 * @Contact 	:
 * @Description : 
 * @History		:
 *
 ******************************************************************************/
public interface SparePartInfoService {
	/**
	 * *获取备件信息
	 * *get data of spare parts
	 * @Author: iGATE 
	 * @return
	 * @Description:
	 */
	public ServiceReturns getSPInfo(SparePartInfo sparePartInfo) throws PmmsException;
	/**
	 * *保存一条备件信息
	 * *save a sparePart record
	 * @Author: iGATE 
	 * @param sparePartInfo
	 * @return
	 * @Description:
	 */
	public ServiceReturns save(SparePartInfo sparePartInfo) throws PmmsException;
	/**
	 * *更新一条备件信息
	 * *update a SPInfo record by id
	 * @Author: iGATE 
	 * @param sparePartInfo
	 * @return
	 * @Description:
	 */
	public ServiceReturns updateSPInfoById(SparePartInfo sparePartInfo) throws PmmsException;
	/**
	 * 批量删除，参数是前台拼接好的字符串，格式为  "1,2,5,8,4,54,"
	 * @Author: iGATE 
	 * @param clazz
	 * @param idStr
	 * @return
	 * @Description:
	 */
	public int deleteByIdStr(Class<SparePartInfo> clazz,String idStr) throws PmmsException;
	/**
	 * 通过备件编号，获取备件信息
	 * @Author: iGATE 
	 * @param sparePartId
	 * @return
	 * @Description:
	 */
	public ServiceReturns getSPInfoBySPId(String sparePartId) throws PmmsException;
	/**
	 * 备件扫描入库
	 * @Author: iGATE 
	 * @param sparePartInfo
	 * @return
	 * @throws PmmsException
	 * @Description:
	 */
	public ServiceReturns sparePartIn(SparePartInfo sparePartInfo) throws PmmsException;
	/**
	 * *备件扫描出库
	 * @Author: iGATE 
	 * @param sparePartInfo
	 * @return
	 * @throws PmmsException
	 * @Description:
	 */
	public ServiceReturns sparePartOut(SparePartInfo sparePartInfo) throws PmmsException;
	/**
	 * export the spare part information
	 * @Author: iGATE 
	 * @return
	 * @throws PmmsException
	 * @Description:
	 */
	public HSSFWorkbook sparePartInfoExportToExcel(String type) throws PmmsException;
	/**
	 * export the spare part information which are less than safety stock.
	 * @Author: iGATE 
	 * @return
	 * @throws PmmsException
	 * @Description:
	 */
	public HSSFWorkbook spInfoLessSafetyStockExportToExcel() throws PmmsException;
	
	
    public String getMaxSparePartId(String sparePartName);
    /**
     * 邮件提醒
     * @Author: iGATE 
     * @param spList
     * @throws PmmsException
     * @Description:
     */
    public void emailSafetySPReminder(List<SparePartInfo> spList) throws PmmsException;
    
    //根据备件名称模糊查询设备
  	public ServiceReturns getSPsBySPName(String spName);
}
