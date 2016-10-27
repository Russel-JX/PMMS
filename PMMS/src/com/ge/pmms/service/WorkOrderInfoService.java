/**
 * ============================================================
 * File : WorkOrderInfoService.java
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

import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.ge.pmms.base.PmmsException;
import com.ge.pmms.base.ServiceReturns;
import com.ge.pmms.po.WorkOrderInfo;


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
public interface WorkOrderInfoService {
   /**
    * 获取工单数据
    * get workOrders data
    * @Author: iGATE 
    * @param workOrderInfo
    * @return
    * @Description:
    */
	public ServiceReturns getWorkOrders(WorkOrderInfo workOrderInfo) throws PmmsException;
	/**
	 * 生成一条故障维修工单
	 * Generate a fault maintenance work order
	 * @Author: iGATE 
	 * @param workOrderInfo
	 * @return
	 * @Description:
	 */
	public ServiceReturns save(WorkOrderInfo workOrderInfo) throws PmmsException;
	/**
	 * *批量添加工单
	 * *
	 * @Author: iGATE 
	 * @param workOrderList
	 * @throws PmmsException
	 * @Description:
	 */
	public void saveAll(List<WorkOrderInfo> workOrderList)throws PmmsException;
	/**
	 * 对工单进行处理，产生一条维修工单记录
	 * generate a maintenance record when deal with the wo
	 * @Author: iGATE 
	 * @param request
	 * @return
	 * @throws Exception
	 * @Description:
	 */
	public ServiceReturns saveMaintOrderInfo(WorkOrderInfo workOrderInfo) throws PmmsException;
	/**
	 * 对工单进行关闭处理
	 * close a work order, make the workOrderStatus==3
	 * @Author: iGATE 
	 * @param request
	 * @return
	 * @throws Exception
	 * @Description:
	 */
	public ServiceReturns closeMaintOrderInfo(Integer id, Integer woMaintId,String operator,String remark) throws PmmsException;
	/**
	 * 根据planId删除计划工单
	 * @Author: iGATE 
	 * @param planId
	 * @return
	 * @throws PmmsException
	 * @Description:
	 */
	public ServiceReturns deleteWOByPlanId(String planId) throws PmmsException;
//	/**
//	 * 根据planId删除计划工单维修记录
//	 * @Author: iGATE 
//	 * @param planId
//	 * @return
//	 * @throws PmmsException
//	 * @Description:
//	 */
//	public ServiceReturns deletePWMOByWOId(String[] woIds) throws PmmsException;
	/**
	 * *查询工单，关联备件
	 * @Author: iGATE 
	 * @param workOrderInfo
	 * @return
	 * @throws PmmsException
	 * @Description:
	 */
	public ServiceReturns getWOForSP(WorkOrderInfo workOrderInfo) throws PmmsException;
	/**
	 * *编辑更新历史工单
	 * @Author: iGATE 
	 * @param workOrderInfo
	 * @return
	 * @throws PmmsException
	 * @Description:
	 */
	public ServiceReturns updateWorkOrder(WorkOrderInfo workOrderInfo) throws PmmsException;
	/**
	 * *编辑更新历史计划保养工单
	 * @Author: iGATE 
	 * @param workOrderInfo
	 * @return
	 * @throws PmmsException
	 * @Description:
	 */
	public ServiceReturns updatePlanWorkOrder(WorkOrderInfo workOrderInfo) throws PmmsException;
	/**
	 * 未关闭的工单，根据开始时间 结束时间查询
	 * @Author: iGATE 
	 * @param startDate
	 * @param endDate
	 * @return
	 * @Description:
	 */
	public List<WorkOrderInfo> getWOInProcess(Date startDate,Date endDate) throws PmmsException;
	/**
	 * 当前故障维修工单导出功能
	 * @Author: iGATE 
	 * @return
	 * @throws PmmsException
	 * @Description:
	 */
	public HSSFWorkbook curFMWOExportToExcel() throws PmmsException;
	/**
	 * 历史故障维修工单导出功能
	 * @Author: iGATE 
	 * @return
	 * @throws PmmsException
	 * @Description:
	 */
	public HSSFWorkbook hisFMWOExportToExcel() throws PmmsException;
	/**
	 * 当前计划保养工单导出功能
	 * @Author: iGATE 
	 * @return
	 * @throws PmmsException
	 * @Description:
	 */
	public HSSFWorkbook curPMWOExportToExcel() throws PmmsException;
	/**
	 * 历史计划保养工单导出功能
	 * @Author: iGATE 
	 * @return
	 * @throws PmmsException
	 * @Description:
	 */
	public HSSFWorkbook hisPMWOExportToExcel() throws PmmsException;
	/**
	 * 创建工单的时候即时提醒
	 * @Author: iGATE 
	 * @param workOrderInfo
	 * @throws PmmsException
	 * @Description:
	 */
	public void woCreatedReminder(WorkOrderInfo workOrderInfo) throws PmmsException;
	/**
	 * 获取故障类型
	 * @Author: iGATE 
	 * @return
	 * @throws PmmsException
	 * @Description:
	 */
	public ServiceReturns getFaultTypes() throws PmmsException;
}

